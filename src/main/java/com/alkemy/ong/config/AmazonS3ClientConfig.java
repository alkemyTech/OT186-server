package com.alkemy.ong.config;
/*
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3ClientConfig {
    //credentials from Environment Variable
    private final String REGION = System.getenv("REGION");
    private final String ENDPOINT_URL = System.getenv("ENDPOINT_URL");
    private final String BUCKET_NAME = System.getenv("BUCKET_NAME");
    private final String AWS_PUBLIC_KEY= System.getenv("AWS_PUBLIC_KEY");
    private final String AWS_SECRET_KEY=System.getenv("AWS_SECRET_KEY");


    @Autowired
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.AWS_PUBLIC_KEY,this.AWS_SECRET_KEY); //authentication
        return AmazonS3ClientBuilder.standard()
                .withRegion(this.REGION)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build(); //return new authenticated client
    }

    public String getENDPOINT_URL() {
        return ENDPOINT_URL;
    }

    public String getBUCKET_NAME() {
        return BUCKET_NAME;
    }
}
*/