package com.asher.debug.runtime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.TimeUnit;

/**
 * Created by ouyangfan on 2017/1/10.
 * <p/>
 * define Aspectj AOP
 * 定义切面， 切入点。
 * 告诉Aspectj 在哪进行代码织入
 */
@Aspect
public class Asher {
    private static volatile boolean enabled = true;

    // 定义切入点为带@Time注解的类的所有方法
    @Pointcut("within(@com.asher.debug.annotation.Time *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    // 定义切入点为带@Time注解的方法
    @Pointcut("execution(@com.asher.debug.annotation.Time * *(..)) || methodInsideAnnotatedType()")
    public void method() {}

    // 定义切入点为带@Time注解的构造方法
    @Pointcut("execution(@com.asher.debug.annotation.Time *.new(..)) || constructorInsideAnnotatedType()")
    public void constructor() {}

    public void setEnabled(boolean enabled) {
        Asher.enabled = enabled;
    }

    // 定义通知，织入代码
    @Around("method() || constructor()")
    public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable{
        enterMethod(joinPoint);

        long startNanos = System.nanoTime();
        Object result = joinPoint.proceed();
        long stopNanos = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);

        exitMethod(joinPoint, result, lengthMillis);
        return result;
    }

    private void enterMethod(JoinPoint joinPoint) {
        // TODO: 2017/1/10
    }

    private void exitMethod(JoinPoint joinPoint, Object result, long lengthMillis) {
        // TODO: 2017/1/10
    }
}
