package nl.vollmar.xmlanalyzer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ComponentTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, 9999);

    @Autowired
    public Controller controller;

    private MockServerClient mockServerClient;

    @Test
    public void requests_and_processes() throws Exception {
        String body = new String(Files.readAllBytes(Paths.get(getClass().getResource("/simple-posts.xml").toURI())));

        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(body)

                );
        ResponseEntity<AnalysisResponse> entity = controller.process(new Controller.AnalysisRequest("http://localhost:" + 9999));
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        AnalysisResponse analysisResponse = entity.getBody();
        assertEquals(0.5, analysisResponse.details.avgScore, 1e-6);
        assertEquals(4, analysisResponse.details.totalPosts);
        assertEquals(1, analysisResponse.details.totalAcceptedPosts);
        assertEquals(LocalDateTime.parse("2015-07-14T18:39:27.757"), analysisResponse.details.firstPost);
        assertEquals(LocalDateTime.parse("2015-07-14T20:05:50.737"), analysisResponse.details.lastPost);

    }
}
