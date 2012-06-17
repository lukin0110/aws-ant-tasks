package com.github.awsanttasks.ant;

import com.github.awsanttasks.ant.util.Base36;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Simple task that generates a base36 encoded value and writes it to
 * the specified property name.
 *
 * The System.currentTimeMillis() is used to generated the value.
 *
 * Why?
 * This can be used for file revving or generate a unique new directory on the fly
 */
public class Base36Task extends Task
{
    private String property;
    private boolean lower;

    public void setProperty(String property)
    {
        this.property = property;
    }

    public void setLower(boolean lower)
    {
        this.lower = lower;
    }

    @Override
    public void execute() throws BuildException
    {
        log("Writing base36 property");

        String key = Base36.encode(System.currentTimeMillis());

        if(lower)
        {
            key = key.toLowerCase();
        }

        getProject().setProperty(property, key);

        log("Property '" + property + "' written: " + key);
    }
}
