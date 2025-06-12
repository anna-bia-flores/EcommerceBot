from botbuilder.schema import HeroCard, CardAction, CardImage, Attachment
from botbuilder.schema import Activity, ActivityTypes, ActionTypes
from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult
from adapters.product_api_adapter import ProductApiAdapter

class ListProductsDialog(ComponentDialog):
    def __init__(self):
        super(ListProductsDialog, self).__init__(ListProductsDialog.__name__)
        self.api = ProductApiAdapter()

        self.add_dialog(
            WaterfallDialog("ListProductsWaterfall", [
                self.show_products_step
            ])
        )

        self.initial_dialog_id = "ListProductsWaterfall"

    async def show_products_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        products = self.api.get_all_products()
        if not products:
            await step_context.context.send_activity("Nenhum produto encontrado.")
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
                        value={
                            "action": "ver",
                            "product_id": product["id"]
                        }
                    ),
                    CardAction(
                        type=ActionTypes.post_back,
                        title="Adicionar ao carrinho",
                        value={
                            "action": "adicionar",
                            "product_id": product["id"]
                        }
                    ),
                    CardAction(
                        type=ActionTypes.post_back,
                        title="Comprar agora",
                        value={
                            "action": "comprar",
                            "product_id": product["id"]
                        }
                    )
                ]
            )


            attachments.append(Attachment(content_type="application/vnd.microsoft.card.hero", content=card))

        await step_context.context.send_activity(Activity(
            type=ActivityTypes.message,
            attachments=attachments
        ))

        await step_context.context.send_activity("Escolha uma opção clicando nos botões.")
        return await step_context.end_dialog()
