package vetweb.store.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoint {
	
	@RequestMapping("test")
	public List<String> test () {
		return Arrays.asList("test", "Arrays", "asList");
	}

}
