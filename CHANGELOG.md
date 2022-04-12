<hr>
<h1 align="center">changelog</h1>
<p align=center><i align="center">Notas de versão</i></p>

<hr>

## v1.0.5-220412

- Normalização da versão da build para o número de versão legado por razões de compatibilidade
- Versionamento será feito pela pasta target
- Ajustes em build.gradle

<br>

## v1.0.4-220408

- Correção de erro de compatibilidade com biblioteca flem-commons.

<br>

## v1.0.3-220408

- Conversão do projeto de Maven para Gradle

<br>

## v1.0.2-220406

- Criação do README
- Criação do CHANGELOG
- Adição de anotações no pom.xml
- Removido slf4j-api com binding api versão < 1.6
- Removido parâmetro de deploy do Netbeans
- Substituição das bibliotecas deprecadas por vulnerabilidades de segurança:
  - [struts2-core 1.3.10](https://mvnrepository.com/artifact/org.apache.struts/struts-core/1.3.10) pela versão 2.5.26
  - [dwr 2.0.8](https://mvnrepository.com/artifact/org.directwebremoting/dwr/2.0.8) pela versão 3.0.2-RELEASE
  - [quartz 2.1.1](https://mvnrepository.com/artifact/org.quartz-scheduler/quartz/2.1.1) pela versão 2.3.2
- Adicionadas no pom.xml as seguintes bibliotecas para resolver problemas de dependência do Maven:
  - commons-email 1.5
  - commons-collections 3.2.1.redhat-7
    <br>

## Commit inicial

- Criação do repositório
