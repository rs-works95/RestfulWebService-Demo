package com.restful.web.services.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJpaResource {
	
//	@Autowired
//	UserDaoServices service;

	@Autowired
	UserReposetory userReposetory;
	
	@Autowired
	PostReposetory postReposetory;
	
	@GetMapping(path = "/jpa/users")
	public List<User> retriveAllUsers() {
		return userReposetory.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	public Resource<User> retriveUser(@PathVariable int id) {
		Optional<User> user = userReposetory.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		//HATEOAS
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@PostMapping(path = "/jpa/users")          //Validation
	public ResponseEntity<Object> CreateUser(@Valid @RequestBody User user) {
		User savedUser = userReposetory.save(user);
		
		URI savedUserLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(savedUserLocation).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
//		User user = service.deleteById(id);
		
		userReposetory.deleteById(id);
		
//		if(user == null) {
//			throw new UserNotFoundException("id-"+id);
//		}
	}
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> retriveAllPostsForUser(@PathVariable int id) {
		Optional<User> userOptional = userReposetory.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		return userOptional.get().getPost();
	}
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userReposetory.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		User user = userOptional.get();
		post.setUser(user);
		
		postReposetory.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
