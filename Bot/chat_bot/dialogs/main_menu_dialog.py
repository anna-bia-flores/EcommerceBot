from botbuilder.schema import Activity, ActivityTypes
from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult, DialogTurnStatus
from components.cards.render_menu_card import render_menu_card

class MainMenuDialog(ComponentDialog):
    def __init__(self):
        super().__init__(MainMenuDialog.__name__)

        self.add_dialog(
            WaterfallDialog("MainMenuWaterfall", [
                self.show_menu_step,
                self.handle_selection_step
            ])
        )

        self.initial_dialog_id = "MainMenuWaterfall"

    async def show_menu_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")
        options = ["Ver produtos", "Pesquisar produto", "Ver meus pedidos"]

        if session and not session.cart.is_empty():
            options += ["Editar carrinho", "Finalizar compra"]

        card = render_menu_card("O que você deseja fazer?", options)
        await step_context.context.send_activity(Activity(type=ActivityTypes.message, attachments=[card]))

        return DialogTurnResult(status=DialogTurnStatus.Waiting)

    async def handle_selection_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        selection = (step_context.context.activity.value or {}).get("label") or step_context.context.activity.text or ""

        match selection:
            case "Ver produtos":
                return await step_context.begin_dialog("ListProductsDialog")
            case "Pesquisar produto":
                return await step_context.begin_dialog("SearchProductDialog")
            case "Ver meus pedidos":
                return await step_context.begin_dialog("ListOrdersDialog")
            case "Editar carrinho":
                return await step_context.begin_dialog("EditCartDialog")
            case "Finalizar compra":
                return await step_context.begin_dialog("StartCheckoutDialog")
            case _:
                await step_context.context.send_activity("Opção inválida. Por favor, clique em uma das opções.")
                return await step_context.replace_dialog(self.initial_dialog_id)
