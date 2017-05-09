package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.CategoriesSet;
import logic.SoloGame;

public class SoloPrepareController extends PrepareController{
    private SoloGame game;

    @FXML
    private TextField nicknameTextField;

    public SoloPrepareController() {
        game = new SoloGame();
    }

    @FXML
    public void initialize() {
        button1.setText(game.banCategory(
                new CategoriesSet().getCategory(game.getBannedCategories())));
        button2.setText(game.banCategory(
                new CategoriesSet().getCategory(game.getBannedCategories())));
        button3.setText(game.banCategory(
                new CategoriesSet().getCategory(game.getBannedCategories())));

        EventHandler handler = (e) -> {
            game.setSeries(((Button) e.getSource()).getText());
        };

        button1.addEventHandler(ActionEvent.ACTION, handler);
        button2.addEventHandler(ActionEvent.ACTION, handler);
        button3.addEventHandler(ActionEvent.ACTION, handler);
    }
}
