package com.github.awsanttasks.cloudfront;

/**
 *
 */
public class CloudFrontResponse
{
    private String id;
    private String callerReference;
    private String status;
    private String domainName;
    private String lastModifiedTime;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCallerReference()
    {
        return callerReference;
    }

    public void setCallerReference(String callerReference)
    {
        this.callerReference = callerReference;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDomainName()
    {
        return domainName;
    }

    public void setDomainName(String domainName)
    {
        this.domainName = domainName;
    }

    public String getLastModifiedTime()
    {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime)
    {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString()
    {
        return "CloudFrontResponse{" +
                "id='" + id + '\'' +
                ", callerReference='" + callerReference + '\'' +
                ", status='" + status + '\'' +
                ", domainName='" + domainName + '\'' +
                ", lastModifiedTime='" + lastModifiedTime + '\'' +
                '}';
    }
}

