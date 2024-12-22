# Projeto Store

Este serviço tem como finalidade fornecer uma interface para consulta e processamento de vendas de produtos.

## Pré-requisitos

Antes de começar, certifique-se de ter os seguintes requisitos instalados:

- **Java 21** (para execução local, com maven)
- **Maven** (para build e execução local)
- **Docker** e **Docker Compose** (para execução via contêiner)

---

## Como clonar o repositório

Clone o repositório do projeto usando o seguinte comando:

```bash
git clone https://github.com/AlissonDiogo/ecommerce-tolerancia-a-falhas.git
cd ecommerce-tolerancia-a-falhas
```

---

## Executando o projeto

### 1. Executando localmente com Maven

1. Navegue até o diretório do projeto:
   ```bash
   cd store
   ```

2. Compile o projeto e execute os testes:
   ```bash
   mvn clean install
   ```

3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

4. O serviço estará disponível em:
   ```
   http://localhost:8082
   ```

---

### 2. Executando com Docker Compose

1. Certifique-se de que o Docker Compose está instalado e em execução no seu sistema.

2. No diretório raiz do projeto, execute:
   ```bash
   docker-compose up --build
   ```

3. O serviço estará disponível em:
   ```
   http://localhost:8082
   ```