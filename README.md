<h1 align="center">Cadastro de Veículos API</h1>

API RESTful para gerenciamento de veículos, permitindo cadastro, atualização, exclusão e consultas detalhadas com base em diversos filtros. A API foi desenvolvida utilizando Java com Spring Boot e utiliza o banco de dados PostgreSQL para armazenamento dos veículos.


## Funcionalidades

Cadastro de novos veículos
Atualização e exclusão de veículos
Exibição da quantidade de veículos não vendidos
Distribuição de veículos por década de fabricação
Distribuição de veículos por fabricante
Exibição de veículos registrados na última semana
Validação de marcas de veículos (evitando erros de digitação)

## Endpoints da API
1. GET /veiculos

Retorna todos os veículos cadastrados.

Resposta:
```
[
  {
    "id": 1,
    "veiculo": "Fusca",
    "marca": "Volkswagen",
    "ano": 1976,
    "descricao": "Veículo clássico",
    "vendido": false,
    "created": "2025-02-01T10:00:00",
    "updated": "2025-02-10T14:00:00"
  },
]
```
2. GET /veiculos?marca={marca}&ano={ano}&cor={cor}
Retorna todos os veículos filtrados de acordo com os parâmetros passados.

Parâmetros de consulta:

marca (opcional)
ano (opcional)
cor (opcional)
Resposta:

json
Copiar
Editar
[
  {
    "id": 1,
    "veiculo": "Fusca",
    "marca": "Volkswagen",
    "ano": 1976,
    "descricao": "Veículo clássico",
    "vendido": false,
    "created": "2025-02-01T10:00:00",
    "updated": "2025-02-10T14:00:00"
  }
]
3. GET /veiculos/{id}
Retorna os detalhes de um veículo específico.

Resposta:

json
Copiar
Editar
{
  "id": 1,
  "veiculo": "Fusca",
  "marca": "Volkswagen",
  "ano": 1976,
  "descricao": "Veículo clássico",
  "vendido": false,
  "created": "2025-02-01T10:00:00",
  "updated": "2025-02-10T14:00:00"
}
4. POST /veiculos
Adiciona um novo veículo.

Corpo da requisição:

json
Copiar
Editar
{
  "veiculo": "Fusca",
  "marca": "Volkswagen",
  "ano": 1976,
  "descricao": "Veículo clássico",
  "vendido": false
}
Resposta:

json
Copiar
Editar
{
  "id": 1,
  "veiculo": "Fusca",
  "marca": "Volkswagen",
  "ano": 1976,
  "descricao": "Veículo clássico",
  "vendido": false,
  "created": "2025-02-01T10:00:00",
  "updated": "2025-02-01T10:00:00"
}
5. PUT /veiculos/{id}
Atualiza todos os dados de um veículo.

Corpo da requisição:

json
Copiar
Editar
{
  "veiculo": "Fusca",
  "marca": "Volkswagen",
  "ano": 1976,
  "descricao": "Veículo restaurado",
  "vendido": true
}
Resposta:

json
Copiar
Editar
{
  "id": 1,
  "veiculo": "Fusca",
  "marca": "Volkswagen",
  "ano": 1976,
  "descricao": "Veículo restaurado",
  "vendido": true,
  "created": "2025-02-01T10:00:00",
  "updated": "2025-02-17T15:00:00"
}
6. PATCH /veiculos/{id}
Atualiza parcialmente os dados de um veículo.

Corpo da requisição:

json
Copiar
Editar
{
  "descricao": "Veículo parcialmente restaurado"
}
Resposta:

json
Copiar
Editar
{
  "id": 1,
  "veiculo": "Fusca",
  "marca": "Volkswagen",
  "ano": 1976,
  "descricao": "Veículo parcialmente restaurado",
  "vendido": false,
  "created": "2025-02-01T10:00:00",
  "updated": "2025-02-17T15:30:00"
}
7. DELETE /veiculos/{id}
Exclui um veículo da base de dados.

Resposta:

json
Copiar
Editar
{
  "message": "Veículo excluído com sucesso"
}
Funcionalidades Adicionais
GET /veiculos/nao-vendidos
Retorna a quantidade de veículos não vendidos.

GET /veiculos/distribuicao-decada
Retorna a distribuição de veículos por década de fabricação.

GET /veiculos/distribuicao-marca
Retorna a distribuição de veículos por fabricante.

GET /veiculos/ultima-semana
Retorna os veículos registrados durante a última semana.

Regras de Validação
As marcas devem ser consistentes e não podem ser inseridas com erros de digitação (ex.: "Volksvagen", "Forde", "Xevrolé" são inválidas).
Testes
A API inclui testes unitários para garantir o bom funcionamento das operações de CRUD e validação de dados.

Tecnologias Utilizadas
Java 17
Spring Boot
PostgreSQL
JUnit
Mockito
Swagger (para documentação da API)
Como Rodar
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/usuario/cadastro-veiculos.git
cd cadastro-veiculos
Configure o banco de dados PostgreSQL.

Execute o projeto com o comando:

bash
Copiar
Editar
./mvnw spring-boot:run
A API estará disponível em http://localhost:8080.

Contribuindo
Contribuições são bem-vindas! Se você deseja melhorar a API ou corrigir um erro, abra um pull request.
