# File
Projeto desenvolvido para permitir operar sobre diretórios e arquivos através de uma API REST. 

## Descrição

 Projeto backend foi desenvolvido para uma posição de desenvolvedor, com foco em gerenciamento de arquivos e diretórios usando Spring Boot.
 Ele aproveita o Spring Data JPA para interações de banco de dados e o Spring Security para proteger o aplicativo. Utiliza swagger para documentar os endpoints 
 O aplicativo é construído com Java 17 e utiliza um banco de dados H2 para desenvolvimento e teste.
 O projeto frontend desenvolvido em ReactJS. Ele se conecta a um backend construído em Spring Boot para gerenciar arquivos e diretórios.


## Tecnologias usadas

	**Backend**
	
- **Java 17**
- **Spring Boot 3.3.4**
  - Spring Data JPA
  - Spring Security
  - Spring Web
  - Spring Boot DevTools
  - SpringDoc OpenAPI
- **H2 Database**
- **JUnit Jupiter**
- **Mockito**
	
	**Frontend**
	
- **React**
- **ReactDOM**
- **React Router DOM**
- **Axios**: 

Este projeto inclui componentes tanto para o backend (Spring Boot) quanto para o frontend (React).

## Pré-requisitos

Certifique-se de ter os seguintes softwares instalados na sua máquina:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Executando a Aplicação com Docker

Para construir e rodar tanto o frontend quanto o backend utilizando Docker, siga os passos abaixo:

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/uniom3/file.git
   
   cd file
   docker-compose up --build
