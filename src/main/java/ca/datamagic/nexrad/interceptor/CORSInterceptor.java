/**
 * 
 */
package ca.datamagic.nexrad.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Greg
 *
 */
public class CORSInterceptor extends HandlerInterceptorAdapter {
	private static Logger _logger = LogManager.getLogger(CORSInterceptor.class);
	
	public CORSInterceptor() {
	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		_logger.debug("preHandle: " + request.getMethod());
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Location");
		response.addHeader("Access-Control-Max-Age", "1728000");
        if(request.getMethod().compareToIgnoreCase("OPTIONS") == 0) {
			response.getWriter().print("OK");
            response.getWriter().flush();
            return false;
        }
        return true;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		_logger.debug("postHandle: " + request.getMethod());
		
	}
}
