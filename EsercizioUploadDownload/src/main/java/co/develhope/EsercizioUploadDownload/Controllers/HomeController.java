package co.develhope.EsercizioUploadDownload.Controllers;

import co.develhope.EsercizioUploadDownload.Services.HomeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws Exception{
        List<String> fileNames = new ArrayList<>();
        for(MultipartFile file: files) {
            String singleUploadedFileName = homeService.upload(file);
            fileNames.add(singleUploadedFileName);
        }
        return fileNames;
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String fileName, HttpServletResponse response) throws Exception{
        System.out.println("Downloading " + fileName);
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        return homeService.download(fileName);
    }
}
