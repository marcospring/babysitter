package com.zhangk.babysitter.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhangk.babysitter.entity.CustomerManager;
import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.utils.common.Constants;
import com.zhangk.babysitter.utils.common.ResultInfo;

public class BaseController {
	@Autowired
	protected HttpServletRequest request;

	protected PageResult res = new PageResult();

	protected PageResult getResult() {
		return new PageResult();
	}

	protected PageResult getResult(ResultInfo info) {
		return new PageResult(info);
	}

	protected PageResult getErrRes() {
		return new PageResult(-1, null);
	}

	protected PageResult getErrRes(int code) {
		return new PageResult(code, null);
	}

	protected PageResult getErrRes(ResultInfo info) {
		return new PageResult(info);
	}

	protected PageResult getErrRes(int code, String msg) {
		return new PageResult(code, msg);
	}

	protected PageResult getErrRes(String msg) {
		return new PageResult(-1, msg);
	}

	public class PageResult extends HashMap<String, Object> {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public PageResult() {
			this.put("code", 0);
		}

		public PageResult(Object info) {
			this.put("code", 0);
			this.put("result", info);
		}

		public PageResult(int code, String errorTextKey) {
			this.put("code", code);
			this.put("msg", errorTextKey);
		}

		public PageResult(ResultInfo info) {
			this.put("code", info.getCode());
			this.put("msg", info.getMsg());
		}

		public PageResult(int code) {
			this.put("code", code);
		}

		public boolean OK() {
			return "0".equals(get("code").toString());
		}

		public void setResult(ResultInfo info) {
			this.remove("result");
			this.put("code", info.getCode());
			this.put("msg", info.getMsg());
		}

		public void login(UserInfo userinfo) {
			request.getSession(true).setAttribute(Constants.SESSION_USER, userinfo);
		}

		public void login(CustomerManager manager) {
			request.getSession(true).setAttribute(Constants.SESSION_MANAGER, manager);
		}

		public void logout() {
			request.getSession(true).setAttribute(Constants.SESSION_USER, null);
			request.getSession(true).removeAttribute(Constants.SESSION_USER);
		}

	}
}
