from domain.entities.cart import Cart
from domain.entities.user import User
from domain.entities.address import Address
from domain.entities.credit_card import CreditCard
from typing import Optional

class UserSession:
    def __init__(self):
        self.user: Optional[User] = None
        self.addresses: list[Address] = []
        self.cards: list[CreditCard] = []
        self.cart: Cart = Cart()
        self.selected_address_id: Optional[str] = None
        self.selected_card_id: Optional[str] = None

    def is_logged_in(self):
        return self.user is not None

    def login(self, user: User):
        self.user = user
        self.addresses = user.addresses
        self.cards = user.credit_cards

    def logout(self):
        self.clear()

    def add_to_cart(self, product, quantity):
        self.cart.add_item(product, quantity)

    def add_to_cart_by_id(self, product_id: str, quantity: int, product_loader):
        self.cart.add_item_by_id(product_id, quantity, product_loader)

    def clear_cart(self):
        self.cart.clear()

    def select_address(self, address_id: str):
        if any(addr.id == address_id for addr in self.addresses):
            self.selected_address_id = address_id
        else:
            raise ValueError("Endereço não encontrado na sessão")

    def select_card(self, card_id: str):
        if any(card.id == card_id for card in self.cards):
            self.selected_card_id = card_id
        else:
            raise ValueError("Cartão não encontrado na sessão")

    def get_selected_address(self):
        return next((addr for addr in self.addresses if addr.id == self.selected_address_id), None)

    def get_selected_card(self):
        return next((card for card in self.cards if card.id == self.selected_card_id), None)

    def clear(self):
        self.user = None
        self.addresses = []
        self.cards = []
        self.cart = Cart()
        self.selected_address_id = None
        self.selected_card_id = None
