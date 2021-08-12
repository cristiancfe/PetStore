//1 pacote
package petstore;

// 2 Biblioteca

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


// 3 classe
public class Pet {
    //3.1 Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    // 3.2 Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir  - Create - post
    @Test // identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // dado  - Quando  - Então
        // Given - When   - Then

        given() // Dado
                .contentType("application/json")  // comum em API REST - antiga era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Heros"))
                .body("status", is ("available"))
                .body("category.name", is("dog")) // is quando tiver categoria sem lista ou array
                .body("tags.name", contains("sta")) // contains quando tiver array, lista


        ;



    }


}
