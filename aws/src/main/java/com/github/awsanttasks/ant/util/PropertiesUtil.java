package com.github.awsanttasks.ant.util;

import com.amazonaws.services.s3.model.Bucket;
import com.github.awsanttasks.cloudfront.CloudFrontResponse;
import org.apache.tools.ant.Project;

public final class PropertiesUtil
{
    public static final String CLOUDFRONT_CREATE = "ant.aws.cloudfront.create.";
    public static final String S3_CREATE = "ant.aws.s3.create.";

    private PropertiesUtil(){}

    public static void write(Project project, CloudFrontResponse response)
    {
        if(project!=null && response!=null)
        {
            project.setProperty(CLOUDFRONT_CREATE + "id", response.getId());
            project.setProperty(CLOUDFRONT_CREATE + "callerReference", response.getCallerReference());
            project.setProperty(CLOUDFRONT_CREATE + "status", response.getStatus());
            project.setProperty(CLOUDFRONT_CREATE + "domainName", response.getDomainName());
            project.setProperty(CLOUDFRONT_CREATE + "lastModified", response.getLastModifiedTime());
        }
    }


    public static void write(Project project, Bucket bucket)
    {
        if(project!=null && bucket!=null)
        {
            project.setProperty(S3_CREATE + "name", bucket.getName());

            if(bucket.getCreationDate()!=null)
            {
                project.setProperty(S3_CREATE + "creationDate", bucket.getCreationDate().toString());
            }

            if(bucket.getOwner()!=null)
            {
                project.setProperty(S3_CREATE + "owner.name", bucket.getOwner().getDisplayName());
                project.setProperty(S3_CREATE + "owner.id", bucket.getOwner().getId());
            }
        }
    }



}





