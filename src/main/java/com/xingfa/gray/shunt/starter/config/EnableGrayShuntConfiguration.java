package com.xingfa.gray.shunt.starter.config;

import com.xingfa.gray.shunt.starter.GrayShuntAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description:starter注解类
 * @author: chenluqiang
 * @time: 2020-04-25:10:47
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GrayShuntAutoConfiguration.class)
public @interface EnableGrayShuntConfiguration {

}