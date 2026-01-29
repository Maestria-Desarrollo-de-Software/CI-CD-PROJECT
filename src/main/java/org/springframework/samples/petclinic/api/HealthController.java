package org.springframework.samples.petclinic.api;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressFBWarnings(
	value = "EI_EXPOSE_REP2",
	justification = "JdbcTemplate is a Spring-managed dependency; not exposed outside.")
@RestController
public class HealthController {

  private final JdbcTemplate jdbcTemplate;

  public HealthController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @GetMapping("/api/health")
  public ResponseEntity<Map<String, Object>> health() {
//    try {
//      Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
//      if (one != null && one == 1) {
//        return ResponseEntity.ok(Map.of("status", "UP", "db", "UP"));
//      }
//      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//          .body(Map.of("status", "DOWN", "db", "unexpected_response"));
//    } catch (Exception ex) {
//      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//          .body(Map.of("status", "DOWN", "db", "DOWN", "error", ex.getClass().getSimpleName()));
//    }
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		  .body(Map.of("status", "DOWN", "db", "DOWN", "error", "ROLLBACK_DEMO"));
  }
}
