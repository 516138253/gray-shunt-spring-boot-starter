package com.xingfa.gray.shunt.starter.condition;

import com.xingfa.gray.shunt.starter.constant.StarterConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @description:判定使用Bean的条件，判断环境中是否有手动配置版本号与机器码，如果都没有，则通过使用默认的配置。
 * @author: chenluqiang
 * @time: 2020-04-21:14:44
 */
public class NotRequestHeaderCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String version = environment.getProperty(StarterConstant.PATH_REQUEST_VERSION);
        String machineCode = environment.getProperty(StarterConstant.PATH_MACHINE_CODE);
        if(StringUtils.isBlank(version)&&StringUtils.isBlank(machineCode)){
            return true;
        }
        return false;
    }
}
