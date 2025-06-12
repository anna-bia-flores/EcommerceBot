from botbuilder.schema import Activity, ActivityTypes, HeroCard, CardAction, ActionTypes, Attachment
from botbuilder.dialogs import ComponentDialog, WaterfallDialog, WaterfallStepContext, DialogTurnResult, DialogTurnStatus
from botbuilder.core import MessageFactory
from adapters.order_api_adapter import OrderApiAdapter  # você precisa ter esse adapter

class ListOrdersDialog(ComponentDialog):
    def __init__(self):
        super().__init__(ListOrdersDialog.__name__)
        self.api = OrderApiAdapter()

        self.add_dialog(
            WaterfallDialog("ListOrdersWaterfall", [
                self.fetch_orders_step
            ])
        )

        self.initial_dialog_id = "ListOrdersWaterfall"

    async def fetch_orders_step(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        session = step_context.context.turn_state.get("session")

        if not session or not session.user:
            await step_context.context.send_activity(
                "🔒 Você precisa estar logado para adicionar produtos ao carrinho. Vamos fazer isso agora."
            )
            return await step_context.begin_dialog("LoginDialog", {
                "redirect_to": ListOrdersDialog.__name__,
            })


        orders = self.api.list_orders_by_user(session.user.id)
        if not orders:
            await step_context.context.send_activity("Você ainda não fez nenhum pedido.")
            return await step_context.end_dialog()

        for order in orders:
            items_summary = "\n".join([f"- {item['product']['name']} (x{item['quantity']})" for item in order["items"]])
            total = order.get("total", 0.0)
            card = HeroCard(
                title=f"Pedido #{order['id']}",
                subtitle=f"Total: R$ {total:.2f}",
                text=items_summary,
                buttons=[
                    CardAction(
                        type=ActionTypes.open_url,
                        title="Ver detalhes",
                        value=f"https://seusite.com/pedidos/{order['id']}"
                    )
                ]
            )
            await step_context.context.send_activity(
                Activity(type=ActivityTypes.message, attachments=[Attachment(content_type=HeroCard.content_type, content=card)])
            )

        return await step_context.end_dialog()
