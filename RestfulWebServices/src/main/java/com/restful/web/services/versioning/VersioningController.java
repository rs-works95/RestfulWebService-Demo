package com.restful.web.services.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningController {
	
	//versioning using URI's
	@GetMapping(path = "/v1/persion")
	public persionV1 persionV1() {
		return new persionV1("Ram Sadhu");
	}
	
	@GetMapping(path = "v2/persion")
	public persionV2 persionV2() {
		return new persionV2(new Name("Ram", "Sadhu"));
	}
	
	//versioning using Request Param
	@GetMapping(path = "/persion/param", params = "version=1")
	public persionV1 paramV1() {
		return new persionV1("Ram Sadhu");
	}
	
	@GetMapping(path = "/persion/param", params = "version=2")
	public persionV2 paramV2() {
		return new persionV2(new Name("Ram", "Sadhu"));
	}
	
	//versioning using Request Header
	@GetMapping(path = "/persion/header", headers = "X-API-VERSION=1")
	public persionV1 headerV1() {
		return new persionV1("Ram Sadhu");
	}
		
	@GetMapping(path = "/persion/header", headers = "X-API-VERSION=2")
	public persionV2 headerV2() {
		return new persionV2(new Name("Ram", "Sadhu"));
	}
	
	//versioning using Produces
	@GetMapping(path = "/persion/produces", produces = "application/vnd.comapany.app-v1+json")
	public persionV1 producesV1() {
		return new persionV1("Ram Sadhu");
	}
			
	@GetMapping(path = "/persion/produces", produces = "application/vnd.comapany.app-v2+json")
	public persionV2 producesV2() {
		return new persionV2(new Name("Ram", "Sadhu"));
	}
}
