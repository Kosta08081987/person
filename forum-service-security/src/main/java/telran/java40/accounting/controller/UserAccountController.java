package telran.java40.accounting.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java40.accounting.dto.RolesResponseDto;
import telran.java40.accounting.dto.UserAccountResponseDto;
import telran.java40.accounting.dto.UserRegisterDto;
import telran.java40.accounting.dto.UserUpdateDto;
import telran.java40.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {

	UserAccountService accountService;

	

	@Autowired
	public UserAccountController(UserAccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/register")
	public UserAccountResponseDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return accountService.addUser(userRegisterDto);
	}

	@PostMapping("/login")
	public UserAccountResponseDto login(Principal principal) {	
		String login = principal.getName();
		return accountService.getUser(login);
	}

	@PutMapping("/user/{login}")
	@PreAuthorize("#login == authentication.name")
	public UserAccountResponseDto updateUser(@PathVariable String login/*Principal principal*/, @RequestBody UserUpdateDto userUpdateDto) {
		return accountService.editUser(/*principal.getName()*/ login, userUpdateDto);
	}

	@DeleteMapping("/user/{login}")
	@PreAuthorize("#login == authentication.name or hasRole('ADMINISTRATOR')")
	public UserAccountResponseDto removeUser(@PathVariable String login /* Principal principal */) {
		return accountService.removeUser(/* principal.getName() */login);
	}

	@PutMapping("/user/{login}/role/{role}")
	public RolesResponseDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public RolesResponseDto removeRole( @PathVariable String login/*Principal principal*/, @PathVariable String role) {
		return accountService.changeRolesList(login, role, false);
	}

	//FIXME
	@PutMapping("/password")
	public void changePassword(@RequestHeader("X-Password") String newPassword, Authentication principal) {
		accountService.changePassword(principal.getName(), newPassword);
	}


}
