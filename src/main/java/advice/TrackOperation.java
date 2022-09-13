package advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class TrackOperation {

    public Object myadvice(ProceedingJoinPoint pjp)throws Throwable//it is around advice
    {
        System.out.println("Additional Concern Before calling actual method");
        Object obj=pjp.proceed();
        System.out.println("Additional Concern After calling actual method");
        return obj;
    }

}
