package com.asher.debug.runtime;

import android.os.Looper;
import android.util.Log;

import com.asher.debug.util.ClassTagUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * Created by ouyangfan on 2017/1/19.
 * <p>
 * define Aspect AOP
 * 定义hook exception 的切面、切点
 */
@Aspect
public class ExceptionIntercept {
    private static volatile boolean enabled = true;

    // NullPointerException
//    private static final String POINTCUT_NULL_POINTER = "execution(* hookException())";
    private static final String POINTCUT_NULL_POINTER = "execution(* *.*(..))";

    @Pointcut(POINTCUT_NULL_POINTER)
    public void exceptionPointCut() {

    }

    @Around("exceptionPointCut()")
    public Object exceptionHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            handlerMethod(joinPoint, e);
        }
        return result;
    }

    private void handlerMethod(JoinPoint joinPoint, Exception e) {
        // 通过CodeSignature签名来加载出对应运行数据
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Class<?> cls = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        String exceptionFile = sourceLocation.getFileName();
        int exceptionLine = sourceLocation.getLine();
        StringBuilder builder = new StringBuilder("\u21E2 ");
        builder.append(methodName).append("(");
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]).append("=");
            builder.append(String.valueOf(parameterValues[i]));
        }
        builder.append(")");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            builder.append(" [Thread: \"").append(Thread.currentThread().getName()).append("\"]");
        }
        builder.append(" \n ");
        builder.append(" throw ");
        builder.append(e);
        builder.append(" \n");
        builder.append("\u21E0");
        builder.append(" at ");
        builder.append(exceptionFile);
        builder.append(" on ");
        builder.append(exceptionLine);
        builder.append(" line ");
        Log.i(ClassTagUtil.asTag(cls), builder.toString());
    }

    /**
     * 这个是在exception发生后，
     *
     * @param e
     */
    @AfterThrowing(value = "exceptionPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Class<?> cls = codeSignature.getDeclaringType();
        if (e instanceof NullPointerException) {
            Log.i(ClassTagUtil.asTag(cls), "throw null pointer == " + e);
        } else {
            Log.i(ClassTagUtil.asTag(cls), "other exception == " + e);
        }
    }

}
