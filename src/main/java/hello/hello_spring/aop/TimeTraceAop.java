package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    // 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
    // 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.

    @Around("execution(* hello.hello_spring..*(..))") // 공통 관심 사항(cross-cutting concern)
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("start = " + joinPoint.toString());
        try {
            return joinPoint.proceed(); // next method
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: = " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
