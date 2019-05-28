package com.onethree.home.board.vo;

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
import javax.persistence.Lob;
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
 * BOARD_DATA_TB
 * */
@Entity
@Table(name = "BOARD_DATA_TB")
public class BoardDataVO extends PageVO implements Serializable{
	@Id @GeneratedValue(generator="DATA_UID")
	@GenericGenerator(name="DATA_UID", strategy = "uuid")
	@Column(name="DATA_UID",length=32, updatable = false, nullable = false)
	private String dataUid;
	
	@Column(name="BOARD_UID", length=32)
	private String boardUid;
	
	@Column(name="DATA_DEP")
	private int dataDep;
	
	@Column(name="DATA_IDX")
	private long dataIdx;
	
	@Column(name="DATA_REF")
	private long dataRef;
	
	@Column(name="DELETE_STATE", length=1)
	private String deleteState = "N";
	
	@Column(name="NOTICE_FLAG", length=1)
	private String noticeFlag;
	
	@Column(name="DATA_SECRET", length=1)
	private String dataSecret;
		
	@Column(name="DATA_TITLE", length=255)
	private String dataTitle;

	@Column(name="DATA_CONTENT")
	@Lob
	private String dataContent;
	
	@Column(name="USER_ID", length=50)
	private String userId;
	
	@Column(name="USER_NAME", length=100)
	private String userName;
	
	@Column(name="USER_IP", length=17)
	private String userIp;
	
	@Column(name="VIEW_COUNT")
	private long viewCount;
	
	@Column(name="REGISTER_DT")
	private Date registerDt;
	
	@Column(name="REGISTER_ID", length=50)
	private String registerId;
	
	@Column(name="UPDATE_DT")
	private Date updateDt;

	@Column(name="UPDATE_ID", length=50)
	private String updateId;
	
	@Column(name="TMP_FIELD1", length=255)
	private String tmpField1;
	
	@Column(name="TMP_FIELD2", length=255)
	private String tmpField2;
	
	@Column(name="TMP_FIELD3", length=255)
	private String tmpField3;
	
	@Column(name="TMP_FIELD4", length=255)
	private String tmpField4;
	
	@Column(name="TMP_FIELD5", length=255)
	private String tmpField5;
	
	@Column(name="TMP_FIELD6", length=255)
	private String tmpField6;
	
	@Column(name="TMP_FIELD7", length=255)
	private String tmpField7;
	
	@Column(name="TMP_FIELD8", length=255)
	private String tmpField8;
	
	@Column(name="TMP_FIELD9", length=255)
	private String tmpField9;
	
	@Column(name="TMP_FIELD10", length=255)
	private String tmpField10;

	public String getDataUid() {
		return dataUid;
	}

	public void setDataUid(String dataUid) {
		this.dataUid = dataUid;
	}

	public String getBoardUid() {
		return boardUid;
	}

	public void setBoardUid(String boardUid) {
		this.boardUid = boardUid;
	}

	public int getDataDep() {
		return dataDep;
	}

	public void setDataDep(int dataDep) {
		this.dataDep = dataDep;
	}

	public long getDataIdx() {
		return dataIdx;
	}

	public void setDataIdx(long dataIdx) {
		this.dataIdx = dataIdx;
	}

	public long getDataRef() {
		return dataRef;
	}

	public void setDataRef(long dataRef) {
		this.dataRef = dataRef;
	}

	public String getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

	public String getNoticeFlag() {
		return noticeFlag;
	}

	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public String getDataSecret() {
		return dataSecret;
	}

	public void setDataSecret(String dataSecret) {
		this.dataSecret = dataSecret;
	}

	public String getDataTitle() {
		return dataTitle;
	}

	public void setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
	}

	public String getDataContent() {
		return dataContent;
	}

	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public long getViewCount() {
		return viewCount;
	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
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

	public String getTmpField1() {
		return tmpField1;
	}

	public void setTmpField1(String tmpField1) {
		this.tmpField1 = tmpField1;
	}

	public String getTmpField2() {
		return tmpField2;
	}

	public void setTmpField2(String tmpField2) {
		this.tmpField2 = tmpField2;
	}

	public String getTmpField3() {
		return tmpField3;
	}

	public void setTmpField3(String tmpField3) {
		this.tmpField3 = tmpField3;
	}

	public String getTmpField4() {
		return tmpField4;
	}

	public void setTmpField4(String tmpField4) {
		this.tmpField4 = tmpField4;
	}

	public String getTmpField5() {
		return tmpField5;
	}

	public void setTmpField5(String tmpField5) {
		this.tmpField5 = tmpField5;
	}

	public String getTmpField6() {
		return tmpField6;
	}

	public void setTmpField6(String tmpField6) {
		this.tmpField6 = tmpField6;
	}

	public String getTmpField7() {
		return tmpField7;
	}

	public void setTmpField7(String tmpField7) {
		this.tmpField7 = tmpField7;
	}

	public String getTmpField8() {
		return tmpField8;
	}

	public void setTmpField8(String tmpField8) {
		this.tmpField8 = tmpField8;
	}

	public String getTmpField9() {
		return tmpField9;
	}

	public void setTmpField9(String tmpField9) {
		this.tmpField9 = tmpField9;
	}

	public String getTmpField10() {
		return tmpField10;
	}

	public void setTmpField10(String tmpField10) {
		this.tmpField10 = tmpField10;
	}

}
