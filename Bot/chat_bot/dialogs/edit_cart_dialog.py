from botbuilder.schema import Activity, ActivityTypes
from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult, TextPrompt
from botbuilder.core import MessageFactory
from components.cards.order_summary_card import render_order_summary_hero_card

class EditCartDialog(ComponentDialog):
    def __init__(self):
        super(EditCartDialog, self).__init__(EditCartDialog.__name__)
        self.add_dialog(TextPrompt("TextPrompt"))
        self.add_dialog(WaterfallDialog("EditFlow", [
            self.show_cart,
            self.ask_action,
            self.handle_action
        ]))
        self.initial_dialog_id = "EditFlow"

    async def show_cart(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        card = render_order_summary_hero_card(session.cart)
        await step_context.context.send_activity(Activity(type=ActivityTypes.message, attachments=[card]))
        return await step_context.prompt("TextPrompt", "Digite o número do item para remover ou '0' para limpar o carrinho:")

    async def ask_action(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        selection = step_context.result.strip()

        if selection == "0":
            session.clear_cart()
            await step_context.context.send_activity("Carrinho limpo.")
            return await step_context.end_dialog()

        index = int(selection) - 1
        if 0 <= index < len(session.cart.items):
            removed = session.cart.items[index]
            session.cart.remove_item(removed.product.id)
            await step_context.context.send_activity(f"{removed.product.name} removido do carrinho.")
        else:
            await step_context.context.send_activity("Item inválido.")
        return await step_context.end_dialog()

    async def handle_action(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        return await step_context.end_dialog()