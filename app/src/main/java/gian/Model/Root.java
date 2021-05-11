package gian.Model;

import java.util.List;

public class Root {
    public List<Result> results;
    public String baseUri;
    public int offset;
    public int number;
    public int totalResults;
    public int processingTimeMs;
    public long expires;


    public List<Result> getResults() {
        return results;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public int getOffset() {
        return offset;
    }

    public int getNumber() {
        return number;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getProcessingTimeMs() {
        return processingTimeMs;
    }

    public long getExpires() {
        return expires;
    }
}
