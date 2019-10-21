package com.restful.web.services.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restful.web.services.exception.UserNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	UserDaoServices service;
	
	@GetMapping(path = "/users")
	public List<User> retriveAllUsers() {
		return service.listAllUser();
	}
	
	@GetMapping(path = "/users/{id}")
	public Resource<User> retriveUser(@PathVariable int id) {
		User user = service.findOneUser(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
		
		//HATEOAS
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@PostMapping(path = "/users")          //Validation
	public ResponseEntity<Object> CreateUser(@Valid @RequestBody User user) {
		User savedUser = service.saveUser(user);
		
		URI savedUserLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(savedUserLocation).build();
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable int id){
		User user = service.deleteById(id);
		
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
	}
}
