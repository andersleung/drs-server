package org.ga4gh.drs.utils.objectloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;

import org.ga4gh.drs.model.AccessMethod;
import org.ga4gh.drs.model.Checksum;
import org.ga4gh.drs.model.ContentsObject;
import org.ga4gh.drs.model.DrsObject;

import javax.net.ssl.HttpsURLConnection;

public class HttpsDrsObjectLoader extends AbstractDrsObjectLoader {

    private URL url;

    public HttpsDrsObjectLoader(String objectId, String objectPath) throws MalformedURLException {
        super(objectId, objectPath);
        url = new URL(objectPath);
    }

    public boolean exists() {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            int responseCode = conn.getResponseCode();
            // TODO: possibly handling HTTPS -> HTTP redirecting?
            return 200 <= responseCode && responseCode < 300;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isBundle() {
        // TODO fill out stub method
        return false;
    }

    public List<AccessMethod> generateAccessMethods() {
        // TODO fill out stub method
        return null;
    }

    public List<ContentsObject> generateContents() {
        // TODO fill out stub method
        return null;
    }

    public String generateId() {
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

    public int imputeSize() {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            int responseCode = conn.getResponseCode();
            // TODO: possibly handling HTTPS -> HTTP redirecting?
            return conn.getContentLength();
        } catch (IOException e) {
            return false;
        }
    }

    public String imputeName() {
        // TODO fill out stub method
        return null;
    }

    public String imputeMimeType() {
        // TODO fill out stub method
        return null;
    }

    public LocalDateTime imputeCreatedTime() {
        // TODO fill out stub method
        return null;
    }
}
