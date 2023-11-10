package com.ak.midentity.azuremidentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/")
public class BlobController {

    @Autowired
    private final AzureBlobAdapter azureBlobAdapter;

    public BlobController(AzureBlobAdapter azureBlobAdapter) {
        this.azureBlobAdapter = azureBlobAdapter;
    } 

    //public ResponseEntity<String> uploadFileToBlob(@RequestParam("file") MultipartFile file, Model model)

    @PostMapping("/blob/upload")
    public ModelAndView uploadFileToBlob(@RequestParam("file") MultipartFile file, Model model) {
        String containerName = "uploads";
        String fileUrl = azureBlobAdapter.uploadFile(containerName, file);
        if (fileUrl != null) {
            model.addAttribute("message", "File uploaded successfully: " + file.getOriginalFilename());
            model.addAttribute("fileUrl", fileUrl);
           // return ResponseEntity.ok("File uploaded successfully: " + result);
           return new ModelAndView("success");
        } else {
            //return ResponseEntity.internalServerError().body("Failed to upload the file");
            model.addAttribute("message", "File upload failed: ");
            return new ModelAndView("upload");
        }
    }

    @GetMapping("/blob/upload")
    public ModelAndView uploadFile() {
        return new ModelAndView("upload");
    }

    
    @GetMapping("/")
    public ModelAndView home(Model model) {
        model.addAttribute("message", "Azure Blob Storage demo app!");
        return new ModelAndView("index");
    }


    @GetMapping("blob/files")
    public ModelAndView listUploadedFiles(Model model) {
        try {
            List<String> fileUrls = azureBlobAdapter.listFiles();
            model.addAttribute("files", fileUrls);
            return new ModelAndView("files");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to retrieve files: " + e.getMessage());
            return new ModelAndView("files"); // Create an error.html template for handling errors
        }
    }

}


