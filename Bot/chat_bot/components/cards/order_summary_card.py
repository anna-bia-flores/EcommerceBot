from botbuilder.schema import HeroCard, CardAction, ActionTypes, Attachment

from domain.entities.cart import Cart

def render_order_summary_hero_card(cart: "Cart") -> Attachment:
    item_lines = "\n".join([f"{item.quantity}x {item.product["name"]}" for item in cart.items])
    total = cart.get_total()

    card = HeroCard(
        title="Resumo do Pedido",
        text=f"{item_lines}\n\n**Total: R$ {total:.2f}**",
        buttons=[
            CardAction(
                type=ActionTypes.post_back,
                title="Confirmar Pedido",
                value={"action": "confirm_order"}
            )
        ]
    )
    return Attachment(
        content_type="application/vnd.microsoft.card.hero",
        content=card
    )