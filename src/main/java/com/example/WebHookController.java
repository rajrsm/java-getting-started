package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		//System.out.println("token"+token+" challenge "+challenge+" mode"+mode);
		if(token!=null & ACCESS_TOKEN.equalsIgnoreCase(token)) {
			return new ResponseEntity(challenge,HttpStatus.OK);
		}
		else {
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

}
