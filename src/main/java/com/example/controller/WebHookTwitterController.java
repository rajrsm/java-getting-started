package com.example.controller;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Produces;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public ResponseEntity getVerifyToken(HttpServletRequest request,HttpServletResponse res) throws Exception {
		
      System.out.println("inside methode  twitter data");
      try{
	      		/*
		 * Gson gson = new Gson(); System.out.println("gson.toJson(obj : "+
		 * gson.toJson(obj));
		
	        String json = gson.toJson(obj);  */
	      	String json=getRequestBody(request);
	      	System.out.println("getRequestBody json : "+ json);
	        System.out.println("isValidSignature : "+isValidSignature(request,res,json));
	        JSONObject jsonObject  =new JSONObject(json);
	        System.out.println("jsonObject : "+ jsonObject);
			//demoPostRESTAPI(obj);
		        return new ResponseEntity(obj,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	private Boolean isValidSignature(HttpServletRequest request, HttpServletResponse res,String body) throws NoSuchAlgorithmException, InvalidKeyException {
		String consumer_secret = "H6hBy75bq1CO5CribSoO5pfzwIB2T9OXCz2Bd5AbStcgxlfT1o";
		String signature = request.getHeader("X-Twitter-Webhooks-Signature");
		String signature2 = request.getHeader("x-twitter-webhooks-signature");
		Mac sha256HMAC = Mac.getInstance("HmacSHA256");
	    	SecretKeySpec secretKey = new SecretKeySpec(consumer_secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
	    	sha256HMAC.init(secretKey);
	        String digest = "sha256="+Base64.encodeBase64String(sha256HMAC.doFinal(body.getBytes(StandardCharsets.UTF_8)));
	    	System.out.println("signature : "+signature);
	    	System.out.println("signature2 : "+signature2);
	    	System.out.println("digest : "+digest);
	    	return digest.equals(signature);
	}
	
	private String getRequestBody(final HttpServletRequest request) {
        final StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = request.getReader()) {
            if (reader == null) {
            	System.out.println("Request body could not be read because it's empty.");
                return null;
            }
            String line;
            while ((line = reader.readLine()) != null) {
            	sb.append(line);
            }
            System.out.println("getRequestBody  "+ sb.toString());
            return sb.toString();
        } catch (final Exception e) {
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
	    }	/*
	 * @Override public void callbackDenied(HttpServletRequest request,
	 * HttpServletResponse response, String token) { log.
	 * trace("Inside @class TWLoginServiceImpl @method callbackDenied token {} ",
	 * token); try { String redirctUrl =
	 * request.getSession().getAttribute("integrationUrl").toString();
	 * log.info("callbackDenied  redirctUrl : {}", redirctUrl);
	 * response.sendRedirect(redirctUrl); } catch (Exception e) {
	 * log.error("Exception @class TWLoginServiceImpl @method callbackDenied  {}" ,
	 * e); }
	 * ystem.out.println("isValidSignature : "+isValidSignature(request,res));
91
                        Gson gson = new Gson(); 
92
                        System.out.println("gson.toJson(obj : "+ gson.toJson(obj));
93
                String json = gson.toJson(obj); 
94
                JSONObject jsonObject  =new JSONObject(json);
95
                System.out.println("jsonObject : "+ jsonObject);
96
                        demoPostRESTAPI(obj);ystem.out.println("isValidSignature : "+isValidSignature(request,res));
91
                        Gson gson = new Gson(); 
92
                        System.out.println("gson.toJson(obj : "+ gson.toJson(obj));
93
                String json = gson.toJson(obj); 
94
                JSONObject jsonObject  =new JSONObject(json);
95
                System.out.println("jsonObject : "+ jsonObject);
96
                        demoPostRESTAPI(obj);


	 * }
	 */
	    finally{
	        httpClient.getConnectionManager().shutdown();
	    }
	}


}
