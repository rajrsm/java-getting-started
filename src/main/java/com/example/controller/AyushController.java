package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Information;



@RestController
@RequestMapping("/ayush")
public class AyushController {

	static {
		System.out.println(" Loaded");
	}
	{System.out.println(" Object created"); info.setName("Ayush Kumar");
	info.setAddress("Indore");list.add(info);
	System.out.println(list.toString());
	}
	
	static Information info = new Information();
	static List<Information> list = new ArrayList<>();
	
	@GetMapping("getName")
	public ResponseEntity getName() {
		return new ResponseEntity<>(info.getName(),HttpStatus.OK);
	}
	
	@GetMapping("getAddress")
	public ResponseEntity getAddress() {
		return new ResponseEntity<>(info.getAddress(),HttpStatus.OK);
	}
	
	@PostMapping("changeName")
	public ResponseEntity changeName(@RequestParam("name") String name) {
		info.setName(name);
		return new ResponseEntity<>(info.getName(),HttpStatus.OK);
	}
	
	@GetMapping("changeAddress/{address}")
	public ResponseEntity changeAddress(@PathVariable("address") String address) {
		info.setAddress(address);
		return new ResponseEntity<>(info.getAddress(),HttpStatus.OK);
	}
	
	@PostMapping("addUser")
	public ResponseEntity addUser(@RequestBody Information in) {
		list.add(in);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
