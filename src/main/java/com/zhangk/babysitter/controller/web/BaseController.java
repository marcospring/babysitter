package com.zhangk.babysitter.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.utils.common.Constants;

public class BaseController {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	@Qualifier("velocityConfigurer")
	protected VelocityConfig velocity;

	protected PageResult res = new PageResult();

	protected VelocityContext velocityContext;

	public VelocityContext getVelocityContext() {
		if (velocityContext == null)
			velocityContext = new VelocityContext();
		return velocityContext;
	}

	public void setVelocityContext(VelocityContext velocityContext) {
		this.velocityContext = velocityContext;
	}

	protected void addContext(String name, Object value) {
		getVelocityContext().put(name, value);
	}

	protected void outputString(String name, HttpServletResponse response) {
		VelocityEngine engine = velocity.getVelocityEngine();
		Template template = engine.getTemplate(name);
		PrintWriter writer = null;
		try {
			StringWriter stringWrite = new StringWriter();
			template.merge(getVelocityContext(), stringWrite);
			writer = response.getWriter();
			writer.print(stringWrite.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}
	}

	protected PageResult getErrRes() {
		return new PageResult(-1, null);
	}

	protected PageResult getErrRes(int code) {
		return new PageResult(code, null);
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
			this.put("info", info);
		}

		public PageResult(int code, String errorTextKey) {
			this.put("code", code);
			this.put("msg", errorTextKey);
		}

		public PageResult(int code) {
			this.put("code", code);
		}

		public boolean OK() {
			return "0".equals(get("code").toString());
		}

		public void login(UserInfo userinfo) {
			request.getSession(true).setAttribute(Constants.SESSION_USER,
					userinfo);
		}

		public void logout() {
			request.getSession(true).setAttribute(Constants.SESSION_USER, null);
			request.getSession(true).removeAttribute(Constants.SESSION_USER);
		}

	}
}
