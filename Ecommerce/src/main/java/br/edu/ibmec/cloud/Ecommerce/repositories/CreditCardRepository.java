package br.edu.ibmec.cloud.Ecommerce.repositories;

import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
