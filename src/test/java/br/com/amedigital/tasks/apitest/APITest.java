package br.com.amedigital.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import sun.util.logging.resources.logging;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;		
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2020-12-20\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2010-12-20\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;		
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		
		// inserir
		Integer id = RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2020-12-20\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			.extract().path("id")
		;
		
		System.out.println("id" + id);
		// remover
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204)
		;
	}

}
