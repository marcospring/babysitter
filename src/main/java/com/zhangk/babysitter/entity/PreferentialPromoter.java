package com.zhangk.babysitter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zhangk.babysitter.utils.common.GUIDCreator;
import com.zhangk.babysitter.viewmodel.PreferentialPromoterView;

/**
 * 优惠卷推广人表
 *
 * @author marcospring
 *
 */
@Entity
@Table(name = "babysitter_preferential_promoter")
public class PreferentialPromoter implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private boolean ovld;
	private String guid;
	private Date createDate;
	private Date updateDate;
	private String telephone;
	private PreferentialType type;
	private int overdue;
	private String barcodesUrl;
	private List<PreferentialReceiveInfo> receivers;

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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@ManyToOne
	@JoinColumn(name = "type_id")
	public PreferentialType getType() {
		return type;
	}

	public void setType(PreferentialType type) {
		this.type = type;
	}

	public int getOverdue() {
		return overdue;
	}

	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}

	public String getBarcodesUrl() {
		return barcodesUrl;
	}

	public void setBarcodesUrl(String barcodesUrl) {
		this.barcodesUrl = barcodesUrl;
	}

	@OneToMany(mappedBy = "promoter")
	public List<PreferentialReceiveInfo> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<PreferentialReceiveInfo> receivers) {
		this.receivers = receivers;
	}

	public Object view() {
		return new PreferentialPromoterView(this);
	}

	public static PreferentialPromoter getInstance() {
		PreferentialPromoter o = new PreferentialPromoter();
		o.setOvld(true);
		o.setGuid(GUIDCreator.GUID());
		o.setCreateDate(new Date());
		o.setUpdateDate(new Date());
		return o;
	}

}
