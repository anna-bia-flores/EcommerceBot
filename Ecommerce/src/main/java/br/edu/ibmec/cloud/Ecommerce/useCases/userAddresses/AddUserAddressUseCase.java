package br.edu.ibmec.cloud.Ecommerce.useCases.userAddresses;

import br.edu.ibmec.cloud.Ecommerce.dtos.address.CreateAddressDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.address.UserAddressDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.models.UserAddress;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserAddressRepository;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddUserAddressUseCase {

    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;

    public AddUserAddressUseCase(UserRepository userRepository, UserAddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public UserAddressDto execute(String userId, CreateAddressDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserAddress address = dto.toEntity(user);
        UserAddress saved = addressRepository.save(address);

        return UserAddressDto.fromEntity(saved);
    }
}
