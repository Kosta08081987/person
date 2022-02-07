package telran.java40.security.service;

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
	UserAccountRepository userAccountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = userAccountRepository.findById(username)
													   .orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(userAccount.getRoles().stream()
																													  .map(r -> "ROLE" + r.toUpperCase())
																													  .toArray(String[]::new)
																							   )
					    );
	}

}
