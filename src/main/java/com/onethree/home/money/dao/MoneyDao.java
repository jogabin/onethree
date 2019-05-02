package com.onethree.home.money.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onethree.home.money.vo.MoneyVO;
import com.onethree.home.user.vo.UserVO;

@Repository("moneyDao")
public class MoneyDao extends HibernateDaoSupport{
	@SuppressWarnings("restriction")
	@Resource(name = "sessionFactory")
	public void setHibernateDaoSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 연간별 회비리스트
	 * */
	@SuppressWarnings("resource")
	public List<MoneyVO> getYearMoneyList(MoneyVO dataVO) {
		Session session = null;
		
		List<MoneyVO> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<MoneyVO> criteriaQuery = builder.createQuery(MoneyVO.class);
			Root<MoneyVO> root = criteriaQuery.from(MoneyVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.equal(root.get("yearDate"), dataVO.getYearDate());
			Predicate p2 = builder.equal(root.get("inoutFlag"), dataVO.getInoutFlag());
			Predicate p3 = builder.equal(root.get("planFlag"), dataVO.getPlanFlag());//회비입금용 플래그
			
			
			criteria = builder.and(criteria, p1);
			criteria = builder.and(criteria, p2);
			criteria = builder.and(criteria, p3);
			
			criteriaQuery.where(criteria);
			criteriaQuery.orderBy(builder.asc(root.get("userUid")),builder.asc(root.get("monthDate")));			
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
	 * 해당날짜까지의 회원별 회비입금내역 리스트
	 * */
	@SuppressWarnings("resource")
	public List<Object[]> getUserYearTotalMoneyList(MoneyVO dataVO) {
		Session session = null;
		//그룹바이 사용시 매칭시키기 위한 Object[]로 설정
		List<Object[]> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
			Root<MoneyVO> root = criteriaQuery.from(MoneyVO.class);
			
			Predicate criteria = builder.conjunction();
			//검색값 추가
			Predicate p1 = builder.lessThan(root.get("inoutDt"), dataVO.getInoutDt());
			Predicate p2 = builder.equal(root.get("inoutFlag"), dataVO.getInoutFlag());
			Predicate p3 = builder.equal(root.get("planFlag"), dataVO.getPlanFlag());//회비입금용 플래그
			
			criteria = builder.and(criteria, p1);
			criteria = builder.and(criteria, p2);
			criteria = builder.and(criteria, p3);
			
			criteriaQuery.where(criteria);	
			//그룹으로 검색할 컬럼설정
			criteriaQuery.multiselect(root.get("userUid"),builder.sum(root.get("money"))).distinct(true);
			criteriaQuery.groupBy(root.get("userUid"));//그룹으로 묶을 컬럼설정
			
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
	 * 입출금정보 등록
	 * */
	public String moneyWrite(MoneyVO moneyVO) {
		// TODO Auto-generated method stub
		return (String)getHibernateTemplate().save(moneyVO);
	}
	
	/**
	 * 입출금정보 수정
	 * */
	public void moneyUpdate(MoneyVO moneyVO) {
		getHibernateTemplate().update(moneyVO);
	}
	
	/**
	 * 입출금 총금액
	 * */
	public int getMaxMoney(MoneyVO dataVO)
	{
		int totalMoney = 0;
		Session session = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Integer> countQuery = builder.createQuery(Integer.class);
			Root<MoneyVO> root = countQuery.from(MoneyVO.class);
			
			Predicate criteria = builder.conjunction();
			
			Predicate p1 = builder.equal(root.get("inoutFlag"), dataVO.getInoutFlag());			
			criteria = builder.and(criteria, p1);
			
			countQuery.where(criteria);
			
			countQuery.select(builder.sum(root.get("money")));
			
			try{
				if(session.createQuery(countQuery).getMaxResults()>0){
					if(session.createQuery(countQuery).getSingleResult()!=null){
						totalMoney = session.createQuery(countQuery).getSingleResult();
					}
				}
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
		return totalMoney;
	}
	
	/**
	 * 회비정보 삭제
	 * */
	@SuppressWarnings("resource")
	public int deleteMoneyInfo(MoneyVO dataVO) {
		Session session = null;
		int result = 0;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			//session.getTransaction().begin();
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaDelete<MoneyVO> criteriaQuery = builder.createCriteriaDelete(MoneyVO.class);
			Root<MoneyVO> root = criteriaQuery.from(MoneyVO.class);
			
			Predicate criteria = builder.conjunction();
			
			Predicate p1 = builder.equal(root.get("moneyUid"), dataVO.getMoneyUid());
			criteria = builder.and(criteria, p1);
			
			criteriaQuery.where(criteria);
			result = session.createQuery(criteriaQuery).executeUpdate();
			
			//session.getTransaction().commit();
			
		}catch(HibernateException e)
		{
			Transaction tx = session.getTransaction();
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}finally
		{
			//if(session != null && session.isOpen()) session.close();
		}
		
		return result;
	}
	
	/**
	 * 입출금 전용 리스트
	 * */
	@SuppressWarnings("resource")
	public List<MoneyVO> getInOutList(MoneyVO dataVO) {
		Session session = null;
		
		List<MoneyVO> resultList = null;
		try{			
			try {
			    session = super.getSessionFactory().getCurrentSession();
			} catch (HibernateException e) {
			    session = super.getSessionFactory().openSession();
			}
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<MoneyVO> criteriaQuery = builder.createQuery(MoneyVO.class);
			Root<MoneyVO> root = criteriaQuery.from(MoneyVO.class);
			
			Predicate criteria = builder.conjunction();
			
			//검색값 추가
			Predicate p1 = builder.equal(root.get("planFlag"), dataVO.getPlanFlag());//회비입금용 플래그
			criteria = builder.and(criteria, p1);
			
			//입출금 플래그
			if(dataVO.getInoutFlag()!=null && !"".equals(dataVO.getInoutFlag())){
				Predicate p2 = builder.equal(root.get("inoutFlag"), dataVO.getInoutFlag());
				criteria = builder.and(criteria, p2);
			}
			//카테고리 플래그
			if(dataVO.getInoutCate1()!=null && !"".equals(dataVO.getInoutCate1())){
				Predicate p3 = builder.equal(root.get("inoutCate1"), dataVO.getInoutCate1());
				criteria = builder.and(criteria, p3);
			}
			
			criteriaQuery.where(criteria);
			criteriaQuery.orderBy(builder.desc(root.get("inoutDt")));			
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
}
