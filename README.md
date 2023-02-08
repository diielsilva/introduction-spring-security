# Básico do Spring Security

### Introdução
Quando iniciamos com o Spring Security, normalmente temos um pouco de dificuldade para fazê-lo funcionar corretamente, e isso se deve ao fato de que não entendemos muito bem o mecanismo por trás do Spring Security.

Quando criamos um endpoint (RestController) livre de autenticação, nós podemos acessá-lo imediamente, mas apenas por adicionarmos a dependência do Spring Security, tudo fica bloqueado por padrão.

Isso acontece, porque o Spring utiliza como base o DispatcherServlet, uma estrutura do JavaEE para a grosso modo "direcionar" nossas URLS para a respectiva classe e método do do nosso RestController, mas quando adicionamos o Spring Security ele adiciona uma cadeia de filtros antes deste DispatcherServlet, ou seja, nós não conseguimos bater diretamente nos nossos controladores.

## Implementação

Neste repositório, irei mostrar como utilizar o JWT com Spring Security para autenticação e autorização.

1. Iremos utilizar o cenário padrão do Spring, que é a autenticação de um usuário armazenado no banco de dados,
para isso iremos utilizar da interface padrão do Spring chamada de UserDetailsService, esta interface é responsável por recuperar um usuário no banco de dados baseado no seu login (apenas o username), esta interface nos retorna um objeto do tipo UserDetails, que também é uma interface, e se observarmos bem, nossa entidade de usuário (UserEntity) implementa esta interface e baseado na regra do polimorfismo, nossa entidade de usuário também é um UserDetails.

2. Precisamos definir um Spring Bean para definirmos nosso algoritmo de senha, neste caso utilizamos o algoritmo padrão, que é o BCrypt.

3. Definimos uma classe para a configuração de segurança do Spring (SecurityConfig), nela definimos os nossos beans, que são eles o PasswordEncoder, o SecurityFilterChain e nosso AuthenticationManager (responsável por processar as requisições de autenticação).

4. Definimos nossa classe AuthenticationService, responsável por criar e processar (validar) nossos tokens JWT.

5. Definimos nossos filtros de autenticação e autorização, responsáveis pelo processo de segurança, agora para testarmos basta mandarmos uma solicitação POST para /login (URL criada por padrão pelo filtro UsernamePasswordAuthenticationFilter, que nosso nós herdamos em nosso filtro de autenticação.

## Referências
[Artigo Marco Behler] https://www.marcobehler.com/guides/spring-security#:~:text=The%20short%20answer%3A,standards%20like%20OAuth2%20or%20SAML
