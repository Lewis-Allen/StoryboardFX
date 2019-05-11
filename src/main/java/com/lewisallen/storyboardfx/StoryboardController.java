package com.lewisallen.storyboardfx;

import com.lewisallen.storyboardfx.story.StoryNode;
import com.lewisallen.storyboardfx.story.StoryTree;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class StoryboardController
{
    @FXML
    private TextArea currentLine;

    @FXML
    private TextArea currentStory;

    @FXML
    private Button topSubmit;

    @FXML
    private TextArea top;

    @FXML
    private Button topGo;

    @FXML
    private Button rightSubmit;

    @FXML
    private TextArea right;

    @FXML
    private Button rightGo;

    @FXML
    private Button bottomSubmit;

    @FXML
    private TextArea bottom;

    @FXML
    private Button bottomGo;

    @FXML
    private Button leftSubmit;

    @FXML
    private TextArea left;

    @FXML
    private Button leftGo;

    @FXML
    private Button goBack;

    @FXML
    private Button goStart;

    @FXML
    private Button goRestart;

    @FXML
    public void initialize()
    {
        StoryNode root = new StoryNode("It's 3 AM. An official phone alert wakes you up.");
        StoryTree tree = new StoryTree(root);
        updateAllStoryLines(tree);

        // Set behaviour of submit buttons - Lock in the text.
        topSubmit.setOnAction((e) -> {
            StoryNode node = new StoryNode(top.getText().trim());
            tree.getCurrent().setTopChild(node);
            topSubmit.setDisable(true);
            top.setEditable(false);
            topGo.setDisable(false);
        });

        rightSubmit.setOnAction((e) -> {
            StoryNode node = new StoryNode(right.getText().trim());
            tree.getCurrent().setRightChild(node);
            rightSubmit.setDisable(true);
            right.setEditable(false);
            rightGo.setDisable(false);
        });

        bottomSubmit.setOnAction((e) -> {
            StoryNode node = new StoryNode(bottom.getText().trim());
            tree.getCurrent().setBottomChild(node);
            bottomSubmit.setDisable(true);
            bottom.setEditable(false);
            bottomGo.setDisable(false);
        });

        leftSubmit.setOnAction((e) -> {
            StoryNode node = new StoryNode(left.getText().trim());
            tree.getCurrent().setLeftChild(node);
            leftSubmit.setDisable(true);
            left.setEditable(false);
            leftGo.setDisable(false);
        });

        // Set behaviour of the Choose buttons - Navigate to that node.
        topGo.setOnAction((e) ->
        {
            tree.setCurrent(tree.getCurrent().getTopChild());
            updateAllStoryLines(tree);
        });

        rightGo.setOnAction((e) -> {
            tree.setCurrent(tree.getCurrent().getRightChild());
            updateAllStoryLines(tree);
        });

        bottomGo.setOnAction((e) -> {
            tree.setCurrent(tree.getCurrent().getBottomChild());
            updateAllStoryLines(tree);
        });

        leftGo.setOnAction((e) -> {
            tree.setCurrent(tree.getCurrent().getLeftChild());
            updateAllStoryLines(tree);
        });

        // Set up back and reset buttons
        goBack.setOnAction((e) -> {
            tree.setCurrent(tree.getCurrent().getParent());
            updateAllStoryLines(tree);
        });

        goStart.setOnAction((e) -> {
            tree.setCurrent(tree.getRoot());
            updateAllStoryLines(tree);
        });

        goRestart.setOnAction((e) -> {
            tree.setCurrent(tree.getRoot());
            tree.getCurrent().setTopChild(null);
            tree.getCurrent().setRightChild(null);
            tree.getCurrent().setBottomChild(null);
            tree.getCurrent().setLeftChild(null);
            updateAllStoryLines(tree);
        });
    }

    /**
     * Given a tree, get the current node and update all story lines in the UI.
     * @param tree Tree containing a current node.
     */
    private void updateAllStoryLines(StoryTree tree)
    {
        setCurrentLine(tree.getCurrent().getValue().get());
        setCurrentStory(tree.getCurrent().getCurrentStory());
        if(tree.getCurrent().getParent() == null)
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
            topGo.setDisable(false);
            top.setEditable(false);
        }
        else
        {
            top.setText("");
            topSubmit.setDisable(false);
            topGo.setDisable(true);
            top.setEditable(true);
        }

        if(tree.getCurrent().getRightChild() != null)
        {
            right.setText(tree.getCurrent().getRightChild().getValue().get());
            rightSubmit.setDisable(true);
            rightGo.setDisable(false);
            right.setEditable(false);
        }
        else
        {
            right.setText("");
            rightSubmit.setDisable(false);
            rightGo.setDisable(true);
            right.setEditable(true);
        }

        if(tree.getCurrent().getBottomChild() != null)
        {
            bottom.setText(tree.getCurrent().getBottomChild().getValue().get());
            bottomSubmit.setDisable(true);
            bottomGo.setDisable(false);
            bottom.setEditable(false);
        }
        else
        {
            bottom.setText("");
            bottomSubmit.setDisable(false);
            bottomGo.setDisable(true);
            bottom.setEditable(true);
        }

        if(tree.getCurrent().getLeftChild() != null)
        {
            left.setText(tree.getCurrent().getLeftChild().getValue().get());
            leftSubmit.setDisable(true);
            leftGo.setDisable(false);
            left.setEditable(false);
        }
        else
        {
            left.setText("");
            leftSubmit.setDisable(false);
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
}
