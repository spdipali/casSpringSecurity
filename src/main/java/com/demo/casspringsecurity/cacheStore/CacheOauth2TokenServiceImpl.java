package com.demo.casspringsecurity.cacheStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheOauth2TokenServiceImpl implements CacheOauth2TokenService {
    //TODO: change the constants to match POS
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheOauth2TokenServiceImpl.class);
    private static final String MTA_OAUTH2_REDIS = "mta:oauth:";
    private static final String ACCESS = MTA_OAUTH2_REDIS + "access:";
    private static final String AUTH_TO_ACCESS = MTA_OAUTH2_REDIS + "auth_to_access:";
    private static final String AUTH = MTA_OAUTH2_REDIS + "auth:";
    private static final String REFRESH_AUTH = MTA_OAUTH2_REDIS + "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = MTA_OAUTH2_REDIS + "access_to_refresh:";
    private static final String REFRESH = MTA_OAUTH2_REDIS + "refresh:";
    private static final String REFRESH_TO_ACCESS = MTA_OAUTH2_REDIS + "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = MTA_OAUTH2_REDIS + "client_id_to_access:";
    private static final String UNAME_TO_ACCESS = MTA_OAUTH2_REDIS + "uname_to_access:";

    TimeUnit EXPIRATION_UNIT = TimeUnit.SECONDS;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public void deleteAccessToken(String key) {
        try {
            redisTemplate.delete(ACCESS + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteAccessToRefreshToken(String key) {
        try {
            redisTemplate.delete(ACCESS_TO_REFRESH + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteAuthToAccessToken(String key) {
        try {
            redisTemplate.delete(AUTH_TO_ACCESS + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteAuthToken(String key) {
        try {
            redisTemplate.delete(AUTH + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteClientIdToAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForSet().remove(CLIENT_ID_TO_ACCESS + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteRefreshToAccessToken(String key) {
        try {
            redisTemplate.delete(REFRESH_TO_ACCESS + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteRefreshToAuthToken(String key) {
        try {
            redisTemplate.delete(REFRESH_AUTH + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteRefreshToken(String key) {
        try {
            redisTemplate.delete(REFRESH + key);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteUsernameToAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForSet().remove(UNAME_TO_ACCESS + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void expireAccessToken(String key, int time) {
        try {
            redisTemplate.expire(ACCESS + key, time, EXPIRATION_UNIT);
        } catch (Exception e) {

        }
    }

    @Override
    public void expireAccessToRefreshToken(String key, int time) {
        try {
            redisTemplate.expire(ACCESS_TO_REFRESH + key, time, EXPIRATION_UNIT);
        } catch (Exception e) {

        }
    }

    @Override
    public void expireAuthToAccessToken(String key, int time) {
        try {
            redisTemplate.expire(AUTH_TO_ACCESS + key, time, EXPIRATION_UNIT);
        } catch (Exception e) {

        }
    }

    @Override
    public void expireAuthToken(String key, int time) {
        try {
            redisTemplate.expire(AUTH + key, time, EXPIRATION_UNIT);
        } catch (Exception e) {

        }
    }

    @Override
    public void expireClientIdToAccessToken(String key, int time) {
        try {
            redisTemplate.expire(CLIENT_ID_TO_ACCESS + key, time, EXPIRATION_UNIT);
        } catch (Exception e) {

        }
    }

    @Override
    public void expireUsernameToAccessToken(String key, int time) {
        try {
            redisTemplate.expire(UNAME_TO_ACCESS + key, time, EXPIRATION_UNIT);
        } catch (Exception e) {

        }
    }

    @Override
    public Object readAccessToken(String key) {
        try {
            return redisTemplate.opsForValue().get(ACCESS + key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Collection readAccessTokenByUser(Set membersToken) {
        try {
            return redisTemplate.opsForValue().multiGet(membersToken);
        } catch (Exception e) {

        }
        return Collections.emptyList();
    }

    @Override
    public Object readAuthForRefresh(String key) {
        try {
            return redisTemplate.opsForValue().get(REFRESH_AUTH + key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Object readAuthToAccess(String key) {
        try {
            return redisTemplate.opsForValue().get(AUTH_TO_ACCESS + key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Object readAuthToken(String key) {
        try {
            return redisTemplate.opsForValue().get(AUTH + key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Set readClientIdToAccessTokenMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(CLIENT_ID_TO_ACCESS + key);
        } catch (Exception e) {

        }
        return Collections.emptySet();
    }

    @Override
    public Object readRefreshToAccessToken(String key) {
        try {
            return redisTemplate.opsForValue().get(REFRESH_TO_ACCESS + key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Object readRefreshToken(String key) {
        try {
            return redisTemplate.opsForValue().get(REFRESH + key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Set readUsernameToAccessTokenMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(UNAME_TO_ACCESS + key);
        } catch (Exception e) {

        }
        return Collections.emptySet();
    }

    @Override
    public void saveAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(ACCESS + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveAccessToRefreshToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(ACCESS_TO_REFRESH + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveAuthToAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(AUTH_TO_ACCESS + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveAuthToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(AUTH + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveClientIdToAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForSet().add(CLIENT_ID_TO_ACCESS + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveRefreshToAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(REFRESH_TO_ACCESS + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveRefreshToAuthToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(REFRESH_AUTH + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveRefreshToken(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(REFRESH + key, value);
        } catch (Exception e) {

        }
    }

    @Override
    public void saveUsernameToAccessToken(String key, Object value) {
        try {
            redisTemplate.opsForSet().add(UNAME_TO_ACCESS + key, value);
        } catch (Exception e) {

        }
    }
}
