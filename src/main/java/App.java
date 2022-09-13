import logic.Operation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    public static void main( String[] args ) {

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        //example of aroundAdvice with AOP xml config
        Operation op = context.getBean("op", Operation.class);
        op.m();
        op.msg();
        op.k();

    }
}
