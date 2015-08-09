package com.zhangk.babysitter.utils.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zhangk.babysitter.entity.UserInfo;
import com.zhangk.babysitter.utils.common.Constants;

public class SessionFilter extends OncePerRequestFilter {

	private static final String AJAX_HEADER = "XMLHttpRequest";
	private Logger logger = LoggerFactory.getLogger(SessionFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String[] notFilter = new String[] { "login.html", "dataProvider",
				"error", "mobile", "file", "wechat" };
		String uri = request.getRequestURI();
		boolean doFilter = true;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1) {
				doFilter = false;
				break;
			}
		}
		if (doFilter) {
			UserInfo user = (UserInfo) request.getSession().getAttribute(
					Constants.SESSION_USER);
			if (user == null) {
				boolean isAjaxRequest = isAjaxRequest(request);
				if (isAjaxRequest) {
					logger.info("Ajax：{}请求没有登陆。", uri);
					response.setCharacterEncoding("UTF-8");
					String msg = "{\"msg\":\"您还没有登陆\",\"code\":302}";
					PrintWriter out = response.getWriter();
					out.print(msg);
				}
				logger.info("{}请求没有登陆。", uri);
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && AJAX_HEADER.equals(header)) {
			return true;
		}
		return false;
	}
}
