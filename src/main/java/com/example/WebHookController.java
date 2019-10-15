package com.example;

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
		return new ResponseEntity("Ayush kumar",HttpStatus.OK);
	}
	
	@GetMapping("getchallenge")
	public ResponseEntity getchallenge(){
		return new ResponseEntity(m,HttpStatus.OK);
	}
	
	@PostMapping("webhook")
	public ResponseEntity getVerifyToken(@RequestBody Object obj) {
		
		System.out.println(" getVerifyToken(-,-) ");
		
		System.out.println(obj.toString());
               return new ResponseEntity(obj,HttpStatus.OK);
		
	}

}
