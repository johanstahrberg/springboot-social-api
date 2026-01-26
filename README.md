# Spring Boot Social API

Backend-API för ett enkelt socialt flöde.
Projektarbete (individanpassat) – Jensen YH.

## Tech stack

- Spring Boot
- Spring Security (JWT)
- PostgreSQL (Neon)
- Docker
- GitHub Actions
- Koyeb

## Authentication

- Login via `/request-token`
- JWT används i `Authorization: Bearer <token>`

## Run locally

1. Sätt miljövariabler (DB + JWT keys)
2. `mvn spring-boot:run`
3. Swagger: `/swagger-ui.html`

## Deployment

- Docker image byggs via GitHub Actions
- Deployad på Koyeb