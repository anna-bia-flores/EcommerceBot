package br.edu.ibmec.cloud.Ecommerce.useCases.userAddresses;

import br.edu.ibmec.cloud.Ecommerce.dtos.address.UpdateAddressDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.address.UserAddressDto;
import br.edu.ibmec.cloud.Ecommerce.models.UserAddress;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserAddressRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserAddressUseCase {

    private final UserAddressRepository repository;

    public UpdateUserAddressUseCase(UserAddressRepository repository) {
        this.repository = repository;
    }

    public UserAddressDto execute(String addressId, UpdateAddressDto dto) {
        UserAddress address = repository.findById(addressId)
            .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        dto.applyTo(address);
        UserAddress updated = repository.save(address);

        return UserAddressDto.fromEntity(updated);
    }
}
