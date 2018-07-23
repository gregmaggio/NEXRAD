/**
 * 
 */
package ca.datmagic.nexrad.inject;

import java.util.Calendar;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 * @author Greg
 *
 */
public class PerformanceInterceptor implements MethodInterceptor {
	private static Logger _logger = LogManager.getLogger(PerformanceInterceptor.class);
	
	private static String getKey(MethodInvocation invocation) {
		StringBuffer key = new StringBuffer();
		key.append(invocation.getThis().getClass().getName());		
		key.append(".");
		key.append(invocation.getMethod().getName());
		key.append("(");
		Object[] arguments = invocation.getArguments();
		if (arguments != null) {
			for (int ii = 0; ii < arguments.length; ii++) {
				if (ii > 0) {
					key.append(",");
				}
				key.append(arguments[ii].toString());
			}
		}
		key.append(")");
		return key.toString();
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Calendar startTime = Calendar.getInstance();
		try {
			return invocation.proceed();
		} finally {
			Calendar endTime = Calendar.getInstance();
			Interval interval = new Interval(startTime.getTimeInMillis(), endTime.getTimeInMillis());
			Duration duration = interval.toDuration();
			String key = getKey(invocation);
			_logger.debug(key + " took " + duration);
		}
	}
}
