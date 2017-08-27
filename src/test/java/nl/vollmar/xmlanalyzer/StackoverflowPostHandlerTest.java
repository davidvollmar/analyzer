package nl.vollmar.xmlanalyzer;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StackoverflowPostHandlerTest {

    @Test
    public void parses_simple_file() {
        AnalysisDetails actual = StackoverflowPostHandler.analyze(StackoverflowPostHandler.class.getResourceAsStream("/simple-posts.xml")).get();
        assertEquals(0.5, actual.avgScore, 1e-6);
        assertEquals(4, actual.totalPosts);
        assertEquals(1, actual.totalAcceptedPosts);
        assertEquals(LocalDateTime.parse("2015-07-14T18:39:27.757"), actual.firstPost);
        assertEquals(LocalDateTime.parse("2015-07-14T20:05:50.737"), actual.lastPost);
    }

    @Test
    public void parses_file_with_arabic_text() {
        AnalysisDetails actual = StackoverflowPostHandler.analyze(StackoverflowPostHandler.class.getResourceAsStream("/arabic-posts.xml")).get();
        assertEquals(2.975, actual.avgScore, 1e-6);
        assertEquals(80, actual.totalPosts);
        assertEquals(7, actual.totalAcceptedPosts);
        assertEquals(LocalDateTime.parse("2015-07-14T18:39:27.757"), actual.firstPost);
        assertEquals(LocalDateTime.parse("2015-09-14T12:46:52.053"), actual.lastPost);
    }

    @Test
    public void parses_bigger_file() {
        AnalysisDetails actual = StackoverflowPostHandler.analyze(StackoverflowPostHandler.class.getResourceAsStream("/3dprinting-posts.xml")).get();
        assertEquals(3.2732824, actual.avgScore, 1e-6);
        assertEquals(655, actual.totalPosts);
        assertEquals(102, actual.totalAcceptedPosts);
        assertEquals(LocalDateTime.parse("2016-01-12T18:45:19.963"), actual.firstPost);
        assertEquals(LocalDateTime.parse("2016-03-04T13:30:22.410"), actual.lastPost);
    }


    public void returns_empty_on_malformed_input() throws Exception {
        assertFalse(StackoverflowPostHandler.analyze(StackoverflowPostHandler.class.getResourceAsStream("/malformed.xml")).isPresent());
    }
}