package com.demo.casspringsecurity.config;

import com.demo.casspringsecurity.cacheStore.RedisTokenStore;
import com.demo.casspringsecurity.service.impl.EventClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class EventClientAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {


	@Autowired
	private RedisTokenStore redisTokenStore;

	//Using default authentication manager
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EventClientDetailsServiceImpl eventClientDetailsService;


	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.withClientDetails(eventClientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(redisTokenStore)
				.authenticationManager(authenticationManager)
		.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}
}