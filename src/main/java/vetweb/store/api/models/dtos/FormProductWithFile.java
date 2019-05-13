package vetweb.store.api.models.dtos;

import org.springframework.web.multipart.MultipartFile;

import vetweb.store.api.models.Product;

public class FormProductWithFile {
	
	private Product product;
	
	private MultipartFile fileImage;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public MultipartFile getFileImage() {
		return fileImage;
	}

	public void setFileImage(MultipartFile fileImage) {
		this.fileImage = fileImage;
	}

}
