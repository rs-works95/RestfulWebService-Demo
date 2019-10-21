package com.restful.web.services.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoServices {
	
	private static List<User> users=new ArrayList<User>();
	private static int userCount = 3;
	
	static {
		users.add(new User(1, "Ram", new Date()));
		users.add(new User(2, "Rohit", new Date()));
		users.add(new User(3, "Vishal", new Date()));
	}
	
	public List<User> listAllUser() {
		return users;
	}
	
	public User saveUser(User user) {
		if(user.getId()==null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOneUser(int id) {
		for(User user: users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId()==id) {
				iterator.remove();
				return user;
			}
		}
//		for(User user: users) {
//			if(user.getId()==id) {
//				users.remove(user);
//				return user;
//			}
//		}
		return null;
	}
}
