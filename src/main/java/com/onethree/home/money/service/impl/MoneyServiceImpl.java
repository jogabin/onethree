package com.onethree.home.money.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onethree.home.money.service.MoneyService;
import com.onethree.home.money.vo.MoneyVO;
import com.onethree.home.money.dao.MoneyDao;

@Repository("moneyService")
public class MoneyServiceImpl implements MoneyService{
	@Autowired
	private MoneyDao moneyDao;
	
	/**
	 * 연간별 회비리스트
	 * */
	public List<MoneyVO> getYearMoneyList(MoneyVO dataVO) {
		return moneyDao.getYearMoneyList(dataVO);
	}
	
	/**
	 * 해당날짜까지의 회원별 회비입금내역 리스트
	 * */
	public List<Object[]> getUserYearTotalMoneyList(MoneyVO dataVO) {
		return moneyDao.getUserYearTotalMoneyList(dataVO);
	}
	
	/**
	 * 입출금정보 등록
	 * */
	@Transactional
	public String moneyWrite(MoneyVO moneyVO) {
		return moneyDao.moneyWrite(moneyVO);
	}
	
	/**
	 * 입출금정보 수정
	 * */
	@Transactional
	public void moneyUpdate(MoneyVO moneyVO) {
		moneyDao.moneyUpdate(moneyVO);
	}
	
	/**
	 * 입출금 총금액
	 * */
	public int getMaxMoney(MoneyVO dataVO){
		return moneyDao.getMaxMoney(dataVO);
	}
	
	/**
	 * 회비정보 삭제
	 * */
	@Transactional
	public int deleteMoneyInfo(MoneyVO dataVO) {
		return moneyDao.deleteMoneyInfo(dataVO);
	}
	
	/**
	 * 입출금 전용 리스트
	 * */
	public List<MoneyVO> getInOutList(MoneyVO dataVO) {
		return moneyDao.getInOutList(dataVO);
	}
}
