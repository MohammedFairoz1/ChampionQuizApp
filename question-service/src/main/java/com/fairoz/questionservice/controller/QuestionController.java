package com.fairoz.questionservice.controller;


import com.fairoz.questionservice.model.Question;
import com.fairoz.questionservice.model.QuestionWrapper;
import com.fairoz.questionservice.model.Response;
import com.fairoz.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    Environment environment;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
            return questionService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }
    @PostMapping("add/")
public ResponseEntity<String> addQuestion(@RequestBody Question question){
       return questionService.addQuestion(question);
}
@GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz
        (@RequestParam String categoryName,@RequestParam Integer numQuestions ){
        return questionService.getQuestionForQuiz(categoryName, numQuestions);
}
@PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
    System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
}
@PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return  questionService.getScore(responses);
}
}
