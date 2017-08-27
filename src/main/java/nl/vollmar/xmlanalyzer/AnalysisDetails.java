package nl.vollmar.xmlanalyzer;

import java.time.LocalDateTime;

public class AnalysisDetails {
    public final LocalDateTime firstPost;
    public final LocalDateTime lastPost;
    public final long totalPosts;
    public final long totalAcceptedPosts;
    public final double avgScore;

    public AnalysisDetails(LocalDateTime firstPost, LocalDateTime lastPost, long totalPosts, long totalAcceptedPosts, double avgScore) {
        this.firstPost = firstPost;
        this.lastPost = lastPost;
        this.totalPosts = totalPosts;
        this.totalAcceptedPosts = totalAcceptedPosts;
        this.avgScore = avgScore;
    }

    @Override
    public String toString() {
        return "AnalysisDetails{" +
                "firstPost=" + firstPost +
                ", lastPost=" + lastPost +
                ", totalPosts=" + totalPosts +
                ", totalAcceptedPosts=" + totalAcceptedPosts +
                ", avgScore=" + avgScore +
                '}';
    }
}
