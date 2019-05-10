package com.lewisallen.storyboardfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StoryBoardApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Storyboard.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 830, 400);

        primaryStage.setTitle("StoryboardFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
