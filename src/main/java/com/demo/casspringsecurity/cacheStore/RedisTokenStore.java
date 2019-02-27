package com.demo.casspringsecurity.cacheStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Component
public class RedisTokenStore implements TokenStore {

    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName =
                authentication.getUserAuthentication() == null ? "" : authentication
                        .getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : (":" + userName));
    }

    @Autowired
    private CacheOauth2TokenService oauthCacheService;

    private AuthenticationKeyGenerator authenticationKeyGenerator =
            new DefaultAuthenticationKeyGenerator();

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Set clientIdToAccessToken = oauthCacheService.readClientIdToAccessTokenMembers(clientId);
        if (clientIdToAccessToken == null) {
            return Collections.<OAuth2AccessToken>emptySet();
        }
        Collection<OAuth2AccessToken> accessTokenCollection =
                oauthCacheService.readAccessTokenByUser(clientIdToAccessToken);
        return Collections.<OAuth2AccessToken>unmodifiableCollection(accessTokenCollection);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId,
                                                                         String userName) {
        Set usernameToAccessToken =
                oauthCacheService.readUsernameToAccessTokenMembers(getApprovalKey(clientId, userName));
        if (usernameToAccessToken == null) {
            return Collections.<OAuth2AccessToken>emptySet();
        }
        Collection<OAuth2AccessToken> accessTokenCollection =
                oauthCacheService.readAccessTokenByUser(usernameToAccessToken);
        return Collections.<OAuth2AccessToken>unmodifiableCollection(accessTokenCollection);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String key = authenticationKeyGenerator.extractKey(authentication);
        OAuth2AccessToken accessToken = (OAuth2AccessToken) oauthCacheService.readAuthToAccess(key);
        if (accessToken != null
                && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken
                .getValue())))) {
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return (OAuth2AccessToken) oauthCacheService.readAccessToken(tokenValue);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return (OAuth2Authentication) oauthCacheService.readAuthToken(token);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        return (OAuth2Authentication) oauthCacheService.readAuthForRefresh(token);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return (OAuth2RefreshToken) oauthCacheService.readRefreshToken(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        removeAccessToken(accessToken.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken =
                (OAuth2AccessToken) oauthCacheService.readAccessToken(tokenValue);
        OAuth2Authentication authentication =
                (OAuth2Authentication) oauthCacheService.readAuthToken(tokenValue);

        oauthCacheService.deleteAccessToken(tokenValue);
        oauthCacheService.deleteAccessToRefreshToken(tokenValue);
        oauthCacheService.deleteAuthToken(tokenValue);

        if (authentication != null) {
            String key = authenticationKeyGenerator.extractKey(authentication);
            oauthCacheService.deleteAuthToAccessToken(key);
            oauthCacheService.deleteUsernameToAccessToken(getApprovalKey(authentication), accessToken);
            oauthCacheService.deleteClientIdToAccessToken(
                    authentication.getOAuth2Request().getClientId(), accessToken);
            oauthCacheService.deleteAccessToken(key);
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        String accessToken = (String) oauthCacheService.readRefreshToAccessToken(refreshToken);
        oauthCacheService.deleteRefreshToAccessToken(refreshToken);
        if (accessToken != null) {
            removeAccessToken(accessToken);
        }
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        removeRefreshToken(refreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        oauthCacheService.deleteRefreshToken(tokenValue);
        oauthCacheService.deleteRefreshToAccessToken(tokenValue);
        oauthCacheService.deleteAccessToRefreshToken(tokenValue);
    }

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String tokenValue = token.getValue();
        String authHashKey = authenticationKeyGenerator.extractKey(authentication);
        String clientId = authentication.getOAuth2Request().getClientId();

        oauthCacheService.saveAccessToken(tokenValue, token);
        oauthCacheService.saveAuthToken(tokenValue, authentication);
        oauthCacheService.saveAuthToAccessToken(authHashKey, token);

        if (token.getExpiration() != null) {
            int seconds = token.getExpiresIn();
            oauthCacheService.expireAccessToken(tokenValue, seconds);
            oauthCacheService.expireAccessToRefreshToken(tokenValue, seconds);
            oauthCacheService.expireAuthToken(tokenValue, seconds);
            oauthCacheService.expireAuthToAccessToken(authHashKey, seconds);
        }
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null && refreshToken.getValue() != null) {
            String auth = token.getValue();

            oauthCacheService.saveRefreshToAccessToken(refreshToken.getValue(), auth);
            oauthCacheService.saveAccessToRefreshToken(auth, refreshToken.getValue());
        }

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        oauthCacheService.saveRefreshToken(refreshToken.getValue(), refreshToken);
        oauthCacheService.saveRefreshToAuthToken(refreshToken.getValue(), authentication);
    }

}
