package org.ga4gh.drs.utils.objectloader;

import org.ga4gh.drs.model.AccessMethod;
import org.ga4gh.drs.model.Checksum;
import org.ga4gh.drs.model.ContentsObject;
import org.ga4gh.drs.model.DrsObject;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class S3DrsObjectLoader extends AbstractDrsObjectLoader {

    @Autowired
    private S3Client s3;

    private String bucket;
    private String key;

    private HeadObjectResponse headObjectResponse;

    public S3DrsObjectLoader(String objectId, String objectPath) {
        super(objectId, objectPath);
        URI uri = URI.create(objectPath);
        bucket = uri.getHost();
        key = uri.getPath();
    }

    @Override
    public boolean exists() {
        try {
            HeadBucketRequest request = HeadBucketRequest.builder()
                .bucket(bucket)
                .build();
            s3.headBucket(request);
        } catch (NoSuchBucketException e) {
            return false;
        }

        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
            // Hold on to this to avoid repeat requests
            headObjectResponse = s3.headObject(request);
        } catch (NoSuchKeyException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isBundle() {
        return key.endsWith("/");
    }

    @Override
    public List<AccessMethod> generateAccessMethods() {
        return null;
    }

    @Override
    public List<ContentsObject> generateContents() {
        // This should only be called if this is a bundle
        ListObjectsV2Request request = ListObjectsV2Request.builder()
            .bucket(bucket)
            .prefix(key)
            .build();
        ListObjectsV2Response response = s3.listObjectsV2(request);
        response.contents()
    }

    @Override
    public DrsObject generateCustomDrsObjectProperties() {
        return null;
    }

    @Override
    public List<Checksum> imputeChecksums() {
        return null;
    }

    @Override
    public long imputeSize() {
        return headObjectResponse.contentLength();
    }

    @Override
    public String imputeName() {
        return key;
    }

    @Override
    public String imputeMimeType() {
        return headObjectResponse.contentType();
    }

    @Override
    public LocalDateTime imputeCreatedTime() {
        return null;
    }
}
