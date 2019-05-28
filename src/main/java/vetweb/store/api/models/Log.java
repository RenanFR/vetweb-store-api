package vetweb.store.api.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_frontend_log")
public class Log {
	
	@EmbeddedId
	private LogPrimaryKey logPrimaryKey;
	
	private String path;
	
	private String message;
	
	private String details;

	public Log() {
	}

	public Log(String path, String message, String details) {
		this.path = path;
		this.message = message;
		this.details = details;
	}
	
	public LogPrimaryKey getLogPrimaryKey() {
		return logPrimaryKey;
	}
	
	public void setLogPrimaryKey(LogPrimaryKey logPrimaryKey) {
		this.logPrimaryKey = logPrimaryKey;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
