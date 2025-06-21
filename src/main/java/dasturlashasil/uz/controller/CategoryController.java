package dasturlashasil.uz.controller;


import dasturlashasil.uz.Dto.CategoryDto;
import dasturlashasil.uz.Enums.LanguageList;
import dasturlashasil.uz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") Integer id, @RequestBody CategoryDto newDto) {
        return ResponseEntity.ok(service.update(id, newDto));
    }

    @DeleteMapping("/DeleteId/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/getList")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(service.getAllByOrder());
    }

    // /api/v1/category/lang?language=uz
    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageList language) {
        return ResponseEntity.ok(service.getAllByLang(language));
    }
}


