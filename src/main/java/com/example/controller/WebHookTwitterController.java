package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("twitter")
public class WebHookTwitterController {
	
	
	
	@GetMapping("webhook")
	public ResponseEntity getVerifyToken(
			@RequestParam("crc_token") String token) {
		
		try {
		     String consumer_secret = "a8Y7Ef80voX2GfR7AvJjK0P4mo061ftSFtm2xsLrUTTzwXCZIO";
		     String crc_token = "Message";

		     Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		     SecretKeySpec secret_key = new SecretKeySpec(consumer_secret.getBytes("UTF-8"), "HmacSHA256");
		     sha256_HMAC.init(secret_key);

		     String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(crc_token.getBytes("UTF-8")));

		     System.out.println("sha256=" + hash);
		     Map<String, String> reposne=new HashMap<>();
		     reposne.put("response_token", hash);
//		     Twitter json response
//		     {
//		    	  "response_token": "sha256=x0mYd8hz2goCTfcNAaMqENy2BFgJJfJOb4PdvTffpwg="
//		    	}
		     return null;
		    }catch (Exception e) {
		    	e.printStackTrace();
		    	return null;
			}
		
	}


}