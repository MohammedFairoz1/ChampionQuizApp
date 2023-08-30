package com.fairoz.questionservice.Doa;


import com.fairoz.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDoa extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "select q.id from question q where q.category=:category order by RAND() limit :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(String category, int numQ);

}
