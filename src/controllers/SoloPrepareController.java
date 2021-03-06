package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import logic.CategoriesSet;
import logic.SoloGame;

import java.io.IOException;

public class SoloPrepareController extends PrepareController {
    private SoloGame game;
    private String nickname1;

    @FXML
    private TextField player1Nickname;

    public SoloPrepareController() {
        game = new SoloGame();
    }

    @FXML
    public void initialize() {
        button1.setText(new CategoriesSet().getCategory(game.getBannedCategories()));
        button2.setText(new CategoriesSet().getCategory(game.getBannedCategories()));
        button3.setText(new CategoriesSet().getCategory(game.getBannedCategories()));

        System.out.println(player1Nickname.getFont().getStyle());
        player1Nickname.getStyleClass().add("custom");
    }

    @FXML
    public void askQuestion(ActionEvent actionEvent) {
        System.out.println(actionEvent);

        nickname1 = player1Nickname.getText();
        game.setPlayer(nickname1);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SoloQuestionScreen.fxml"));
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cat = ((Button)actionEvent.getSource()).getText();
        game.setSeries(cat);
        game.getBannedCategories().removeLast();
        game.getBannedCategories().removeLast();
        game.getBannedCategories().removeLast();
        game.banCategory(cat);
        SoloQuestionScreenController controller = loader.getController();
        controller.setMainController(getMainController());
        controller.setGame(game);
        getMainController().addToStackPane(pane);
    }
}