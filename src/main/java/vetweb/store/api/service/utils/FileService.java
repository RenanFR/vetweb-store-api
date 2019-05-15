package vetweb.store.api.service.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	
	public String storeFile(MultipartFile file) {
		String baseDirectory = "images/";
		String realPath = getClass().getClassLoader().getResource(baseDirectory).getPath();
		try {
			String filePath = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(filePath));
			String storedPath = baseDirectory + file.getOriginalFilename();
			return storedPath;
		} catch (FileAlreadyExistsException fileAlreadyExistsException) {
			LOGGER.error("File exists");
		} catch (IOException exception) {
			LOGGER.error("Unknown error " + exception.getMessage());
		}
		return null;
	}

}
