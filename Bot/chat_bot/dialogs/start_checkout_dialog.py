from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult
from components.cards.order_summary_card import render_order_summary_hero_card
from adapters.product_api_adapter import ProductApiAdapter
from components.cards.render_product_hero_card import render_product_hero_card

class StartCheckoutDialog(ComponentDialog):
    def __init__(self):
        super(StartCheckoutDialog, self).__init__(StartCheckoutDialog.__name__)

        self.add_dialog(WaterfallDialog("CheckoutFlow", [
            self.check_login,
            self.select_address,
            self.select_card,
            self.confirm_order
        ]))
        self.initial_dialog_id = "CheckoutFlow"
        self.api = ProductApiAdapter()

    async def check_login(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        if not session.is_logged_in():
            return await step_context.begin_dialog("LoginDialog")
        return await step_context.next(step_context.options)

    async def select_address(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        if not session.addresses:
            return await step_context.begin_dialog("AddAddressDialog")
        return await step_context.next(step_context.options)

    async def select_card(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        if not session.cards:
            return await step_context.begin_dialog("AddCardDialog")
        return await step_context.next(step_context.options)

    async def confirm_order(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        product_id = step_context.options.get("product_id") if step_context.options else None

        if product_id:
            # Compra direta de um produto
            product = self.api.get_product_by_id(product_id)
            if not product:
                await step_context.context.send_activity("❌ Produto não encontrado.")
                return await step_context.end_dialog()

            card = render_product_hero_card(product)
            await step_context.context.send_activity(card)
            await step_context.context.send_activity(f"✅ Pedido de `{product['name']}` finalizado com sucesso!")
        else:
            # Checkout do carrinho
            card = render_order_summary_hero_card(session.cart)
            await step_context.context.send_activity(card)
            await step_context.context.send_activity("✅ Pedido finalizado com sucesso!")

        return await step_context.end_dialog()
