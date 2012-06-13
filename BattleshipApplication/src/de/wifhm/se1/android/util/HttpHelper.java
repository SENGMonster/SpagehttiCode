package de.wifhm.se1.android.util;

import java.util.List;

import org.ksoap2.HeaderProperty;

public class HttpHelper {
	
	private static final String JSESSIONID = "JSESSIONID=";
	
	public static String readJSessionId(List<HeaderProperty> responseHeaders){
		String result = null; 
		if(responseHeaders != null){
			for(HeaderProperty hp : responseHeaders){
				if(hp.getKey().equalsIgnoreCase("set-cookie")){
					String cookie = hp.getValue();
					int startIndex = cookie.indexOf(JSESSIONID);
					if(startIndex >= 0){
						int endIndex = cookie.indexOf(";", startIndex);
						if(endIndex < 0){
							endIndex = cookie.length();
						}
						result = cookie.substring(startIndex + JSESSIONID.length(), endIndex);
						break;
					}
				}
			}
		}
		return result;
	}
	
	public static HeaderProperty getSessionIdHeader(String sessionId){
		HeaderProperty result = null;
		if(sessionId != null){
			result = new HeaderProperty("cookie", JSESSIONID + sessionId);
		}
		return result;
	}

}
