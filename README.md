# PlatCorp API

API com Crud de Clientes, usando API's externas de geolocalização e clima

# Como usar os serviços 
http://localhost:8080/swagger-ui.html 
Ou 

- Verificar status do serviço
  $ curl -X GET http://localhost:8080/actuator/health
  Retorna: {status:up} caso a aplicação esteja online
- Adicionar um novo Cliente 
  $ curl -X POST -F 'nome={nome do novo cliente}' -F 'idade={idade do novo cliente}' http://localhost:8080/api/v1/cliente
  Retorna: "Solicitação concluida com sucesso"
- Editar Cliente



# Ferramentas utilizadas
* IDE
Eclipse Java EE IDE for Web Developers.
IDE mais utilizada pelos desenvolvedores por sua facilidade e usabilidade.

* Gerenciador de dependencias e Build
Maven 3.5.2
Pelo conhecimento e experiencia, optou-se pelo Maven, apesar do Gradle ser uma opção 
mais amigavel visualmente e o build ser mais simples e declarativo.

* Base de Dados 
H2
Ferramenta simples e com baixa curva de aprendizado, salva os dados na memoria interna.

# Infraestrutura adicional
 - Porta padrão do serviço: 8080
 - Verificar no applications.properties pasta padrão para ciraço da base de dados
   Propriedade: spring.datasource.url

- Como usar os serviços
- Como executar, testar, empacotar e entregar o seu projeto
- Instruções para como montar o ambiente de produção onde seus serviços devem ser executados (preferencialmente citando ferramentas e processos)



