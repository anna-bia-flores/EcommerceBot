package br.edu.ibmec.cloud.Ecommerce.controllers;

import br.edu.ibmec.cloud.Ecommerce.dtos.address.CreateAddressDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.address.UpdateAddressDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.address.UserAddressDto;
import br.edu.ibmec.cloud.Ecommerce.useCases.userAddresses.AddUserAddressUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.userAddresses.RemoveUserAddressUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.userAddresses.UpdateUserAddressUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/addresses")
public class UserAddressController {

    private final AddUserAddressUseCase add;
    private final RemoveUserAddressUseCase remove;
    private final UpdateUserAddressUseCase update;

    public UserAddressController(
        AddUserAddressUseCase add,
        RemoveUserAddressUseCase remove,
        UpdateUserAddressUseCase update
    ) {
        this.add = add;
        this.remove = remove;
        this.update = update;
    }

    @PostMapping
    public ResponseEntity<UserAddressDto> addAddress(
        @PathVariable String userId,
        @Valid @RequestBody CreateAddressDto dto
    ) {
        UserAddressDto address = add.execute(userId, dto);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<UserAddressDto> updateAddress(
        @PathVariable String addressId,
        @Valid @RequestBody UpdateAddressDto dto
    ) {
        UserAddressDto updated = update.execute(addressId, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String addressId) {
        remove.execute(addressId);
        return ResponseEntity.noContent().build();
    }
}
