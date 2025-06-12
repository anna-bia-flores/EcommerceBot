from botbuilder.schema import HeroCard, CardAction, ActionTypes, Attachment

def render_address_hero_card(address) -> Attachment:
    card = HeroCard(
        title="Endereço",
        text=address.get_full_address(),
        buttons=[
            CardAction(
                type=ActionTypes.post_back,
                title="Selecionar",
                value={"action": "select_address", "id": address.id}
            )
        ]
    )

    return Attachment(
        content_type="application/vnd.microsoft.card.hero",
        content=card
    )
