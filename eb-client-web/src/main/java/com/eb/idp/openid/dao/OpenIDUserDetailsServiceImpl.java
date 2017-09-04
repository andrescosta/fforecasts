package com.eb.idp.openid.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Component;

import com.eb.contracts.EBUser;
import com.eb.dao.services.UserService;

@Component
public class OpenIDUserDetailsServiceImpl implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	
	@Autowired
	private UserService service;
	
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	@Override
	public UserDetails loadUserByUsername(String userName) {
        return createUserDetails(userName);
    }

	
	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
		String openId = (String)token.getPrincipal();
		EBUser user = service.getUserByOpenId(openId);
		if (null==null)
		{
			throw (new UserNotFoundException(openId));
		}
		return createUserDetails(user.getOpenId());
	}

	protected UserDetails createUserDetails(String userName) {
		List<SimpleGrantedAuthority> auths = Collections.singletonList(new SimpleGrantedAuthority(DEFAULT_ROLE));
		return new org.springframework.security.core.userdetails.User(userName, "nopassword", auths);
	}
	

}
