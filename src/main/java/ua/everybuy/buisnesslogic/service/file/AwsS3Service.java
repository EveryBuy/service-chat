package ua.everybuy.buisnesslogic.service.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FileFormatException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.FileNotPresentException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsS3Service {
    private final AmazonS3 s3Client;

    @Value("${aws.bucket}")
    private String bucketName;

    @Value("${aws.bucket.prefix}")
    private String urlFilePrefix;

    public String uploadFile(MultipartFile file) throws IOException {
        System.out.println("Contnt type =  " + file.getContentType() +" ," + "name = " + file.getOriginalFilename());
        checkIfFileEmpty(file);
        isImageOrPdf(file);

        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new IOException("Bucket '" + bucketName + "' does not exist.");
        }

        String fileUrl = generateFileUrl();
        String fileName = extractFileNameFromUrl(fileUrl);

        try {
            ObjectMetadata metadata = createMetadata(file);
            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

        } catch (AmazonServiceException e) {
            throw new IOException("Failed to upload photos to S3: " + e.getErrorMessage(), e);

        } catch (SdkClientException e) {
            throw new IOException("Failed to upload photos to S3: " + e.getMessage(), e);
        }

        return fileUrl;
    }

    public void deleteFile(String fileUrl) {
        try{
            String fileName = extractFileNameFromUrl(fileUrl);
            s3Client.deleteObject(bucketName, fileName);
            log.info("file was deleted from s3 {}", fileName);
        }catch (AmazonServiceException e) {
            log.error("Failed to delete file from S3: {}", e.getErrorMessage(), e);
        }
    }

    private void checkIfFileEmpty(MultipartFile file){
        if (file.isEmpty()){
            throw new FileNotPresentException();
        }
    }

    private void isImageOrPdf(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        log.info(mimeType);

        if (!(mimeType.startsWith("image/") || mimeType.equals("application/pdf"))) {
            throw new FileFormatException();
        }
    }

    private String generateFileUrl(){
        String fileName = UUID.randomUUID().toString();
        return urlFilePrefix + fileName;
    }

    private String extractFileNameFromUrl(String fileUrl){
        return fileUrl.replace(urlFilePrefix, "");
    }

    private ObjectMetadata createMetadata(MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }

}
