from __future__ import annotations

class Address:
    def __init__(self, id: str, street: str, number: str, city: str, state: str, postal_code: str, country: str):
        required = [id, street, number, city, state, postal_code, country]
        if not all(required):
            raise ValueError("Todos os campos do endereço são obrigatórios")

        self.id = id
        self.street = street
        self.number = number
        self.city = city
        self.state = state
        self.postal_code = postal_code
        self.country = country

    def is_valid(self) -> bool:
        return all([
            self.street.strip(),
            self.number.strip(),
            self.city.strip(),
            self.state.strip(),
            self.postal_code.strip(),
            self.country.strip()
        ])

    def get_full_address(self) -> str:
        return f"{self.street}, {self.number} - {self.city}, {self.state} - {self.postal_code}, {self.country}"

    def to_dict(self) -> dict:
        return {
            "id": self.id,
            "street": self.street,
            "number": self.number,
            "city": self.city,
            "state": self.state,
            "postal_code": self.postal_code,
            "country": self.country
        }

    @staticmethod
    def from_dict(data: dict) -> Address:
        required_keys = ["id", "street", "number", "city", "state", "postal_code", "country"]
        if not all(k in data for k in required_keys):
            raise ValueError("Dados incompletos para criar um endereço")

        return Address(
            id=data["id"],
            street=data["street"],
            number=data["number"],
            city=data["city"],
            state=data["state"],
            postal_code=data["postal_code"],
            country=data["country"]
        )
