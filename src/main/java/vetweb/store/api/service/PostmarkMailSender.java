package vetweb.store.api.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.data.model.templates.TemplatedMessage;
import com.wildbit.java.postmark.client.exception.PostmarkException;

import vetweb.store.api.models.Category;

@Service
public class PostmarkMailSender {
	
	private static final String API_TOKEN = "0aacc2e4-0f02-40d2-b572-049a96dbb3bf";
	private static final Logger LOGGER = LoggerFactory.getLogger(PostmarkMailSender.class);
	private static final String FROM = "renan.rodrigues@accountfy.com";

	public void sendNewCategory(Category category, String msg, String dateInclusion) {
		TemplatedMessage message = new TemplatedMessage(FROM, "renan.rodrigues@accountfy.com");
		message.setTemplateAlias("new-category");
		Map<String, Object> propertiesTemplate = new HashMap<>();
		propertiesTemplate.put("commenter_name", category.getDescription());
		propertiesTemplate.put("timestamp", dateInclusion);
		propertiesTemplate.put("body", msg);
		message.setTemplateModel(propertiesTemplate);
		ApiClient apiClient = Postmark.getApiClient(API_TOKEN);
		try {
			MessageResponse messageResponse = apiClient.deliverMessageWithTemplate(message);
		} catch (PostmarkException postmarkException) {
			LOGGER.error("Library error " + postmarkException.getMessage());
			
		} catch (IOException ioException) {
			LOGGER.error("Generic error " + ioException.getMessage());
		}
	}
	
	
}
