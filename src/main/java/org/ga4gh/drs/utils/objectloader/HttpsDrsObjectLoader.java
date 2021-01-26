package org.ga4gh.drs.utils.objectloader;


import org.ga4gh.drs.model.AccessMethod;
import org.ga4gh.drs.model.AccessType;
import org.ga4gh.drs.model.Checksum;
import org.ga4gh.drs.model.ContentsObject;
import org.ga4gh.drs.model.DrsObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HttpsDrsObjectLoader extends AbstractDrsObjectLoader {

    private static final String DRS_CREATED_TIME_HEADER = "DRS_CREATED_TIME";

    private URL url;

    public HttpsDrsObjectLoader(String objectId, String objectPath) throws MalformedURLException {
        super(objectId, objectPath);
        url = new URL(objectPath);
    }

    public boolean exists() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            return 200 <= responseCode && responseCode < 300;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isBundle() {
        return getObjectPath().endsWith("/");
    }

    public List<AccessMethod> generateAccessMethods() {
        // TODO currently access ID has no meaning within the app, should be
        // used to populate a lookup cache
        String accessID = UUID.randomUUID().toString();
        AccessMethod accessMethod = new AccessMethod(accessID, AccessType.HTTPS);
        return Collections.singletonList(accessMethod);
    }

    public List<ContentsObject> generateContents() {
        // TODO fill out stub method
        return null;
    }

    public DrsObject generateCustomDrsObjectProperties() {
        // TODO fill out stub method
        return null;
    }

    public List<Checksum> imputeChecksums() {
        // TODO fill out stub method
        return null;
    }

    public long imputeSize() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            return connection.getContentLengthLong();
        } catch (IOException e) {
            return 0;
        }
    }

    public String imputeName() {
        return url.getFile();
    }

    public String imputeMimeType() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            return connection.getContentType();
        } catch (IOException e) {
            return null;
        }
    }

    public LocalDateTime imputeCreatedTime() {
        // TODO: Discussion on DRS_CREATED_TIME header
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            String createdTime = connection.getHeaderField(HttpsDrsObjectLoader.DRS_CREATED_TIME_HEADER);
            if (createdTime == null) {
                return null;
            }
            return LocalDateTime.parse(createdTime);
        } catch (IOException | DateTimeParseException e) {
            return null;
        }
    }
}
