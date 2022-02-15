package telran.java40.security.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

@Service
public class ExpiredFilter extends GenericFilterBean {
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request =  (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		UserProfile userProfile = getProfile();
		if( userProfile != null && checkEndPoint(request.getMethod(),request.getServletPath())) {
			if(!userProfile.isPasswordNotExpired()) {
				response.sendError(403,"Is expired");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return !("PUT".equalsIgnoreCase(method) && path.matches("/account/password/?"));
	}
	
	public UserProfile getProfile() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserProfile) {
	    return (UserProfile)principal;
		}
		return null;
	}

}
