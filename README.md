# Spring Cloud Feign + OAuth 2.0

This is an example of Spring Cloud Feign and Spring Security OAuth2.


- API server
  - Spring Boot web server running on port 8081
  - Enable request logging (`CommonsRequestLoggingFilter`)
  - Enable OAuth authorization server
  - Enable OAuth resource server
  - Configure OAuth client ID and secret
  - Configure resource owner user and password
  - Example REST controller
- API client
  - Spring Boot without web server
  - Enable access token request logging
  - Enable Feign client
  - Disable Hystrix
  - Enable Feign request interceptor for OAuth 2.0 client (`OAuth2FeignRequestInterceptor`)
  - Example REST client for the API server using resource owner password grant
  - Example REST client for Twitter API using client credentials grant


## Run the authorization sequence (resource owner password grant)

```sh
./gradlew server:bootRun
```

```sh
./gradlew client:bootRun
```

API client:

```
2016-12-08 22:56:17.170 DEBUG [-,,,] 9273 --- [  restartedMain] o.s.web.client.RestTemplate              : Created POST request for "http://localhost:8081/oauth/token"
2016-12-08 22:56:17.646 DEBUG [-,,,] 9273 --- [  restartedMain] o.s.web.client.RestTemplate              : POST request for "http://localhost:8081/oauth/token" resulted in 200 (OK)
2016-12-08 22:56:17.701 DEBUG [-,,,] 9273 --- [  restartedMain] o.s.w.c.HttpMessageConverterExtractor    : Reading [interface org.springframework.security.oauth2.common.OAuth2AccessToken] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@70795435]
2016-12-08 22:56:17.935 DEBUG [-,,,] 9273 --- [  restartedMain] o.s.w.c.HttpMessageConverterExtractor    : Reading [class example.client.Hello] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@3cf1ab45]
2016-12-08 22:56:18.036  INFO [-,,,] 9273 --- [  restartedMain] example.client.HelloService              : Received from API server: example.client.Hello(world)
```

API server:

```
2016-12-08 22:56:17.446 DEBUG 9267 --- [qtp842001136-17] o.s.w.f.CommonsRequestLoggingFilter      : Before request [uri=/oauth/token;client=127.0.0.1;user=theId;headers={Authorization=[Basic dGhlSWQ6dGhlU2VjcmV0], Accept=[application/json, application/x-www-form-urlencoded], Cache-Control=[no-cache], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], Host=[localhost:8081], Pragma=[no-cache], Content-Length=[72], Content-Type=[application/x-www-form-urlencoded;charset=UTF-8]}]
2016-12-08 22:56:17.644 DEBUG 9267 --- [qtp842001136-17] o.s.w.f.CommonsRequestLoggingFilter      : After request [uri=/oauth/token;client=127.0.0.1;user=theId;headers={Authorization=[Basic dGhlSWQ6dGhlU2VjcmV0], Accept=[application/json, application/x-www-form-urlencoded], Cache-Control=[no-cache], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], Host=[localhost:8081], Pragma=[no-cache], Content-Length=[72], Content-Type=[application/x-www-form-urlencoded;charset=UTF-8]};payload=password=thePassword&grant_type=password&scope=the]
2016-12-08 22:56:17.769 DEBUG 9267 --- [qtp842001136-18] o.s.w.f.CommonsRequestLoggingFilter      : Before request [uri=/hello;client=127.0.0.1;user=theUser;headers={Authorization=[Bearer c8bdc5d3-0070-48b4-b3ad-1a14f05cf0c8], X-Span-Name=[http:/hello], Accept=[*/*], X-B3-SpanId=[3311ccccfaa36566], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], X-B3-Sampled=[0], X-B3-TraceId=[3311ccccfaa36566], Host=[localhost:8081]}]
2016-12-08 22:56:17.863 DEBUG 9267 --- [qtp842001136-18] o.s.w.f.CommonsRequestLoggingFilter      : After request [uri=/hello;client=127.0.0.1;user=theUser;headers={Authorization=[Bearer c8bdc5d3-0070-48b4-b3ad-1a14f05cf0c8], X-Span-Name=[http:/hello], Accept=[*/*], X-B3-SpanId=[3311ccccfaa36566], User-Agent=[Java/1.8.0_102], Connection=[keep-alive], X-B3-Sampled=[0], X-B3-TraceId=[3311ccccfaa36566], Host=[localhost:8081]}]
```


## Try the authorization sequence (resource owner password grant)

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
  -d username=theUser \
  -d password=thePassword \
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
{"name":"world"}
```
