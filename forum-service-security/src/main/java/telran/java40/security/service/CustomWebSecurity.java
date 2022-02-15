package telran.java40.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import telran.java40.accounting.dao.UserAccountRepository;
import telran.java40.forum.dao.PostRepository;
import telran.java40.forum.model.Post;

@Service("customSecurity")// class name in the scope or default in camel case begins with low case character
public class CustomWebSecurity {
	PostRepository postRepository;
	UserAccountRepository userRepository;

	@Autowired
	public CustomWebSecurity(PostRepository postRepository, UserAccountRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	
	public boolean checkPostAuthority(String postId, String userName) {
		Post post = postRepository.findById(postId).orElse(null);
		
		return post != null && userName.equals(post.getAuthor());
	}
	
	public boolean checkExpPassword(Authentication authentication){
		return((UserProfile)authentication.getPrincipal()).isPasswordNotExpired();
	}
	
	
}
