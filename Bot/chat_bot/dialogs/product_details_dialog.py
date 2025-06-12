from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult
from botbuilder.schema import HeroCard, CardImage, CardAction, ActionTypes, Attachment, Activity, ActivityTypes
from adapters.product_api_adapter import ProductApiAdapter

class ProductDetailsDialog(ComponentDialog):
    def __init__(self):
        super(ProductDetailsDialog, self).__init__(ProductDetailsDialog.__name__)
        self.api = ProductApiAdapter()

        self.add_dialog(
            WaterfallDialog("ProductDetailsWaterfall", [
                self.show_details_step
            ])
        )
        self.initial_dialog_id = "ProductDetailsWaterfall"

    async def show_details_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        product_id = step_context.options.get("product_id")
        print(product_id)
        if not product_id:
            await step_context.context.send_activity("❌ ID do produto não fornecido.")
            return await step_context.end_dialog()

        product = self.api.get_product(product_id)
        if not product:
            await step_context.context.send_activity(f"⚠️ Produto com ID `{product_id}` não encontrado.")
            return await step_context.end_dialog()

        # Cria o HeroCard com detalhes do produto
        card = HeroCard(
            title=product["name"],
            subtitle=f"R$ {float(product['price']):.2f}",
            text=product.get("description", "Sem descrição disponível."),
            images=[CardImage(url=product["image_url"])] if product.get("image_url") else [],
            buttons=[
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



        await step_context.context.send_activity(Activity(
            type=ActivityTypes.message,
            attachments=[Attachment(
                content_type="application/vnd.microsoft.card.hero",
                content=card
            )]
        ))

        return await step_context.end_dialog()
