package com.ego.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbUser;

/**
 * Login拦截器
 * @author Bernard
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse rsp, Object obj) throws Exception {
		//获取Cookie中的token
		String token = CookieUtils.getCookieValue(req, "eToken", false);
		if (token!=null && !token.equals("")) {
			//获取Redis键值对数据
			String json = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
			if (json!=null && !json.equals("")) {
				EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
				if (er.getStatus()==200) {
					return true;
				}
			}
		}
		
		//获取req的num参数
		String num = req.getParameter("num");
		//重定向到登录页面，携带interurl参数
		rsp.sendRedirect("http://localhost:8084/user/showLogin?interurl="+req.getRequestURL()+"%3Fnum="+num);
		
		return false;
	}

}
