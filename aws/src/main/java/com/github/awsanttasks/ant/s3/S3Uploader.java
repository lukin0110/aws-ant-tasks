package com.github.awsanttasks.ant.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.awsanttasks.ant.util.StringUtils;
import org.apache.tools.ant.ProjectComponent;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * the expires field has to be in the proper format:
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
 *
 * DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
 * df.setTimeZone(TimeZone.getTimeZone("GMT"));
 * Calendar calendar = Calendar.getInstance();
 * calendar.set(2016, 5, 12, 23, 59, 59);  //12 june 2016 at 23u59s59
 *
 * http://code.google.com/speed/page-speed/docs/caching.html#LeverageProxyCaching
 * Vary: Accept-Encoding
 */
public class S3Uploader
{
    private AmazonS3Client client;
    private String bucketName;
    private String targetDir;
    private ProjectComponent pc;
    private String expires;
    private String encoding;
    private String vary;        //Vary: Accept-Encoding
    private CannedAccessControlList acl;


    public S3Uploader(AmazonS3Client client, String bucketName, ProjectComponent pc, CannedAccessControlList acl)
    {
        this.client = client;
        this.bucketName = bucketName;
        this.pc = pc;
        this.acl = acl;
    }

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

    /**
     * Will store a file in the cloud :)
     *
     * @param key the key for the file, 'the path on S3'
     * @param file local file which will be stored on S3
     * */
    public void putFile(String key, File file)
    {
        String keyStr = calculateKey(key);
        pc.log(" - Put " + keyStr + ": " + file.getAbsolutePath());

        // If you put null values as metadata the AmazonS3Client will throw a NullPointerException
        ObjectMetadata omd = new ObjectMetadata();

        if(StringUtils.isNotBlank(expires))
        {
            omd.setHeader("Expires", expires);
        }

        if(StringUtils.isNotBlank(encoding))
        {
            omd.setHeader("Content-Encoding", encoding);
        }

        if(StringUtils.isNotBlank(vary))
        {
            omd.setHeader("Vary", vary);
        }


        PutObjectRequest req = new PutObjectRequest(bucketName, keyStr, file);
        req.setCannedAcl(acl);
        req.setMetadata(omd);

        client.putObject(req);
    }


    public String calculateKey(String key)
    {
        if(key==null)
        {
            throw new NullPointerException("Key cant be null");
        }

        StringBuilder sb = new StringBuilder();

        if(targetDir!=null)
        {
            String tmp = targetDir.replace("\\","/");
            sb.append(tmp);

            if(!tmp.endsWith("/"))
            {
                sb.append("/");
            }
        }


        String tmp = key.replace("\\","/");

        if(tmp.startsWith("/"))
        {
            sb.append(tmp.substring(1));
        }
        else
        {
            sb.append(tmp);
        }

        return sb.toString();
    }





    public static void main(String[] args)
    {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 5, 12, 23, 59, 59);  //12 june 2016 at 23u59s59

        System.out.println(df.format(calendar.getTime()));
    }




    /*
    Expires is a Http header that allows you to define when a resource (image, css, ...) will need to be reloaded. It is a String representation of a
    Date in the format EEE, dd MMM yyyy HH:mm:ss z. There is lots of Java code available on the internet to produce the Expires header, the most part
    is similar to the following code I used during my experiments:

        public static void setCacheExpireDate(HttpServletResponse response,
                int seconds) {
            if (response != null) {
                Calendar cal = new GregorianCalendar();
                cal.roll(Calendar.SECOND, seconds);
                response.setHeader("Cache-Control", "PUBLIC, max-age=" + seconds + ", must-revalidate");
                response.setHeader("Expires", htmlExpiresDateFormat().format(cal.getTime()));
            }
        }

            public static DateFormat htmlExpiresDateFormat() {
                DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                return httpDateFormat;
            }
    With the code, the browser started receiving headers like these:

            Last-Modified:  Sun, 19 Sep 2004 22:06:49 GMT
            Cache-Control:  PUBLIC, max-age=57600, must-revalidate
            Expires:         Thu, 09 Aug 2007 05:22:55 GMT
            Content-Type:   image/gif
            Content-Length: 377
            Date:             Wed, 08 Aug 2007 13:22:55 GMT
            Server:           Apache-Coyote/1.1
    */
}
