package com.adamvduke.dowhatnow.config.guice;

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AnyInvocationInterceptor implements MethodInterceptor {

	@Override
	public Object invoke( MethodInvocation invocation ) throws Throwable {

		long start = System.nanoTime();

		try {
			return invocation.proceed();
		}
		finally {
			System.out.println( String.format( "Invocation of method %s() with parameters %s took %.1f ms.", invocation.getMethod().getName(), Arrays.toString( invocation.getArguments() ),
					( System.nanoTime() - start ) / 1000000.0 ) );
		}
	}
}
