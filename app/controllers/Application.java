package controllers;

import forms.PlayerForm;
import models.Game;
import models.Player;
import models.Turn;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    private static Result notFound = notFound("Game was not found").as("text/html");

    public static Result index() {
        return ok(index.render());
    }

    public static Result launchGame() {
        PlayerForm form = Form.form(PlayerForm.class).bindFromRequest().get();
        List<Player> players = new ArrayList<>();
        players.add(new Player(form.getFirstPlayerName()));
        players.add(new Player(form.getSecondPlayerName()));
        Game gravaHalGame = new Game(players);
        gravaHalGame.save();
        return redirect(routes.Application.playGame(gravaHalGame.getId()));
    }

    public static Result playGame(String gameId) {
        Game gravaHalGame = new Model.Finder<>(String.class, Game.class).byId(gameId);

        if (gravaHalGame != null) {
            return ok(game.render(gravaHalGame));
        }
        else {
            return notFound;
        }
    }

    public static Result round(String gameId, String playerName, int pitIndex) {
        Game gravaHalGame = new Model.Finder<>(String.class, Game.class).byId(gameId);

        if (gravaHalGame != null) {
            gravaHalGame.round(new Turn(gravaHalGame, playerName, pitIndex));
            gravaHalGame.save();
            return ok(game.render(gravaHalGame));
        }
        else {
            return notFound;
        }
    }
}
