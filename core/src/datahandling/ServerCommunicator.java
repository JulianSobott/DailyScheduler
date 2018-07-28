package datahandling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

import datahandling.http.PostAction;
import datahandling.http.Response;
import datahandling.http.Status;
import utils.Logger;

public class ServerCommunicator implements HttpResponseListener{
	
	public enum Action{
		save
	}
	
	private String URL = "http://daily_scheduler.com/saver.php";
	private Response t_resp = new Response();
	private boolean requestHandled = false;
	
	public ServerCommunicator() {
		
		
	}

	public Response post(PostAction action, String value) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpMethods.POST);
		httpRequest.setUrl(URL);
		httpRequest.setContent(action.action_string+ "=" + value );
			
		Gdx.net.sendHttpRequest(httpRequest, this);	
		
		while(!requestHandled) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return t_resp;
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		t_resp.message = httpResponse.getResultAsString();
		t_resp.status = Status.successfully;
		requestHandled = true;
	}

	@Override
	public void failed(Throwable t) {
		//t.printStackTrace();
		t_resp.message = t.getMessage();
		t_resp.status = Status.failed;
		requestHandled = true;
	}

	@Override
	public void cancelled() {
		Logger.log("Http post canceled", Logger.warning | Logger.datahandling);
		t_resp.message = "HTTP response canceled";
		t_resp.status = Status.failed;
		requestHandled = true;
	}
}
