import com.lxq.platform.util.MD5;
public class testMD5 {
	public static void main(String[] args) {
		
		System.out.println(MD5.encode("1234abcd"));
		
		//结果：
		//1234abcd : ef73781effc5774100f87fe2f437a435
	}
}
