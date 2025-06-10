package br.edu.ibmec.cloud.Ecommerce.repositories;

import br.edu.ibmec.cloud.Ecommerce.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
}
