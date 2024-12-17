package utils

import (
	"fmt"
	"math/rand"
	"os"
)

// Usado para gerar uma probabilidade de falha (10% de chance de ocorrer)
func CheckGenerateOfFail() bool {
	return rand.Int31n(10) == 0
}

// Usado para parar o programa em caso de falha
func StopProgram() {
	fmt.Println("Parando programa devido a uma falha.")
	os.Exit(3)
}

// Usado para gerar uma cotação fictícia do dolar entre 5,7 e 6,1
func GenerateFakeDolarValue() float64 {
	return 5.7 + rand.Float64()*(6.1-5.7)
}
