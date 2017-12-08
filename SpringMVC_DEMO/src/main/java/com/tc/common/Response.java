package com.tc.common;


public class Response {

	public static final int STATE_SUCCESS = 0;
	public static final int STATE_APP_EXCEPTION = 1;
	public static final int STATE_EXCEPTION = 2;
	public static final int STATE_NO_SESSION = 3;

	public static Response noSession = new Response(null, STATE_NO_SESSION);

	private Object result;

	private int state;

	public Response() {
	}
	
	public Response(Object result, int state) {
		this.result = result;
		this.state = state;
	}
	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
