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
			
			result = new User(soapUsername, soapPassword);
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
	 * @see battleship.common.BattleshipSystem#setClientSystemSetting(battleship.common.ClientSystemSettings)
	 */
	public void setClientSystemSetting(ClientSystemSettings settings)
			throws SoapFault {
		String METHOD_NAME = "setClientSystemSettings";
		executeSoapAction(METHOD_NAME, settings);

	}
	
	/*
	 * (non-Javadoc)
	 * @see battleship.common.BattleshipSystem#setClientSystemSettings(boolean, int)
	 */
	public void setClientSystemSettings(boolean savePasswordUsername,
			int boardlength) throws SoapFault {
		String METHOD_NAME = "setClientSystemSettings";
		executeSoapAction(METHOD_NAME, savePasswordUsername, boardlength);
	}
	
	/*
	 * (non-Javadoc)
	 * @see battleship.common.BattleshipSystem#getClientSystemSettings()
	 */
	public ClientSystemSettings getClientSystemSettings() throws SoapFault {
		String METHOD_NAME = "getClientSystemSettings";
		Object result = executeSoapAction(METHOD_NAME);
		return (ClientSystemSettings) result;
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
	 * @see battleship.common.BattleshipSystem#getHighscore()
	 */
	public Highscore getHighscore() throws SoapFault {
		String METHOD_NAME = "getHighscore";
		return (Highscore)executeSoapAction(METHOD_NAME);
	}
	/*
	 * (non-Javadoc)
	 * @see de.wifhm.se1.android.common.BattleshipSystem#getHighscoreList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Highscore> getHighscoreList() throws SoapFault {
		String METHOD_NAME = "getHighscoreList";
		return (List<Highscore>)executeSoapAction(METHOD_NAME);
	}
	

	@Override
	public void setGameState(String gamestate) throws SoapFault {
		String METHOD_NAME = "setGameState";
		executeSoapAction(METHOD_NAME, gamestate);		
	}

	@Override
	public String getGameState() throws SoapFault {
		String METHOD_NAME = "getGameState";
		return (String) executeSoapAction(METHOD_NAME);
	}

	@Override
	public void addPoints(int points) throws SoapFault {
		String METHOD_NAME = "addPoints";
		executeSoapAction(METHOD_NAME, points);
	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 * @throws SoapFault
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

	
}
