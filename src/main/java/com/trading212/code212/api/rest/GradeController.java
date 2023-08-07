package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.GradeEditRequest;
import com.trading212.code212.core.GradeService;
import com.trading212.code212.core.models.GradeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/{examId}/students/{userId}")
    public List<GradeDTO> getGradesForStudent(@PathVariable int examId, @PathVariable long userId) {
        List<GradeDTO> gradeForStudent = gradeService.getGradeForStudent(examId, userId);
        System.out.println(gradeForStudent);
        return gradeForStudent;
    }

    @PatchMapping
    public GradeDTO editReport(@RequestBody GradeEditRequest request) {
        return gradeService.editReport(request);
    }

}
