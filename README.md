<h1 align="center"> 
  Ecommerce service
</h1>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre</a> •
  <a href="#-funcionalidades">Funcionalidades</a> •
  <a href="#-como-executar-o-projeto">Como executar</a> •
  <a href="#-tecnologias">Tecnologias</a> • 
  <a href="#-autores">Autores</a>
</p>

<br>

## 💻 Sobre o projeto

Projeto desenvolvido para a disciplina de Tópicos Especiais em Engenharia de Software IV com uma simulação de um sistema de ecommerce que se comunica com outros serviços. O objetivo principal é projetar um sistema capaz de tolerar eventuais erros desses serviços.

---

## ⚙ Funcionalidades

1. Ecommerce: Sistema principal que possibilita a comunicação entre o cliente e os outros serviços responsáveis pela compra.
2. Store: Serviço que provê informações de produtos e confirmação de vendas.
3. Exchange: Serviço que informa o atual valor de câmbio entre BRL e USD.
4. Fidelity: Serviço que salva um bônus para o usuário após a compra.

---

## 🚀 Como executar o projeto

Clone este repositório

```bash
git clone https://github.com/AlissonDiogo/ecommerce-tolerancia-a-falhas.git
```

### Executar todos (Com Docker)

Inicie o swarm

```bash
docker swarm init
```

Rode o arquivo docker-compose.build.yml para buildar a imagem de todos os projetos

```bash
docker compose -f docker-compose.build.yml build
```

Faça o deploy do docker-compose.yml para executar todos os serviços de uma vez

```bash
docker stack deploy -c docker-compose.yml ecommerce_project
```

Para remover todos os serviços da stack de uma vez

```bash
docker stack rm ecommerce_project
```

### Executar individualmente (Com ou sem Docker)

Para executar os serviços:

1. [Ecommerce](ecommerce/README.md)
2. [Store](store/README.md)
3. [Exchange](exchange/README.md)
4. [Fidelity](fidelity/README.md)

---

## 🛠 Tecnologias

- Java e Spring
- Go
- Node.js e TypeScript

---

## 👥 Autores

- Alisson Diogo
- Fabiana Pereira
- Pedro Lucas
