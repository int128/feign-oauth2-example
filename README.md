# Spring Cloud Feign + OAuth 2.0

This is an example of Spring Cloud Feign and Spring Security OAuth2.


- API server
  - Spring Boot web server running on port 8081
  - Enable request logging (`CommonsRequestLoggingFilter`)
  - Enable OAuth authorization server
  - Enable OAuth resource server
  - Configure OAuth client ID and secret
  - Configure resource owners (`theUser1` and `theUser2`)
  - Example REST controller
- API client (CLI)
  - Spring Boot without web server
  - Enable OAuth client access token request logging
  - Enable Feign client logging
  - Enable Feign client
  - Disable Hystrix
  - Enable Feign request interceptor for OAuth 2.0 client (`OAuth2FeignRequestInterceptor`)
  - Example REST client for the API server using resource owner password grant
  - Example REST client for Twitter API using client credentials grant
- API client (web)
  - Spring Boot web server running on port 8082
  - Enable request logging (`CommonsRequestLoggingFilter`)
  - Enable OAuth client access token request logging
  - Enable Feign client logging
  - Enable Feign client
  - Disable Hystrix
  - Enable Feign request interceptor for OAuth 2.0 client (`OAuth2FeignRequestInterceptor`)
    - Use Client Credentials Grant if not logged in
    - Use Resource Owner Password Grant if logged in
  - Configure to renew session on login
  - Example REST client for the API server using resource owner password grant
  - Example REST client for Twitter API using client credentials grant


## Run the client CLI

```
./gradlew server:bootRun
```

```
./gradlew client-cli:bootRun
```

API client does:

- Acquire an access token from the API server
- Send a request with the access token to the API server
- Acquire an access token from Twitter API
- Send a request with the access token to Twitter API

API client:

```
2016-12-10 22:24:38.257  INFO [-,,,] 16048 --- [  restartedMain] example.client.App                       : Started App in 20.16 seconds (JVM running for 22.875)
2016-12-10 22:24:38.384 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.web.client.RestTemplate              : Created POST request for "http://localhost:8081/oauth/token"
2016-12-10 22:24:38.463 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.web.client.RestTemplate              : POST request for "http://localhost:8081/oauth/token" resulted in 200 (OK)
2016-12-10 22:24:38.540 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.w.c.HttpMessageConverterExtractor    : Reading [interface org.springframework.security.oauth2.common.OAuth2AccessToken] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@6b4afe9]
2016-12-10 22:24:38.586 DEBUG [-,,,] 16048 --- [  restartedMain] example.client.HelloClient               : [HelloClient#hello] ---> GET http://localhost:8081/hello HTTP/1.1
2016-12-10 22:24:38.645 DEBUG [-,,,] 16048 --- [  restartedMain] example.client.HelloClient               : [HelloClient#hello] <--- HTTP/1.1 200 OK (54ms)
2016-12-10 22:24:38.765 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.w.c.HttpMessageConverterExtractor    : Reading [class example.client.Hello] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@454a8dd2]
2016-12-10 22:24:38.905  INFO [-,,,] 16048 --- [  restartedMain] example.client.HelloService              : Received from API server: example.client.Hello(theUser1)
2016-12-10 22:24:39.255 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.web.client.RestTemplate              : Created POST request for "https://api.twitter.com/oauth2/token"
2016-12-10 22:24:39.973 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.web.client.RestTemplate              : POST request for "https://api.twitter.com/oauth2/token" resulted in 200 (OK)
2016-12-10 22:24:39.974 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.w.c.HttpMessageConverterExtractor    : Reading [interface org.springframework.security.oauth2.common.OAuth2AccessToken] as "application/json;charset=utf-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@7763b804]
2016-12-10 22:24:40.205 DEBUG [-,,,] 16048 --- [  restartedMain] o.s.w.c.HttpMessageConverterExtractor    : Reading [class example.client.TwitterSearch] as "application/json;charset=utf-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@454a8dd2]
2016-12-10 22:24:40.329  INFO [-,,,] 16048 --- [  restartedMain] example.client.TwitterService            : Received from Twitter API: example.client.TwitterSearch([example.client.TwitterSearch$Status(...)])
```

API server:

```
2016-12-10 22:24:38.411 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : Secure object: FilterInvocation: URL: /oauth/token; Attributes: [fullyAuthenticated]
2016-12-10 22:24:38.411 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : Previously Authenticated: org.springframework.security.authentication.UsernamePasswordAuthenticationToken@428ee622: Principal: org.springframework.security.core.userdetails.User@693716c: Username: theId; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_USER; Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@957e: RemoteIpAddress: 127.0.0.1; SessionId: null; Granted Authorities: ROLE_USER
2016-12-10 22:24:38.412 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : Authorization successful
2016-12-10 22:24:38.412 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : RunAsManager did not change Authentication object
2016-12-10 22:24:38.413 DEBUG 15598 --- [tp1430058124-18] o.s.w.f.CommonsRequestLoggingFilter      : Before request [uri=/oauth/token;client=127.0.0.1;user=theId;headers={Authorization=[Basic dGhlSWQ6dGhlU2VjcmV0], Accept=[application/json, application/x-www-form-urlencoded], Cache-Control=[no-cache], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], Host=[localhost:8081], Pragma=[no-cache], Content-Length=[72], Content-Type=[application/x-www-form-urlencoded;charset=UTF-8]}]
2016-12-10 22:24:38.419 DEBUG 15598 --- [tp1430058124-18] o.s.w.f.CommonsRequestLoggingFilter      : After request [uri=/oauth/token;client=127.0.0.1;user=theId;headers={Authorization=[Basic dGhlSWQ6dGhlU2VjcmV0], Accept=[application/json, application/x-www-form-urlencoded], Cache-Control=[no-cache], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], Host=[localhost:8081], Pragma=[no-cache], Content-Length=[72], Content-Type=[application/x-www-form-urlencoded;charset=UTF-8]};payload=password=thePassword&grant_type=password&scope=the]
2016-12-10 22:24:38.424 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.ExceptionTranslationFilter     : Chain processed normally
2016-12-10 22:24:38.608 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : Secure object: FilterInvocation: URL: /hello; Attributes: [#oauth2.throwOnError(authenticated)]
2016-12-10 22:24:38.609 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : Previously Authenticated: org.springframework.security.oauth2.provider.OAuth2Authentication@8449a742: Principal: org.springframework.security.core.userdetails.User@af827fdc: Username: theUser1; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_USER; Credentials: [PROTECTED]; Authenticated: true; Details: remoteAddress=127.0.0.1, tokenType=BearertokenValue=<TOKEN>; Granted Authorities: ROLE_USER
2016-12-10 22:24:38.609 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : Authorization successful
2016-12-10 22:24:38.609 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : RunAsManager did not change Authentication object
2016-12-10 22:24:38.610 DEBUG 15598 --- [tp1430058124-15] o.s.w.f.CommonsRequestLoggingFilter      : Before request [uri=/hello;client=127.0.0.1;user=theUser1;headers={Authorization=[Bearer 4113439f-6847-490c-af9d-1c7240aeb855], X-Span-Name=[http:/hello], Accept=[*/*], X-B3-SpanId=[46f80160a8979524], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], X-B3-Sampled=[0], X-B3-TraceId=[46f80160a8979524], Host=[localhost:8081]}]
2016-12-10 22:24:38.622 DEBUG 15598 --- [tp1430058124-15] o.s.w.f.CommonsRequestLoggingFilter      : After request [uri=/hello;client=127.0.0.1;user=theUser1;headers={Authorization=[Bearer 4113439f-6847-490c-af9d-1c7240aeb855], X-Span-Name=[http:/hello], Accept=[*/*], X-B3-SpanId=[46f80160a8979524], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], X-B3-Sampled=[0], X-B3-TraceId=[46f80160a8979524], Host=[localhost:8081]}]
2016-12-10 22:24:38.623 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.ExceptionTranslationFilter     : Chain processed normally
```


## Run the client web

```
./gradlew server:bootRun
```

```
./gradlew client-web:bootRun
```

Access to http://localhost:8082/hello and 


API client:

```
2016-12-10 22:32:59.582 DEBUG [-,d9e9ef7a60746d62,5f4b7aec6370e786,false] 16077 --- [tp1310768211-20] o.s.web.client.RestTemplate              : Created POST request for "http://localhost:8081/oauth/token"
2016-12-10 22:32:59.613 DEBUG [-,d9e9ef7a60746d62,5f4b7aec6370e786,false] 16077 --- [tp1310768211-20] o.s.web.client.RestTemplate              : POST request for "http://localhost:8081/oauth/token" resulted in 200 (OK)
2016-12-10 22:32:59.646 DEBUG [-,d9e9ef7a60746d62,5f4b7aec6370e786,false] 16077 --- [tp1310768211-20] o.s.w.c.HttpMessageConverterExtractor    : Reading [interface org.springframework.security.oauth2.common.OAuth2AccessToken] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@115129e]
2016-12-10 22:32:59.678 DEBUG [-,d9e9ef7a60746d62,5f4b7aec6370e786,false] 16077 --- [tp1310768211-20] example.client.HelloClient               : [HelloClient#hello] ---> GET http://localhost:8081/hello HTTP/1.1
2016-12-10 22:32:59.701 DEBUG [-,d9e9ef7a60746d62,5f4b7aec6370e786,false] 16077 --- [tp1310768211-20] example.client.HelloClient               : [HelloClient#hello] <--- HTTP/1.1 200 OK (21ms)
2016-12-10 22:32:59.770 DEBUG [-,d9e9ef7a60746d62,5f4b7aec6370e786,false] 16077 --- [tp1310768211-20] o.s.w.c.HttpMessageConverterExtractor    : Reading [class example.client.Hello] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@6e75f0a8]
2016-12-10 22:34:49.696 DEBUG [-,91b0a56fcd7c1738,8ad7bc50d83b3cd2,false] 16077 --- [tp1310768211-17] example.client.HelloClient               : [HelloClient#hello] ---> GET http://localhost:8081/hello HTTP/1.1
2016-12-10 22:34:49.714 DEBUG [-,91b0a56fcd7c1738,8ad7bc50d83b3cd2,false] 16077 --- [tp1310768211-17] example.client.HelloClient               : [HelloClient#hello] <--- HTTP/1.1 200 OK (16ms)
2016-12-10 22:34:49.715 DEBUG [-,91b0a56fcd7c1738,8ad7bc50d83b3cd2,false] 16077 --- [tp1310768211-17] o.s.w.c.HttpMessageConverterExtractor    : Reading [class example.client.Hello] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@6e75f0a8]
```

API server:

```
2016-12-10 22:32:59.600 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : Secure object: FilterInvocation: URL: /oauth/token; Attributes: [fullyAuthenticated]
2016-12-10 22:32:59.600 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : Previously Authenticated: org.springframework.security.authentication.UsernamePasswordAuthenticationToken@428ee622: Principal: org.springframework.security.core.userdetails.User@693716c: Username: theId; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_USER; Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@957e: RemoteIpAddress: 127.0.0.1; SessionId: null; Granted Authorities: ROLE_USER
2016-12-10 22:32:59.600 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : Authorization successful
2016-12-10 22:32:59.600 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.i.FilterSecurityInterceptor    : RunAsManager did not change Authentication object
2016-12-10 22:32:59.601 DEBUG 15598 --- [tp1430058124-18] o.s.w.f.CommonsRequestLoggingFilter      : Before request [uri=/oauth/token;client=127.0.0.1;user=theId;headers={Authorization=[Basic dGhlSWQ6dGhlU2VjcmV0], Accept=[application/json, application/x-www-form-urlencoded], Cache-Control=[no-cache], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], Host=[localhost:8081], Pragma=[no-cache], Content-Length=[72], Content-Type=[application/x-www-form-urlencoded;charset=UTF-8]}]
2016-12-10 22:32:59.607 DEBUG 15598 --- [tp1430058124-18] o.s.w.f.CommonsRequestLoggingFilter      : After request [uri=/oauth/token;client=127.0.0.1;user=theId;headers={Authorization=[Basic dGhlSWQ6dGhlU2VjcmV0], Accept=[application/json, application/x-www-form-urlencoded], Cache-Control=[no-cache], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], Host=[localhost:8081], Pragma=[no-cache], Content-Length=[72], Content-Type=[application/x-www-form-urlencoded;charset=UTF-8]};payload=password=thePassword&grant_type=password&scope=the]
2016-12-10 22:32:59.607 DEBUG 15598 --- [tp1430058124-18] o.s.s.w.a.ExceptionTranslationFilter     : Chain processed normally
2016-12-10 22:32:59.686 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : Secure object: FilterInvocation: URL: /hello; Attributes: [#oauth2.throwOnError(authenticated)]
2016-12-10 22:32:59.686 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : Previously Authenticated: org.springframework.security.oauth2.provider.OAuth2Authentication@8449a742: Principal: org.springframework.security.core.userdetails.User@af827fdc: Username: theUser1; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_USER; Credentials: [PROTECTED]; Authenticated: true; Details: remoteAddress=127.0.0.1, tokenType=BearertokenValue=<TOKEN>; Granted Authorities: ROLE_USER
2016-12-10 22:32:59.687 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : Authorization successful
2016-12-10 22:32:59.687 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.i.FilterSecurityInterceptor    : RunAsManager did not change Authentication object
2016-12-10 22:32:59.687 DEBUG 15598 --- [tp1430058124-15] o.s.w.f.CommonsRequestLoggingFilter      : Before request [uri=/hello;client=127.0.0.1;user=theUser1;headers={Authorization=[Bearer 4113439f-6847-490c-af9d-1c7240aeb855], X-Span-Name=[http:/hello], Accept=[*/*], X-B3-SpanId=[420a59bc1f5b11f9], X-B3-ParentSpanId=[5f4b7aec6370e786], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], X-B3-Sampled=[0], X-B3-TraceId=[d9e9ef7a60746d62], Host=[localhost:8081]}]
2016-12-10 22:32:59.691 DEBUG 15598 --- [tp1430058124-15] o.s.w.f.CommonsRequestLoggingFilter      : After request [uri=/hello;client=127.0.0.1;user=theUser1;headers={Authorization=[Bearer 4113439f-6847-490c-af9d-1c7240aeb855], X-Span-Name=[http:/hello], Accept=[*/*], X-B3-SpanId=[420a59bc1f5b11f9], X-B3-ParentSpanId=[5f4b7aec6370e786], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], X-B3-Sampled=[0], X-B3-TraceId=[d9e9ef7a60746d62], Host=[localhost:8081]}]
2016-12-10 22:32:59.691 DEBUG 15598 --- [tp1430058124-15] o.s.s.w.a.ExceptionTranslationFilter     : Chain processed normally
```


## Try the authorization sequence with curl

Make a request without an access token and the server will return 401.

```sh
curl -v http://localhost:8081/hello
```

```json
{"error":"unauthorized",
"error_description":"Full authentication is required to access this resource"}
```

Acquire an access token.

```sh
curl -v -u theId:theSecret \
  http://localhost:8081/oauth/token \
  -d grant_type=password \
  -d username=theUser1 \
  -d password=theResourceOwnerPassword \
  -d scope=foo
```

```json
{"access_token":"50480ab0-4616-449c-823b-e5eb41ebe44f",
"token_type":"bearer",
"refresh_token":"3c526955-848c-4c85-b22f-ef4879d4a2be",
"expires_in":43199,
"scope":"foo"}
```

Make a request with the access token.

```sh
curl -v -H 'Authorization: Bearer 50480ab0-4616-449c-823b-e5eb41ebe44f' \
  http://localhost:8081/hello
```

```json
{"name":"theUser1"}
```

```sh
curl -v -H 'Authorization: Bearer 50480ab0-4616-449c-823b-e5eb41ebe44f' \
  http://localhost:8081/user
```

```json
{
  "details": {
    "remoteAddress": "0:0:0:0:0:0:0:1",
    "sessionId": null,
    "tokenValue": "50480ab0-4616-449c-823b-e5eb41ebe44f",
    "tokenType": "Bearer",
    "decodedDetails": null
  },
  "authorities": [
    {
      "authority": "ROLE_USER"
    }
  ],
  "authenticated": true,
  "userAuthentication": {
    "details": {
      "grant_type": "password",
      "scope": "foo",
      "username": "theUser1"
    },
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ],
    "authenticated": true,
    "principal": {
      "password": null,
      "username": "theUser1",
      "authorities": [
        {
          "authority": "ROLE_USER"
        }
      ],
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true
    },
    "credentials": null,
    "name": "theUser1"
  },
  "oauth2Request": {
    "clientId": "theId",
    "scope": [
      "foo"
    ],
    "requestParameters": {
      "grant_type": "password",
      "scope": "foo",
      "username": "theUser1"
    },
    "resourceIds": [],
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ],
    "approved": true,
    "refresh": false,
    "redirectUri": null,
    "responseTypes": [],
    "extensions": {},
    "grantType": "password",
    "refreshTokenRequest": null
  },
  "clientOnly": false,
  "principal": {
    "password": null,
    "username": "theUser1",
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ],
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true
  },
  "credentials": "",
  "name": "theUser1"
}
```
