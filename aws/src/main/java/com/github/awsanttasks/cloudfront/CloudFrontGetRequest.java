package com.github.awsanttasks.cloudfront;


/**
 *
 */
public class CloudFrontGetRequest extends com.amazonaws.AmazonWebServiceRequest
{
    private String distribution;

    public CloudFrontGetRequest(String distribution)
    {
        this.distribution = distribution;
    }

    public String getDistribution()
    {
        return distribution;
    }
}




