package vetweb.store.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vetweb.store.api.models.Log;
import vetweb.store.api.service.FrontendLogService;

@RestController
@RequestMapping("log")
public class LoggingResource {
	
	@Autowired
	private FrontendLogService frontendLogService;
	
	@PostMapping
	public ResponseEntity<String> persistLog(Log log) {
		return ResponseEntity
				.ok(frontendLogService.saveLog(log));
	}

}
