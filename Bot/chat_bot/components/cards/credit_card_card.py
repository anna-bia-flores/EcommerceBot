from botbuilder.schema import HeroCard, CardAction, ActionTypes, Attachment

from domain.entities.credit_card import CreditCard

def render_credit_card_hero_card(card: "CreditCard") -> Attachment:
    label = f"{card.brand} **** {card.last_digits}"
    card = HeroCard(
        title="Cartão de Crédito",
        text=label,
        buttons=[
            CardAction(
                type=ActionTypes.post_back,
                title="Selecionar",
                value={"action": "select_card", "id": card.id}
            )
        ]
    )
    return Attachment(
        content_type="application/vnd.microsoft.card.hero",
        content=card
    )
