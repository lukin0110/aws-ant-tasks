package com.github.awsanttasks.ant.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;

import java.io.File;

/**
 *
 */
public class PutTask extends BaseS3Task
{
    private String targetDir;           // target dir in the bucket:  bucket.s3.amazonaws.com/<targetDir> (optional)
    private String expires;             // expires header to set on the the objects (optional)
    private String encoding;            // Content-Encoding header
    private String vary;                // Vary: Accept-Encoding

    public void setTargetDir(String targetDir)
    {
        this.targetDir = targetDir;
    }

    public void setExpires(String expires)
    {
        this.expires = expires;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public void setVary(String vary)
    {
        this.vary = vary;
    }

    @Override
    protected void executeInternal(AmazonS3Client client) throws Exception
    {
        log("Properties: ");
        log(" - Access Key: " + accessKey);
        log(" - Bucket: " + bucket);
        log(" - Target directory: " + targetDir);
        log("\n Files:");

        S3Uploader uploader = new S3Uploader(client, bucket, this, aclEnum);
        uploader.setTargetDir(targetDir);
        uploader.setExpires(expires);
        uploader.setEncoding(encoding);
        uploader.setVary(vary);

        for(FileSet fs:filesets)
        {
            DirectoryScanner ds = fs.getDirectoryScanner();
            String[] arr = ds.getIncludedFiles();

            for(String f:arr)
            {
                File file = new File(fs.getDir(), f);
                uploader.putFile(f, file);
            }
        }
    }
}
