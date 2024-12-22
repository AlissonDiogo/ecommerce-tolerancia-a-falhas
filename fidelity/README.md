# Fidelity

## Sobre o serviço

Serviço que salva um bônus para o usuário após a compra desenvolvido utilizando Node.js e TypeScript.

---

## Como executar o projeto

Clone o repositório. Caso não tenha, execute no terminal:

```bash
git clone https://github.com/AlissonDiogo/ecommerce-tolerancia-a-falhas.git
```

Se estiver na raiz do repositório, acesse a pasta do serviço no terminal/cmd:

```bash
cd fidelity
```

### Sem Docker

Para executar o projeto sem o Docker, siga os seguintes passos:

1. É preciso ter o Node.js instalado na sua máquina.
2. Instale as dependências

```bash
npm ci
```

3. Execute a aplicação

```bash
npm start
```

### Com Docker

1. É preciso ter o Docker configurado na sua máquina.
2. No terminal execute:

```bash
docker compose up -d
```
