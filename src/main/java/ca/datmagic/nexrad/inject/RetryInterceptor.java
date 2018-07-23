/**
 * 
 */
package ca.datmagic.nexrad.inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Greg
 *
 */
public class RetryInterceptor implements MethodInterceptor {
	private static Logger _logger = LogManager.getLogger(RetryInterceptor.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		_logger.debug("invoke");
		int tries = 0;
		try {
			return invocation.proceed();
		} catch (Throwable t) {
			if (++tries > 5) {
				throw new Throwable("Exception", t);
			}
			_logger.warn("Exception", t);
		}
		return null;
	}
}
