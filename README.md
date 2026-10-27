# Loja Web — projeto inicial

Projeto-base da atividade prática de Programação para Web I.

## Objetivo

Implementar uma pequena loja Web usando Java EE 8, Servlets, HttpSession, JSP, EL/JSTL e endpoints orientados a recursos.

## Importante

Os arquivos Java fornecidos são apenas esqueletos e não possuem a implementação da atividade.
As páginas JSP possuem conteúdo estático propositalmente. O aluno deverá substituir os dados fixos por dados enviados pelos Servlets e acessados com Expression Language/JSTL.

## Endpoints esperados

- GET `/produtos`
- GET `/carrinho`
- POST `/carrinho/itens`
- DELETE `/carrinho/itens?id={id}`
- DELETE `/carrinho`

## Restrições

- O carrinho deve ser mantido em `HttpSession`.
- O atributo da sessão deve se chamar `carrinho`.
- Não usar Scriptlets nas JSPs.
- Não usar variável `static` para armazenar carrinhos.
- Identificadores específicos devem ser enviados por query string quando aplicável.
