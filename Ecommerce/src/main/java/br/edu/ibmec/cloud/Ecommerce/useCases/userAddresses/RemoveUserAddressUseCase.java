package br.edu.ibmec.cloud.Ecommerce.useCases.userAddresses;

import br.edu.ibmec.cloud.Ecommerce.repositories.UserAddressRepository;
import org.springframework.stereotype.Service;

@Service
public class RemoveUserAddressUseCase {

    private final UserAddressRepository repository;

    public RemoveUserAddressUseCase(UserAddressRepository repository) {
        this.repository = repository;
    }

    public void execute(String addressId) {
        if (!repository.existsById(addressId)) {
            throw new IllegalArgumentException("Address not found");
        }
        repository.deleteById(addressId);
    }
}
