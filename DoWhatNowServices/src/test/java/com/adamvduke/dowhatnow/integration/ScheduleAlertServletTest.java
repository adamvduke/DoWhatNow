//package com.adamvduke.dowhatnow.integration;
//
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//
//import com.adamvduke.dowhatnow.servlet.alert.ScheduleAlertServlet;
//
//@SuppressWarnings( "all" )
//public class ScheduleAlertServletTest {
//
//	/** Servlet under test. */
//	private ScheduleAlertServlet servlet;
//
//	/** Mock request. */
//	private HttpServletRequest request;
//
//	/** Mock response. */
//	private HttpServletResponse response;
//
//	/** Session's attribute map. */
//	private Map attributes;
//
//	/** Request's parameter map. */
//	private Map parameters;
//
//	/**
//	 * Launches Mockito configuration from annotations.
//	 */
//	@Before
//	public void setUp() throws Exception {
//
//		attributes = new HashMap();
//		parameters = new HashMap();
//
//		servlet = new ScheduleAlertServlet();
//		request = mock( HttpServletRequest.class );
//		response = mock( HttpServletResponse.class );
//
//		when( request.getMethod() ).thenReturn( "POST" );
//		when( request.getParameterMap() ).thenReturn( parameters );
//		when( response.getWriter() ).thenReturn( new PrintWriter( new StringWriter() ) );
//		when( request.getParameter( anyString() ) ).thenAnswer( new Answer() {
//
//			/**
//			 * @see org.mockito.stubbing.Answer#answer(org.mockito.invocation.InvocationOnMock)
//			 */
//			@Override
//			public Object answer( InvocationOnMock aInvocation ) throws Throwable {
//
//				String key = (String) aInvocation.getArguments()[0];
//
//				return parameters.get( key );
//			}
//		} );
//
//		// when( session.getAttribute( anyString() ) ).thenAnswer( new Answer() {
//		//
//		// /**
//		// * @see org.mockito.stubbing.Answer#answer(org.mockito.invocation.InvocationOnMock)
//		// */
//		// @Override
//		// public Object answer( InvocationOnMock aInvocation ) throws Throwable {
//		//
//		// String key = (String) aInvocation.getArguments()[0];
//		//
//		// return attributes.get( key );
//		// }
//		// } );
//		//
//		// Mockito.doAnswer( new Answer() {
//		//
//		// /**
//		// * @see org.mockito.stubbing.Answer#answer(org.mockito.invocation.InvocationOnMock)
//		// */
//		// @Override
//		// public Object answer( InvocationOnMock aInvocation ) throws Throwable {
//		//
//		// String key = (String) aInvocation.getArguments()[0];
//		// Object value = aInvocation.getArguments()[1];
//		// attributes.put( key, value );
//		//
//		// return null;
//		// }
//		//
//		// } ).when( session ).setAttribute( anyString(), anyObject() );
//	}
//
//	/**
//	 * Test method for {@link SessionServlet#doGet(HttpServletRequest, HttpServletResponse)} .
//	 * 
//	 * @throws IOException
//	 * @throws ServletException
//	 */
//	@Test
//	public void testDoPost() throws ServletException, IOException {
//
//		parameters.put( "action", "add" );
//		servlet.doPost( request, response );
//	}
// }
