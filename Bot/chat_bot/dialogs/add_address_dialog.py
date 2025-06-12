from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult, TextPrompt
from adapters.address_api_adapter import AddressApiAdapter

class AddAddressDialog(ComponentDialog):
    def __init__(self):
        super(AddAddressDialog, self).__init__(AddAddressDialog.__name__)
        self.api = AddressApiAdapter()

        self.add_dialog(TextPrompt("TextPrompt"))
        self.add_dialog(WaterfallDialog("Flow", [
            self.ask_street,
            self.ask_number,
            self.ask_city,
            self.ask_state,
            self.ask_postal_code,
            self.ask_country,
            self.save_address
        ]))
        self.initial_dialog_id = "Flow"

    async def ask_street(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        return await step_context.prompt("TextPrompt", "Rua:")

    async def ask_number(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["street"] = step_context.result
        return await step_context.prompt("TextPrompt", "Número:")

    async def ask_city(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["number"] = step_context.result
        return await step_context.prompt("TextPrompt", "Cidade:")

    async def ask_state(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["city"] = step_context.result
        return await step_context.prompt("TextPrompt", "Estado:")

    async def ask_postal_code(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["state"] = step_context.result
        return await step_context.prompt("TextPrompt", "CEP:")

    async def ask_country(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["postal_code"] = step_context.result
        return await step_context.prompt("TextPrompt", "País:")

    async def save_address(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["country"] = step_context.result
        session = step_context.context.turn_state.get("session")

        payload = {
            "street": step_context.values["street"],
            "number": step_context.values["number"],
            "city": step_context.values["city"],
            "state": step_context.values["state"],
            "postal_code": step_context.values["postal_code"],
            "country": step_context.values["country"]
        }

        result = self.api.add_address(session.user.id, payload)
        if result:
            await step_context.context.send_activity("Endereço adicionado com sucesso.")
        else:
            await step_context.context.send_activity("Erro ao adicionar endereço.")
        return await step_context.end_dialog()