## Passos iniciais

### Instalando o projeto

Requisitos:

- Java 21 ou superior
- Maven 3.3.3 ou superior
- Spring Boot 3.3.3 ou superior

### Criar o pacote para execução por comando

```bash
mvn clean package
```

Para executar o comando manual:

```bash
java -jar target/hierarquia-0.0.1-SNAPSHOT.jar analyze --depth 2 "FRASE" --verbose
```

Exemplo:

```bash
java -jar target/hierarquia-0.0.1-SNAPSHOT.jar analyze --depth 2 "Eu vi onça-pintada e papagaios." --verbose
```

## Passos adicionais

Para rodar a aplicação web, basta executar o comando `mvn spring-boot:run` e o servidor `http://localhost:8000` será aberto automaticamente.
