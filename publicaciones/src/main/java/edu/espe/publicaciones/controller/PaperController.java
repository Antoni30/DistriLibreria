package edu.espe.publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.espe.publicaciones.dto.PaperDto;
import edu.espe.publicaciones.dto.ResponseDto;
import edu.espe.publicaciones.service.PaperService;

import java.util.List;

@RestController
@RequestMapping("/papers")
public class PaperController {
    @Autowired
    private PaperService paperService;

    // CREATE
    @PostMapping
    public ResponseEntity<ResponseDto> createPaper(@RequestBody PaperDto paperDto) {
        ResponseDto response = paperService.createPaper(paperDto);
        return ResponseEntity.ok(response);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAllPapers() {
        return ResponseEntity.ok(paperService.getAllPapers());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPaperById(@PathVariable int id) {
        return ResponseEntity.ok(paperService.getPaper(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updatePaper(@PathVariable int id, @RequestBody PaperDto paperDto) {
        return ResponseEntity.ok(paperService.updatePaper(id, paperDto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePaper(@PathVariable int id) {
        return ResponseEntity.ok(paperService.deletePaper(id));
    }

}
