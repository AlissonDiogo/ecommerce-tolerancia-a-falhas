# Projeto exchange

Este projeto representa o serviço exchange, que provê uma API para retornar a atual cotação do dolar.

## Como executar o projeto

### Pré-requisitos <hr/>
- Ter o go instalado. Caso não tenha, acesse a página de download [aqui](https://go.dev/dl/).
- Ter o repositório clonado. Caso não tenha, clone com: 

```shell 
git clone https://github.com/AlissonDiogo/ecommerce-intolerancia-a-falhas.git
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
PORT=8081 go run main.go
```
4. Caso queira o valor atual(e não fictício) do dolar insira a variavel de ambiente "EXCHANGE_KEY" do [ExchangeRate-API](app.exchangerate-api.com)
```shell
PORT=8081 EXCHANGE_KEY="your_api_key" go run main.go
```

