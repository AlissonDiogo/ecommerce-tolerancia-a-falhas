package main

import (
	"encoding/json"
	"exchange/types"
	"exchange/utils"
	"fmt"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	var value float64

	//endpoint exchange
	r.GET("/exchange", func(ctx *gin.Context) {
		if utils.CheckGenerateOfFail() {
			utils.StopProgram()
		}

		if value != 0.0 {
			ctx.JSON(http.StatusOK, gin.H{
				"value": value,
			})
		} else {
			ctx.JSON(http.StatusOK, gin.H{
				"value": utils.GenerateFakeDolarValue(),
			})
		}
	})

	if exchangeKey := os.Getenv("EXCHANGE_KEY"); exchangeKey != "" {
		value = requestToExchangeRateAPI(exchangeKey)
		if value != 0.0 {
			fmt.Printf("Valor do dólar: %f\n", value)
		}
	}

	r.Run()
}

// Faz a requisição à API de cotação do dólar e retorna o valor de câmbio
func requestToExchangeRateAPI(key string) float64 {
	resp, err := http.Get(fmt.Sprintf("https://v6.exchangerate-api.com/v6/%s/pair/USD/BRL", key))
	if err != nil {
		fmt.Println("Erro ao fazer a requisição:", err)
		return 0.0
	}
	defer resp.Body.Close()

	var data types.ApiExchangeRateResponse

	err = json.NewDecoder(resp.Body).Decode(&data)
	if err != nil {
		fmt.Println("Erro ao decodificar o JSON:", err)
		return 0.0
	}

	return data.ConversionRate
}
