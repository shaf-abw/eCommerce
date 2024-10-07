package com.abw.ecommerce.UserService.api;

import com.abw.ecommerce.UserService.model.dto.request.Login;
import com.abw.ecommerce.UserService.model.dto.request.SignUp;
import com.abw.ecommerce.UserService.model.dto.response.TokenValidationResponse;
import com.abw.ecommerce.UserService.model.dto.response.InformationMessage;
import com.abw.ecommerce.UserService.model.dto.response.JwtResponseMessage;
import com.abw.ecommerce.UserService.model.dto.response.ResponseMessage;
import com.abw.ecommerce.UserService.security.jwt.JwtProvider;
import com.abw.ecommerce.UserService.security.validate.AuthorityTokenUtil;
import com.abw.ecommerce.UserService.service.EmailService;
import com.abw.ecommerce.UserService.service.UserService;
import com.abw.ecommerce.UserService.security.validate.TokenValidate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@OpenAPIDefinition( 
        info = @Info (
            title = "User Authentication API",
            description = "APIs for user registration, login, and authentication"
        )
)
public class UserAuth {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserAuth(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping({"/signup", "/register"})
    public Mono<ResponseMessage> register(@Valid @RequestBody SignUp signUp) {
        return userService.register(signUp)
                .map(user -> new ResponseMessage("Create user: " + signUp.getUsername() + " successfully."))
                .onErrorResume(error -> Mono.just(new ResponseMessage("Error occurred while creating the account."))
                );
    }

    @Operation(summary = "User login", description = "Logs in a user with the provided credentials.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping({"/signin", "/login"})
    public Mono<ResponseEntity<JwtResponseMessage>> login(@Valid @RequestBody Login signInForm) {
        return userService.login(signInForm)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    System.out.println(error);
                    JwtResponseMessage errorjwtResponseMessage = new JwtResponseMessage(
                            null,
                            null,
                            new InformationMessage()
                    );
                    return Mono.just(new ResponseEntity<>(errorjwtResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @Operation(summary = "User logout", description = "Logs out the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logged out successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Mono<ResponseEntity<String>> logout() {
        log.info("Logout endpoint called");
        return userService.logout()
                .then(Mono.just(new ResponseEntity<>("Logged out successfully.", HttpStatus.OK)))
                .onErrorResume(error -> {
                    log.error("Logout failed", error);
                    return Mono.just(new ResponseEntity<>("Logout failed.", HttpStatus.BAD_REQUEST));
                });
    }


//    @PostMapping("/reset-password")
//    public Mono<ResponseEntity<String>> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequest resetPasswordRequest) {
//        if (isValidToken(token)) {
//            // Token hợp lệ, đặt mật khẩu mới và cập nhật trong cơ sở dữ liệu
//            updatePassword(userEmail, resetPasswordRequest.getNewPassword());
//            return Mono.just(ResponseEntity.ok("Password reset successful"));
//        } else {
//            // Token không hợp lệ
//            return Mono.just(ResponseEntity.badRequest().body("Invalid token"));
//        }
//    }


//    @PostMapping({"/refresh", "/refresh-token"})
//    public Mono<ResponseEntity<JwtResponseMessage>> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
//        return userService.refreshToken(refreshToken)
//                .map(newAccessToken -> {
//                    JwtResponseMessage jwtResponseMessage = new JwtResponseMessage(newAccessToken, null, null);
//                    return ResponseEntity.ok(jwtResponseMessage);
//                })
//                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
//    }

    @Operation(summary = "Validate JWT token", description = "Validates the provided JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping({"/validateToken", "/validate-token"})
    public Boolean validateToken(@RequestHeader(name = "Authorization") String authorizationToken) {
        TokenValidate validate = new TokenValidate();
        if (validate.validateToken(authorizationToken)) {
            return ResponseEntity.ok(new TokenValidationResponse("Valid token")).hasBody();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenValidationResponse("Invalid token")).hasBody();
        }
    }

    @Operation(summary = "Check user authority", description = "Checks if the user has the specified authority.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role access API"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping({"/hasAuthority", "/authorization"})
    public Boolean getAuthority(@RequestHeader(name = "Authorization") String authorizationToken,
                                String requiredRole) {
        AuthorityTokenUtil authorityTokenUtil = new AuthorityTokenUtil();
        List<String> authorities = authorityTokenUtil.checkPermission(authorizationToken);

        if (authorities.contains(requiredRole)) {
            return ResponseEntity.ok(new TokenValidationResponse("Role access api")).hasBody();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponse("Invalid token")).hasBody();
        }
    }

}
