package vom.spring.domain.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping(value = "/api/album")
    public ResponseEntity<HttpStatus> uploadAlbum(
            @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {
        albumService.uploadAlbum(multipartFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value="/api/album/{albumId}/delete")
    public ResponseEntity<HttpStatus> deleteAlbum(
            @PathVariable("albumId") Integer albumId
    ) {
        albumService.deleteFile(albumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

