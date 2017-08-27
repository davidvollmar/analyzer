package nl.vollmar.xmlanalyzer;

import java.time.ZonedDateTime;

public class AnalysisResponse {
    public final ZonedDateTime analyseDate;
    public final AnalysisDetails details;

    public AnalysisResponse(ZonedDateTime analyseDate, AnalysisDetails details) {
        this.analyseDate = analyseDate;
        this.details = details;
    }
}
