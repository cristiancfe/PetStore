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
    @Test(priority=1) //  @test - identifica o método ou função como um teste para o TestNG e se usa priority para colocar a ordem de execução
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
                .body("status", is("available"))
                .body("category.name", is("dog")) // is quando tiver categoria sem lista ou array
                .body("tags.name", contains("sta")) // contains quando tiver array, lista
        ;

    }
    // consultar - GET
    @Test(priority=2) // a ordem de execução com priority somente é obedecida pelo build.gradle não pela classe
    public void consultarPet(){
        String petId = "10302197300";

        String token =   // variavel para guardar o tokem pego pelo extract e o path e o system.out.println com valor do token deve ficar fora do ";"

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" +  petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Heros"))
                .body("category.name", is("dog")) // vai ser usado dog como valor de token
                .body("status", is("available"))
        .extract()
            .path("category.name")
       ;
        System.out.println("O token é : " + token); // file - file Properties- file Encoding e troca para windows 1252  e clica em convert para aceitar acento na palavra "é"
        //system.out.println com valor do token deve ficar fora do ";" anterior  pois ele já tem seu próprio ";"

    }
    @Test(priority=3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .put(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Heros"))
                .body("status",is("sold"))
        ;
    }
    @Test (priority=4)
    public void excluirPet(){

        String petId = "10302197300";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .delete(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))

        ;
    }

}
