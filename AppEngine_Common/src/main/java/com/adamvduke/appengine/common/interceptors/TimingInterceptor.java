package com.adamvduke.appengine.common.interceptors;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Creates an interceptor that logs the name of the method being invoked, it's arguments, and the
 * elapsed time to execute the invocation
 * 
 * @author adamd
 */
public class TimingInterceptor implements MethodInterceptor {

	private static final Logger logger = Logger.getLogger( TimingInterceptor.class.getName() );

	@Override
	public Object invoke( MethodInvocation invocation ) throws Throwable {

		long start = System.nanoTime();

		try {
			return invocation.proceed();
		}
		finally {
			String formatString = "Invocation of method %s() with parameters %s took %.1f ms.";
			String methodName = invocation.getMethod().getName();
			String arguments = Arrays.toString( invocation.getArguments() );
			double stop = ( System.nanoTime() - start ) / 1000000.0;
			logger.log( Level.FINE, String.format( formatString, methodName, arguments, stop ) );
		}
	}
}
