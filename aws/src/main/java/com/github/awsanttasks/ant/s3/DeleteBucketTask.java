package com.github.awsanttasks.ant.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteBucketRequest;

/**
 * Be careful with this class, will delete a complete S3 bucket
 *
 */
public class DeleteBucketTask extends BaseS3Task
{
    @Override
    protected void executeInternal(AmazonS3Client client)
    {
        DeleteBucketRequest request = new DeleteBucketRequest(bucket);
        client.deleteBucket(request);
    }
}
