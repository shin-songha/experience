package com.study.exprience.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.exprience.exception.BrickException;

@Aspect
public class PreAccountAuthorizeAspect {
	private static final Logger log = LoggerFactory.getLogger(PreAccountAuthorizeAspect.class);

	public PreAccountAuthorizeAspect() {
	}

	@Before("@annotation(preAccountAuthorize)")
	public void methodBefore(JoinPoint joinPoint, PreAccountAuthorize preAccountAuthorize) {
		this.checkBefore(joinPoint, preAccountAuthorize);
	}

	@Before("@target(preAccountAuthorize) && bean(*Controller)")
	public void classBefore(JoinPoint joinPoint, PreAccountAuthorize preAccountAuthorize) {
		this.checkBefore(joinPoint, preAccountAuthorize);
	}

	public void checkBefore(JoinPoint joinPoint, PreAccountAuthorize preAccountAuthorize) {
		this.checkBanInfo(preAccountAuthorize);
		this.checkRealName(preAccountAuthorize);
	}

	private void checkBanInfo(PreAccountAuthorize preAccountAuthorize) {
		if (preAccountAuthorize.checkBanInfo()) {
			boolean banInfo = false;
			log.debug("checkBanInfo banInfo : {}", banInfo);
			if (banInfo) {
				throw new BrickException(preAccountAuthorize.checkBanInfoErrorMessageCode());
			}
		}
	}

	private void checkRealName(PreAccountAuthorize preAccountAuthorize) {
		if (preAccountAuthorize.checkRealName()) {
			boolean notExistRealName = false;
			log.debug("notExistRealName : {}", notExistRealName);
			if (notExistRealName) {
				throw new BrickException(preAccountAuthorize.checkRealNameErrorMessageCode());
			}
		}
	}
	
}
