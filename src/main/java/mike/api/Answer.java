package mike.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long questionId;
    private long examId;
    private String response;

    public Answer() {
    }

    public Answer(long questionId, long examId, String response) {
        this.questionId = questionId;
        this.examId = examId;
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
    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    @JsonProperty
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
