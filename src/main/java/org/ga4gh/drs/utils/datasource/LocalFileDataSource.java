package org.ga4gh.drs.utils.datasource;

import org.ga4gh.drs.utils.objectloader.FileDrsObjectLoader;

public class LocalFileDataSource implements DataSource<FileDrsObjectLoader> {

    private String idPrefix;
    private String rootDir;

    public LocalFileDataSource() {

    }

    public LocalFileDataSource(String idPrefix, String rootDir) {
        this.idPrefix = idPrefix;
        this.rootDir = rootDir;
    }

    @Override
    public boolean objectIdMatches(String objectId) {
        return objectId.startsWith(getIdPrefix());
    }

    public String renderObjectPath(String objectId) {
        String postPrefix = objectId.replaceFirst(getIdPrefix(), "");
        String trailingFilePath = postPrefix.replaceAll("-", "/");
        return getRootDir() + trailingFilePath;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getRootDir() {
        return rootDir;
    }
}
