package com.learn.quizapp.service;

import com.learn.quizapp.dao.QuestionDAO;
import com.learn.quizapp.dao.QuizDAO;
import com.learn.quizapp.dao.ResponseDAO;
import com.learn.quizapp.dao.UserDetailDao;
import com.learn.quizapp.model.*;
import com.learn.quizapp.vo.QuestionVO;
import com.learn.quizapp.vo.QuizVO;
import com.learn.quizapp.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDAO quizDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private ResponseDAO responseDAO;

    public void createQuiz(QuizVO quizVO) {
        List<Question> questions = questionDAO.findByCategoryAndDifficultyLevel(QuestionCategory.valueOf(quizVO.getCategory()), QuestionDifficultyLevel.valueOf(quizVO.getDifficultyLevel()));
        Collections.shuffle(questions);
        if(questions.size()>quizVO.getNumberOfQuestions())
            questions=questions.subList(0,quizVO.getNumberOfQuestions());
        Quiz quiz = new Quiz();
        quiz.setTitle(quizVO.getTitle());
        quiz.setQuestions(questions);
        quizDAO.save(quiz);
    }

    public List<QuestionVO> getQuestions(String title) {
        Optional<Quiz> q= quizDAO.findByTitle(title);
        List<QuestionVO> ques= new ArrayList<>();
        if(q.isPresent()) {
            for (Question question : q.get().getQuestions()) {
                QuestionVO questionVO = new QuestionVO();
                questionVO.setId(question.getId());
                questionVO.setQuestion(question.getQuestion());
                questionVO.setOption1(question.getOption1());
                questionVO.setOption2(question.getOption2());
                questionVO.setOption3(question.getOption3());
                questionVO.setOption4(question.getOption4());
                ques.add(questionVO);
            }
        }
        return ques;
    }

    public String calculateScore(Long id, List<ResponseVO> response, String username) {
        Optional<Quiz> quiz= quizDAO.findById(id);
        List<Question> questions= new ArrayList<>();
        quiz.ifPresent(q->{
            questions.addAll(q.getQuestions());
        });
        int result=0;
        int i=0;
        for(ResponseVO responseVO:response){
            if(responseVO.getResponse().equals(questions.get(i).getAnswer())){
                result++;
            }
            i++;
        }
        Response res =new Response();
        res.setQuiz(quiz.get());
        res.setUser(userDetailDao.findByUsername(username));
        res.setScore(result);
        res.setQuizTitle(quiz.get().getTitle());
        res.setUsername(username);
        responseDAO.save(res);
        return result+" Out of "+ questions.size();
    }

    public List<QuizVO> getAllQuizes() {
        List<Quiz> quizes = quizDAO.findAll();

        List<QuizVO>  quizVOS= new ArrayList<>();
        for(Quiz quiz:quizes){
            QuizVO quizVO = new QuizVO();
            quizVO.setTitle(quiz.getTitle()+" - "+quiz.getId());
            if(!quiz.getQuestions().isEmpty()){
                quizVO.setCategory(quiz.getQuestions().get(0).getCategory().toString());
                quizVO.setDifficultyLevel(quiz.getQuestions().get(0).getDifficultyLevel().toString());
            }
            quizVO.setNumberOfQuestions(quiz.getQuestions().size());
            quizVOS.add(quizVO);
        }
        return quizVOS;
    }

    public boolean checkDuplicateTitle(String title) {
        Optional<Quiz> q= quizDAO.findByTitle(title);
        return q.isPresent();
    }
}
