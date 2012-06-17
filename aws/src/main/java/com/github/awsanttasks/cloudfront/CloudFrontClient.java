package com.github.awsanttasks.cloudfront;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.handlers.RequestHandler;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpMethodName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;


/**
 * Making CloudFront requests
 * http://docs.amazonwebservices.com/AmazonCloudFront/latest/DeveloperGuide/index.html?RESTRequests.html
 *
 * Signing those requests
 * http://docs.amazonwebservices.com/AmazonCloudFront/2010-11-01/DeveloperGuide/index.html?RESTAuthentication.html
 */
public class CloudFrontClient extends com.amazonaws.AmazonWebServiceClient
{
    private CloudFrontResponseHandler responseHandler = new CloudFrontResponseHandler();
    private CloudFrontErrorHandler errorHandler = new CloudFrontErrorHandler();
    private AWSCredentials awsCredentials;

    public CloudFrontClient(ClientConfiguration clientConfiguration)
    {
        super(clientConfiguration);
    }

    public CloudFrontClient(AWSCredentials awsCredentials)
    {
        super(new ClientConfiguration());
        this.awsCredentials = awsCredentials;
    }


    @SuppressWarnings("unchecked")
    public CloudFrontResponse createDistribution(CloudFrontCreateRequest request)
    {
        try
        {
            Request<Void> awsRequest = new DefaultRequest<Void>(request, "Amazon CouldFront");
            awsRequest.setEndpoint(new URI("https://cloudfront.amazonaws.com"));
            awsRequest.setResourcePath("/2010-11-01/distribution");
            awsRequest.setHttpMethod(HttpMethodName.POST);
            awsRequest.setContent(createInputStream(request));
            awsRequest.addHeader("Host", "cloudfront.amazonaws.com");
            awsRequest.addHeader("Content-Type", "application/xml");

            ExecutionContext executionContext = new ExecutionContext(new ArrayList<RequestHandler>());

            CloudFrontSigner signer = new CloudFrontSigner();
            signer.sign(awsRequest, awsCredentials);

            return client.execute(awsRequest, responseHandler, errorHandler, executionContext);
        }
        catch (Exception e)
        {
            throw new RuntimeException("", e);
        }
    }




    /**
     * POST /2010-11-01/distribution HTTP/1.1
     * Host: cloudfront.amazonaws.com
     * Authorization: AWS O39F6A430S6FJNPPGDR2:frJIUN8DYpKDtOLEXAMPLE=
     * Date: Thu, 19 Nov 2009 19:37:58 GMT
     * [Other required headers]
     *
     * <?xml version="1.0" encoding="UTF-8"?>
     * <DistributionConfig xmlns="http://cloudfront.amazonaws.com/doc/2010-11-01/">
     *    <S3Origin>
     *       <DNSName>mybucket.s3.amazonaws.com</DNSName>
     *    </S3Origin>
     *    <CallerReference>20091130090000</CallerReference>
      *   <Comment>My comments</Comment>
     *    <Enabled>true</Enabled>
     * </DistributionConfig>
     *
     * @param request cloudfrontrequest object
     * @return inputstream
     * @throws java.io.UnsupportedEncodingException if, euh, something went wrong with the string
     */
    private InputStream createInputStream(CloudFrontCreateRequest request) throws UnsupportedEncodingException
    {
        String callerreference = "" + System.currentTimeMillis();

        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<DistributionConfig xmlns=\"http://cloudfront.amazonaws.com/doc/2010-11-01/\">\n" +
                "   <S3Origin>\n" +
                "      <DNSName>" + request.getS3bucket() + ".s3.amazonaws.com</DNSName>\n" +
                "   </S3Origin>" +
                "   <CallerReference>" + callerreference + "</CallerReference>\n" +
                "   <Comment>Ant: " + new Date().toString() + "</Comment>\n" +
                "   <Enabled>true</Enabled>\n" +
                "</DistributionConfig>";

        return new ByteArrayInputStream(s.getBytes("UTF-8"));
    }





    //http://docs.amazonwebservices.com/AmazonCloudFront/latest/APIReference/index.html?GetDistribution.html
    public CloudFrontResponse getDistribution(CloudFrontGetRequest request)
    {
        try
        {
            Request<Void> awsRequest = new DefaultRequest<Void>(request, "Amazon CouldFront");
            awsRequest.setEndpoint(new URI("https://cloudfront.amazonaws.com"));
            awsRequest.setResourcePath("/2010-11-01/distribution/" + request.getDistribution());
            awsRequest.setHttpMethod(HttpMethodName.GET);
            awsRequest.addHeader("Host", "cloudfront.amazonaws.com");

            ExecutionContext executionContext = new ExecutionContext(new ArrayList<RequestHandler>());

            CloudFrontSigner signer = new CloudFrontSigner();
            signer.sign(awsRequest, awsCredentials);

            return client.execute(awsRequest, responseHandler, errorHandler, executionContext);
        }
        catch (Exception e)
        {
            throw new RuntimeException("", e);
        }
    }


}




