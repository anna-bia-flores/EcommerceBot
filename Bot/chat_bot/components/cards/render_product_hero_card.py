from botbuilder.schema import HeroCard, CardAction, CardImage, ActionTypes, Attachment
from domain.entities.product import Product

def render_product_hero_card(product: Product | dict, use_im_back: bool = False) -> Attachment:
    if isinstance(product, dict):
        product = Product(**product)

    action_type = ActionTypes.im_back if use_im_back else ActionTypes.post_back

    card = HeroCard(
        title=product.name,
        images=[CardImage(url=product.image_url)] if product.image_url else [],
        text=f"R$ {product.price:.2f}",
        buttons=[
            CardAction(type=action_type, title="Ver detalhes", value=f"details:{product.id}"),
            CardAction(type=action_type, title="Adicionar ao carrinho", value=f"add_to_cart:{product.id}"),
            CardAction(type=action_type, title="Comprar agora", value=f"buy_now:{product.id}")
        ]
    )

    return Attachment(content_type="application/vnd.microsoft.card.hero", content=card)
