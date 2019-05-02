package com.onethree.home.money.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.onethree.home.money.vo.MoneyVO;

public interface MoneyService {
	/**
	 * 연간별 회비리스트
	 * */
	public List<MoneyVO> getYearMoneyList(MoneyVO dataVO);
	
	/**
	 * 해당날짜까지의 회원별 회비입금내역 리스트
	 * */
	public List<Object[]> getUserYearTotalMoneyList(MoneyVO dataVO);
	
	/**
	 * 입출금정보 등록
	 * */
	public String moneyWrite(MoneyVO moneyVO);
	
	/**
	 * 입출금정보 수정
	 * */
	public void moneyUpdate(MoneyVO moneyVO);
	
	/**
	 * 입출금 총금액
	 * */
	public int getMaxMoney(MoneyVO dataVO);
	
	/**
	 * 회비정보 삭제
	 * */
	public int deleteMoneyInfo(MoneyVO dataVO);
	
	/**
	 * 입출금 전용 리스트
	 * */
	public List<MoneyVO> getInOutList(MoneyVO dataVO);
}
