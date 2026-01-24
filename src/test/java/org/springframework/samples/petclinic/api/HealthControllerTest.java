package org.springframework.samples.petclinic.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = HealthControllerTest.TestApp.class)
@AutoConfigureMockMvc
@Import(HealthController.class)
class HealthControllerTest {

	@Autowired MockMvc mockMvc;

	@DynamicPropertySource
	static void mysqlProps(DynamicPropertyRegistry r) {
		String host = System.getenv().getOrDefault("DB_HOST", "localhost");
		String port = System.getenv().getOrDefault("DB_PORT", "3306");
		String name = System.getenv().getOrDefault("DB_NAME", "petclinic");
		String user = System.getenv().getOrDefault("DB_USER", "petclinic");
		String pass = System.getenv().getOrDefault("DB_PASS", "petclinic");

		r.add("spring.datasource.url",
			() -> "jdbc:mysql://" + host + ":" + port + "/" + name
				+ "?useSSL=false&allowPublicKeyRetrieval=true");
		r.add("spring.datasource.username", () -> user);
		r.add("spring.datasource.password", () -> pass);
		r.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");

		// ✅ evita que Spring intente ejecutar scripts/init
		r.add("spring.sql.init.mode", () -> "never");
	}

	@Test
	void health_shouldBeUp_whenMySqlIsReachable() throws Exception {
		mockMvc.perform(get("/api/health"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("UP"))
			.andExpect(jsonPath("$.db").value("UP"));
	}

	@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
		org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class
	})
	static class TestApp {}
}
