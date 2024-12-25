package gabri.dev.javaspringcompose.configs;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(@Value("${minio.bucket.endpoint}") String endpoint,
                                   @Value("${minio.bucket.access-key}") String accessKey,
                                   @Value("${minio.bucket.secret-key}") String secretKey) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
