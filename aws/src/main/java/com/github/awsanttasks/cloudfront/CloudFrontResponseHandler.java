package com.github.awsanttasks.cloudfront;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Distribution xmlns="http://cloudfront.amazonaws.com/doc/2010-11-01/">
 *     <Id>EDFDVBD632BHDS5</Id>
 *     <Status>InProgress</Status>
 *     <LastModifiedTime>2009-11-19T19:37:58Z</LastModifiedTime>
 *     <DomainName>d604721fxaaqy9.cloudfront.net</DomainName>
 *     <DistributionConfig>
 *       <S3Origin>
 *          <DNSName>mybucket.s3.amazonaws.com</DNSName>
 *       </S3Origin>
 *       <CallerReference>20091130090000</CallerReference>
 *       <CNAME>beagles.com</CNAME>
 *       <Comment>My comments</Comment>
 *       <Enabled>true</Enabled>
 *       <Logging>
 *          <Bucket>mylogs.s3.amazonaws.com</Bucket>
 *          <Prefix>myprefix/</Prefix>
 *       </Logging>
 *     </DistributionConfig>
 * </Distribution>
 *
 */
public class CloudFrontResponseHandler implements com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonWebServiceResponse<CloudFrontResponse>>
{

    @Override
    public AmazonWebServiceResponse<CloudFrontResponse> handle(HttpResponse httpResponse) throws Exception
    {

        CloudFrontResponse cfResponse = parse(httpResponse.getContent());
        AmazonWebServiceResponse<CloudFrontResponse> response = new AmazonWebServiceResponse<CloudFrontResponse>();
        response.setResult(cfResponse);
        return response;
    }

    private CloudFrontResponse parse(InputStream is) throws ParserConfigurationException, IOException, SAXException
    {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        CloudFrontResponse response = new CloudFrontResponse();
        response.setId(getString(document, "Id"));
        response.setStatus(getString(document, "Status"));
        response.setDomainName(getString(document, "DomainName"));
        response.setLastModifiedTime(getString(document, "LastModifiedTime"));
        response.setCallerReference(getString(document, "CallerReference"));

        return response;
    }

    private String getString(Document doc, String node)
    {
        try
        {
            NodeList nodeList = doc.getElementsByTagName(node);
            return nodeList.item(0).getTextContent();
        }
        catch (Exception e)
        {
            return null;
        }
    }


    public boolean needsConnectionLeftOpen()
    {
        //System.out.print("Needs connection left open invoked " + new Date());
        return false;
    }


}


