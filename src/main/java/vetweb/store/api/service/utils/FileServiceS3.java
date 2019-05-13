package vetweb.store.api.service.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component(value = "s3")
public class FileServiceS3 {
	
	private static final String BUCKET = "http://localhost:9444/ui/vetweb-store";
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceS3.class);
	
	@Autowired
	private AmazonS3Client s3Client;
	
	public String storeFile(MultipartFile file) {
		try {
			s3Client
				.putObject("vetweb-store",
						file.getOriginalFilename(),
						file.getInputStream(),
						new ObjectMetadata());
			return BUCKET + file.getOriginalFilename() + "?noAuth=true"; 
		} catch (AmazonClientException | IOException exception) {
			LOGGER.error("Error while saving file on bucket");
			throw new RuntimeException(exception.getMessage());
		}
	}
	
}
