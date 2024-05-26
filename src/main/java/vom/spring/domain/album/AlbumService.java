package vom.spring.domain.album;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vom.spring.domain.homepy.Homepy;
import vom.spring.domain.homepy.HomepyRepository;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final HomepyRepository homepyRepository;
    private final MemberRepository memberRepository;
    private AmazonS3Client amazonS3Client;

    @Autowired
    public void setS3Client(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    /**
     * 사진 등록
     */
    @Transactional
    public void uploadAlbum(Long memberId, MultipartFile multipartFile) throws IOException {
        Homepy homepy = homepyRepository.findByMember_id(memberId);
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

        albumRepository.save(
                Album.builder()
                        .homepy(homepy)
                        .name(originalFilename)
                        .img_url(amazonS3Client.getUrl(bucket, originalFilename).toString())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * 사진 삭제
     */
    @Transactional
    public void deleteFile(Long albumId) {
        Album album = albumRepository.findById(albumId);
        String albumName = album.getName();
        // S3버킷에서 객체(파일) 삭제
        amazonS3Client.deleteObject(bucket, albumName);
        // DB에서 사진 삭제 날짜 변경
        album.setDeletedAt(LocalDateTime.now());
    }

    /**
     * 사진 조회
     */
    @Transactional
    public List<AlbumDto.Info> getAlbum(Long memberId) {
        Homepy homepy = homepyRepository.findByMember_id(memberId);
        return albumRepository.findByHomepy_id(homepy.getId()).stream().map(AlbumDto.Info::of).collect(Collectors.toList());
    }

}
