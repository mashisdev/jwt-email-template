# 🔑 JWT Email Authentication Template

This is a security template designed to be easily implemented and adapted to other Java and Spring Boot backend projects. It provides a robust authentication system using JWT (JSON Web Tokens) and email verification.

## ✨ Key Features
-   **User Registration:** Allows users to register via the `/auth/signup` endpoint.
-   **Email Verification:** Sends a 6-digit verification code to the registered email.
-   **Account Verification:** Users can verify their account using the `/auth/verify` endpoint.
-   **User Login:** Generates a JWT token upon successful login via the `/auth/login` endpoint.
-   **Password Management:**
    -   Allows users to change their password if they know the current one.
    -   Provides a "forgot password" flow to reset the password via a link sent to their email.
-   **Profile Updates:** Users can update their first name, last name, username, and email (with new email verification).

## 🚦 Authentication Flow
1.  **Registration (`/auth/signup`):**
    -   Users register with their username, email, first name, last name, and password (as defined in `RegisterRequestDto`).
    -   Upon successful validation, a 6-digit verification code is generated and sent to the registered email address (from the email specified in the `SUPPORT_EMAIL` environment variable).

2.  **Verification (`/auth/verify`):**
    -   Users submit the 6-digit code received in their email along with their email (as defined in `VerifyRequestDto`) to the `/auth/verify` endpoint.
    -   If the code is correct and not expired, the user's account (`enabled` property in the `User` model) is set to `true`.

3.  **Login (`/auth/login`):**
    -   Verified users can log in using their email and password (as defined in `LoginRequestDto`) via the `/auth/login` endpoint.
    -   Upon successful authentication, a JWT token is generated with an expiration time specified in the environment variables. This token is returned to the user.

4.  **Profile Updates:**
    -   Users can update their first name, last name, and username.
    -   When updating the email, a new 6-digit verification code is sent to the new email address, requiring the user to verify the new email through a similar `/auth/verify` process.

5.  **Password Reset:**
    -   Users can change their password if they know their current password.
    -   A "forgot password" feature allows users to request a password reset link to be sent to their registered email. They can then use this link to set a new password.

## 🛠️ Technologies Used

-   [Spring Boot](https://spring.io/projects/spring-boot)
-   [Spring Web](https://spring.io/projects/spring-web)
-   [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
-   [MySQL Driver](https://dev.mysql.com/downloads/connector/j/)
-   [Spring Security](https://spring.io/projects/spring-security)
-   [Validation](https://jakarta.ee/specifications/bean-validation/)
-   [Java Mail Sender](https://spring.io/guides/gs/sending-email/)
-   [Java JWT](https://github.com/jwtk/jjwt)
