
package com.kaonykk.financetutorapi.exception;


public class LimitExceededException extends RuntimeException {
	public LimitExceededException(String message){
		super(message);
	}
}
