import requests

class UserApiAdapter:
    BASE_URL = "http://localhost:8080/users"

    def get_user(self, user_id):
        try:
            response = requests.get(f"{self.BASE_URL}/{user_id}")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar usuário: {e}")
            return None

    def get_all_users(self):
        try:
            response = requests.get(self.BASE_URL)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao buscar todos os usuários: {e}")
            return []

    def login_user(self, email, password):
        try:
            response = requests.post(f"{self.BASE_URL}/login", json={
                "email": email,
                "password": password
            })
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao fazer login: {e}")
            return None

    def update_user(self, user_id, data):
        try:
            response = requests.put(f"{self.BASE_URL}/{user_id}", json=data)
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Erro ao atualizar usuário: {e}")
            return None