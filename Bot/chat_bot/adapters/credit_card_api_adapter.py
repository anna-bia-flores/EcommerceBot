import requests

class CreditCardApiAdapter:
    BASE_URL = "http://localhost:8080/api/users"

    def get_cards(self, user_id):
        try:
            response = requests.get(f"{self.BASE_URL}/{user_id}/cards")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar cartões: {e}")
            return []

    def create_card(self, user_id, card_data):
        try:
            response = requests.post(f"{self.BASE_URL}/{user_id}/cards", json=card_data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao criar cartão: {e}")
            return None

    def update_card(self, user_id, card_id, data):
        try:
            response = requests.put(f"{self.BASE_URL}/{user_id}/cards/{card_id}", json=data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao atualizar cartão: {e}")
            return None

    def delete_card(self, user_id, card_id):
        try:
            response = requests.delete(f"{self.BASE_URL}/{user_id}/cards/{card_id}")
            response.raise_for_status()
            return True
        except Exception as e:
            print(f"Erro ao deletar cartão: {e}")
            return False