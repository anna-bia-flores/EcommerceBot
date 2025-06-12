from botbuilder.schema import HeroCard, CardAction, ActionTypes, Attachment

def render_menu_card(title: str, options: list[str], action_prefix: str = "menu_") -> Attachment:
    """
    Gera um HeroCard com botões para cada opção do menu.
    """
    card = HeroCard(
        title=title,
        buttons=[
            CardAction(
                type=ActionTypes.post_back,
                title=option,
                value={"action": f"{action_prefix}{i}", "label": option}
            )
            for i, option in enumerate(options)
        ]
    )
    return Attachment(
        content_type="application/vnd.microsoft.card.hero",
        content=card
    )
