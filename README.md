# Spring Boot Social API

Detta är backend-delen av projektarbetet i kursen.
Projektet är individanpassat och jag har arbetat själv, men följt ett GitHub-arbetsflöde som vid grupparbete.

Frontend ingår inte i denna inlämning.

## Tekniker

- Java / Spring Boot
- Spring Security med JWT
- PostgreSQL (Neon)
- Docker
- GitHub Actions
- Koyeb

## Starta lokalt (utan Docker)

Applikationen använder miljövariabler för databas och JWT-nycklar.

Starta med:
mvn spring-boot:run

Swagger UI finns på:
http://localhost:8080/swagger-ui.html

## Köra med Docker

Bygg image:
docker build -t springboot-social-api .

Kör container (med samma miljövariabler som lokalt):
docker run -p 8080:8080 springboot-social-api

## CI / Deploy

- Docker image byggs automatiskt via GitHub Actions
- Samma image används vid deploy till Koyeb
- Databas körs via Neon