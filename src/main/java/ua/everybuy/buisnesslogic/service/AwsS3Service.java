package ua.everybuy.buisnesslogic.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FileNotPresentException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AmazonS3 s3Client;

    @Value("${aws.bucket}")
    private String bucketName;

    @Value("${aws.bucket.prefix}")
    private String urlFilePrefix;

    public String uploadFile(MultipartFile file) throws IOException {
        checkIfFileEmpty(file);

        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new IOException("Bucket '" + bucketName + "' does not exist.");
        }

        String fileName = UUID.randomUUID().toString();
        String fileUrl = urlFilePrefix + fileName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

        } catch (AmazonServiceException e) {
            throw new IOException("Failed to upload photos to S3: " + e.getErrorMessage(), e);

        } catch (SdkClientException e) {
            throw new IOException("Failed to upload photos to S3: " + e.getMessage(), e);
        }

        return fileUrl;
    }

    private void checkIfFileEmpty(MultipartFile file){
        if (file.isEmpty()){
            throw new FileNotPresentException();
        }
    }
}
