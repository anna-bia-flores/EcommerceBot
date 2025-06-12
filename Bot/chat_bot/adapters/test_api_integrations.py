from user_api_adapter import UserApiAdapter
from product_api_adapter import ProductApiAdapter
from order_api_adapter import OrderApiAdapter
from address_api_adapter import AddressApiAdapter
from credit_card_api_adapter import CreditCardApiAdapter

def test_all_adapters():
    print("🔍 Iniciando testes de integração...")


    product_api = ProductApiAdapter()
    print("Produtos:", product_api.get_all_products())


if __name__ == "__main__":
    test_all_adapters()