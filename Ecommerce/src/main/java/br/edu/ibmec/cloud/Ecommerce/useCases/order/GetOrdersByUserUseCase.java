package br.edu.ibmec.cloud.Ecommerce.useCases.order;

import br.edu.ibmec.cloud.Ecommerce.dtos.order.OrderDto;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.OrderCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GetOrdersByUserUseCase {

    private final OrderCosmosRepository repository;

    public List<OrderDto> execute(String userId) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(order -> userId.equals(order.getUserId()))
                .map(OrderDto::fromEntity)
                .collect(Collectors.toList());
    }
}
