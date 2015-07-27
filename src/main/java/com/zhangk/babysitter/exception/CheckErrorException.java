package com.zhangk.babysitter.exception;

public class CheckErrorException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CheckErrorException() {
		super("月嫂不存在！");
	}
}
