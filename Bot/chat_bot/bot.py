from botbuilder.core import ActivityHandler, TurnContext, ConversationState, UserState
from botbuilder.dialogs import DialogSet, DialogTurnStatus
from botbuilder.schema import ChannelAccount

from session.user_session import UserSession

# Diálogos
from dialogs.search_product_dialog import SearchProductDialog
from dialogs.list_orders_dialog import ListOrdersDialog
from dialogs.main_menu_dialog import MainMenuDialog
from dialogs.list_products_dialog import ListProductsDialog
from dialogs.add_to_cart_dialog import AddToCartDialog
from dialogs.login_dialog import LoginDialog
from dialogs.start_checkout_dialog import StartCheckoutDialog
from dialogs.product_details_dialog import ProductDetailsDialog
from dialogs.add_address_dialog import AddAddressDialog
from dialogs.add_card_dialog import AddCardDialog
from dialogs.edit_cart_dialog import EditCartDialog


class MyBot(ActivityHandler):
    def __init__(self, conversation_state: ConversationState, user_state: UserState):
        self.conversation_state = conversation_state
        self.user_state = user_state

        self.dialog_state = self.conversation_state.create_property("DialogState")
        self.user_session_accessor = self.user_state.create_property("UserSession")

        self.dialogs = DialogSet(self.dialog_state)

        # Registro dos diálogos
        self.dialogs.add(MainMenuDialog())
        self.dialogs.add(ListProductsDialog())
        self.dialogs.add(AddToCartDialog())
        self.dialogs.add(LoginDialog())
        self.dialogs.add(StartCheckoutDialog())
        self.dialogs.add(ProductDetailsDialog())
        self.dialogs.add(AddAddressDialog())
        self.dialogs.add(AddCardDialog())
        self.dialogs.add(EditCartDialog())
        self.dialogs.add(SearchProductDialog())

        self.dialogs.add(ListOrdersDialog())

    async def on_message_activity(self, turn_context: TurnContext):
        # ✅ Garante que a sessão esteja carregada ou inicializada do UserState
        session = await self.user_session_accessor.get(turn_context, lambda: UserSession())
        turn_context.turn_state["session"] = session

        dialog_context = await self.dialogs.create_context(turn_context)
        activity = turn_context.activity

        # 🔁 Botões com action
        if activity.value:
            action = activity.value.get("action")
            product_id = activity.value.get("product_id")

            if action and product_id:
                if action in ("ver", "details"):
                    return await dialog_context.begin_dialog("ProductDetailsDialog", {"product_id": product_id})
                elif action in ("adicionar", "add_to_cart"):
                    return await dialog_context.begin_dialog("AddToCartDialog", {"product_id": product_id})
                elif action in ("comprar", "buy_now"):
                    return await dialog_context.begin_dialog("StartCheckoutDialog", {"product_id": product_id})

        # ▶️ Fluxo normal de diálogo
        result = await dialog_context.continue_dialog()
        if result.status == DialogTurnStatus.Empty:
            await dialog_context.begin_dialog(MainMenuDialog.__name__)

        # ✅ Salva a sessão atualizada no UserState
        await self.user_session_accessor.set(turn_context, session)
        await self.user_state.save_changes(turn_context)
        await self.conversation_state.save_changes(turn_context)

    async def on_members_added_activity(self, members_added: list[ChannelAccount], turn_context: TurnContext):
        pass
