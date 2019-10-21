package com.restful.web.services.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReposetory extends JpaRepository<Post, Integer>{

}
