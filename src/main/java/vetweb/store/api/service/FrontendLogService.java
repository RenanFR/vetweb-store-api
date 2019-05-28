package vetweb.store.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vetweb.store.api.models.Log;
import vetweb.store.api.persistence.LogRepository;

@Service
public class FrontendLogService {
	
	@Autowired
	private LogRepository logRepository;
	
	public String saveLog(Log log) {
		String user = logRepository.save(log).getLogPrimaryKey().getUser();
		String logTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(log.getLogPrimaryKey().getTimeStamp())), TimeZone.getDefault().toZoneId()));
		return "Logged saved for user " + user + " at " + logTime;
	}

}
