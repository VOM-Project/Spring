package vom.spring.domain.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AlbumController {
    private AlbumService albumService;
    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping(value = "/api/album/{member-id}/new")
    public ResponseEntity<HttpStatus> uploadAlbum(
            @PathVariable("member-id") Long memberId,
            @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {
        // member-id 로 homepy-id 찾아야함
        albumService.uploadAlbum(memberId, multipartFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value="/api/album/{member-id}/{album-id}/delete")
    public ResponseEntity<HttpStatus> deleteAlbum(
            @PathVariable("member-id") Long memberId,
            @PathVariable("album-id") Long albumId
    ) {
        albumService.deleteFile(albumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/album/{member-id}")
    public ResponseEntity<?> getAlbum(
            @PathVariable("member-id") Long memberId
    ) {
        //member-id로 homepy-id 찾아야함
        //homepy-id로 album-id 찾아야함
        return new ResponseEntity<>(albumService.getAlbum(memberId), HttpStatus.OK);
    }
}

