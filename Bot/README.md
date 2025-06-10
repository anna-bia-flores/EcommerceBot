## Guia de Execução do Projeto: Chat Bot

Este projeto consiste em um bot de resposta automatizada (chat_bot) desenvolvido em Python. Siga os passos abaixo para configurar e executar o projeto localmente no seu ambiente.

---

### 1. Navegue até o diretório do projeto

Acesse a pasta onde o projeto está localizado. Isso garante que todos os comandos sejam executados no contexto correto.

```bash
cd chat_bot
```

---

### 2. Instale as dependências do projeto

Utilize o `requirements.txt` para instalar automaticamente todas as bibliotecas Python utilizadas pelo projeto.

```bash
pip install -r requirements.txt
```

> ⚠️ Certifique-se de estar utilizando um ambiente virtual (`venv`) para isolar as dependências do projeto.

---

### 3. Inicie a aplicação do bot

Com as dependências instaladas, inicie o bot com o seguinte comando:

```bash
python app.py
```

Esse comando executa o script principal que inicializa o servidor do bot.

---

### 4. Confirme que o bot está rodando corretamente

Se tudo estiver correto, você deve ver uma mensagem semelhante a esta no terminal:

```text
Running on http://localhost:3978
```

Isso indica que o bot está ativo, aguardando requisições HTTP na porta 3978.

> 🔗 Acesse `http://localhost:3978` no navegador ou configure um canal de comunicação (como o Bot Framework Emulator) para testar seu bot localmente.

---

### 5. Encerrando o bot

Para finalizar a execução do bot, pressione `CTRL + C` no terminal.

---
