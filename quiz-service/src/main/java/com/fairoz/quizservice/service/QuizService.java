package com.fairoz.quizservice.service;


import com.fairoz.quizservice.dao.QuizDao;
import com.fairoz.quizservice.feign.QuizInterface;
import com.fairoz.quizservice.model.QuestionWrapper;
import com.fairoz.quizservice.model.Quiz;
import com.fairoz.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Sucess Fairoz", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
       Quiz quiz = quizDao.findById(id).get();
       List<Integer> questionIds = quiz.getQuestionIds();
       quizInterface.getQuestionsFromId(questionIds);
       ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
       ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
