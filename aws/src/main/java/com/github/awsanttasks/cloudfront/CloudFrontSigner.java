package com.github.awsanttasks.cloudfront;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AbstractAWSSigner;
import com.amazonaws.auth.SigningAlgorithm;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.ServiceUtils;

import java.util.Date;

/**
 * For more information about signing CloudFront request:
 * http://docs.amazonwebservices.com/AmazonCloudFront/2010-11-01/DeveloperGuide/index.html?RESTAuthentication.html
 *
 * Check some source code
 * http://grepcode.com/file/repo1.maven.org$maven2@com.amazonaws$aws-java-sdk@1.1.9@com$amazonaws$services$s3$internal$S3Signer.java
 *
 * For CloudFront, the canonical string to sign is simply the value of the Date header (or the x-amz-date header if you include it in the request)
 * Authorization: "AWS" + " " + AWSAccessKeyID + ":" + Base64(HMAC-SHA1(UTF-8(Date), UTF-8(AWSSecretAccessKey)))
 */
public class CloudFrontSigner extends AbstractAWSSigner
{

    public void sign(Request<?> request, AWSCredentials awsCredentials) throws AmazonClientException
    {
        request.addHeader(Headers.DATE, ServiceUtils.formatRfc822Date(new Date()));
        String canonicalString = ServiceUtils.formatRfc822Date(new Date());

        //log.debug("Calculated string to sign:\n\"" + canonicalString + "\"");
        AWSCredentials sanitizedCredentials = sanitizeCredentials(awsCredentials);

        String signature = super.sign(canonicalString, sanitizedCredentials.getAWSSecretKey(), SigningAlgorithm.HmacSHA1);
        request.addHeader("Authorization", "AWS " + sanitizedCredentials.getAWSAccessKeyId() + ":" + signature);
    }

}

