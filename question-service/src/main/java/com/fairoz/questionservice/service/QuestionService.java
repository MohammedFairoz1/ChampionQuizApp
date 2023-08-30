package com.fairoz.questionservice.service;


import com.fairoz.questionservice.Doa.QuestionDoa;
import com.fairoz.questionservice.model.Question;
import com.fairoz.questionservice.model.QuestionWrapper;
import com.fairoz.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDoa questionDoa;
    public ResponseEntity<List<Question>> getAllQuestions() {
       try {
           return new  ResponseEntity<>(questionDoa.findAll(), HttpStatus.OK);
       }catch (Exception e){
        e.printStackTrace();
    }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return new ResponseEntity<>(questionDoa.findByCategory(category), HttpStatus.OK) ;
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDoa.save(question);
        return new ResponseEntity<>("Sucess", HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions =questionDoa.findRandomQuestionByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
      List<QuestionWrapper> wrappers = new ArrayList<>();
      List<Question> questions = new ArrayList<>();

        for (Integer id:
             questionIds) {
            questions.add(questionDoa.findById(id).get());
        }
        for (Question question:
             questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
            int right = 0;
            for (Response response: responses
            ) {
                Question question =questionDoa.findById(response.getId()).get();
                if(response.getResponse().equals(question.getRightAnswer()))
                    right++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
