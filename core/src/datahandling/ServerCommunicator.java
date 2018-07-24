package datahandling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import datahandling.http.PostAction;
import datahandling.http.Response;
import datahandling.http.Status;

public class ServerCommunicator {
	
	public enum Action{
		save
	}
	
	private String URL = "http://daily_scheduler.com/saver.php";
	private HttpClient client = HttpClients.createDefault();
	private HttpPost post = new HttpPost(URL);
	
	private HttpResponse response = null;
	private HttpEntity entity;
	
	public ServerCommunicator() {
		
		
	}

	public Response post(PostAction action, String value) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(action.action_string, value));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		
		Response t_resp = new Response();
		try {
			response = client.execute(post);	
			entity = response.getEntity();			
			t_resp.message = EntityUtils.toString(response.getEntity());
			if( response.getStatusLine().toString() != "500") {
				t_resp.status = Status.successfully;
			}
			else {
				t_resp.status = Status.failed;
			}
		} catch (ClientProtocolException e) { 
			t_resp.status = Status.server_offline;
		} catch (IOException e) {
			t_resp.status = Status.server_offline;
		}
		return t_resp;
	}
}
