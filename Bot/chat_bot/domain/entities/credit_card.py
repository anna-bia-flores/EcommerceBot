from __future__ import annotations
from datetime import datetime

class CreditCard:
    def __init__(self, id: str, user_id: str, number: str, expiry_month: int, expiry_year: int, brand: str):
        if not id or not user_id or not number or expiry_month < 1 or expiry_month > 12 or expiry_year < 2020:
            raise ValueError("Dados do cartão inválidos")

        self.id = id
        self.user_id = user_id
        self.number = number
        self.expiry_month = expiry_month
        self.expiry_year = expiry_year
        self.brand = brand

    def get_last_digits(self):
        return self.number[-4:]

    @staticmethod
    def from_dict(data: dict) -> CreditCard:
        required_keys = ["id", "user_id", "number", "expiry_month", "expiry_year", "brand"]
        if not all(k in data for k in required_keys):
            raise ValueError("Dados incompletos para criar um cartão")

        return CreditCard(
            id=data["id"],
            user_id=data["user_id"],
            number=data["number"],
            expiry_month=int(data["expiry_month"]),
            expiry_year=int(data["expiry_year"]),
            brand=data["brand"]
        )

    def is_expired(self):
        now = datetime.now()
        return (self.expiry_year < now.year) or (self.expiry_year == now.year and self.expiry_month < now.month)

    def is_valid(self):
        return not self.is_expired()

    def belongs_to(self, user):
        return self.user_id == user.id

    def to_dict(self):
        return {
            "id": self.id,
            "user_id": self.user_id,
            "last_digits": self.get_last_digits(),
            "expiry_month": self.expiry_month,
            "expiry_year": self.expiry_year,
            "brand": self.brand
        }
