import requests

class ProductApiAdapter:
    BASE_URL = "http://localhost:8080/api/products"

    def get_product(self, product_id):
        try:
            response = requests.get(f"{self.BASE_URL}/{product_id}")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar produto: {e}")
            return None

    def get_all_products(self):
        try:
            response = requests.get(self.BASE_URL)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao listar produtos: {e}")
            return []

    def search_products(self, term):
        try:
            response = requests.get(f"{self.BASE_URL}/search", params={"q": term})
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar produtos: {e}")
            return []