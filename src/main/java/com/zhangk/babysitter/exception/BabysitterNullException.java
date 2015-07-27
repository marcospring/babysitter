package com.zhangk.babysitter.exception;

public class BabysitterNullException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BabysitterNullException() {
		super("验证码错误！");
	}
}
