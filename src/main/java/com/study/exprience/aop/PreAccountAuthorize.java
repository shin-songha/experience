package com.study.exprience.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasRole('ROLE_USER')")
public @interface PreAccountAuthorize {
	boolean checkBanInfo() default false;

	String checkBanInfoErrorMessageCode() default "security.BANNED_ACCOUNT";

	boolean checkRealName() default false;

	String checkRealNameErrorMessageCode() default "security.NOT_EXIST_REALNAME";

}
