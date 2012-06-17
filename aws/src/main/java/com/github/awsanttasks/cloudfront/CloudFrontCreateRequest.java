package com.github.awsanttasks.cloudfront;


/**
 *
 */
public class CloudFrontCreateRequest extends com.amazonaws.AmazonWebServiceRequest
{
    private String s3bucket;

    public CloudFrontCreateRequest(String s3bucket)
    {
        this.s3bucket = s3bucket;
    }

    public String getS3bucket()
    {
        return s3bucket;
    }
}
