package dao;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.onethree.home.money.dao.MoneyDao;
import com.onethree.home.money.vo.MoneyVO;
import com.onethree.home.user.dao.UserDao;
import com.onethree.home.user.vo.UserVO;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-*.xml" })
public class UserDaoTest {
	@Autowired
	private SessionFactory sessionFactory;

	@Resource(name="moneyDao")
	private MoneyDao moneyDao;
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Before
	public void BeforeTest(){
		
	}
	
	@Test
	public void test(){
		/*UserVO userVO = new UserVO();
		userVO.setStateNum(3);//탈퇴회원제외
		userVO.setMoneyFlag("Y");//회비전용플래그
		List<UserVO> userList = userDao.getUserMoneyList(userVO);

		
		 
		int sYear= 2014; 
		int sMonth = 5; 
		int eYear = 2018; 
		int eMonth = 10; 
		int month_diff = (eYear - sYear)* 12 + (eMonth - sMonth); 
		System.out.println(month_diff);*/ 

		
		MoneyVO moneyVO = new MoneyVO();
		moneyVO.setYearDate(2019);
		moneyVO.setInoutFlag("A");
		moneyVO.setPlanFlag("Y");
		List<Object[]> moneyList = moneyDao.getUserYearTotalMoneyList(moneyVO);
		
		if(moneyList!=null && moneyList.size()>0){
			for(Object[] dataObj:moneyList){
				System.out.println("===============================");
				System.out.println(dataObj[0]);
				System.out.println(dataObj[1]);
			}
		}
		
	}
	
	@After
	public void AfterTest(){
		
	}

}
