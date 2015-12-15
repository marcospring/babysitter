package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;

/**
 * 优惠卷优惠行为表
 *
 * @author marcospring
 *
 */
@Entity
@Table(name = "babysitter_preferential_behavior")
public class PreferentialBehavior implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private boolean ovld;
	private String uuid;
	private Date createDate;
	private Date updateDate;
	private String name;
	// 1表示给分享者，2表示给使用者
	private int toUser;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isOvld() {
		return ovld;
	}

	public void setOvld(boolean ovld) {
		this.ovld = ovld;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getToUser() {
		return toUser;
	}

	public void setToUser(int toUser) {
		this.toUser = toUser;
	}

	public static PreferentialBehavior getInstance() {
		PreferentialBehavior o = new PreferentialBehavior();
		o.setOvld(true);
		o.setUuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}
}
