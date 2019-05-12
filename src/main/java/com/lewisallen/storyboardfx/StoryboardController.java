package com.lewisallen.storyboardfx;

import com.lewisallen.storyboardfx.story.StoryNode;
import com.lewisallen.storyboardfx.story.StoryTree;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StoryboardController
{
    @FXML
    private GridPane container;

    @FXML
    private TextArea currentLine;

    @FXML
    private TextArea currentStory;

    @FXML
    private TextArea top;

    @FXML
    private Button topSubmit;

    @FXML
    private Button topEdit;

    @FXML
    private Button topGo;

    @FXML
    private TextArea right;

    @FXML
    private Button rightSubmit;

    @FXML
    private Button rightEdit;

    @FXML
    private Button rightGo;

    @FXML
    private TextArea bottom;

    @FXML
    private Button bottomSubmit;

    @FXML
    private Button bottomEdit;

    @FXML
    private Button bottomGo;

    @FXML
    private TextArea left;

    @FXML
    private Button leftSubmit;

    @FXML
    private Button leftEdit;

    @FXML
    private Button leftGo;

    @FXML
    private Button goBack;

    @FXML
    private Button goStart;

    @FXML
    private Button goRestart;

    @FXML
    private Button printBreaks;

    @FXML
    private Button printSpaces;

    @FXML
    public void initialize()
    {
        StoryNode root = new StoryNode("It's 3 AM. An official phone alert wakes you up.");
        StoryTree tree = new StoryTree(root);
        updateAllStoryLines(tree);

        // Set behaviour of submit buttons - Lock in the text.
        topSubmit.setOnAction((e) ->
        {
            StoryNode node = new StoryNode(top.getText().trim());
            tree.getCurrent().setTopChild(node);
            topSubmit.setDisable(true);
            top.setEditable(false);
            topEdit.setDisable(false);
            topGo.setDisable(false);
        });

        rightSubmit.setOnAction((e) ->
        {
            StoryNode node = new StoryNode(right.getText().trim());
            tree.getCurrent().setRightChild(node);
            rightSubmit.setDisable(true);
            right.setEditable(false);
            rightEdit.setDisable(false);
            rightGo.setDisable(false);
        });

        bottomSubmit.setOnAction((e) ->
        {
            StoryNode node = new StoryNode(bottom.getText().trim());
            tree.getCurrent().setBottomChild(node);
            bottomSubmit.setDisable(true);
            bottom.setEditable(false);
            bottomEdit.setDisable(false);
            bottomGo.setDisable(false);
        });

        leftSubmit.setOnAction((e) ->
        {
            StoryNode node = new StoryNode(left.getText().trim());
            tree.getCurrent().setLeftChild(node);
            leftSubmit.setDisable(true);
            left.setEditable(false);
            leftEdit.setDisable(false);
            leftGo.setDisable(false);
        });

        // Set behaviour of edit buttons - Edit locked in text.
        topEdit.setOnAction((e) -> {
            tree.getCurrent().setTopChild(null);
            topSubmit.setDisable(false);
            topEdit.setDisable(true);
            topGo.setDisable(true);
            top.setEditable(true);
        });

        rightEdit.setOnAction((e) -> {
            tree.getCurrent().setTopChild(null);
            rightSubmit.setDisable(false);
            rightEdit.setDisable(true);
            rightGo.setDisable(true);
            right.setEditable(true);
        });

        bottomEdit.setOnAction((e) -> {
            tree.getCurrent().setTopChild(null);
            bottomSubmit.setDisable(false);
            bottomEdit.setDisable(true);
            bottomGo.setDisable(true);
            bottom.setEditable(true);
        });

        leftEdit.setOnAction((e) -> {
            tree.getCurrent().setTopChild(null);
            leftSubmit.setDisable(false);
            leftEdit.setDisable(true);
            leftGo.setDisable(true);
            left.setEditable(true);
        });

        // Set behaviour of the Choose buttons - Navigate to that node.
        topGo.setOnAction((e) ->
        {
            tree.setCurrent(tree.getCurrent().getTopChild());
            updateAllStoryLines(tree);
        });

        rightGo.setOnAction((e) ->
        {
            tree.setCurrent(tree.getCurrent().getRightChild());
            updateAllStoryLines(tree);
        });

        bottomGo.setOnAction((e) ->
        {
            tree.setCurrent(tree.getCurrent().getBottomChild());
            updateAllStoryLines(tree);
        });

        leftGo.setOnAction((e) ->
        {
            tree.setCurrent(tree.getCurrent().getLeftChild());
            updateAllStoryLines(tree);
        });

        // Set up back, start and reset buttons
        goBack.setOnAction((e) ->
        {
            tree.setCurrent(tree.getCurrent().getParent());
            updateAllStoryLines(tree);
        });

        goStart.setOnAction((e) ->
        {
            tree.setCurrent(tree.getRoot());
            updateAllStoryLines(tree);
        });

        goRestart.setOnAction((e) ->
        {
            tree.setCurrent(tree.getRoot());
            tree.getCurrent().setTopChild(null);
            tree.getCurrent().setRightChild(null);
            tree.getCurrent().setBottomChild(null);
            tree.getCurrent().setLeftChild(null);
            updateAllStoryLines(tree);
        });

        // Set behaviour of print buttons - save story to file.
        printBreaks.setOnAction((event) -> {
            try
            {
                File selectedDirectory = showSelectedFileDialog();

                if(selectedDirectory != null)
                {
                    writeStoryToFile(selectedDirectory.getAbsolutePath(), tree.getCurrent().getCurrentStory(), System.lineSeparator());
                }
            }
            catch (IOException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not write story to file.");

                alert.showAndWait();
            }
        });

        printSpaces.setOnAction((event) -> {
            try
            {
                File selectedDirectory = showSelectedFileDialog();

                if(selectedDirectory != null)
                {
                    writeStoryToFile(selectedDirectory.getAbsolutePath(), tree.getCurrent().getCurrentStory(), " ");
                }
            }
            catch (IOException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not write story to file.");

                alert.showAndWait();
            }
        });

    }

    /**
     * Given a tree, get the current node and update all story lines in the UI.
     *
     * @param tree Tree containing a current node.
     */
    private void updateAllStoryLines(StoryTree tree)
    {
        setCurrentLine(tree.getCurrent().getValue().get());
        setCurrentStory(String.join("\n", tree.getCurrent().getCurrentStory()));

        if (tree.getCurrent().getParent() == null)
        {
            goBack.setDisable(true);
        }
        else
        {
            goBack.setDisable(false);
        }

        if (tree.getCurrent().getTopChild() != null)
        {
            top.setText(tree.getCurrent().getTopChild().getValue().get());
            topSubmit.setDisable(true);
            topEdit.setDisable(false);
            topGo.setDisable(false);
            top.setEditable(false);
        }
        else
        {
            top.setText("");
            topSubmit.setDisable(false);
            topEdit.setDisable(true);
            topGo.setDisable(true);
            top.setEditable(true);
        }

        if (tree.getCurrent().getRightChild() != null)
        {
            right.setText(tree.getCurrent().getRightChild().getValue().get());
            rightSubmit.setDisable(true);
            rightEdit.setDisable(false);
            rightGo.setDisable(false);
            right.setEditable(false);
        }
        else
        {
            right.setText("");
            rightSubmit.setDisable(false);
            rightEdit.setDisable(true);
            rightGo.setDisable(true);
            right.setEditable(true);
        }

        if (tree.getCurrent().getBottomChild() != null)
        {
            bottom.setText(tree.getCurrent().getBottomChild().getValue().get());
            bottomSubmit.setDisable(true);
            bottomEdit.setDisable(false);
            bottomGo.setDisable(false);
            bottom.setEditable(false);
        }
        else
        {
            bottom.setText("");
            bottomSubmit.setDisable(false);
            bottomEdit.setDisable(true);
            bottomGo.setDisable(true);
            bottom.setEditable(true);
        }

        if (tree.getCurrent().getLeftChild() != null)
        {
            left.setText(tree.getCurrent().getLeftChild().getValue().get());
            leftSubmit.setDisable(true);
            leftEdit.setDisable(false);
            leftGo.setDisable(false);
            left.setEditable(false);
        }
        else
        {
            left.setText("");
            leftSubmit.setDisable(false);
            leftEdit.setDisable(true);
            leftGo.setDisable(true);
            left.setEditable(true);
        }
    }

    private void setCurrentStory(String currentStory)
    {
        this.currentStory.setText(currentStory);
    }

    private void setCurrentLine(String line)
    {
        currentLine.setText(line);
    }

    private File showSelectedFileDialog()
    {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);

        return fc.showSaveDialog(container.getScene().getWindow());
    }

    private void writeStoryToFile(String directoryPath, List<String> sentenceList, CharSequence delimiter) throws IOException
    {
        String story = String.join(delimiter, sentenceList);
        Path newFilePath = Paths.get(directoryPath);
        Files.write(newFilePath, story.getBytes());

        System.out.println(String.format("Wrote file to %s", newFilePath.toString()));
    }
}
