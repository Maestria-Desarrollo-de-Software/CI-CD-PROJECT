package org.springframework.samples.petclinic.api;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	private final JdbcTemplate jdbcTemplate;

	public HealthController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/api/health")
	public ResponseEntity<Map<String, Object>> health() {
		try {
			Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
			if (one != null && one == 1) {
				return ResponseEntity.ok(Map.of("status", "UP", "db", "UP"));
			}
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Map.of("status", "DOWN", "db", "unexpected_response"));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Map.of("status", "DOWN", "db", "DOWN", "error", ex.getClass().getSimpleName()));
		}
	}
}
