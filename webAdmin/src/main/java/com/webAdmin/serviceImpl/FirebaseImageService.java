package com.webAdmin.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.webAdmin.service.IImageService;

import lombok.Data;

@Service
public class FirebaseImageService implements IImageService {

	private Properties properties;

	@Autowired
	public FirebaseImageService(@Lazy Properties properties) {
		this.properties = properties;
	}

	@EventListener
	public void init(ApplicationReadyEvent event) {

		// initialize Firebase

		try {

			ClassPathResource serviceAccount = new ClassPathResource("firebase.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))

					.setStorageBucket(properties.getBucketName()).build();

			FirebaseApp.initializeApp(options);

		} catch (Exception ex) {

			ex.printStackTrace();

		}
	}

	@Override
	public String getImageUrl(String name) {

		return String.format(properties.imageUrl, name);
	}

	@Override
	public Map<String, String> save(MultipartFile file) throws IOException {

		Bucket bucket = StorageClient.getInstance().bucket();

		if(StringUtils.isEmpty(file.getOriginalFilename()))
		{
			return new HashMap<>();
		}
		String name = generateFileName(file.getOriginalFilename());

		bucket.create(name, file.getBytes(), file.getContentType());

		Storage storage = bucket.getStorage();
		BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), name).setContentType(file.getContentType()).build();
		URL url = storage.signUrl(blobInfo, 5, TimeUnit.DAYS, Storage.SignUrlOption.withV2Signature());
		String signedPath = url.toString();
		Map<String, String> uploadInfo = new HashMap<String, String>();
		uploadInfo.put("fileName", name);
		uploadInfo.put("dowloadUrl", signedPath);

		return uploadInfo;
	}

	@Override
	public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {

		byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

		Bucket bucket = StorageClient.getInstance().bucket();

		String name = generateFileName(originalFileName);

		bucket.create(name, bytes);

		return name;
	}

	@Override
	public void delete(String name) throws IOException {

		Bucket bucket = StorageClient.getInstance().bucket();

		if (StringUtils.isEmpty(name)) {
			throw new IOException("invalid file name");
		}

		Blob blob = bucket.get(name);

		if (blob == null) {
			throw new IOException("file not found");
		}

		blob.delete();
	}

	@Data
	@Configuration
	@ConfigurationProperties(prefix = "firebase")
	public class Properties {

		private String bucketName;

		private String imageUrl;
	}

}
