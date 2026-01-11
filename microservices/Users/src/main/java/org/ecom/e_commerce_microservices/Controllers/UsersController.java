package org.ecom.e_commerce_microservices.Controllers;


import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Request.UserRequest;
import org.ecom.e_commerce_microservices.DTO.Response.UserResponse;
import org.ecom.e_commerce_microservices.Services.UsersService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(required = false,defaultValue = "0") int pageNo,
                                                          @RequestParam(required = false,defaultValue = "10") int pageSize,
                                                          @RequestParam(required = false,defaultValue = "id") String sortedBy,
                                                          @RequestParam(required = false,defaultValue = "ASC") String sortOrder){

        Sort sort=null;
        if(sortOrder.equalsIgnoreCase("ASC")) sort= Sort.by(sortedBy).ascending();
        else sort=Sort.by(sortedBy).descending();
        return new ResponseEntity<>(userService.fetchAllUsers(PageRequest.of(pageNo,pageSize,sort)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id,
                                             @RequestBody UserRequest updateUserRequest){
        boolean updated = userService.updateUser(id, updateUserRequest);
        if (updated)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }
}
