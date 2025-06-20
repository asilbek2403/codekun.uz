package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.regionD.RegionDto;
import dasturlashasil.uz.Dto.regionD.RegionLangDto;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("mm")
    public ResponseEntity<String> qad() {
        return ResponseEntity.ok("Muvaffaqqiyat");
    }

    // 1. Create (ADMIN)
    @PostMapping("/create")
    public ResponseEntity<RegionDto> create(@RequestBody RegionDto dto) {
        RegionDto regionDto = regionService.create(dto);
        return new ResponseEntity<>(regionDto, HttpStatus.CREATED);
    }

    // 2. Update (ADMIN)
    @PutMapping("/update/{id}")
    public ResponseEntity<RegionDto> update(@PathVariable Integer id, @RequestBody RegionDto dto) {
        RegionDto regionDto = regionService.update(id, dto);
//            return ResponseEntity.ok(regionDto);
        return new ResponseEntity<>(regionDto, HttpStatus.CREATED);

    }

    // 3. Delete (ADMIN)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 4. Get List (ADMIN)
    @GetMapping("/list")
    public ResponseEntity<List<RegionDto>> getAll() {
        List<RegionDto> regionDto = regionService.getAll();
        return ResponseEntity.ok(regionDto);
    }

    // 5. Get By Lang
    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<RegionLangDto>> getByLangA(@PathVariable LanguageList lang) {
        List<RegionLangDto> list = regionService.getAllLang(lang);
        return ResponseEntity.ok(list);
    }


}
