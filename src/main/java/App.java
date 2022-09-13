import domain.Employee;
import logic.Operation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.EmployeeDao;

public class App
{
    public static void main( String[] args ) {

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        //example of aroundAdvice with AOP xml config
        Operation op = context.getBean("op", Operation.class);
        op.m();
        op.msg();
        op.k();

        //example of JDBC template
        EmployeeDao dao = context.getBean("eDao", EmployeeDao.class);
        int status = dao.save(new Employee(1, "ahmad", 120000));
        System.out.println(status);

    }
}
