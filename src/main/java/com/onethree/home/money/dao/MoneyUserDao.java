package com.onethree.home.money.dao;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onethree.home.money.vo.MoneyUserVO;
import com.onethree.home.user.vo.UserVO;

@Repository("moneyUserDao")
public class MoneyUserDao extends HibernateDaoSupport{
	@SuppressWarnings("restriction")
	@Resource(name = "sessionFactory")
	public void setHibernateDaoSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 회비회원총갯수
	 * */
	public long count(MoneyUserVO dataVO)
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
			Root<MoneyUserVO> root = countQuery.from(MoneyUserVO.class);
			
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
	 * 회비회원리스트 페이징
	 * */
	@SuppressWarnings("resource")
	public List<MoneyUserVO> getMoneyUserListPaging(MoneyUserVO dataVO) {
		Session session = null;
		
		List<MoneyUserVO> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<MoneyUserVO> criteriaQuery = builder.createQuery(MoneyUserVO.class);
			Root<MoneyUserVO> root = criteriaQuery.from(MoneyUserVO.class);
			
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
	public List<MoneyUserVO> getMoneyUserList(MoneyUserVO dataVO) {
		Session session = null;
		
		List<MoneyUserVO> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<MoneyUserVO> criteriaQuery = builder.createQuery(MoneyUserVO.class);
			Root<MoneyUserVO> root = criteriaQuery.from(MoneyUserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("deleteFlag"), dataVO.getDeleteFlag());
			criteria = builder.and(criteria, p1);	
			
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
	 * 회비회원정보 조회
	 * */
	@SuppressWarnings("resource")
	public MoneyUserVO getMoneyUserVO(MoneyUserVO dataVO) {
		Session session = null;
		
		MoneyUserVO result = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<MoneyUserVO> criteriaQuery = builder.createQuery(MoneyUserVO.class);
			Root<MoneyUserVO> root = criteriaQuery.from(MoneyUserVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("moneyUserUid"), dataVO.getMoneyUserUid());
			criteria = builder.and(criteria, p1);	
			
			criteriaQuery.where(criteria);
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
	 * 회비회원등록
	 * */
	public String writeMoneyUser(MoneyUserVO userVO) {
		// TODO Auto-generated method stub
		return (String)getHibernateTemplate().save(userVO);
	}
	
	/**
	 * 회비회원수정
	 * */
	public void updateMoneyUser(MoneyUserVO userVO)
	{
		getHibernateTemplate().update(userVO);
	}
	
}
