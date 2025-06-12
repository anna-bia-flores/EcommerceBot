from typing import List, Callable
from .cart_item import CartItem
from .product import Product

class Cart:
    def __init__(self):
        self.items: List[CartItem] = []

    def add_item(self, product: Product, quantity: int):
        if quantity <= 0:
            raise ValueError("A quantidade deve ser positiva")

        existing = self._find_item(product["id"])
        if existing:
            existing.increase_quantity(quantity)
        else:
            self.items.append(CartItem(product, quantity))

    def add_item_by_id(self, product_id: str, quantity: int, product_loader: Callable[[str], Product]):
        if quantity <= 0:
            raise ValueError("A quantidade deve ser positiva")
        product = product_loader(product_id)
        if not product:
            raise ValueError("Produto não encontrado")
        self.add_item(product, quantity)

    def remove_item(self, product_id: str):
        item = self._find_item(product_id)
        if item:
            self.items.remove(item)

    def update_quantity(self, product_id: str, quantity: int):
        if quantity < 0:
            raise ValueError("Quantidade inválida")
        item = self._find_item(product_id)
        if item:
            if quantity == 0:
                self.items.remove(item)
            else:
                item.set_quantity(quantity)

    def get_total(self):
        return sum(item.get_total_price() for item in self.items)

    def clear(self):
        self.items.clear()

    def is_empty(self):
        return len(self.items) == 0

    def _find_item(self, product_id: str):
        return next((i for i in self.items if i.product.id == product_id), None)

    def to_dict(self):
        return [item.to_dict() for item in self.items]
