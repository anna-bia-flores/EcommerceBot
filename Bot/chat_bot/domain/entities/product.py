class Product:
    def __init__(
        self,
        id: str,
        name: str,
        price: float,
        stock: int,
        image_url: str = "",
        description: str = "",
        category: str = "",
        tags: list[str] = None
    ):
        # Conversões seguras de tipos
        self.id = str(id)
        self.name = str(name)
        self.price = float(price)
        self.stock = int(stock)
        self.image_url = image_url
        self.description = description
        self.category = category
        self.tags = tags or []

        if not self.id or not self.name or self.price < 0 or self.stock < 0:
            raise ValueError("Produto inválido")

    def is_available(self, quantity: int = 1):
        return self.stock >= quantity

    def is_out_of_stock(self):
        return self.stock == 0

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "price": self.price,
            "stock": self.stock,
            "image_url": self.image_url,
            "description": self.description,
            "category": self.category,
            "tags": self.tags,
        }
