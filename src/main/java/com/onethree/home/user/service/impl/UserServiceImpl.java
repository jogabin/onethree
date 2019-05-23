package com.onethree.home.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onethree.home.user.service.UserService;
import com.onethree.home.user.dao.UserDao;
import com.onethree.home.user.vo.UserVO;

@Repository("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao; 
	
	/**
	 * 회원총갯수
	 * */
	public long count(UserVO dataVO){
		return userDao.count(dataVO);
	}
	
	/**
	 * 회원리스트
	 * */
	public List<UserVO> getUserList(UserVO dataVO) {
		return userDao.getUserList(dataVO);
	}
	
	/**
	 * 회비용 회원리스트 
	 * */
	public List<UserVO> getUserMoneyList(UserVO dataVO) {
		return userDao.getUserMoneyList(dataVO);
	}
	
	/**
	 * 회원정보 조회
	 * */
	public UserVO getUserVO(UserVO dataVO) {
		return userDao.getUserVO(dataVO);
	}
	
	/**
	 * 회원등록
	 * */
	@Transactional
	public String writeUser(UserVO userVO) {
		return userDao.writeUser(userVO);
	}
	
	/**
	 * 회원수정
	 * */
	@Transactional
	public void updateUser(UserVO userVO){
		userDao.updateUser(userVO);
	}
	
	/**
	 * 회원삭제
	 * */
	@Transactional
	public void deleteUser(UserVO userVO)
	{
		userDao.deleteUser(userVO);
	}
	
	/**
	 * 아이디 패스워드 조회
	 * 파라미터*
	 * 아이디,패스워드
	 * */
	public UserVO getLoginUser(UserVO dataVO) {
		return userDao.getLoginUser(dataVO);
	}
	
	/**
	 * 아이디로 회원정보 찾기
	 * */
	public long getLoginUserIdCount(UserVO dataVO) {
		return userDao.getLoginUserIdCount(dataVO);
	}
	
	/**
	 * 이메일로 회원정보 찾기
	 * */
	public long getUserEmailCount(UserVO dataVO) {
		return userDao.getUserEmailCount(dataVO);
	}
}
