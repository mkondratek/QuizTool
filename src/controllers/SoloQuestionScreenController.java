package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import logic.*;
import javafx.scene.layout.Pane;

import static logic.AnsState.GOOD;


import java.io.IOException;

public class SoloQuestionScreenController {
    private Game game;
    private SoloGame soloGame;
    private int questionNumber;
    private Question question;
    private boolean changingColors = true;
    private boolean nextQuestion = false;

    @FXML
    public Label setLabel;
    @FXML
    public Label qLabel;
    @FXML
    private Button ans3;
    @FXML
    private Button ans4;
    @FXML
    private Button ans1;
    @FXML
    private Button ans2;
    @FXML
    private Label questionLabel;

    private MainController mainController;

    @FXML
    public void backToMainMenu(ActionEvent actionEvent) {
        System.out.println(actionEvent);
        mainController.reinitialize();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        questionLabel.setWrapText(true);
        ans1.setWrapText(true);
        ans2.setWrapText(true);
        ans3.setWrapText(true);
        ans4.setWrapText(true);
        questionNumber = 0;
    }

    public void setQuestion(Question question) {
        ans1.setStyle("");
        ans2.setStyle("");
        ans3.setStyle("");
        ans4.setStyle("");

        qLabel.setText("Q: " + (questionNumber + 1) + " / 5");
        this.question = question;
        questionLabel.setText(question.getContent());
        ans1.setText(question.getMaskedAnswer(0));
        ans2.setText(question.getMaskedAnswer(1));
        ans3.setText(question.getMaskedAnswer(2));
        ans4.setText(question.getMaskedAnswer(3));
        questionNumber++;
    }

    @FXML
    public void setAnswer(ActionEvent actionEvent) {
        System.out.println(actionEvent);
        int tmp = 0;
        if (actionEvent.getSource() == ans1) tmp = 0;
        else if (actionEvent.getSource() == ans2) tmp = 1;
        else if (actionEvent.getSource() == ans3) tmp = 2;
        else if (actionEvent.getSource() == ans4) tmp = 3;

        question.answer(tmp);
        System.out.println(question.getState());

        if(!nextQuestion) {
            setCorrespondingColor((Button) actionEvent.getSource(), question.getState());
            changingColors = false;
            nextQuestion = true;
        }
        else {
            loadNextQuestion();
            changingColors = true;
            nextQuestion = false;
        }
    }

    public void loadNextQuestion() {
        if (questionNumber < game.getSeries().size()) {
            setQuestion(game.getSeries().get(questionNumber));
        } else {
            System.out.println("Out of questions! Continuing...");
            game.incrementSeriesNo();

            if (game.getSeriesNo() == 3) {
                RankAdder rankAdder = new RankAdder();
                rankAdder.add(new RankRecord(soloGame.getPlayer(), soloGame.getPlayerpoints()));

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SoloSummaryScreen.fxml"));
                Pane pane = null;

                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                SoloSummaryController controller = loader.getController();
                controller.setMainController(mainController);
                controller.setSummary(soloGame);
                mainController.addToStackPane(pane);
            }
            else {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Continue.fxml"));
                Pane pane = null;

                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ContinueController controller = loader.getController();
                controller.setMainController(mainController);
                controller.setGame(game);
                mainController.addToStackPane(pane);
            }
        }
    }

    public void setGame(SoloGame game) {
        this.game = game;
        this.soloGame = game;
        setQuestion(game.getSeries().get(0));
        setLabel.setText("Set: " + (game.getSeriesNo() + 1) + " / 3");
        qLabel.setText("Q: 1 / 5");
    }

    @FXML
    public void reDye(MouseEvent mouseEvent) {
        if (!changingColors) return;
        if(mouseEvent.getSource() == ans1) ans1.setStyle("-fx-background-color:#fcfa63;");
        if(mouseEvent.getSource() == ans2) ans2.setStyle("-fx-background-color:#fcfa63;");
        if(mouseEvent.getSource() == ans3) ans3.setStyle("-fx-background-color:#fcfa63;");
        if(mouseEvent.getSource() == ans4) ans4.setStyle("-fx-background-color:#fcfa63;");
    }
    @FXML
    public void dye(MouseEvent mouseEvent) {
        if (!changingColors) return;
        if(mouseEvent.getSource() == ans1) ans1.setStyle("");
        if(mouseEvent.getSource() == ans2) ans2.setStyle("");
        if(mouseEvent.getSource() == ans3) ans3.setStyle("");
        if(mouseEvent.getSource() == ans4) ans4.setStyle("");
    }

    public void setCorrespondingColor(Button button, AnsState AS){
        button.setStyle("");
        if (AS == GOOD) {
            this.soloGame.setPlayerpoints(soloGame.getPlayerpoints() + 1);
            button.setStyle("-fx-background-color:#9aee7d");
        } else {
            this.soloGame.setPlayerpoints(soloGame.getPlayerpoints() - 1);
            button.setStyle("-fx-background-color:#ff7581;");
            Integer id = question.getRightAnswer();
            if(id == 0) ans1.setStyle("-fx-background-color:#9aee7d");
            else if(id == 1) ans2.setStyle("-fx-background-color:#9aee7d");
            else if(id == 2) ans3.setStyle("-fx-background-color:#9aee7d");
            else if(id == 3) ans4.setStyle("-fx-background-color:#9aee7d");
        }
    }
}
