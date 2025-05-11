# Agenda Digital - UBS

Este sistema permite o gerenciamento de agendamentos médicos de uma Unidade Básica de Saúde (UBS), incluindo usuários, profissionais, especialidades, matriz de horários e consultas.

## 🚀 Tecnologias

* Java 21
* Spring Boot 3.4.5
* PostgreSQL
* Docker / Docker Compose
* Swagger (SpringDoc OpenAPI)

---

## 📆 Endpoints principais

### 👤 Usuários

* `GET /usuarios` → Lista todos
* `GET /usuarios/{id}` → Busca por ID
* `GET /usuarios/cpf/{cpf}` → Busca por CPF
* `GET /usuarios/sus/{sus}` → Busca por número SUS
* `GET /usuarios/nome?nome=carla` → Busca por nome

### 🦥 Profissionais

* `GET /profissionais` → Lista todos
* `GET /profissionais/nome?nome=ana` → Busca por nome

### 🗕️ Agendamentos

* `GET /agendamentos` → Lista todos
* `GET /agendamentos/{id}` → Busca por ID
* `GET /agendamentos/nome?nome=joao` → Busca por paciente
* `GET /agendamentos/data?data=2025-05-15` → Busca por data
* `POST /agendamentos` → Cria um novo agendamento

---

## 🧪 Documentação Swagger

Acesse no navegador após iniciar o app:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🐳 Como rodar com Docker

```bash
docker compose up --build -d
```

---

## 📁 Estrutura da API (v2)

* `/model` → Entidades JPA
* `/DTOs` → Entrada e saída de dados
* `/repository` → Interfaces de persistência
* `/service` → Lógica de negócio
* `/controller` → Rotas REST
* `/config` → Swagger e configurações

---

### 📌 Observações

* O campo `procedimento` em agendamentos é flexível (ex: `CONSULTA`, `RETORNO`, `VACINA`)
* O agendamento é vinculado à **especialidade**, e não a um médico fixo
* Os horários de atendimento são definidos pela **matriz da especialidade**

Este é um protótipo criado como parte do **Projeto Integrador em Computação I** do grupo **DRP01-PJI110-SALA-001GRUPO-016 - EIXO DE COMPUTAÇÃO**

**Desenvolvimento de um software com framework web que utilize noções de banco de dados, praticando controle de versão.**

 
