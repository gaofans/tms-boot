package com.bettem.tms.boot.base.controller;

import com.bettem.tms.boot.commons.dto.RR;
import com.bettem.tms.boot.utils.HttpKit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author GaoFans
 */
public class BaseController {

	protected static String REDIRECT = "redirect:";
	protected static String FORWARD = "forward:";

	protected static RR<Object> SUCCESS_RESULT = new RR<Object>(RR.CodeStatus.OK);

	protected static RR<Object> FAIL_RESULT = new RR<Object>(RR.CodeStatus.FAIL);

	protected HttpServletRequest getHttpServletRequest() {
		return HttpKit.getRequest();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return HttpKit.getResponse();
	}

	protected HttpSession getSession() {
		return HttpKit.getRequest().getSession();
	}

	protected HttpSession getSession(Boolean flag) {
		return HttpKit.getRequest().getSession(flag);
	}

	protected String getPara(String name) {
		return HttpKit.getRequest().getParameter(name);
	}

	protected void setAttr(String name, Object value) {
		HttpKit.getRequest().setAttribute(name, value);
	}

	/**
	 * 删除cookie
	 */
	protected void deleteCookieByName(String cookieName) {
		Cookie[] cookies = this.getHttpServletRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				Cookie temp = new Cookie(cookie.getName(), "");
				temp.setMaxAge(0);
				this.getHttpServletResponse().addCookie(temp);
			}
		}
	}

	/**
	 * 删除所有cookie
	 */
	protected void deleteAllCookie() {
		Cookie[] cookies = this.getHttpServletRequest().getCookies();
		for (Cookie cookie : cookies) {
			Cookie temp = new Cookie(cookie.getName(), "");
			temp.setMaxAge(0);
			temp.setPath("/");
			this.getHttpServletResponse().addCookie(temp);
		}
	}

}
