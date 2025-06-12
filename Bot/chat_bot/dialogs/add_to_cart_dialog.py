from botbuilder.dialogs import (
    ComponentDialog,
    WaterfallDialog,
    WaterfallStepContext,
    DialogTurnResult,
    NumberPrompt,
    PromptOptions,
    TextPrompt
)
from botbuilder.dialogs.prompts import PromptValidatorContext
from botbuilder.core import MessageFactory
from adapters.product_api_adapter import ProductApiAdapter


class AddToCartDialog(ComponentDialog):
    def __init__(self):
        super(AddToCartDialog, self).__init__(AddToCartDialog.__name__)
        self.api = ProductApiAdapter()

        self.add_dialog(TextPrompt("TextPrompt"))
        self.add_dialog(NumberPrompt("QuantityPrompt", AddToCartDialog.validate_quantity))

        self.add_dialog(WaterfallDialog("AddToCartFlow", [
            self.ask_quantity_step,
            self.add_to_cart_step
        ]))

        self.initial_dialog_id = "AddToCartFlow"

    async def ask_quantity_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        product_id = step_context.options.get("product_id")
        if not product_id:
            await step_context.context.send_activity("❌ Produto não informado.")
            return await step_context.end_dialog()

        product = self.api.get_product(product_id)
        if not product:
            await step_context.context.send_activity("⚠️ Produto não encontrado.")
            return await step_context.end_dialog()

        step_context.values["product"] = product

        return await step_context.prompt("QuantityPrompt", PromptOptions(
            prompt=MessageFactory.text(
                f"🛒 Quantas unidades de **{product['name']}** você deseja adicionar ao carrinho?"
            )
        ))

    async def add_to_cart_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        quantity = step_context.result
        product = step_context.values["product"]
        session = step_context.context.turn_state.get("session")

        if not session or not session.user:
            await step_context.context.send_activity(
                "🔒 Você precisa estar logado para adicionar produtos ao carrinho. Vamos fazer isso agora."
            )
            return await step_context.begin_dialog("LoginDialog", {
                "redirect_to": AddToCartDialog.__name__,
                "product_id": product["id"]
            })

        session.add_to_cart_by_id(product["id"], quantity, self.api.get_product)

        await step_context.context.send_activity(
            f"✅ {quantity} unidade(s) de **{product['name']}** adicionadas ao carrinho."
        )

        # 🔁 Redireciona de volta ao menu principal
        return await step_context.replace_dialog("MainMenuDialog")

    @staticmethod
    async def validate_quantity(prompt_context: PromptValidatorContext) -> bool:
        try:
            value = int(prompt_context.recognized.value)
            return value > 0
        except:
            return False
