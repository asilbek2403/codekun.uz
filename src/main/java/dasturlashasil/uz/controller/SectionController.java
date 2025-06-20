package dasturlashasil.uz.controller;



import dasturlashasil.uz.Dto.LangResponseDto;
import dasturlashasil.uz.Dto.SectionDto;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.service.SectionService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/api/section")

public class SectionController {


    @Autowired
    private SectionService service;

    @PostMapping("/create")
    public ResponseEntity<SectionDto> create(@Valid @RequestBody SectionDto dto) {

        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SectionDto> update(@PathVariable("id") Integer id, @RequestBody SectionDto newDto) {
        return ResponseEntity.ok(service.update(id, newDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SectionDto>> getAll() {
        return ResponseEntity.ok(service.getAllByOrder());
    }

    // /api/v1/category/lang?language=uz
    @GetMapping("/lang")
    public ResponseEntity<List<LangResponseDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "UZ") LanguageList language) {
        return ResponseEntity.ok(service.getAllbyLang(language));
    }


}
