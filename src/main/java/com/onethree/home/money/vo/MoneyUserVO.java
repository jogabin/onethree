package com.onethree.home.money.vo;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.onethree.home.common.vo.PageVO;

/**
 * MONEY_USER_TB
 * 회비 회원 테이블
 * */
@Entity
@Table(name = "MONEY_USER_TB")
public class MoneyUserVO extends PageVO implements Serializable{
	@Id @GeneratedValue(generator="MONEY_USER_UID")
	@GenericGenerator(name="MONEY_USER_UID", strategy = "uuid")
	@Column(name="MONEY_USER_UID",length=32, updatable = false, nullable = false)
	private String moneyUserUid;
	
	@Column(name="MONEY_USER_NAME", length=100)
	private String moneyUserName;
	
	@Column(name="REGISTER_DT")
	private Date registerDt;
	
	@Column(name="REGISTER_ID", length=50)
	private String registerId;
	
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_ID", length=50)
	private String updateId;
	
	/**
	 * 삭제여부 Y:삭제 N:미삭제
	 * */
	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	public String getMoneyUserUid() {
		return moneyUserUid;
	}

	public void setMoneyUserUid(String moneyUserUid) {
		this.moneyUserUid = moneyUserUid;
	}

	public String getMoneyUserName() {
		return moneyUserName;
	}

	public void setMoneyUserName(String moneyUserName) {
		this.moneyUserName = moneyUserName;
	}

	public Date getRegisterDt() {
		return registerDt;
	}

	public void setRegisterDt(Date registerDt) {
		this.registerDt = registerDt;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
