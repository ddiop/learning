package com.diop.learning.rest;

/**
 * Connected Manager 
 * @author djibi
 *
 */
public class ConnectedManager {

	public static final ThreadLocal customerThreadLocal = new ThreadLocal();
	
	public static void set(Context user) {
		customerThreadLocal.set(user);
	}

	public static void clear() {
		customerThreadLocal.remove();
	}

	public static Context get() {
		return (Context) customerThreadLocal.get();
	}
}
