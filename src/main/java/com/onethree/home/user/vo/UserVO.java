package com.onethree.home.user.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.onethree.home.common.vo.PageVO;

/**
 * USER_TB
 * */
@Entity
@Table(name = "USER_TB")
public class UserVO extends PageVO implements Serializable{
	@Id @GeneratedValue(generator="USER_UID")
	@GenericGenerator(name="USER_UID", strategy = "uuid")
	@Column(name="USER_UID",length=32, updatable = false, nullable = false)
	private String userUid;
	
	@Column(name="USER_ID", length=50)
	private String userId;
	
	@Column(name="USER_PASS", length=255)
	private String userPass;

	@Column(name="USER_NAME", length=100)
	private String userName;
	
	/**
	 * 권한여부
	 * 1:준회원 2:정회원 9:관리자
	 * */
	@Column(name="USER_AUTHOR")
	private int userAuthor;
	
	/**
	 * 상태
	 * 0:신청
	 * 1:승인
	 * 2:탈퇴
	 * */
	@Column(name="STATE_NUM", length=10)
	private int stateNum;
	
	/**
	 * 회비연동여부
	 * */
	@Column(name="MONEY_FLAG", length=5)
	private String moneyFlag;
	
	@Column(name="REGISTER_DT")
	private Date registerDt;
	
	@Column(name="REGISTER_ID", length=50)
	private String registerId;
	
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_ID", length=50)
	private String updateId;

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAuthor() {
		return userAuthor;
	}

	public void setUserAuthor(int userAuthor) {
		this.userAuthor = userAuthor;
	}

	public int getStateNum() {
		return stateNum;
	}

	public void setStateNum(int stateNum) {
		this.stateNum = stateNum;
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

	public String getMoneyFlag() {
		return moneyFlag;
	}

	public void setMoneyFlag(String moneyFlag) {
		this.moneyFlag = moneyFlag;
	}
	
	
	
}
