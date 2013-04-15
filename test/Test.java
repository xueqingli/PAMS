import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lxq.platform.goalManage.service.intef.IGoalService;
import com.lxq.platform.userManage.pojo.Department;

public class Test {

	private static ApplicationContext ctx = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/conf/applicationContext.xml");
	
	public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ParseException {
		test();
	}
	
	static void test(){
		
		
		IGoalService goalService = (IGoalService) ctx.getBean("goalService");
		
		Department dept = new Department();
		dept.setUid(1);
		
		System.out.println(goalService.getUserByRole (dept , "100").getUserName());
		
		
	}
}

