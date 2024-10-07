package com.abw.ecommerce.UserService.api;

import com.abw.ecommerce.UserService.exception.wrapper.TokenErrorOrAccessTimeOut;
import com.abw.ecommerce.UserService.exception.wrapper.UserNotFoundException;
import com.abw.ecommerce.UserService.http.HeaderGenerator;
import com.abw.ecommerce.UserService.model.dto.request.ChangePasswordRequest;
import com.abw.ecommerce.UserService.model.dto.request.SignUp;
import com.abw.ecommerce.UserService.model.dto.request.UserDto;
import com.abw.ecommerce.UserService.model.dto.response.ResponseMessage;
import com.abw.ecommerce.UserService.security.jwt.JwtProvider;
import com.abw.ecommerce.UserService.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/manager")
@OpenAPIDefinition(
        info = @Info(
                title = "User API", description = "Operations related to users"
        )
)
public class UserManager {
    private final ModelMapper modelMapper;

    private final UserService userService;
    private final HeaderGenerator headerGenerator;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserManager(UserService userService, HeaderGenerator headerGenerator, JwtProvider jwtProvider,
                       ModelMapper modelMapper) {
        this.userService = userService;
        this.headerGenerator = headerGenerator;
        this.jwtProvider = jwtProvider;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Update user information", description = "Update the user information with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping("update/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Mono<ResponseEntity<ResponseMessage>> update(@PathVariable("id") Long id, @RequestBody SignUp updateDTO) {
        return userService.update(id, updateDTO)
                .flatMap(user -> Mono.just(new ResponseEntity<>(
                        new ResponseMessage("Update user: " + updateDTO.getUsername() + " successfully."),
                        HttpStatus.OK))
                )
                .onErrorResume(
                        error -> Mono.just(new ResponseEntity<>(
                                new ResponseMessage("Update user: " + updateDTO.getUsername() + " failed " + error.getMessage()),
                                HttpStatus.BAD_REQUEST)
                        )
                );
    }

    @Operation(summary = "Change user password", description = "Change the password for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Password changed successfully")
    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Mono<String> changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request);
    }

    @Operation(summary = "Delete user",
            description = "Delete a user with the specified ID.")
    @DeleteMapping("delete/{id}")
    @PreAuthorize("isAuthenticated() and (hasAuthority('USER') or hasAuthority('ADMIN'))")
    public String delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @Operation(summary = "Get user by username",
            description = "Retrieve user information based on the provided username.")
    @GetMapping("/user")
    @PreAuthorize("(isAuthenticated() and (hasAuthority('USER') and principal.username == #username) or hasAuthority('ADMIN'))")
    public ResponseEntity<?> getUserByUsername(@RequestParam(value = "username") String username) {
        Optional<UserDto> user = Optional.ofNullable(userService.findByUsername(username)
                .map((element) -> modelMapper.map(element, UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found with: " + username)));
        return user.map(u -> new ResponseEntity<>(u,
                        headerGenerator.getHeadersForSuccessGetMethod(),
                        HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(null,
                        headerGenerator.getHeadersForError(),
                        HttpStatus.NOT_FOUND)
                );
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user information based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and principal.id == #id")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<UserDto> userDTO = Optional.ofNullable(userService.findById(id)
                .map((element) -> modelMapper.map(element, UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found with: " + id)));
        return (userDTO.isPresent())
                ? new ResponseEntity<>(userDTO.get(), headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK)
                : new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get a secure user resource", security = { @SecurityRequirement(name = "JWT") })
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "ASC") String sortOrder) {

        Page<UserDto> usersPage = userService.findAllUsers(page, size, sortBy, sortOrder);
        return new ResponseEntity<>(usersPage, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
    }

    @Operation(summary = "Get user information from token",
            description = "Retrieve user information based on the provided JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        String username = jwtProvider.getUserNameFromToken(token);
        UserDto user = userService.findByUsername(username)
                .map((element) -> modelMapper.map(element, UserDto.class))
                .orElseThrow(() -> new TokenErrorOrAccessTimeOut("Token error or access timeout"));

        return (user != null)
                ? new ResponseEntity<>(user, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK)
                : new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    }

}
