package gabri.dev.javaspringcompose.service;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioService {

    private static final String BUCKET_NAME = "mi-bucket";
    private static final String ENDPOINT = "http://localhost:9000"; // Tu endpoint de Minio

    @Autowired
    MinioClient minioClient;

    public String uploadFile(MultipartFile file, String type) {
        try {
            // Verificar/crear bucket
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(BUCKET_NAME)
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(BUCKET_NAME)
                        .build());
            }

            String objectName = type + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Subir archivo
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // Retornar URL directa
            return String.format("%s/%s/%s", ENDPOINT, BUCKET_NAME, objectName);

        } catch (Exception e) {
            throw new RuntimeException("Error al subir archivo a MinIO: " + e.getMessage(), e);
        }
    }
}