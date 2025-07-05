# Social-SSO-Login

SSO login through Github & Google

# 🛡️ Social Login with GitHub & Google (Spring Boot + OAuth2)

This project demonstrates how to integrate **GitHub and Google OAuth2 login** into a Spring Boot application, fetch user details, and display them on a simple HTML frontend.

---

## 📆 Tech Stack

* Java 8+
* Spring Boot 2.7.13
* Spring Security (OAuth2 Client)
* GitHub & Google OAuth2
* jQuery + Bootstrap UI
* Embedded Tomcat

---

## 🚀 Features

* OAuth2 login using GitHub and Google
* `/user` endpoint returns authenticated user's profile
* Secure session handling
* Logout support
* CSRF protection using cookies
* Bootstrap-based frontend

---

## ⚙️ Setup Instructions

### 1⃣ Clone the Repository

```bash
git clone https://github.com/Harshitsriv007/Social-SSO-Login.git
cd Social-SSO-Login
```

### 2⃣ Create GitHub & Google OAuth Apps

#### 🔧 GitHub

* Go to [GitHub Developer Settings](https://github.com/settings/developers)
* Click **"New OAuth App"**

  * **App name**: Social Login Demo
  * **Homepage URL**: `http://localhost:9090/`
  * **Authorization Callback URL**: `http://localhost:9090/login/oauth2/code/github`

#### 🔧 Google

* Go to [Google Cloud Console](https://console.cloud.google.com/)
* Create OAuth 2.0 Credentials
* Set Authorized redirect URI:

  `http://localhost:9090/login/oauth2/code/google`

Copy the generated:

* `Client ID`
* `Client Secret`

---

### 3⃣ Configure Credentials

In your `application.properties`:

```properties
server.port=9090

spring.security.oauth2.client.registration.github.client-id=your-github-client-id
spring.security.oauth2.client.registration.github.client-secret=your-github-client-secret

spring.security.oauth2.client.registration.google.client-id=your-google-client-id
spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret
```

Or in `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: your-github-client-id
            client-secret: your-github-client-secret
            scope: read:user, read:org
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
          google:
            client-id: your-google-client-id
            client-secret: your-google-client-secret
            scope: openid, profile, email
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
        provider:
          google:
            authorization-uri: https://accounts.google.com/o/oauth2/v2/auth
            token-uri: https://oauth2.googleapis.com/token
            user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo
```

---

## 🧪 Run the Application

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Visit: [http://localhost:9090](http://localhost:9090)

<img width="1440" alt="Screenshot 2025-07-05 at 3 52 18 PM" src="https://github.com/user-attachments/assets/f3b0c509-319a-41ef-81be-8205a5b3579f" />

---

## 📄 API Endpoints

| Endpoint  | Method | Auth Required | Description                            |
| --------- | ------ | ------------- | -------------------------------------- |
| `/`       | GET    | ❌ No          | Shows homepage with login/logout       |
| `/user`   | GET    | ✅ Yes         | Returns authenticated user info        |
| `/logout` | POST   | ✅ Yes         | Logs out the user                      |
| `/error`  | GET    | ❌ No          | Returns OAuth error message if present |

---

## 🧑‍💻 Example Response from `/user`

<img width="1440" alt="Screenshot 2025-07-05 at 3 52 35 PM" src="https://github.com/user-attachments/assets/b95e8180-9e1a-443a-866c-685e86e07ceb" />

```json
{
  "login": "siddroykapoor",
  "name": "sidd kapoor",
  "avatar_url": "https://avatars.githubusercontent.com/u/12345678?v=4",
  "email": "siddharth@example.com"
}
```

---

## 💻 Frontend Behavior

* If user is not logged in, shows login links for GitHub & Google
* After login, displays:

  * Username or full name
  * Logout button
* Uses jQuery to call `/user`, `/logout`, and `/error`

---

## 🔐 Security Configuration

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .antMatchers("/", "/error", "/webjars/**").permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )
        .oauth2Login(oauth -> oauth
            .defaultSuccessUrl("/", true)
        )
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/").permitAll()
        );

    return http.build();
}
```

---

## 🛠️ Troubleshooting

| Issue                                                  | Solution                                      |
| ------------------------------------------------------ | --------------------------------------------- |
| `No qualifying bean of type 'ServletWebServerFactory'` | Add `spring-boot-starter-web` to dependencies |
| OAuth2 login redirects to `.well-known/...`            | Add `.defaultSuccessUrl("/", true)` in config |
| 401 on `/user`                                         | Log in via GitHub or Google first             |
| CSRF errors on logout                                  | Pass `X-XSRF-TOKEN` header with CSRF token    |

---

## 📂 Folder Structure

```
src
└── main
    ├── java/com/example/social
    │   ├── SocialApplication.java
    │   └── controller/SocialApplicationController.java
    └── resources
        ├── static/index.html
        └── application.properties or application.yml
```

---