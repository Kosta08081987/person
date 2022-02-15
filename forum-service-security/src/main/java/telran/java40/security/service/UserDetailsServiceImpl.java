package telran.java40.security.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.java40.accounting.dao.UserAccountRepository;
import telran.java40.accounting.model.UserAccount;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserAccountRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = repository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		String[] roles = userAccount.getRoles().stream()
							.map(r -> "ROLE_" + r.toUpperCase())
							.toArray(String[]::new);
		boolean credentialsNonExpired = userAccount.getExperationDate().isAfter(LocalDate.now());
//		return new User(username, userAccount.getPassword(), true, true, credentialsNonExpired , true, AuthorityUtils.createAuthorityList(roles));
			
//		return new User(username, userAccount.getPassword(), 
//				AuthorityUtils.createAuthorityList(roles));
		return new UserProfile(username, userAccount.getPassword(), 
				AuthorityUtils.createAuthorityList(roles),credentialsNonExpired);
		
	}

}
