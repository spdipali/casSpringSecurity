package com.demo.casspringsecurity.cacheStore;

import java.util.Collection;
import java.util.Set;

public interface CacheOauth2TokenService {
    void deleteAccessToken(String key);

    void deleteAccessToRefreshToken(String key);

    void deleteAuthToAccessToken(String key);

    void deleteAuthToken(String key);

    void deleteClientIdToAccessToken(String key, Object value);

    void deleteRefreshToAccessToken(String key);

    void deleteRefreshToAuthToken(String key);

    void deleteRefreshToken(String key);

    void deleteUsernameToAccessToken(String key, Object value);

    void expireAccessToken(String key, int time);

    void expireAccessToRefreshToken(String key, int time);

    void expireAuthToAccessToken(String key, int time);

    void expireAuthToken(String key, int time);

    void expireClientIdToAccessToken(String key, int time);

    void expireUsernameToAccessToken(String key, int time);

    Object readAccessToken(String key);

    Collection readAccessTokenByUser(Set membersToken);

    Object readAuthForRefresh(String key);

    Object readAuthToAccess(String key);

    Object readAuthToken(String key);

    Set readClientIdToAccessTokenMembers(String key);

    Object readRefreshToAccessToken(String key);

    Object readRefreshToken(String key);

    Set readUsernameToAccessTokenMembers(String key);

    void saveAccessToken(String key, Object value);

    void saveAccessToRefreshToken(String key, Object value);

    void saveAuthToAccessToken(String key, Object value);

    void saveAuthToken(String key, Object value);

    void saveClientIdToAccessToken(String key, Object value);

    void saveRefreshToAccessToken(String key, Object value);

    void saveRefreshToAuthToken(String key, Object value);

    void saveRefreshToken(String key, Object value);

    void saveUsernameToAccessToken(String key, Object value);
}
