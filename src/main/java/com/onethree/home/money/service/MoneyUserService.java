package com.onethree.home.money.service;

import java.util.List;

import com.onethree.home.money.vo.MoneyUserVO;

public interface MoneyUserService {
	/**
	 * 회비회원총갯수
	 * */
	public long count(MoneyUserVO dataVO);
	
	/**
	 * 회비회원리스트 페이징
	 * */
	public List<MoneyUserVO> getMoneyUserListPaging(MoneyUserVO dataVO);
	
	/**
	 * 회비용 회원리스트 
	 * */
	public List<MoneyUserVO> getMoneyUserList(MoneyUserVO dataVO);
	
	/**
	 * 회비회원정보 조회
	 * */
	public MoneyUserVO getMoneyUserVO(MoneyUserVO dataVO);
	
	/**
	 * 회비회원등록
	 * */
	public String writeMoneyUser(MoneyUserVO userVO);
	
	/**
	 * 회비회원수정
	 * */
	public void updateMoneyUser(MoneyUserVO userVO);
}
