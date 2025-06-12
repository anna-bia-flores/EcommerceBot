from .product import Product

class CartItem:
    def __init__(self, product: Product, quantity: int):
        if quantity <= 0:
            raise ValueError("Quantidade deve ser positiva")

        self.product = product
        self.quantity = quantity

    def increase_quantity(self, amount: int):
        if amount <= 0:
            raise ValueError("Aumentos devem ser positivos")
        self.quantity += amount

    def set_quantity(self, amount: int):
        if amount <= 0:
            raise ValueError("Quantidade deve ser positiva")
        self.quantity = amount

    def get_total_price(self):
        return self.product.price * self.quantity

    def to_dict(self):
        return {
            "product": self.product.to_dict(),
            "quantity": self.quantity,
            "total": self.get_total_price()
        }
