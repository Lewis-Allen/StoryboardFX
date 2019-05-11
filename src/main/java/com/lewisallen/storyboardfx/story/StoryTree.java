package com.lewisallen.storyboardfx.story;

public class StoryTree
{
    private StoryNode current;
    private StoryNode root;

    public StoryTree(StoryNode root)
    {
        this.root = root;
        this.current = root;
    }

    public StoryNode getCurrent()
    {
        return current;
    }

    public void setCurrent(StoryNode newCurrent)
    {
        this.current = newCurrent;
    }

    public StoryNode getRoot()
    {
        return root;
    }
}
