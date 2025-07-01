package dasturlashasil.uz.controller;//package dasturlashasil.uz.controller;
//
//import dasturlashasil.uz.Dto.AttachDto;
//import dasturlashasil.uz.service.AttachService;
//import dasturlashasil.uz.util.PageUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/attach")
//public class AttachController{
//
//
//   @Autowired
//   private AttachService attachServiceAt;
//
//
////    @PostMapping("/upload")
////    public String upload(@RequestParam("file") MultipartFile file) {
////        String fileName = attachServiceAt.saveToSystem(file);
////        return fileName;
////    }
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<AttachDto> create(@RequestParam("file") MultipartFile file) {
//        AttachDto fileName = attachServiceAt.upload(file);
//        return ResponseEntity.ok(fileName);
//    }
//
//
//
//

import dasturlashasil.uz.Dto.AttachDto;
import dasturlashasil.uz.service.AttachService;
import dasturlashasil.uz.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

////    @GetMapping("/open/{fileName}")
////    public ResponseEntity<Resource> fopen(@PathVariable String fileName) throws Exception {
////        return attachServiceAt.open(fileName);
////    }
//
//
//    @GetMapping("/open/{fileId}")
//    public ResponseEntity<Resource> IdOpen(@PathVariable String fileId) {
//        return attachServiceAt.open(fileId);
//    }
//
//
//
//    @GetMapping("/download/{fileId}")
//    public ResponseEntity<Resource> downloadFileId(@PathVariable String fileId) {
//        return attachServiceAt.download(fileId);
//    }
//
//
//    @DeleteMapping("/{fileId}")
//    public ResponseEntity<Boolean> deleteF(@PathVariable String fileId) {
//        return ResponseEntity.ok(attachServiceAt.delete(fileId));
//    }
//
//
//
//
//
//
//
//
//
//    @GetMapping("/pagination/attachz")
//    public ResponseEntity<Page<AttachDto>> getAll(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size) {
//        return ResponseEntity.ok(attachServiceAt.pagination(PageUtil.page(page), size));
//    }
//
//
//}


    @RestController
    @RequestMapping("/kunuz/attach")
public class AttachController {


    @Autowired
    private AttachService attachService;


    @PostMapping("/upload/l")
    public String upload(@RequestParam("file")MultipartFile file){
        String fileName = attachService.saveToSystem(file);
        return fileName;

    }


    @PostMapping("/upload")
    public ResponseEntity<AttachDto> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping("/open/fileName/{fileName}")
    public ResponseEntity<Resource> openFile(@PathVariable("fileName") String fileName){
        return attachService.openF(fileName);
    }

    @GetMapping("/open/fileId/{fileId}")
    public ResponseEntity<Resource> openI(@PathVariable("fileId") String fileId){
        return attachService.openId( fileId);
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFileId(@PathVariable String fileId) {
        return attachService.download(fileId);
    }


    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Boolean> deleteF(@PathVariable String fileId) {
        return ResponseEntity.ok(attachService.sremove(fileId));
    }




    @GetMapping("/paginationAll")
    public ResponseEntity<Page<AttachDto>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(attachService.pagination(PageUtil.page(page), size));
    }

}