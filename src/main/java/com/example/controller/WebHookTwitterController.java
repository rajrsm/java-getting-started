package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Produces;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("twitter")
public class WebHookTwitterController {
	
	
	
	@GetMapping("webhook")
	@Produces(value ="application/json")
	public Object getVerifyToken(
			@RequestParam("crc_token") String token) {
		
		System.out.println("inside methode token :  "+token);
		
		try {
		     String consumer_secret = "H6hBy75bq1CO5CribSoO5pfzwIB2T9OXCz2Bd5AbStcgxlfT1o";
		     System.out.println("token :  "+token);
		     String crc_token = token;
		     Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		     SecretKeySpec secret_key = new SecretKeySpec(consumer_secret.getBytes("UTF-8"), "HmacSHA256");
		     sha256_HMAC.init(secret_key);

		     String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(crc_token.getBytes("UTF-8")));

		     System.out.println("sha256=" + hash);
		     Map<String, String> reposne=new HashMap<>();
		     reposne.put("response_token", "sha256="+hash);
		     JSONObject json = new JSONObject();
		     
		     json.put("response_token","sha256=" +hash);
		     HttpHeaders httpHeaders = new HttpHeaders();
		     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		     
		     //String reponse="\"{\"response_token\": \"sha256="+hash+"\"}\"";
		     System.out.println("reponse  :  "+json);
//		     Twitter json response
//		     {
//		    	  "response_token": "sha256=x0mYd8hz2goCTfcNAaMqENy2BFgJJfJOb4PdvTffpwg="
//		    	}
		     return json.toString();
		    }catch (Exception e) {
		    	e.printStackTrace();
		    	return null;
			}
		
	}
	
	@PostMapping("webhook")
	public ResponseEntity getVerifyToken(HttpServletRequest request,@RequestBody Object obj) throws Exception {
		
      System.out.println(" getVerifyToken(-,-) ");
      System.out.println(obj.toString());
		
		try{
		System.out.println("response_token getVerifyToken "+request.getAttribute("response_token"));	
			Gson gson = new Gson(); 
			System.out.println("gson.toJson(obj : "+ gson.toJson(obj));
	        String json = gson.toJson(obj); 
	        JSONObject jsonObject  =new JSONObject(json);
	        System.out.println("jsonObject : "+ jsonObject);
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
	        HttpPost postRequest = new HttpPost("http://60.254.111.202/ticketManagement/unauthorize/pushTWData");
	         
	        postRequest.addHeader("content-type", "application/json");
	       // StringEntity params =new StringEntity(obj.toString());
	        Gson gson = new Gson(); 
	        String json = gson.toJson(obj); 
	        StringEntity params =new StringEntity(json);
	        postRequest.setEntity(params);
	          
	        //Send the request; It will immediately return the response in HttpResponse object if any
	        HttpResponse response = httpClient.execute(postRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != 200){
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	        System.out.println("statusCode::"+statusCode);
	    }
	    finally{
	        httpClient.getConnectionManager().shutdown();
	    }
	}


}
