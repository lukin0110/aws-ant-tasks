package com.github.awsanttasks.ant.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

/**
 * TODO TODO
 */
public class DeleteFilesTask extends BaseS3Task
{
    private String targetDir;

    public void setTargetDir(String targetDir)
    {
        this.targetDir = targetDir;
    }

    @Override
    protected void executeInternal(AmazonS3Client client)
    {
        log("TargetDir = " + targetDir);
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, "keykey");

    }

}
