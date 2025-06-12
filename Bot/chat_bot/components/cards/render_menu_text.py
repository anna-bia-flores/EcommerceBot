def render_menu_text(title: str, options: list[str]) -> str:
    if not options:
        return f"{title}\n(Nenhuma opção disponível)"

    lines = [f"{title.strip()}\n"]
    for i, option in enumerate(options, 1):
        lines.append(f"{i}. {option}")
    lines.append("\nDigite o número ou nome da opção:")
    return "\n".join(lines)
