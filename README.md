# Projeto Movie Awards

## Sobre o projeto
Projeto desenvolvido em Java 21 e Spring Boot para carga em banco de dados de arquivo csv contendo premiações de filmes com o objetivo de disponibilizar serviço REST para identificação de produtores com maior e menor intervalo entre dois prêmios consecutivos.

## Pré-requisitos
### Git
### Java 21
### Maven
### Docker
> [!IMPORTANT]
> # ATENÇÃO: DOCKER REQUERIDO!
> ### ![Docker](https://img.shields.io/badge/Docker-Required-blue?style=for-the-badge&logo=docker&logoColor=white) 
> Sem o **Docker**  instalado e ativo, a aplicação não será construída pois TestContainers são utilizados como tecnologia para testes integrados. Certifique-se de tê-lo instalado e rodando em sua máquina antes de prosseguir.

## Instruções de uso

1. Execute o Docker
2. Abra o projeto em seu IDE
3. Compile/instale o projeto localmente
   ```bash
   mvn clean install
   ```
4. Execute o programa MoviesApplication.java

5. No seu navegador ou em ferramenta para execução de serviços REST, acesse o endereço http://localhost:8080/awarded-movies para verificar os resultados
