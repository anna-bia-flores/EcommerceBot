import requests

class AddressApiAdapter:
    BASE_URL = "http://localhost:8080/api/users"

    def get_addresses(self, user_id):
        try:
            response = requests.get(f"{self.BASE_URL}/{user_id}/addresses")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar endereços: {e}")
            return []

    def create_address(self, user_id, address_data):
        try:
            response = requests.post(f"{self.BASE_URL}/{user_id}/addresses", json=address_data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao criar endereço: {e}")
            return None

    def update_address(self, user_id, address_id, data):
        try:
            response = requests.put(f"{self.BASE_URL}/{user_id}/addresses/{address_id}", json=data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao atualizar endereço: {e}")
            return None

    def delete_address(self, user_id, address_id):
        try:
            response = requests.delete(f"{self.BASE_URL}/{user_id}/addresses/{address_id}")
            response.raise_for_status()
            return True
        except Exception as e:
            print(f"Erro ao deletar endereço: {e}")
            return False