/**
 *@Generated by QuickVO Tools 2.0
 */
package sqltoy.showcase.system.vo;

import org.sagacity.sqltoy.config.annotation.SqlToyEntity;
import java.math.BigDecimal;
import java.util.Date;
import sqltoy.showcase.system.vo.base.AbstractShardingHisVO;

/**
 * @project sqltoy-showcase
 * @author zhongxuchen
 * @version 1.0.0
 * 分片测试历史表  		
 * ShardingHisVO generated by sys_sharding_his
 */
@SqlToyEntity
public class ShardingHisVO extends AbstractShardingHisVO {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2120356544444138010L;
	
	/** default constructor */
	public ShardingHisVO() {
		super();
	}
	
	/*---begin-constructor-area---don't-update-this-area--*/
	/** pk constructor */
	public ShardingHisVO(BigDecimal id)
	{
		this.id=id;
	}

	/** minimal constructor */
	public ShardingHisVO(BigDecimal id,String staffId,Date createTime)
	{
		this.id=id;
		this.staffId=staffId;
		this.createTime=createTime;
	}

	/** full constructor */
	public ShardingHisVO(BigDecimal id,String staffId,String postType,Date createTime,String comments)
	{
		this.id=id;
		this.staffId=staffId;
		this.postType=postType;
		this.createTime=createTime;
		this.comments=comments;
	}

	/*---end-constructor-area---don't-update-this-area--*/
	
	/**
     *@todo vo columns to String
     */
	public String toString() {
		return super.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public ShardingHisVO clone() {
		try {
			// TODO Auto-generated method stub
			return (ShardingHisVO) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}