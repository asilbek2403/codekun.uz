package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.profile.ProfileDto;
import dasturlashasil.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDto>
    create(@Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PostMapping("/update")
    public ResponseEntity<ProfileDto>
    update(@Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/getlist")
    public ResponseEntity<Page<ProfileDto>>
    getAllProfiles(@RequestParam(defaultValue = "1") int page,
                   @RequestParam(defaultValue = "3") int size) {
        Page<ProfileDto> profilePage = profileService.getAllProfiles(page, size);
        return ResponseEntity.ok(profilePage);
    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO newDto) {
//        return ResponseEntity.ok(service.update(id, newDto));
//    }
//
    @DeleteMapping("/{id}")
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
//    public ResponseEntity<List<ProfileDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") AppLanguageEnum language) {
//        return ResponseEntity.ok(profileService.getA(language));
//    }

}
