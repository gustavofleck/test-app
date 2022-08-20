# Teste Sicredi

## Arquitetura

Para esse teste foi escolhida uma abordagem utilizando o padrão arquitetural MVVM (Model-View-ViewModel) com uso dos conceitos do Clean Architecture para definir as camadas e relações entre elas. O aplicativo foi dividido em quatro módulos:

- **Data**: responsável pela obtenção dos dados das fontes externas, e mapeamento dos dados para entidades de domínio que podem ser utilizadas com maior segurança pelo aplicativo

- **Domain**: responsável pela modelagem das regras de negócio, é um módulo totalmente isolado dos demais, não dependendo de nenhum outro módulo. Essa abordagem isola a camada de regras de negócio e possibilita alterações com poucos impactos colaterais, aumentando a segurança de desenvolvimento de novos comportamentos

- **Presentation**: responsável pelos ViewModels que orquestram os estímulos recebidos pela View de forma que não exista nenhum tippo de acoplamento entre a camada de visualização e a de obtenção dos dados

- **App**: módulo core responsável por gerenciar todas as dependencias entre os módulos e o framework do Android, também é nesse módulo que se encontram as Activities e Fragments

## Tech stack

- **MockK**: ferramenta utilizada na construção dos testes unitários que facilita o mock de dependencias, isolando apenas o necessário para o teste em questão. É uma biblioteca desenvolvida para trabalhar com Kotlin e que possiblita uma escrita dos testes mais idiomática, facilitando o entendimento

- **Coroutines/Flow**: biblioteca que possibilita trabalhar em múltiplas threads com facilidade e segurança, possibilitando o processamento de requisições em segundo plano, evitando o congelamento da tela do usuário. Por contar com diversas estruturas prontas, facilita muito a implementação de comunicação assíncrona

- **Koin**: biblioteca responsável pelo controle da injeção de dependencias. Ferramenta que traz de forma simples e idiomática a orquestração de dependencias, tem um menor tempo de build, mas pode causar crashes em tempo de execução

- **Turbine**: biblioteca que facilita o teste de Flow, fornecendo uma estrutura que contém valores emitidos, bem como erros disparados

- **Glide**: ferramenta para download de imagens, muito utilizada na comunidade Android

- **Retrofit**: biblioteca responsável pelas interfaces de comunicação com APIs REST

- **Navigation**: biblioteca que facilita o gerenciamento de navegações do aplicativo e fornece uma visualização do comportamento das navegações

## Considerações

Em alguns momentos algumas camadas da arquitetura acabam servindo apenas como *proxies*, mas são importantes para explicitar o padrão arquitetural escolhido e facilitar a colaboração com outros desenvolvedores. Dessa forma também é possível, por exemplo, ao abrir o pacote de Use Cases do módulo de Domain, entender todas as funcionalidades do aplicativo

Os testes unitários foram desenvolvidos utilizando técnicas que facilitem seu entendimento, tornando assim uma documentação viva do código que está sendo desenvolvido
