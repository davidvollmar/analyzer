package nl.vollmar.xmlanalyzer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.ZonedDateTime;

@RestController
public class Controller {

    public static class AnalysisRequest {
        public String url;

        public AnalysisRequest() {
        }

        public AnalysisRequest(String url) {
            this.url = url;
        }
    }

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> process(@RequestBody AnalysisRequest request) throws XMLStreamException, IOException {
        URL website = new URL(request.url);
        try (ReadableByteChannel byteChannel = Channels.newChannel(website.openStream());) {
            AnalysisResponse analysisResponse = StackoverflowPostHandler.analyze(Channels.newInputStream(byteChannel))
                    .map(details -> new AnalysisResponse(ZonedDateTime.now(), details))
                    .orElseThrow(ProcessingException::new);
            return ResponseEntity.ok(analysisResponse);
        }
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity.status(500).body("Error retrieving file to analyze: " + ex.getMessage());
    }

    @ExceptionHandler(XMLStreamException.class)
    public ResponseEntity<String> handleXMLStreamException(XMLStreamException ex) {
        return ResponseEntity.status(500).body("Error analyzing file: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(Exception ex) {
        return ResponseEntity.status(500).body("Error processing request: " + ex.getMessage());
    }
}
