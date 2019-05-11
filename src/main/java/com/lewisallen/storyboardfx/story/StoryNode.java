package com.lewisallen.storyboardfx.story;

import javafx.beans.property.SimpleStringProperty;

public class StoryNode
{
    private SimpleStringProperty value;

    private StoryNode parent,
                 topChild,
                 rightChild,
                 bottomChild,
                 leftChild;

    public StoryNode(String value)
    {
        this.value = new SimpleStringProperty(value);
    }

    public StoryNode getParent()
    {
        return parent;
    }

    public StoryNode getTopChild()
    {
        return topChild;
    }

    public void setTopChild(StoryNode topChild)
    {
        this.topChild = topChild;
        if(topChild != null)
        {
            topChild.parent = this;
        }
    }

    public StoryNode getRightChild()
    {
        return rightChild;
    }

    public void setRightChild(StoryNode rightChild)
    {
        this.rightChild = rightChild;
        if(rightChild != null)
        {
            rightChild.parent = this;
        }
    }

    public StoryNode getBottomChild()
    {
        return bottomChild;
    }

    public void setBottomChild(StoryNode bottomChild)
    {
        this.bottomChild = bottomChild;
        if(bottomChild != null)
        {
            bottomChild.parent = this;
        }
    }

    public StoryNode getLeftChild()
    {
        return leftChild;
    }

    public void setLeftChild(StoryNode leftChild)
    {
        this.leftChild = leftChild;
        if(leftChild != null)
        {
            leftChild.parent = this;
        }
    }

    public SimpleStringProperty getValue()
    {
        return value;
    }

    /**
     * Gets the full current story based off this node and any parents.
     * @return Full story
     */
    public String getCurrentStory()
    {
        String story = concatenateStory("");

        return story;
    }

    public String concatenateStory(String story)
    {
        if(this.parent != null)
        {
            story = parent.concatenateStory(story);
        }

        story += "- " + value.get() + "\n";
        return story;
    }
}
