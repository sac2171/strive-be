package strive.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tests")
@NamedQueries({
        @NamedQuery(name = "strive.api.Test.findAll",
                query = "select t from Test t"),
})
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private Integer grade;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false, insertable=false, updatable=false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="test")
    private List<Answer> answers;

    public Test() {
    }

    public Test(long userId) {
        this.userId = userId;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @JsonProperty
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
