package com.github.awsanttasks.ant.cloudfront;

import com.amazonaws.auth.BasicAWSCredentials;
import com.github.awsanttasks.ant.BaseAwsTask;
import com.github.awsanttasks.ant.util.PropertiesUtil;
import com.github.awsanttasks.cloudfront.CloudFrontClient;
import com.github.awsanttasks.cloudfront.CloudFrontCreateRequest;
import com.github.awsanttasks.cloudfront.CloudFrontResponse;
import org.apache.tools.ant.BuildException;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CreateTask extends BaseAwsTask
{
    private String s3bucket;

    public void setS3bucket(String s3bucket)
    {
        this.s3bucket = s3bucket;
    }

    @Override
    public void execute() throws BuildException
    {
        log("Creating distribution for bucket: " + s3bucket);

        try
        {
            CloudFrontClient client = new CloudFrontClient(new BasicAWSCredentials(accessKey, secretKey));
            CloudFrontCreateRequest request = new CloudFrontCreateRequest(s3bucket);
            CloudFrontResponse response = client.createDistribution(request);
            log("Response: \n" + response.toString());

            if(properties)
            {
                PropertiesUtil.write(getProject(), response);
            }

            log("Distribution created: " + s3bucket);
        }
        catch (Exception e)
        {
            log("Distribution failed: " + e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new BuildException("Error: " + sw.toString(), e);
        }
    }

}





