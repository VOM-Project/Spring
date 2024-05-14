package vom.spring.domain.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AlbumController {
    private AlbumService albumService;
    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping(value = "/api/album/{member-id}")
    public ResponseEntity<String> uploadAlbum(
            @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {
        albumService.uploadAlbum(multipartFile);
        return ResponseEntity.ok().build();
    }
}
