package de.wifhm.se1.android.util;

import java.util.List;

import org.ksoap2.HeaderProperty;

import android.util.Log;

public class HttpHelper {
	
	private static final String TAG = "HTTP-Helper";
	
	private static final String JSESSIONID = "JSESSIONID=";

	/**
	 * Diese Methode liefert aus einer Liste von HeaderProperties, wie sie im Response eines SOAP-Requests geliefert werden,
	 * den Wert von JSESSIONID, sofern einer vorhanden ist.
	 * @param responseHeaders
	 * @return
	 */
	public static String readJSessionId(List<HeaderProperty> responseHeaders) {
    	String result = null;
		if (responseHeaders != null) {
			for(HeaderProperty hp : responseHeaders) {
	    		if (hp.getKey().equalsIgnoreCase("set-cookie")) {
	    			String cookie = hp.getValue();
	    			int startIndex = cookie.indexOf(JSESSIONID);
	    			if (startIndex >= 0) {
	    				int endIndex = cookie.indexOf(";", startIndex);
	    				if (endIndex < 0) { endIndex = cookie.length(); }
		    			result = cookie.substring(startIndex + JSESSIONID.length(), endIndex);
		    			break;
	    			}
	    		}
	    	}
    	}
		Log.d(TAG, " "+result);
		return result;
	}
	
	/**
	 * Erzeugt einen Header-Eintrag mit dem übergebenen String als JSESSIONID.
	 * @param sessionId
	 * @return
	 */
	public static HeaderProperty getSessionIdHeader(String sessionId) {
		HeaderProperty result = null;
		if (sessionId != null) {
			result = new HeaderProperty("cookie", JSESSIONID + sessionId);
		}
		return result;
	}
	
	
}