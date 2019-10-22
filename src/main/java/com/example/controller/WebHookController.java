package com.example.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("facebook")
public class WebHookController {
	String m=null;
	@GetMapping("webhook")
	public ResponseEntity getVerifyToken(
			@RequestParam("hub.verify_token") String token,
			@RequestParam("hub.challenge") String challenge,
			@RequestParam("hub.mode") String mode) {
		
		String ACCESS_TOKEN= "INNOEYE";
		m=challenge;
		System.out.println("IN VERIFICATION");
		//System.out.println("token"+token+" challenge "+challenge+" mode"+mode);
		if(token!=null & ACCESS_TOKEN.equalsIgnoreCase(token)) {
			System.out.println(" VERIFICATION  SUCCESS");
			return new ResponseEntity(challenge,HttpStatus.OK);
		}
		else {
			System.out.println(" VERIFICATION  UNSUCCESS ");
			return new ResponseEntity("NOT CHALLENGE_ACCEPTED",HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("getName")
	public ResponseEntity getName(){
		return new ResponseEntity("Ayush kumar hhhgg  000",HttpStatus.OK);
	}
	
	@GetMapping("getchallenge")
	public ResponseEntity getchallenge(){
		return new ResponseEntity(m,HttpStatus.OK);
	}
	
	@PostMapping("webhook")
	public ResponseEntity getVerifyToken(@RequestBody Object obj) throws Exception {
		
      System.out.println(" getVerifyToken(-,-) ");
      System.out.println(obj.toString());
		
		try{
			demoPostRESTAPI(obj);
			
	               return new ResponseEntity(obj,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void demoPostRESTAPI(Object obj) throws Exception{
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    try {
	        HttpPost postRequest = new HttpPost("http://60.254.111.202:18091/printEventData");
	         
	        postRequest.addHeader("content-type", "application/json");
	        StringEntity params =new StringEntity(obj.toString());
	        
	        postRequest.setEntity(params);
	          
	        //Send the request; It will immediately return the response in HttpResponse object if any
	        HttpResponse response = httpClient.execute(postRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != 201){
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	    }
	    finally{
	        httpClient.getConnectionManager().shutdown();
	    }
	}

}
