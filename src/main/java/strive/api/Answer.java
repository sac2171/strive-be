package strive.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long questionId;
    private long testId;
    private String response;

    @ManyToOne
    @JoinColumn(name="testid", nullable=false)
    private Test test;

    @ManyToOne
    @JoinColumn(name="questionid", nullable=false)
    private Question question;

    public Answer() {
    }

    public Answer(long questionId, long testId, String response) {
        this.questionId = questionId;
        this.testId = testId;
        this.response = response;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    @JsonProperty
    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    @JsonProperty
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @JsonProperty
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
