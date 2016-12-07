# Spring Cloud Feign + OAuth 2.0

## Getting Started

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


