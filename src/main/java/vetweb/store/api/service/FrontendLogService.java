package vetweb.store.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import vetweb.store.api.models.Log;
import vetweb.store.api.persistence.LogRepository;

@Service
public class FrontendLogService {
	
	@Autowired
	private LogRepository logRepository;
	
	public String saveLog(@RequestBody Log log) {
		return logRepository.save(log).getLogPrimaryKey().getUser();
	}

}
