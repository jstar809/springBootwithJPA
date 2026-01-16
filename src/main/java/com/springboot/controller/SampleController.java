package com.springboot.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
	
	Logger log = LogManager.getLogger();
	
	@GetMapping("/hello")
	public void hello(Model model) {
		log.info("hello");
		
		model.addAttribute("msg" , "hellowordl");
	}
	
	@GetMapping("/ex/ex1")
	public void ex1(Model model) {
		model.addAttribute("list" , Arrays.asList("a" ,"tb" , "tc" ,"td" ,"te","tf"));
	}
	
	class SampleDTO{
		private String p1,p2,p3;
		
		public String getP1() {
			return p1;
		}
		public String getP2() {
			return p2;
		}
		
		public String getP3() {
			return p3;
		}
	}
	
	@GetMapping("/ex/ex2")
	public void ex2(Model model) {
		log.info("              ex2 ");
		
		List<String> strList = IntStream.range(1, 10).mapToObj(i -> "data"+i).collect(Collectors.toList());
		
		model.addAttribute("list" , strList);
		
		Map<String ,String > map = new HashMap<>();
		
		map.put("a", "aaaa");
		map.put("b", "bbbb");
		
		model.addAttribute("map" , map);
		
		
		SampleDTO sampleDTO = new SampleDTO();
		
		sampleDTO.p1 ="va-1";
		sampleDTO.p2 ="va-2";
		sampleDTO.p3 ="va-3";
		
		model.addAttribute("dto" , sampleDTO);
		
	}
	
	@GetMapping("/ex/ex3")
	public void ex3(Model model) {
		log.info("              ex2 ");
		
		List<String> strList = IntStream.range(1, 10).mapToObj(i -> "data"+i).collect(Collectors.toList());
		
		model.addAttribute("list" , strList);
		
		Map<String ,String > map = new HashMap<>();
		
		map.put("a", "aaaa");
		map.put("b", "bbbb");
		
		model.addAttribute("map" , map);
		
		
		SampleDTO sampleDTO = new SampleDTO();
		
		sampleDTO.p1 ="va-1";
		sampleDTO.p2 ="va-2";
		sampleDTO.p3 ="va-3";
		
		model.addAttribute("dto" , sampleDTO);
		
	}
	
}
