from .cart import Cart

class OrderRequest:
    def __init__(self, user_id: str, cart: Cart, address_id: str, card_id: str, cvv: str):
        if not user_id or not address_id or not card_id or not cvv:
            raise ValueError("Dados obrigatórios ausentes para o pedido")
        if cart.is_empty():
            raise ValueError("Carrinho vazio não pode ser finalizado")

        self.user_id = user_id
        self.cart = cart
        self.address_id = address_id
        self.card_id = card_id
        self.cvv = cvv  # Transmitido uma única vez, não armazenado depois

    def to_api_payload(self):
        return {
            "userId": self.user_id,
            "addressId": self.address_id,
            "cardId": self.card_id,
            "cvv": self.cvv,
            "items": [
                {
                    "productId": item.product.id,
                    "quantity": item.quantity
                }
                for item in self.cart.items
            ]
        }

    def get_total(self):
        return self.cart.get_total()
