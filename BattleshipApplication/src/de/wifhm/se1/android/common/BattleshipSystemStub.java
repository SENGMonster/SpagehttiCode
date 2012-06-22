package de.wifhm.se1.android.common;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import de.wifhm.se1.android.util.HttpHelper;
/**
 * 
 * @author Holger Tenbeitel
 *
 *BattleShipSystem implementiert das BattleshipSyst CLientseitig
 */
public class BattleshipSystemStub implements BattleshipSystem {

	private static final String NAMESPACE = "http://server.battleship.se1.wifhm.de/";
	
	private static final String URL = "http://10.0.2.2:8080/BattleshipSystem/BattleshipSystemWebservice";
	
	private static final String TAG = BattleshipSystemStub.class.getName();
	
	private String sessionId;
	
	/*
	 * (non-Javadoc)
	 * @see battleship.common.BattleshipSystem#login(java.lang.String, java.lang.String)
	 */
	public User login(String username, String password) throws SoapFault {
	
		User result = null;
		String METHOD_NAME = "login";
		
		Object response = executeSoapAction(METHOD_NAME, username, password);
		
		
		if(response instanceof SoapObject){
			SoapObject soapUser = (SoapObject)response;
			String soapUsername = soapUser.getPropertyAsString("username");
			String soapPassword = soapUser.getPropertyAsString("password");
			String soapPlayerGameState = soapUser.getPropertyAsString("playerGameState");
			String soapAgentGameState = soapUser.getPropertyAsString("agentGameState");
			int soapHighscore = new Integer(soapUser.getPropertyAsString("highscore"));
			
			result = new User(soapUsername, soapPassword);
			result.setPlayerGameState(soapPlayerGameState);
			result.setAgentGameState(soapAgentGameState);
			result.setHighscore(soapHighscore);
		}
		
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see battleship.common.BattleshipSystem#logout()
	 */
	public void logout() throws SoapFault {
		String METHOD_NAME = "logout";
		executeSoapAction(METHOD_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see battleship.common.BattleshipSystem#register(java.lang.String, java.lang.String)
	 */
	public void register(String username, String password) throws SoapFault {
		String METHOD_NAME = "register";
		executeSoapAction(METHOD_NAME, username, password);
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see battleship.common.BattleshipSystem#setHighscore(int)
	 */
	public void setHighscore(int points) throws SoapFault {
		String METHOD_NAME = "setHighscore";
		executeSoapAction(METHOD_NAME, points);

	}
	
	
	/*
	 * (non-Javadoc)
	 * @see de.wifhm.se1.android.common.BattleshipSystem#getHighscoreList()
	 */
	@Override
	public List<User> getHighscoreList() throws SoapFault {
		String METHOD_NAME = "getHighscoreList";
		List<User> list = new ArrayList<User>();
		Object o = executeSoapAction(METHOD_NAME);
		if(o instanceof SoapObject){
			SoapObject response = (SoapObject) executeSoapAction(METHOD_NAME);
			
			 final int intPropertyCount = response.getPropertyCount();

		        for (int i = 0; i < intPropertyCount; i++) {
		        	Object object = response.getProperty(i);
		        	if(object instanceof SoapObject){
		        		SoapObject responseChild = (SoapObject) object;
		                
		                User tempObj = new User();

		                if (responseChild.hasProperty("username")) {
		                    tempObj.setUsername(responseChild.getPropertyAsString("username"));
		                }
		                if (responseChild.hasProperty("password")) {
		                    tempObj.setPassword(responseChild.getPropertyAsString("password"));
		                }

		               
		                if (responseChild.hasProperty("highscore")) {
		                    tempObj.setHighscore(new Integer(responseChild.getPropertyAsString("highscore")));
		                }

		                list.add(tempObj);	
		        	}
		        }
		

       
        	
        }

   
		
		return list;
	}
	
	@Override
	public void addPoints(int points) throws SoapFault {
		// TODO Auto-generated method stub
		String METHOD_NAME = "addPoints";
		executeSoapAction(METHOD_NAME, points);
	}

	@Override
	public void setPlayerGameState(String playergamestate) throws SoapFault {
		// TODO Auto-generated method stub
		String METHOD_NAME = "setPlayerGameState";
		executeSoapAction(METHOD_NAME, playergamestate);
	}

	@Override
	public String getPlayerGameState() throws SoapFault {
		// TODO Auto-generated method stub
		String METHOD_NAME = "getPlayerGameState";
		return (String) executeSoapAction(METHOD_NAME);
	}

	@Override
	public void setAgentGameState(String agentgamestate) throws SoapFault {
		// TODO Auto-generated method stub
		String METHOD_NAME = "setAgentGameState";
		executeSoapAction(METHOD_NAME, agentgamestate);
	}

	@Override
	public String getAgentGameState() throws SoapFault {
		// TODO Auto-generated method stub
		String METHOD_NAME = "getAgentGameState";
		return (String) executeSoapAction(METHOD_NAME);
	}

	


	/**
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 * @throws SoapFault
	 * 
	 * Methode spricht den Webservice des BattleshipSystems an und sendet den Methoden-Name plus die zugehörigen Parameter. Die Methode erhält von dem Server
	 * ein Objekt zurück welches den Rückgabe-Wert der entsprechenden Anfrage beinhalten
	 */
	private Object executeSoapAction(String methodName, Object... args) throws SoapFault {
		Object result = null;
		
		SoapObject request = new SoapObject(NAMESPACE, methodName);
		
		for(int i = 0; i < args.length; i++){
			Log.i(TAG, ""+args[i].toString());
			request.addPropertyIfValue("arg" + i, args[i]);
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		
		List<HeaderProperty> reqHeaders = null;
		if(this.sessionId != null){
			
			reqHeaders = new ArrayList<HeaderProperty>();
			reqHeaders.add(HttpHelper.getSessionIdHeader(this.sessionId));			
		}
		
		try{
			@SuppressWarnings("unchecked")
			List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE + methodName, envelope, reqHeaders);
			Log.i(TAG, ""+respHeaders.size());
			
			String httpSession = HttpHelper.readJSessionId(respHeaders);
			
			if(httpSession != null){
				this.sessionId = httpSession;
			}
			
			result = envelope.getResponse();
		}
		catch(SoapFault e){
			throw e;
		} catch (IOException e) {
			Log.e(TAG,"IOException: "+ e.toString());
		} catch (XmlPullParserException e) {
			Log.e(TAG,"XmlPullParserException: "+ e.toString());
		}
		return result;
		
	}
	
	/**
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 * @throws SoapFault
	 * 
	 * Methode spricht den Webservice des BattleshipSystems an und sendet den Methoden-Name plus die zugehörigen Parameter. Die Methode erhält von dem Server
	 * ein Objekt zurück welches die Rückgabe-Werte, als Liste, der entsprechenden Anfrage beinhalten
	 */
	private Object executeSoapActionList(String methodName, Object... args) throws SoapFault {
		Object result = null;
		
		SoapObject request = new SoapObject(NAMESPACE, methodName);
		
		for(int i = 0; i < args.length; i++){
			Log.i(TAG, ""+args[i].toString());
			request.addPropertyIfValue("arg" + i, args[i]);
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		
		List<HeaderProperty> reqHeaders = null;
		if(this.sessionId != null){
			
			reqHeaders = new ArrayList<HeaderProperty>();
			reqHeaders.add(HttpHelper.getSessionIdHeader(this.sessionId));			
		}
		
		try{
			@SuppressWarnings("unchecked")
			List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE + methodName, envelope, reqHeaders);
			Log.i(TAG, ""+respHeaders.size());
			
			String httpSession = HttpHelper.readJSessionId(respHeaders);
			
			if(httpSession != null){
				this.sessionId = httpSession;
			}
			
			result = envelope.bodyIn;
		}
		catch(SoapFault e){
			throw e;
		} catch (IOException e) {
			Log.e(TAG,"IOException: "+ e.toString());
		} catch (XmlPullParserException e) {
			Log.e(TAG,"XmlPullParserException: "+ e.toString());
		}
		return result;
		
	}

	

	
}
