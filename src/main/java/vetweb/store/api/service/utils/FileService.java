package vetweb.store.api.service.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {
	
	@Autowired
	private HttpServletRequest request;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	
	public String storeFile(MultipartFile file) {
		String baseDirectory = "images/";
		String realPath = getClass().getClassLoader().getResource(baseDirectory).getPath();
		try {
			String filePath = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(filePath));
			return baseDirectory + "/" + file.getOriginalFilename();
		} catch (FileAlreadyExistsException fileAlreadyExistsException) {
			LOGGER.error("File exists");
		} catch (IOException exception) {
			LOGGER.error("Unknown error " + exception.getMessage());
		}
		return null;
	}

}
