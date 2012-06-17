package com.github.awsanttasks.ant.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.github.awsanttasks.ant.util.PropertiesUtil;



public class CreateTask extends BaseS3Task
{
    @Override
    protected void executeInternal(AmazonS3Client client) throws Exception
    {
        CreateBucketRequest request = new CreateBucketRequest(bucket);
        request.setCannedAcl(aclEnum);
        Bucket bucket = client.createBucket(request);

        log(" Bucket name = " + bucket.getName());

        if(bucket.getCreationDate()!=null)
        {
            log(" Creation Date = " + bucket.getCreationDate());
        }

        if(bucket.getOwner()!=null)
        {
            log(" Owner name = " + bucket.getOwner().getDisplayName());
            log(" Owner id = " + bucket.getOwner().getId());
        }

        if(properties)
        {
            PropertiesUtil.write(getProject(), bucket);
        }
    }
}



