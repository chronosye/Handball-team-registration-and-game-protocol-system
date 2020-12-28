package com.handball.system.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @NotNull
    private Tournament tournament;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Nav izvēlēts datums un laiks!")
    @Future(message = "Ievadītais datums ir pagātnē")
    private Date date;

    private boolean ended;

    @OneToOne
    @NotNull(message = "Nav izvēlēta mājas komanda!")
    private Team homeTeam;

    @OneToOne
    @NotNull(message = "Nav izvēlēta viesu komanda!")
    private Team awayTeam;

    @NotNull
    private Integer homeTeamGoals;

    @NotNull
    private Integer awayTeamGoals;

    @OneToOne
    @NotNull(message = "Nav izvēlēts protokolists!")
    private User protocolist;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Protocol protocol;

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(Integer homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public Integer getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(Integer awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public User getProtocolist() {
        return protocolist;
    }

    public void setProtocolist(User protocolist) {
        this.protocolist = protocolist;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public void validateGameForm(BindingResult bindingResult) {
        Game game = this;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);
        if (game.getAwayTeam() == game.getHomeTeam() && game.getAwayTeam() != null) {
            bindingResult.rejectValue("homeTeam", "errors.homeTeam", "Mājas un viesu komandas nevar būt vienādas");
            bindingResult.rejectValue("awayTeam", "errors.awayTeam", "Mājas un viesu komandas nevar būt vienādas");
        }
        for (ConstraintViolation<Game> violation : violations) {
            if ((!violation.getPropertyPath().toString().equals("homeTeamGoals")) &&
                    (!violation.getPropertyPath().toString().equals("awayTeamGoals")) &&
                    (!violation.getPropertyPath().toString().equals("tournament"))) {
                bindingResult.rejectValue(violation.getPropertyPath().toString(), "errors." + violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
    }
}
