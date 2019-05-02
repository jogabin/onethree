package com.onethree.home.money.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * MONEY_TB
 * 입출금 테이블
 * */
@Entity
@Table(name = "MONEY_TB")
public class MoneyVO implements Serializable{
	@Id @GeneratedValue(generator="MONEY_UID")
	@GenericGenerator(name="MONEY_UID", strategy = "uuid")
	@Column(name="MONEY_UID",length=32, updatable = false, nullable = false)
	private String moneyUid;
	
	/**
	 * A 입금 B 출금
	 * */
	@Column(name="INOUT_FLAG", length=5)
	private String inoutFlag;
	
	@Column(name="USER_UID", length=32)
	private String userUid;
	
	@Column(name="YEAR_DATE")
	private int yearDate;
	
	@Column(name="MONTH_DATE")
	private int monthDate;
	
	@Column(name="MONEY")
	private int money;
	
	@Column(name="INOUT_DT", length=10)
	private String inoutDt;
	
	/**
	 * Y 정기입금 N 보통입출금
	 * */
	@Column(name="PLAN_FLAG", length=5)
	private String planFlag;
	
	/**
	 * 기타 입출금 구분
	 * 1:기타 2:회식 3:축하 4:경조사 5:애경사
	 * */
	@Column(name="INOUT_CATE1", length=5)
	private String inoutCate1;
	
	@Column(name="MEMO")
	private String memo;
		
	@Column(name="REGISTER_DT")
	private Date registerDt;
	
	@Column(name="REGISTER_ID", length=50)
	private String registerId;
	
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_ID", length=50)
	private String updateId;

	public String getMoneyUid() {
		return moneyUid;
	}

	public void setMoneyUid(String moneyUid) {
		this.moneyUid = moneyUid;
	}

	public String getInoutFlag() {
		return inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public int getYearDate() {
		return yearDate;
	}

	public void setYearDate(int yearDate) {
		this.yearDate = yearDate;
	}

	public int getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(int monthDate) {
		this.monthDate = monthDate;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getInoutDt() {
		return inoutDt;
	}

	public void setInoutDt(String inoutDt) {
		this.inoutDt = inoutDt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getPlanFlag() {
		return planFlag;
	}

	public void setPlanFlag(String planFlag) {
		this.planFlag = planFlag;
	}

	public String getInoutCate1() {
		return inoutCate1;
	}

	public void setInoutCate1(String inoutCate1) {
		this.inoutCate1 = inoutCate1;
	}

	
	
}
