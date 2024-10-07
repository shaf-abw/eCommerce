package com.abw.ecommerce.UserService.api;

import com.abw.ecommerce.UserService.http.HeaderGenerator;
import com.abw.ecommerce.UserService.service.RoleService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/role")
@OpenAPIDefinition(
        info = @Info (
                title = "User Role API", description = "Operations related to user roles"
        )
)
public class UserRole {

    private final RoleService roleService;
    private final HeaderGenerator headerGenerator;

    @Autowired
    public UserRole(RoleService roleService, HeaderGenerator headerGenerator) {
        this.roleService = roleService;
        this.headerGenerator = headerGenerator;
    }

    @Operation(summary = "Assign roles to user", description = "Assign roles to a user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/{id}/assign-roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> assignRoles(@PathVariable Long id, @RequestBody String roleNames) {
        boolean success = roleService.assignRole(id, roleNames);
        if (success) {
            return new ResponseEntity<>("Roles have been assigned to users with IDs " + id,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Has full rights for the User" + id,
                headerGenerator.getHeadersForError(),
                HttpStatus.OK);
    }

    @Operation(summary = "Revoke roles from user", description = "Revoke roles from a user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles revoked successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/{id}/revoke-roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> revokeRoles(@PathVariable Long id, @RequestBody String roleNames) {
        boolean success = roleService.revokeRole(id, roleNames);
        if (success) {
            return new ResponseEntity<>("Roles have been assigned to users with IDs " + id,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Has full rights for the User" + id,
                headerGenerator.getHeadersForError(),
                HttpStatus.OK);
    }

    @Operation(summary = "Get user roles", description = "Retrieve roles assigned to a user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User roles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}/user-roles")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable Long id) {
        List<String> userRoles = roleService.getUserRoles(id);
        return new ResponseEntity<>(userRoles,
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK);
    }

}
