package com.github.awsanttasks.ant;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.util.ArrayList;
import java.util.List;


/**
 * The source code of aws-sdk-for-java can be found at:
 * https://github.com/amazonwebservices/aws-sdk-for-java/
 */
public class BaseAwsTask extends Task
{
    protected List<FileSet> filesets = new ArrayList<FileSet>();
    protected String accessKey;           // AWS access key
    protected String secretKey;           // AWS secret key
    protected boolean verbose;            // output or not
    protected boolean properties;         // write the result to system properties


    public final void addFileset(FileSet fileset)
    {
        filesets.add(fileset);
    }

    public final void setAccessKey(String accessKey)
    {
        this.accessKey = accessKey;
    }

    public final void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    public final void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    public void setProperties(boolean properties)
    {
        this.properties = properties;
    }
}


