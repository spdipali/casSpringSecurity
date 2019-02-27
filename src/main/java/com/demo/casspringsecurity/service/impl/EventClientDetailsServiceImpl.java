package com.demo.casspringsecurity.service.impl;

import com.demo.casspringsecurity.entity.EventAppClient;
import com.demo.casspringsecurity.repository.EventAppClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import javax.persistence.Cacheable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Cacheable
public class EventClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    EventAppClientRepository eventAppClientRepository;

//    static final String GRANT_TYPE_PASSWORD = "password";
//    static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
//    static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
//    static final String GRANT_TYPE_IMPLICIT = "implicit";
//    static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
//
//    static final String SCOPE_READ = "read";
//    static final String SCOPE_WRITE = "write";
//    static final String SCOPE_TRUST = "trust";
//
//    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
//    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    //Todo: handle null pointer exception PosAppClient being null throw not found exception
    @Override
    public ClientDetails loadClientByClientId(String clientUserName) throws ClientRegistrationException {

        EventAppClient eventAppClient = eventAppClientRepository.findOne(clientUserName);
        String authorizedGrants = eventAppClient.getAuthorizedGrantTypes();
        List<String> authorizedGrantTypes = Arrays.asList(authorizedGrants.split(","));
        String authorities = eventAppClient.getClientAuthorityList();


        List<String> authorityList = Arrays.asList(authorities.split(","));

        List<SimpleGrantedAuthority> grantedAuthorityList = authorityList.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

        List<String> eventClientScopeList = Arrays.asList(eventAppClient.getClientScopeList().split(","));

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(eventAppClient.getClientId());
        clientDetails.setClientSecret(eventAppClient.getClientSecret());
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        clientDetails.setAuthorities(grantedAuthorityList);
        clientDetails.setScope(eventClientScopeList);
        clientDetails.setRefreshTokenValiditySeconds(eventAppClient.getRefreshTokenValiditySeconds());
        clientDetails.setAccessTokenValiditySeconds(eventAppClient.getAccessTokenValiditySeconds());

        return clientDetails;

        }
    }
