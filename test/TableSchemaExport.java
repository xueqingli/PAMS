import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class TableSchemaExport {
	
	public static void main(String[] args) {
		
		// 读取hibernate.cfg.xml文件
		Configuration cfg = new Configuration().configure();

		// 用配置文件信息，构建ddl的操作对象
		SchemaExport export = new SchemaExport(cfg);

		// 根据po配置文件中属性的描述，显示ddl脚本，并创建对应的表结构，
		export.create(true, true);
	}
}
