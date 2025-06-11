package br.edu.ibmec.cloud.Ecommerce.useCases.order;

import br.edu.ibmec.cloud.Ecommerce.dtos.order.CreateOrderDto;
import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import br.edu.ibmec.cloud.Ecommerce.models.Order;
import br.edu.ibmec.cloud.Ecommerce.models.OrderItem;
import br.edu.ibmec.cloud.Ecommerce.models.Product;
import br.edu.ibmec.cloud.Ecommerce.repositories.CreditCardRepository;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.OrderCosmosRepository;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PlaceOrderUseCase {

    private final OrderCosmosRepository repository;
    private final CreditCardRepository creditCardRepository;
    private final ProductCosmosRepository productCosmosRepository;

    public Order execute(CreateOrderDto dto) {
        System.out.println("📥 Pedido recebido para usuário: " + dto.getUserId());

        // Construção base do pedido
        Order order = dto.toEntity();

        // Itens
        for (OrderItem item : order.getItems()) {
            Product product = productCosmosRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("❌ Produto não encontrado: " + item.getProductId()));

            if (!product.isInStock(item.getQuantity())) {
                throw new IllegalStateException("❌ Estoque insuficiente para: " + product.getName());
            }

            item.setName(product.getName());
            item.setUnitPriceDecimal(product.getPriceDecimal());

            product.removeFromStock(item.getQuantity());
            productCosmosRepository.save(product);

            System.out.println("✅ Item validado: " + item.getName() + " x" + item.getQuantity());
        }

        // Cartão
        CreditCard card = creditCardRepository.findById(dto.getCreditCardId())
                .orElseThrow(() -> new IllegalArgumentException("❌ Cartão não encontrado: " + dto.getCreditCardId()));


        // Processamento e validação
        order.placeOrder(card, dto.getCvv());

        System.out.println("💳 Total: " + order.getTotalAmount() + " | Novo saldo: " + card.getBalance());

        try {
            Order saved = repository.save(order);
            System.out.println("💾 Pedido salvo com ID: " + saved.getId());
            return saved;
        } catch (Exception e) {
            System.err.println("❌ Falha ao salvar pedido:");
            e.printStackTrace();
            throw new IllegalStateException("Failed to insert item");
        }
    }
}
