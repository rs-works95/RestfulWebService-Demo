package com.restful.web.services.helloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/helloWorld")
	public String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping(path = "/hello-word-internationalized")   //@RequestHeader(name = "Accept-Language", required = false) Locale locale
	public String helloWorledInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	
}
