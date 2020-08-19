package ca.ised.sts.integration.exception;


import org.junit.Test;

public class EndpointExceptionTest {

	// Test constructors
	@Test
	public void EndpointExceptionConstructorTest() {
		
		String message = "Test exception message";
		EndpointException ex = new EndpointException(message);
		assert(ex.getMessage().contains(message));
		
		ex = new EndpointException(message, new Exception());
		assert(ex.getMessage().contains(message));
	}
	
}
