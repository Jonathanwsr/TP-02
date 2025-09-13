# Projeto Java utilizando Spring Boot 

## Descrição

Este projeto tem como objetivo auxiliar empresas a obterem insights sobre sua produtividade e faturamento. Através de uma interface intuitiva desenvolvida em Angular, os usuários poderão consultar dados históricos sobre a produção em diferentes períodos (dia, semana, mês), além de acompanhar o faturamento da empresa.

#  TopReports

**TopReports** é um sistema web desenvolvido para auxiliar empresas a obter insights sobre sua **produtividade** e **faturamento**. Ele permite acompanhar vendas, gerar relatórios acumulados e visualizar dados históricos organizados por **dia, semana ou mês**.

---

##  Funcionalidades Principais

- Consulta de vendas por período com valores somados (`/acumulados`)
- Autenticação de usuários com Spring Security + JWT
- Registro de novos usuários (`/auth/registro`)
- Login com geração de token JWT (`/auth/login`)
- Consulta do usuário logado (`/auth/Eu`)
- Front-end desenvolvido em Angular (interface intuitiva)
- Backend em Java com Spring Boot + MySQL

---

##  Tecnologias Utilizadas

**Back-end**
- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- MySQL
- Swagger/OpenAPI

**Front-end (em outro repositório)**
- Angular

---

## 🛠️ Instalação e Execução

###  Clone o banco de dados

```

CREATE DATABASE reports;
USE reports;

-- Tabela de usuários
CREATE TABLE perfil_usuario (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_name VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(50)
);

-- Tabela de acumulados
CREATE TABLE acumulados (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  venda VARCHAR(255),
  tipo VARCHAR(255),
  valor DECIMAL(10, 2),
  data DATE
);




INSERT INTO perfil_usuario (user_name, password, role) 
VALUES ('teste', '$2a$10$E0NRpO15SkOaJOqBG/tvUeGjzTkY63tmFIUx7v5lIObnVzyrJwqC6', 'MESTRE');

-- Vendas fixas
INSERT INTO acumulados (venda, tipo, valor, data) VALUES
('Venda de Eletrônicos', 'Produto', 1500.50, '2024-12-01'),
('Assinatura Mensal', 'Serviço', 200.00, '2024-12-03');

-- Vendas diárias para testes
INSERT INTO acumulados (venda, tipo, valor, data)
SELECT 
  'Venda Exemplo',
  'Tipo Exemplo',
  ROUND(RAND() * 100 + 1, 2),
  DATE_ADD('2023-01-01', INTERVAL seq DAY)
FROM (
  SELECT @rownum := @rownum + 1 AS seq
  FROM (SELECT 0 UNION ALL SELECT 1 ... UNION ALL SELECT 9) t1,
       (SELECT 0 UNION ALL SELECT 1 ... UNION ALL SELECT 9) t2,
       (SELECT @rownum := -1) r
  LIMIT 31
) seq_table;

```

 Rotas da API
 Autenticação
POST /auth/login
Faz login e retorna um token JWT.
Corpo da requisição:

json
Copiar
Editar
{
  "userName": "user",
  "password": "12345"
}

POST /auth/registro
Registra novo usuário:

json
Copiar
Editar
{
  "userName": "NOVOUSER",
  "password": "senha123",
  "role": "ADMIN"
}

GET /auth/Eu
Retorna o usuário logado (Requer Authorization: Bearer <token>)


Relatórios
GET /acumulados
Retorna todas as vendas.

GET /acumulados/periodo?inicio=2025-01-01&fim=2025-08-01
Retorna todas as vendas entre duas datas.

#  O formato de data usado é: "YYYY-MM-DD" (padrão em todo o projeto)

#  Como usar o Swagger
Este projeto já vem com integração ao Swagger UI, que facilita testar os endpoints.

#  Acesse:
bash
Copiar
Editar
http://localhost:8080/swagger-ui/index.html


 Tutorial Swagger:
Inicie o projeto

Vá ao link acima

Teste as rotas diretamente da interface web (envie JSONs, execute GETs etc.)

# Segurança
Este projeto usa autenticação baseada em JWT. A cada login, um token é gerado. Você deve incluí-lo nos headers das próximas requisições:

makefile
Copiar
Editar
Authorization: Bearer <seu-token-aqui>

 # Exemplo de resposta JSON (GET /acumulados)
json
Copiar
Editar
[
  {
    "id": 20,
    "venda": "Porta",
    "tipo": "Cor branca",
    "valor": 500,
    "data": "2025-08-05"
  }
]

## Autor

**Jonathan Rocha**  
- GitHub: [https://github.com/Jonathanwsr](https://github.com/Jonathanwsr)  
- LinkedIn: [https://www.linkedin.com/in/jonathan-rocha-51b8ab268](https://www.linkedin.com/in/jonathan-rocha-51b8ab268)

---

## Observações

- As senhas dos usuários são armazenadas utilizando **criptografia BCrypt** para maior segurança.  
- O token **JWT** possui tempo de expiração definido e deve ser renovado através de um novo login.  

---

## Como Clonar o Repositório

1. Clone o repositório:
  
   Clone o repositorio  https://github.com/Jonathanwsr/TP-02.git


   # Autor

**Jonathan Rocha**  
- GitHub: [https://github.com/Jonathanwsr](https://github.com/Jonathanwsr)  
- LinkedIn: [https://www.linkedin.com/in/jonathan-rocha-51b8ab268](https://www.linkedin.com/in/jonathan-rocha-51b8ab268)

---



