package com.onethree.home.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.onethree.home.user.vo.UserVO;

public interface UserService {
	
	/**
	 * 회원총갯수
	 * */
	public long count(UserVO dataVO);
	
	/**
	 * 회원리스트
	 * */
	public List<UserVO> getUserList(UserVO dataVO);
	
	/**
	 * 회비용 회원리스트 
	 * */
	public List<UserVO> getUserMoneyList(UserVO dataVO);
	
	/**
	 * 회원정보 조회
	 * */
	public UserVO getUserVO(UserVO dataVO);
	
	/**
	 * 회원등록
	 * */
	public String writeUser(UserVO userVO);
	
	/**
	 * 회원수정
	 * */
	public void updateUser(UserVO userVO);
	
	/**
	 * 아이디 패스워드 조회
	 * 파라미터*
	 * 아이디,패스워드
	 * */
	public UserVO getLoginUser(UserVO dataVO);
	
}
