package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.service.AwsS3Service;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/bucket")
@CrossOrigin(originPatterns = "*")
public class AwsS3Controller {
    @Autowired
    AwsS3Service bucketService;

    @GetMapping
    public void getBucketList() {
        List<Bucket> bucketList = bucketService.getBucketList();
        System.out.println("bucketList:"+bucketList);
    }

    @GetMapping("/downloadObj")
    public void downloadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName) throws Exception {
        bucketService.getObjectFromBucket(bucketName, objName);
    }

    @PostMapping("/uploadObj")
    public void uploadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName) throws Exception {
        bucketService.putObjectIntoBucket(bucketName, objName,"opt/test/v1/uploadfile.txt");
    }

    @PostMapping("/createBucket")
    public String createBucket(@RequestParam("bucketName") String bucketName) {
        bucketService.createBucket(bucketName);
        return "done";
    }
}
