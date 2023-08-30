package com.fairoz.quizservice.controller;


import com.fairoz.quizservice.model.QuestionWrapper;
import com.fairoz.quizservice.model.Quizto;
import com.fairoz.quizservice.model.Response;
import com.fairoz.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody Quizto quizto){
        return quizService.createQuiz(quizto.getCategoryName(), quizto.getNumQuestion(), quizto.getTitle());
    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return  quizService.getQuizQuestions(id);
    }
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses ){
        return quizService.calculateResult(id, responses);
    }
}
