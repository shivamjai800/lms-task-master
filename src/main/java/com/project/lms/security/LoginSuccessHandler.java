package com.project.lms.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.lms.details.CustomUserDetails;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws javax.servlet.ServletException, IOException {
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//		 System.out.print("here ");
		 
		String redirectURL;
//		System.out.println(userDetails.getRoles());
		
		if(userDetails.getRoles().equals("ROLE_ADMIN"))
		{
			redirectURL = "/admin";
		}
		else
			redirectURL = "/user";
		response.sendRedirect(request.getContextPath() + redirectURL);
		 
		 
		 super.onAuthenticationSuccess(request, response, authentication);

	}

}
