from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult, TextPrompt
from adapters.credit_card_api_adapter import CreditCardApiAdapter

class AddCardDialog(ComponentDialog):
    def __init__(self):
        super(AddCardDialog, self).__init__(AddCardDialog.__name__)
        self.api = CreditCardApiAdapter()

        self.add_dialog(TextPrompt("TextPrompt"))
        self.add_dialog(WaterfallDialog("Flow", [
            self.ask_number,
            self.ask_holder,
            self.ask_expiry,
            self.ask_cvv,
            self.save_card
        ]))
        self.initial_dialog_id = "Flow"

    async def ask_number(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        return await step_context.prompt("TextPrompt", "Número do cartão:")

    async def ask_holder(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["number"] = step_context.result
        return await step_context.prompt("TextPrompt", "Nome no cartão:")

    async def ask_expiry(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["holder"] = step_context.result
        return await step_context.prompt("TextPrompt", "Data de expiração (MM/AA):")

    async def ask_cvv(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["expiry"] = step_context.result
        return await step_context.prompt("TextPrompt", "CVV:")

    async def save_card(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["cvv"] = step_context.result
        session = step_context.context.turn_state.get("session")

        payload = {
            "number": step_context.values["number"],
            "holder": step_context.values["holder"],
            "expiry": step_context.values["expiry"],
            "cvv": step_context.values["cvv"]
        }

        result = self.api.add_card(session.user.id, payload)
        if result:
            await step_context.context.send_activity("Cartão adicionado com sucesso.")
        else:
            await step_context.context.send_activity("Erro ao adicionar cartão.")
        return await step_context.end_dialog()