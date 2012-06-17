package com.github.awsanttasks.ant.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.github.awsanttasks.ant.BaseAwsTask;
import com.github.awsanttasks.ant.util.StringUtils;
import org.apache.tools.ant.BuildException;

/**
 *
 */
public abstract class BaseS3Task extends BaseAwsTask
{
    protected String bucket;                        // the bucket name to work on (required)
    protected String acl;                           // the CannedAccessControlList value (optional)
    protected CannedAccessControlList aclEnum;      // holds the parsed value of acl string

    public final void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public final void setAcl(String acl)
    {
        this.acl = acl;
    }

    /**
     * The acl field is not required.  If the value is empty it will return null.
     * If the field is set it will try to parse it as a CannedAccessControllList enum.
     * Will throw a BuildException if it can't be parsed
     *
     * @param acl string value to parse
     * @return the an enum or null
     * @throws BuildException if the parsing fails
     */
    protected CannedAccessControlList parseAcl(String acl) throws BuildException
    {
        if(StringUtils.isNotBlank(acl))
        {
            try
            {
                return CannedAccessControlList.valueOf(acl);
            }
            catch (Exception e)
            {
                String errorMsg = "Invalid acl parameter, value must be:\n" +
                        " - Private\n" +
                        " - PublicRead\n" +
                        " - PublicReadWrite\n" +
                        " - AuthenticatedRead\n" +
                        " - LogDeliveryWrite\n" +
                        " - BucketOwnerRead\n" +
                        " - BucketOwnerFullControl";

                throw new BuildException(errorMsg);
            }
        }

        return null;
    }


    protected AmazonS3Client createClient(String accessKey, String secretKey)
    {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AmazonS3Client(credentials);
    }


    @Override
    public final void execute() throws BuildException
    {
        log("AWS S3 task " + getClass().getSimpleName() + " started");

        if(StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey) || StringUtils.isBlank(bucket))
        {
            throw new BuildException("'accessKey', 'secretKey' and 'bucket' are required properties");
        }

        //1. Parsing the CannedAccessControlList
        aclEnum = parseAcl(acl);


        try
        {
            //2. Creating the Amazon client
            AmazonS3Client client = createClient(accessKey, secretKey);

            //3. Execute the S3 Task
            executeInternal(client);
            log("AWS S3 task " + getClass().getSimpleName() + " finished");
        }
        catch (Exception e)
        {
            // a catchall
            e.printStackTrace(System.err);
            throw new BuildException("Could not execute task '" + getClass().getSimpleName() + "', bucket = "  + bucket, e);
        }
    }


    protected abstract void executeInternal(AmazonS3Client client) throws Exception;

}



