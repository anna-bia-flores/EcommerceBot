from __future__ import annotations
from typing import Optional
from .address import Address
from .credit_card import CreditCard

class User:
    def __init__(self, id: str, name: str, email: str):
        if not id or not name or not email:
            raise ValueError("Usuário inválido: id, nome e email são obrigatórios")

        self.id = id
        self.name = name
        self.email = email
        self.addresses: list[Address] = []
        self.credit_cards: list[CreditCard] = []

    def add_address(self, address: Address):
        self.addresses.append(address)

    def add_credit_card(self, card: CreditCard):
        self.credit_cards.append(card)

    def has_valid_cards(self) -> bool:
        return any(card.is_valid() for card in self.credit_cards)

    def has_valid_addresses(self) -> bool:
        return any(addr.is_valid() for addr in self.addresses)

    def get_address_by_id(self, address_id: str) -> Optional[Address]:
        return next((a for a in self.addresses if a.id == address_id), None)

    def get_card_by_id(self, card_id: str) -> Optional[CreditCard]:
        return next((c for c in self.credit_cards if c.id == card_id), None)

    def to_dict(self) -> dict:
        return {
            "id": self.id,
            "name": self.name,
            "email": self.email
        }

    @staticmethod
    def from_dict(data: dict) -> User:
        # Cria o usuário base
        user = User(
            id=data["id"],
            name=data["name"],
            email=data["email"]
        )

        # Addresses (compatível com postalCode ou postal_code)
        for addr in data.get("addresses", []):
            address = Address.from_dict({
                "id": addr["id"],
                "user_id": data["id"],
                "street": addr["street"],
                "number": addr["number"],
                "city": addr["city"],
                "state": addr["state"],
                "postal_code": addr.get("postal_code") or addr.get("postalCode"),
                "country": addr["country"]
            })
            user.add_address(address)

        # Credit Cards (compatível com number ou last4Digits + expirationDate)
        for card in data.get("credit_cards", []) + data.get("creditCards", []):
            if "number" in card:
                number = card["number"]
            elif "last4Digits" in card:
                number = f"****-****-****-{card['last4Digits']}"
            else:
                raise ValueError("Cartão inválido: número não encontrado")

            if "expiry_month" in card and "expiry_year" in card:
                expiry_month = int(card["expiry_month"])
                expiry_year = int(card["expiry_year"])
            elif "expirationDate" in card:
                parts = card["expirationDate"].split("/")
                expiry_month = int(parts[0])
                expiry_year = int("20" + parts[1]) if len(parts[1]) == 2 else int(parts[1])
            else:
                raise ValueError("Cartão inválido: validade não encontrada")

            credit_card = CreditCard.from_dict({
                "id": card["id"],
                "user_id": data["id"],
                "number": number,
                "expiry_month": expiry_month,
                "expiry_year": expiry_year,
                "brand": "VISA"
            })
            user.add_credit_card(credit_card)

        return user
