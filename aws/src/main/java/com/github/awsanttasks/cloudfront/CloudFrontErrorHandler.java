package com.github.awsanttasks.cloudfront;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.HttpResponse;
import com.github.awsanttasks.ant.util.IOUtils;

import java.io.InputStream;

/**
 *
 */
public class CloudFrontErrorHandler implements com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonServiceException>
{
    public AmazonServiceException handle(HttpResponse httpResponse) throws Exception
    {

        InputStream is = httpResponse.getContent();
        String error = IOUtils.toString(is);
        System.err.println("Error Response:");
        System.err.println(error);
        //System.out.println("Error Response " + u);
        //TODO, return AmazonServiceException
        return null;
    }


    public boolean needsConnectionLeftOpen()
    {
        //System.out.print("Here 1");
        return false;
    }

}

