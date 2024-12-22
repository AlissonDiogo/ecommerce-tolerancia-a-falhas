# Projeto exchange

Este projeto representa o serviço exchange, que provê uma API para retornar a atual cotação do dolar.

## Como executar o projeto

### Pré-requisitos (sem docker) <hr/>
- Ter o go instalado. Caso não tenha, acesse a página de download [aqui](https://go.dev/dl/).
- Ter o repositório clonado. Caso não tenha, clone com: 

```shell 
git clone https://github.com/AlissonDiogo/ecommerce-tolerancia-a-falhas.git
```


### Execução <hr/>

1. Se estiver na raiz do repositório, acesse a pasta exchange
```shell
cd exchange
```
2. Rode o projeto com o comando abaixo
```shell
go run main.go
```
3. Caso necessite iniciar a API em outra porta:
```shell
PORT=8083 go run main.go
```
4. Caso queira o valor atual(e não fictício) do dolar insira a variavel de ambiente "EXCHANGE_KEY" do [ExchangeRate-API](app.exchangerate-api.com)
```shell
PORT=8083 EXCHANGE_KEY="your_api_key" go run main.go
```

### Pré-requisitos (com docker) <hr/>
- Ter o repositório clonado. Caso não tenha, clone com: 
```shell 
git clone https://github.com/AlissonDiogo/ecommerce-tolerancia-a-falhas.git
```
- Ter o docker instalado
- Iniciar o docker swarm, caso não tenha iniciado.
```shell 
docker swarm init
```

### Execução <hr/>

1. Se estiver na raiz do repositório, acesse a pasta exchange
```shell
cd exchange
```
2. Crie a imagem a partir do Dockerfile já presente no projeto
```shell
docker build -t exchange-api:latest .
```
3. Crie o serviço com 2 replicas a partir da imagem criada
```shell
docker service create --name exchange-api --replicas 2 --restart-delay 10s -p 8083:8083 -e PORT=8083 exchange-api:latest
```
Altere os parâmetros de "replicas", "restart-delay" e portas, de acordo com a necessidade.
4. Para remover o serviço:
```shell
docker service rm exchange-api
```

