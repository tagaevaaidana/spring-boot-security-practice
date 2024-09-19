package peaksoft.springbootsecuritypractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootsecuritypractice.dto.UserRequest;
import peaksoft.springbootsecuritypractice.dto.UserResponse;
import peaksoft.springbootsecuritypractice.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse save(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse updateById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateById(id, userRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

}
