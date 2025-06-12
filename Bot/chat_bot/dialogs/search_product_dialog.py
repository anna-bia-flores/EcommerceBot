from botbuilder.schema import HeroCard, CardAction, CardImage, Attachment
from botbuilder.schema import Activity, ActivityTypes, ActionTypes
from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult, TextPrompt, PromptOptions
from botbuilder.core import MessageFactory

from adapters.product_api_adapter import ProductApiAdapter


class SearchProductDialog(ComponentDialog):
    def __init__(self):
        super(SearchProductDialog, self).__init__(SearchProductDialog.__name__)
        self.api = ProductApiAdapter()

        self.add_dialog(TextPrompt("ProductNamePrompt"))

        self.add_dialog(
            WaterfallDialog("SearchProductWaterfall", [
                self.ask_product_name_step,
                self.search_and_show_products_step
            ])
        )

        self.initial_dialog_id = "SearchProductWaterfall"

    async def ask_product_name_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        return await step_context.prompt("ProductNamePrompt", PromptOptions(
            prompt=MessageFactory.text("🔍 Digite o nome do produto que você está procurando:")
        ))

    async def search_and_show_products_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        query = step_context.result.strip()
        products = self.api.search_products(query)

        if not products:
            await step_context.context.send_activity(f"Nenhum produto encontrado com o nome **{query}**.")
            return await step_context.end_dialog()

        attachments = []
        for product in products:
            card = HeroCard(
                title=product["name"],
                text=f"R$ {float(product['price']):.2f}",
                images=[CardImage(url=product["image_url"])] if product.get("image_url") else [],
                buttons=[
                    CardAction(
                        type=ActionTypes.post_back,
                        title="Ver detalhes",
                        value={"action": "ver", "product_id": product["id"]}
                    ),
                    CardAction(
                        type=ActionTypes.post_back,
                        title="Adicionar ao carrinho",
                        value={"action": "adicionar", "product_id": product["id"]}
                    ),
                    CardAction(
                        type=ActionTypes.post_back,
                        title="Comprar agora",
                        value={"action": "comprar", "product_id": product["id"]}
                    )
                ]
            )
            attachments.append(Attachment(content_type=HeroCard.content_type, content=card))

        await step_context.context.send_activity(Activity(
            type=ActivityTypes.message,
            attachments=attachments
        ))

        await step_context.context.send_activity(f"🧾 Resultado da busca por: **{query}**")
        return await step_context.end_dialog()
