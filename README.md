# Social-SSO-Login
SSO login through Github &amp; Google

````markdown
# 🛡️ Social Login with GitHub (Spring Boot + OAuth2)

This project demonstrates how to integrate **GitHub OAuth2 login** into a Spring Boot application, fetch user details, and display them on a simple HTML frontend.

---

## 📦 Tech Stack

- Java 8+
- Spring Boot 2.7.13
- Spring Security (OAuth2 Client)
- GitHub OAuth2
- jQuery + Bootstrap UI
- Embedded Tomcat

---

## 🚀 Features

- OAuth2 login using GitHub
- `/user` endpoint returns authenticated user's GitHub profile
- Secure session handling
- Logout support
- CSRF protection using cookies
- Bootstrap-based frontend

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Harshitsriv007/Social-SSO-Login.git
cd Social-SSO-Login
````

### 2️⃣ Create a GitHub OAuth App

* Go to [GitHub Developer Settings](https://github.com/settings/developers)
* Click **"New OAuth App"**

    * **App name**: Social Login Demo
    * **Homepage URL**: `http://localhost:9090/`
    * **Authorization Callback URL**: `http://localhost:9090/login/oauth2/code/github`
* Click **Register Application**

Copy the generated:

* `Client ID`
* `Client Secret`

---

### 3️⃣ Configure Credentials

In your `application.properties`:

```properties
server.port=9090

spring.security.oauth2.client.registration.github.client-id=your-client-id
spring.security.oauth2.client.registration.github.client-secret=your-client-secret
```

Or in `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: your-client-id
            client-secret: your-client-secret
```

---

## 🧪 Run the Application

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Visit: [http://localhost:9090](http://localhost:9090)

---

## 📄 API Endpoints

| Endpoint  | Method | Auth Required | Description                            |
| --------- | ------ | ------------- | -------------------------------------- |
| `/`       | GET    | ❌ No          | Shows homepage with login/logout       |
| `/user`   | GET    | ✅ Yes         | Returns authenticated GitHub user info |
| `/logout` | POST   | ✅ Yes         | Logs out the user                      |

---

## 🧑‍💻 Example Response from `/user`

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

* If user is not logged in, shows "Login with GitHub" button
* After login, displays:

    * GitHub name or username
    * Logout button
* Uses jQuery to call `/user` and update DOM

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

## 🐛 Troubleshooting

| Issue                                                  | Solution                                               |
| ------------------------------------------------------ | ------------------------------------------------------ |
| `No qualifying bean of type 'ServletWebServerFactory'` | Make sure you added `spring-boot-starter-web`          |
| OAuth2 login redirects to `.well-known/...`            | Add `.defaultSuccessUrl("/", true)` in security config |
| 401 on `/user`                                         | You're not authenticated — log in via GitHub           |
| CSRF errors on logout                                  | Ensure CSRF token is passed in `X-XSRF-TOKEN` header   |

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
        └── application.properties
```

---

## 🤝 Contributing

PRs and suggestions are welcome!

---

## 📜 License

MIT License. Use freely for learning or integration.

```

---

Let me know if you want:
- Markdown badges (build, license, etc.)
- Docker instructions
- DB user saving + entity setup included

I can tailor this further!
```