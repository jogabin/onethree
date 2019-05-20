package com.onethree.home.money.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onethree.home.money.dao.MoneyUserDao;
import com.onethree.home.money.service.MoneyService;
import com.onethree.home.money.service.MoneyUserService;
import com.onethree.home.money.vo.MoneyUserVO;

@Repository("moneyUserService")
public class MoneyUserServiceImpl implements MoneyUserService{
	@Autowired
	private MoneyUserDao moneyUserDao;
	
	/**
	 * 회비회원총갯수
	 * */
	public long count(MoneyUserVO dataVO) {
		return (long)moneyUserDao.count(dataVO);
	}
	
	/**
	 * 회비회원리스트 페이징
	 * */
	public List<MoneyUserVO> getMoneyUserListPaging(MoneyUserVO dataVO) {
		return moneyUserDao.getMoneyUserListPaging(dataVO);
	}
	
	/**
	 * 회비용 회원리스트 
	 * */
	public List<MoneyUserVO> getMoneyUserList(MoneyUserVO dataVO) {
		return moneyUserDao.getMoneyUserList(dataVO);
	}
	
	/**
	 * 회비회원정보 조회
	 * */
	public MoneyUserVO getMoneyUserVO(MoneyUserVO dataVO) {
		return moneyUserDao.getMoneyUserVO(dataVO);
	}
	
	/**
	 * 회비회원등록
	 * */
	@Transactional
	public String writeMoneyUser(MoneyUserVO userVO) {
		return moneyUserDao.writeMoneyUser(userVO);
	}
	
	/**
	 * 회비회원수정
	 * */
	@Transactional
	public void updateMoneyUser(MoneyUserVO userVO) {
		moneyUserDao.updateMoneyUser(userVO);
	}
}
