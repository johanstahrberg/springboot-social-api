# Spring Boot Social API

Detta är backend-delen av projektarbetet.
Projektet är gjort individuellt och bygger vidare på den applikation
som utvecklades gemensamt under kursen.

Frontend ingår ej.

## Funktionalitet

- Registrering och inloggning med JWT
- Autentisering med Bearer-token
- Global feed med posts (nyast först)
- Användarens wall (nyast först)
- Skapa, uppdatera och ta bort egna posts
- Endast ägaren kan ändra eller ta bort sina posts

## Tekniker

- Java / Spring Boot
- Spring Security med JWT
- PostgreSQL (Neon)
- Docker
- GitHub Actions
- Koyeb
- Swagger

## Tester

- Tre enhetstester för service-lagret
- Mockito används för att mocka repositories

## Swagger

Swagger UI används för att testa API:t lokalt:
http://localhost:8080/swagger-ui.html

## Deployment

Applikationen har deployats till Koyeb och kopplats till PostgreSQL via Neon.
Deployment har verifierats enligt projektkraven.

Exempel på lyckad körning:
https://petite-norah-bullen1-f35d24ac.koyeb.app/

## Köra applikationen lokalt

Starta med:

```bash
mvn spring-boot:run

