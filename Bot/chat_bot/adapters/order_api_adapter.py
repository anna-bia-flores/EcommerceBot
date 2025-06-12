import requests

class OrderApiAdapter:
    BASE_URL = "http://localhost:8080/api/orders"

    def place_order(self, order_data):
        try:
            response = requests.post(self.BASE_URL, json=order_data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao criar pedido: {e}")
            return None

    def list_orders_by_user(self, user_id):
        try:
            response = requests.get(f"{self.BASE_URL}?userId={user_id}")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao listar pedidos: {e}")
            return []

    def get_order(self, order_id):
        try:
            response = requests.get(f"{self.BASE_URL}/{order_id}")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar pedido: {e}")
            return None