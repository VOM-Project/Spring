package vom.spring.domain.album;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private AmazonS3Client amazonS3Client;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 사진 등록
     */
    @Transactional
    public void uploadAlbum(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

        albumRepository.save(
                Album.builder()
                        .image_url(amazonS3Client.getUrl(bucket, originalFilename).toString())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}
