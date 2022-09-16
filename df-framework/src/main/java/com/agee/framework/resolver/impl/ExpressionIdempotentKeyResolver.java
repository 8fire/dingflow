package com.agee.framework.resolver.impl;

import cn.hutool.core.util.ArrayUtil;
import com.agee.framework.annotation.Idempotent;
import com.agee.framework.resolver.IdempotentKeyResolver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author qimingjin
 * @date 2022-08-05 08:28
 * @Description: 支持 Spring EL 表达式的形式key
 */
public class ExpressionIdempotentKeyResolver implements IdempotentKeyResolver {

    private final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final ExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        Method method = getMethod(joinPoint);
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = this.parameterNameDiscoverer.getParameterNames(method);
        // 准备 Spring EL 表达式解析的上下文
        StandardEvaluationContext evaluationContext=new StandardEvaluationContext();
        if(ArrayUtil.isNotEmpty(parameterNames)){
            for (int i=0;i<parameterNames.length;i++){
                evaluationContext.setVariable(parameterNames[i],args[i]);
            }
        }
        //解析参数
        Expression expression = expressionParser.parseExpression(idempotent.keyArg());
        return expression.getValue(evaluationContext,String.class);
    }


    private static Method getMethod(JoinPoint joinPoint){
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //声明在类上
        if(!method.getDeclaringClass().isInterface()){
            return method;
        }
        //声明在接口上
        try {
            return joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),method.getParameterTypes());
        }catch (NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }
}
