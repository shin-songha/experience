package com.study.experience.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleApiController {

	@GetMapping(value = "/sample-swagger-test", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> sampleSwaggerTest() {
		Map<String, String> map = new HashMap<>();
		map.put("sampleKey", "sampleValue");
		return map;
	}
}
