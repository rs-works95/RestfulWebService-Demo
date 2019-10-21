package com.restful.web.services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	SimpleBeanPropertyFilter filter=null;
	
	@GetMapping(path = "/filtering")
	public MappingJacksonValue retriveSomeBean() {
		SomeBean someBean = new SomeBean("Value1", "Value2", "Value3");
		
		filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		
		return doFilter(filter, new MappingJacksonValue(someBean));
	}
	
	@GetMapping(path = "/filtering-list")
	public MappingJacksonValue retriveListOfSomeBean() {
		List<SomeBean> list =  Arrays.asList(new SomeBean("Value1", "Value2", "Value3"),
				new SomeBean("Value12", "Value22", "Value32"));

		filter= SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		
		return doFilter(filter, new MappingJacksonValue(list));
	}
	
	public MappingJacksonValue doFilter(SimpleBeanPropertyFilter filter, MappingJacksonValue mapping) {
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
//		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		
		mapping.setFilters(filters);
		
		return mapping;
	}
}
