from botbuilder.dialogs import (
    ComponentDialog,
    WaterfallDialog,
    WaterfallStepContext,
    DialogTurnResult,
    TextPrompt,
    PromptOptions
)
from botbuilder.core import MessageFactory

from adapters.user_api_adapter import UserApiAdapter
from domain.entities.user import User
from session.user_session import UserSession


class LoginDialog(ComponentDialog):
    def __init__(self):
        super(LoginDialog, self).__init__(LoginDialog.__name__)
        self.api = UserApiAdapter()

        self.add_dialog(TextPrompt("TextPrompt"))
        self.add_dialog(WaterfallDialog("MainFlow", [
            self.ask_email,
            self.ask_password,
            self.do_login,
            self.redirect_if_needed
        ]))
        self.initial_dialog_id = "MainFlow"

    async def ask_email(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        return await step_context.prompt("TextPrompt", PromptOptions(
            prompt=MessageFactory.text("📧 Digite seu e-mail:")
        ))

    async def ask_password(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        step_context.values["email"] = step_context.result
        return await step_context.prompt("TextPrompt", PromptOptions(
            prompt=MessageFactory.text("🔑 Digite sua senha:")
        ))

    async def do_login(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        email = step_context.values["email"]
        password = step_context.result

        result = self.api.login_user(email, password)
        if result is None:
            await step_context.context.send_activity("❌ E-mail ou senha inválidos. Vamos tentar de novo.")
            options = step_context.options or {}
            redirect_to = options.get("redirect_to")
            other_options = {k: v for k, v in options.items() if k != "redirect_to"}
            return await step_context.replace_dialog(LoginDialog.__name__, {
                "redirect_to": redirect_to,
                **other_options
            })

        session: UserSession = step_context.context.turn_state.get("session")
        user = User.from_dict(result)
        session.login(user)

        await step_context.context.send_activity("✅ Login realizado com sucesso!")
        return await step_context.next(None)

    async def redirect_if_needed(self, step_context: WaterfallStepContext) -> DialogTurnResult:
        options = step_context.options or {}
        redirect_to = options.get("redirect_to")
        redirect_options = {k: v for k, v in options.items() if k != "redirect_to"}

        if redirect_to:
            return await step_context.begin_dialog(redirect_to, redirect_options)

        # 🔁 Se não tiver redirecionamento, volta ao menu principal
        return await step_context.replace_dialog("MainMenuDialog")
