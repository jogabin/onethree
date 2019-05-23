package com.onethree.home.user.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onethree.home.user.vo.UserVO;

@Repository("userDao")
public class UserDao extends HibernateDaoSupport{
	@SuppressWarnings("restriction")
	@Resource(name = "sessionFactory")
	public void setHibernateDaoSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 회원총갯수
	 * */
	public long count(UserVO dataVO)
	{
		long count = 0;
		Session session = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
			Root<UserVO> root = countQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			if((dataVO.getSearchType() != null && !"".equals(dataVO.getSearchType()))
				&& 	dataVO.getKeyword() != null && !"".equals(dataVO.getKeyword()))
			{
				Predicate p1 = builder.like(root.get(dataVO.getSearchType()), "%"+dataVO.getKeyword()+"%");	
				criteria = builder.and(criteria, p1);
			}			
			
			countQuery.where(criteria);
			
			countQuery.select(builder.count(root));
			
			try{
				count = session.createQuery(countQuery).getSingleResult();
			}catch (NoResultException nre){
				//Ignore this because as per your logic this is ok!
			}
			
		}catch(HibernateException e)
		{
			throw e;
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return count;
	}
	
	/**
	 * 회원리스트 페이징
	 * */
	@SuppressWarnings("resource")
	public List<UserVO> getUserList(UserVO dataVO) {
		Session session = null;
		
		List<UserVO> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<UserVO> criteriaQuery = builder.createQuery(UserVO.class);
			Root<UserVO> root = criteriaQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			if((dataVO.getSearchType() != null && !"".equals(dataVO.getSearchType()))
				&& 	dataVO.getKeyword() != null && !"".equals(dataVO.getKeyword()))
			{
				Predicate p1 = builder.like(root.get(dataVO.getSearchType()), "%"+dataVO.getKeyword()+"%");			
				criteria = builder.and(criteria, p1);
			}	
			
			criteriaQuery.where(criteria);
			criteriaQuery.orderBy(builder.desc(root.get("registerDt")));
			criteriaQuery.select(root).distinct(true);
			
			resultList = session.createQuery(criteriaQuery).setFirstResult(dataVO.getStartRow()).setMaxResults(dataVO.getRowCount()).getResultList();
			
		}catch(HibernateException e)
		{
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return resultList;
	}
	
	/**
	 * 회비용 회원리스트 
	 * */
	@SuppressWarnings("resource")
	public List<UserVO> getUserMoneyList(UserVO dataVO) {
		Session session = null;
		
		List<UserVO> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<UserVO> criteriaQuery = builder.createQuery(UserVO.class);
			Root<UserVO> root = criteriaQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.notEqual(root.get("stateNum"), dataVO.getStateNum());
			Predicate p2 = builder.equal(root.get("moneyFlag"), dataVO.getMoneyFlag());
			criteria = builder.and(criteria, p1);	
			criteria = builder.and(criteria, p2);	
			
			criteriaQuery.where(criteria);
			criteriaQuery.orderBy(builder.asc(root.get("registerDt")));
			criteriaQuery.select(root).distinct(true);
			
			resultList = session.createQuery(criteriaQuery).getResultList();
			
		}catch(HibernateException e)
		{
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return resultList;
	}
	
	/**
	 * 회원정보 조회
	 * */
	@SuppressWarnings("resource")
	public UserVO getUserVO(UserVO dataVO) {
		Session session = null;
		
		UserVO result = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<UserVO> criteriaQuery = builder.createQuery(UserVO.class);
			Root<UserVO> root = criteriaQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("userUid"), dataVO.getUserUid());
			criteria = builder.and(criteria, p1);	
			
			criteriaQuery.where(criteria);
			criteriaQuery.orderBy(builder.asc(root.get("registerDt")));
			criteriaQuery.select(root).distinct(true);
			
			result = session.createQuery(criteriaQuery).getSingleResult();
			
		}catch(HibernateException e)
		{
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return result;
	}
	
	/**
	 * 회원등록
	 * */
	public String writeUser(UserVO userVO) {
		// TODO Auto-generated method stub
		return (String)getHibernateTemplate().save(userVO);
	}
	
	/**
	 * 회원수정
	 * */
	public void updateUser(UserVO userVO)
	{
		getHibernateTemplate().update(userVO);
	}
	
	/**
	 * 회원삭제
	 * */
	public void deleteUser(UserVO userVO)
	{
		getHibernateTemplate().delete(userVO);
	}
	
	/**
	 * 아이디 패스워드 조회
	 * 파라미터*
	 * 아이디,패스워드
	 * */
	@SuppressWarnings("resource")
	public UserVO getLoginUser(UserVO dataVO) {
		Session session = null;
		
		UserVO result = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<UserVO> criteriaQuery = builder.createQuery(UserVO.class);
			Root<UserVO> root = criteriaQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("userId"), dataVO.getUserId());
			criteria = builder.and(criteria, p1);	
			Predicate p2 = builder.equal(root.get("userPass"), dataVO.getUserPass());
			criteria = builder.and(criteria, p2);	
			
			criteriaQuery.where(criteria);
			criteriaQuery.select(root).distinct(true);
			
			try {
				result = session.createQuery(criteriaQuery).getSingleResult();
		    } catch (NoResultException nre) {
		        result = null;
		    }
			
		}catch(HibernateException e)
		{
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return result;
	}
	
	/**
	 * 아이디로 회원정보 찾기
	 * */
	@SuppressWarnings("resource")
	public long getLoginUserIdCount(UserVO dataVO) {
		long count = 0;
		Session session = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
			Root<UserVO> root = countQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("userId"), dataVO.getUserId());
			criteria = builder.and(criteria, p1);			
			
			countQuery.where(criteria);
			
			countQuery.select(builder.count(root));
			
			try{
				count = session.createQuery(countQuery).getSingleResult();
			}catch (NoResultException nre){
				//Ignore this because as per your logic this is ok!
			}
			
		}catch(HibernateException e)
		{
			throw e;
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return count;
	}
	
	/**
	 * 이메일로 회원정보 찾기
	 * */
	@SuppressWarnings("resource")
	public long getUserEmailCount(UserVO dataVO) {
		long count = 0;
		Session session = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
			Root<UserVO> root = countQuery.from(UserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("userEmail"), dataVO.getUserEmail());
			criteria = builder.and(criteria, p1);			
			
			countQuery.where(criteria);
			
			countQuery.select(builder.count(root));
			
			try{
				count = session.createQuery(countQuery).getSingleResult();
			}catch (NoResultException nre){
				//Ignore this because as per your logic this is ok!
			}
			
		}catch(HibernateException e)
		{
			throw e;
		}finally
		{
			if(session != null && session.isOpen()) session.close();
		}
		return count;
	}
}
