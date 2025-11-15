# Projeto HUMAIN
## Integrantes da equipe Sapiens
- Juan Francsico Alves Muradas | RM: 555541
- Gustavo Oliveira de Moura | RM: 555827
- Lynn Bueno Rosa | RM: 551102

## Explicando a Solução
Tendo em vista a diferença na velocidade de evolução do mercado de trabalho e das pessoas, desenvolvesmos o HUMAIN, o aplicativo que irá preparar e treinar as pessoas a se adaptarem ao novo mundo com as IAs.<br><br>
O HUMAIN vai contar com trilhas de apredizagem, onde o usuário desenvolverá softskills e outras habilidades requisitadas pelo mercado e, além de ajudar as pessoas se adaptarem, as empresas também poderão acompanhar o desenvolvimento de seus funcionários.

## Versão do Java & Spring Boot
> Spring Boot v3.5.7 <br>
> Java v17 <br>

## Como executar o código
1. Clone este repositório no seu computador
2. Verifique se o Java e Maven estão instalados, utilizando os comandos *java -version* e *mvn -v* no terminal
3. Instale as dependências com *mvn clean install* no terminal
4. Ainda no terminal, execute o programa com *mvn spring-boot:run*
5. Por fim, caso queira testar as requisições, acesse o **Insomnia** e use a URL **http://localhost:8080**

## Exemplos de Requisição | Como testar
### 1. Cadastrar usuário
**(POST) /humain/usuario**

Cadastra um novo usuário no sistema

**Exemplo de inserção de dados para CADASTRAR USUÁRIO:**
```json
[
  {
    "nome": "Julia Bergmann",
    "email": "juberg@gmail.com",
    "area_atuacao": "Coordenadora de Marketing"
  }
]
```

### 2. Completar trilha
**(DELETE) /humain/usuario/{idUser}/matricular/{idTrilha}**

Conclui uma trilha em que o usuário está cadastrado e remove da lista de matriculas daquele user.<br>
O usuário recebe 100 pontos ao completar uma trilha.

**Exemplo de inserção de dados para COMPLETAR TRILHA:**
```json
[
  {
    "id_trilha": 4
  }
]
```

### 3. Listar funcionários de uma empresa
**(GET) /humain/empresa/{idEmpresa}/funcionarios**

Lista todos os funcionários de uma determinada empresa, exibindo o ID dele e seu cargo.

**Exemplo de retorno para LISTAR FUNCIONÁRIOS de uma determinada empresa:**
```json
[
  {
    "id_empresa": 4,
    "id_usuario": 2,
    "cargo": "Estagiário"
  }
]
```

### 4. Listar competências de uma trilha
**(GET) /humain/trilha/{idTrilha}/competencias**

Lista as competências de uma trilha específica, exibindo o ID da trilha e ID da competência

**Exemplo de retorno para LISTA COMPETENCIAS de uma trilha específica:**
```json
[
  {
    "id_trilha": 12,
    "id_competência": 7
  }
]
```
