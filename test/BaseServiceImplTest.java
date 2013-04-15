

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lxq.platform.service.intef.IBaseService;
import com.lxq.platform.userManage.service.intef.IUserService;

public class BaseServiceImplTest {
	
	static ApplicationContext ctx;
	public static IBaseService baseService; 
	public static IUserService userService; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		baseService = (IBaseService) ctx.getBean("baseService");
		userService = (IUserService) ctx.getBean("userService");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByHQLString() {
		//Menu menu = (Menu)baseService.findUniqueByHQL("from Menu where uid=1");
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
