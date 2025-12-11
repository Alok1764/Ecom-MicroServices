package org.ecom.e_commerce_microservices.Services;

import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Request.UserRequest;
import org.ecom.e_commerce_microservices.DTO.Response.AddressDTO;
import org.ecom.e_commerce_microservices.DTO.Response.UserResponse;
import org.ecom.e_commerce_microservices.Entities.Address;
import org.ecom.e_commerce_microservices.Entities.Users;
import org.ecom.e_commerce_microservices.Repositories.UsersRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {


    private final UsersRepo usersRepo;

    public List<UserResponse> fetchAllUsers(Pageable pageable) {
        return usersRepo.findAll(pageable).getContent()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return usersRepo.findById(id)
                .map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest) {
        Users user = new Users();
        updateUserFromRequest(user, userRequest);
        usersRepo.save(user);
    }


    public boolean updateUser(Long id, UserRequest updateUserRequest) {
        return usersRepo.findById(id)
                .map(users -> {
                    updateUserFromRequest(users,updateUserRequest);
                    usersRepo.save(users);
                    return true;
                })
                .orElse(false);
    }

    private void updateUserFromRequest(Users user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = Address.builder()
                    .street(userRequest.getAddress().getStreet())
                    .state(userRequest.getAddress().getState())
                    .zipcode(userRequest.getAddress().getZipcode())
                    .city(userRequest.getAddress().getCity())
                    .country(userRequest.getAddress().getCountry())
                    .build();

            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(Users user){
        UserResponse response = UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();

        if (user.getAddress() != null) {
            AddressDTO addressDTO = AddressDTO.builder()
                    .street(user.getAddress().getStreet())
                    .city(user.getAddress().getCity())
                    .state(user.getAddress().getState())
                    .country(user.getAddress().getCountry())
                    .zipcode(user.getAddress().getZipcode())
                    .build();

            response.setAddress(addressDTO);
        }
        return response;
    }
}
