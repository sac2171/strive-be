package mike.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
@NamedQueries({
        @NamedQuery(name = "mike.api.Question.findAll",
                query = "select q from Question q"),
})
public class Question {
    @Id
    private long id;
    private String prompt;
    private String responseType;

    public Question() {
    }

    public Question(long id, String prompt, String responseType) {
        this.id = id;
        this.prompt = prompt;
        this.responseType = responseType;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getPrompt() {
        return prompt;
    }

    @JsonProperty
    public String getResponseType() {
        return responseType;
    }
}
