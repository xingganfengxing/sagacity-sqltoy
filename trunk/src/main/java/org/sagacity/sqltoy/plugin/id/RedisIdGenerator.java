/**
 * 
 */
package org.sagacity.sqltoy.plugin.id;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sagacity.sqltoy.plugin.IdGenerator;
import org.sagacity.sqltoy.utils.DateUtil;
import org.sagacity.sqltoy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * @project sagacity-sqltoy4.0
 * @description 基于redis的集中式主键生成策略
 * @author chenrenfei <a href="mailto:zhongxuchen@gmail.com">联系作者</a>
 * @version id:RedisIdGenerator.java,Revision:v1.0,Date:2018年1月30日
 */
public class RedisIdGenerator implements IdGenerator {
	private final static Pattern DF_REGEX = Pattern.compile("(?i)\\@(date|day|df)\\([\\w|\\W]*\\)");

	/**
	 * 日期格式
	 */
	private String dateFormat;

	private RedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sagacity.sqltoy.plugin.IdGenerator#getId(java.lang.String,
	 * java.lang.String, java.lang.Object[], int)
	 */
	@Override
	public Object getId(String tableName, String signature, Object relatedColValue, int jdbcType, int length)
			throws Exception {
		String key = (signature == null ? "" : signature)
				.concat(((relatedColValue == null) ? "" : relatedColValue.toString()));
		String realKey = key;
		if (key.indexOf("@") > 0) {
			Matcher m = DF_REGEX.matcher(key);
			if (m.find()) {
				String df = m.group();
				df = df.substring(df.indexOf("(") + 1, df.indexOf(")")).replaceAll("\'|\"", "").trim();
				if (df.equals(""))
					df = "yyMMdd";
				realKey = key.substring(0, m.start()).concat(DateUtil.formatDate(new Date(), df))
						.concat(key.substring(m.end()));
			}
		} else if (dateFormat != null)
			realKey = key.concat(DateUtil.formatDate(new Date(), dateFormat));
		Long result = generate(realKey);
		return realKey + StringUtil.addLeftZero2Len("" + result, length - key.length());
	}

	/**
	 * 根据key获取+1后的key值
	 * 
	 * @param key
	 * @return
	 */
	public long generate(String key) {
		return generate(key, 1, null);
	}

	/**
	 * @todo 批量获取key值
	 * @param key
	 * @param increment
	 * @return
	 */
	public long generate(String key, int increment) {
		return generate(key, increment, null);
	}

	/**
	 * 批量获取key值,并指定过期时间
	 * 
	 * @param key
	 * @param increment
	 * @param expireTime
	 * @return
	 */
	public long generate(String key, int increment, Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		if (expireTime != null)
			counter.expireAt(expireTime);
		if (increment > 1)
			return counter.addAndGet(increment);
		else
			return counter.incrementAndGet();
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}