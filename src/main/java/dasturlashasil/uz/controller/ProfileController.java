package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.profile.ProfileDto;
import dasturlashasil.uz.Dto.profile.ProfileFilterDto;
import dasturlashasil.uz.Dto.regionD.RegionLangDto;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.service.ProfileService;
import dasturlashasil.uz.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/admin/create")
    public ResponseEntity<ProfileDto>
    create(@Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/admin/update/id/{id}")
    public ResponseEntity<ProfileDto> update ( @PathVariable Integer id, @Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.update(id,dto));
    } // ADMIN

    @PutMapping("/updateDetails")           // ANY Roldagilar qilaoldi
    public ResponseEntity<ProfileDto> updateDetail ( ) {
        return null;
    }

    // ADMIN
    @GetMapping("/admin/getlist")
    public ResponseEntity<Page<ProfileDto>>
    getAllProfiles(@RequestParam(defaultValue = "1") int page,
                   @RequestParam(defaultValue = "3") int size) {
        Page<ProfileDto> profilePage = profileService.getAllProfiles(PageUtil.page(page), size);
        return ResponseEntity.ok(profilePage);
    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO newDto) {
//        return ResponseEntity.ok(service.update(id, newDto));
//    }
//

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Boolean>
    delete(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }
//
//    @GetMapping("")
//    public ResponseEntity<List<CategoryDTO>> getAll() {
//        return ResponseEntity.ok(service.getAllByOrder());
//    }
//
//    // /api/v1/category/lang?language=uz
//    @GetMapping("/lang")
//    public ResponseEntity<List<ProfileDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageList language) {
//        List<ProfileDto> list = profileService.getAllLang(language);
//        return ResponseEntity.ok(list);
//    }

    // ADMIN
    @GetMapping("/admin/pagination")//user uchun ekan
    public ResponseEntity<PageImpl<ProfileDto>> pagination(
            @RequestParam   (value = "page" , defaultValue = "1") int page,
            @RequestParam   (value = "size" ,defaultValue = "2") int size) {
        return ResponseEntity.ok(profileService.pagination(PageUtil.page(page), size));
    }

    // ADMIN
    @GetMapping("/admin/filter")
    public ResponseEntity<PageImpl<ProfileDto>> filter( @RequestBody ProfileFilterDto profileFilterDto ,
                                                        @RequestParam ( value = "page",defaultValue = "1") int page ,
                                                        @RequestParam (value = "size" , defaultValue = "3") int size){
    return ResponseEntity.ok(profileService.filter(profileFilterDto,page-1,size));
    }




}
