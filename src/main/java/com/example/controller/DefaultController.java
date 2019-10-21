package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/")
public class DefaultController {


	@RequestMapping("/")
	public ResponseEntity getName() {
		List<String> strings = new ArrayList<String>();
		strings.add("Application is Running well");
		strings.add("Some URL as");
		strings.add("/ayush/getName");
		strings.add("/ayush/getAddress");
		strings.add("/facebook/webhook");
		return new ResponseEntity<>(strings,HttpStatus.OK);
	}
}
