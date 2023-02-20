# API REST

<h4 align="center"> 
	🚧   🚀 Em construção...  🚧
</h4>


## API REST com persistência de dados.

Desenvolvi uma API REST que foi criada tendo como base o fórum da Alura, onde um usuário cria um tópico para determinado
curso, após isso, ele pode receber respostas de outros usuários nesse determinado tópico.

Essa API faz todo processo de CRUD (CREATE, READ, UPDATE, DELETE) de tópicos no fórum.

Essa API foi testada utilizando o Postman, que é uma plataforma para projetar, construir, testar e iterar APIs.

### Spring Profile

Após fazer a etapa de autenticação, observei que pode ser um processo burocrático para testar os endpoints da API. Obviamente
é essencial que a API tenha autenticação no ambiente de produção. (API já no "ar")

Com o Spring Profile, é possível definir por meio de anotações, qual classe será carregada dependendo da configuração de
perfil que estiver configurada no momento de rodar a aplicação.

Utilizando profiles, é possível determinar como o projeto irá rodar dependendo da configuração de perfil na IDE.

Com isso, é possível definir um perfil de Desenvolvimento, Testes e Produção. Utilizando os recursos de Spring Profile
pode aumentar grandemente a produtividade no desenvolvimente, testes e deploy da aplicação.

Fiz isso para criar um profile onde a classe de configuração de segurança, não define nenhum tipo de autenticação, agilizando
o processo de testes das requisições HTTP por não precisar de autenticação para os Endpoints da API.

*ATENÇÃO* : Obviamente a aplicação precisa de autenticação em ambiente de produção.




## 🛠 Tecnologias

- [x] Spring Boot
- [x] Spring Data
- [x] Spring Security
- [x] Postman
- [x] JUnit
- [x] Swagger



## Features

- [x] CRUD dos tópicos
- [x] Autenticação e Segurança da API.
- [x] Monitoramento da API.
- [x] Documentação da API com Swagger.
- [x] Utilização de Profiles no Spring visando produtividade.
- [x] Testes automatizados.
- [x] Deploy com Docker;
