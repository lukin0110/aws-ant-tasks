package com.github.awsanttasks.ant.cloudfront;

import com.amazonaws.auth.BasicAWSCredentials;
import com.github.awsanttasks.ant.BaseAwsTask;
import com.github.awsanttasks.ant.util.PropertiesUtil;
import com.github.awsanttasks.cloudfront.CloudFrontClient;
import com.github.awsanttasks.cloudfront.CloudFrontGetRequest;
import com.github.awsanttasks.cloudfront.CloudFrontResponse;
import org.apache.tools.ant.BuildException;

import java.io.PrintWriter;
import java.io.StringWriter;


public class GetTask extends BaseAwsTask
{
    private String distributionId;

    public void setDistributionId(String distributionId)
    {
        this.distributionId = distributionId;
    }


    @Override
    public void execute() throws BuildException
    {
        log("Fetching distribution details: " + distributionId);

        try
        {
            CloudFrontClient client = new CloudFrontClient(new BasicAWSCredentials(accessKey, secretKey));
            CloudFrontGetRequest request = new CloudFrontGetRequest(distributionId);
            CloudFrontResponse response = client.getDistribution(request);

            if(properties)
            {
                log("Do persist");
                PropertiesUtil.write(getProject(), response);
            }

            log(" Id = " + response.getId());
            log(" CallerReference = " + response.getCallerReference());
            log(" Status = " + response.getStatus());
            log(" DomainName = " + response.getDomainName());
            log(" LastModified = " + response.getLastModifiedTime());
        }
        catch (Exception e)
        {
            log("Request failed: " + e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new BuildException("Error: " + sw.toString(), e);
        }
    }


}



