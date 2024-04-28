package com.example.resourcesactivities.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PresignedUrlDownloadRequest;
import com.amazonaws.services.s3.model.PresignedUrlUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
public interface AwsS3Service {
    // get list of buckets for given user
    List<Bucket> getBucketList();

    // check if given bucket name valid
    boolean validateBucket(String bucketName);

    // download given objectName from the bucket
    void getObjectFromBucket(String bucketName, String objectName) throws IOException;

    // upload given file as objectName to S3 bucket
    void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload);

    // create Bucket with provided name (throws exception if bucket already present)
    void createBucket(String bucket);
}
