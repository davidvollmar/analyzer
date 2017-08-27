package nl.vollmar.xmlanalyzer;


import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

public class StackoverflowPostHandler {

    private long posts;
    private long acceptedAnswers;
    private long cumulativeScore;
    private LocalDateTime firstPost;
    private LocalDateTime lastPost;

    private StackoverflowPostHandler() {
        posts = 0;
        acceptedAnswers = 0;
        cumulativeScore = 0;
        firstPost = null;
        lastPost = null;
    }

    public static Optional<AnalysisDetails> analyze(InputStream stream) {
        StackoverflowPostHandler handler = new StackoverflowPostHandler();
        try {
            XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(stream);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if ("row".equals(qName)) {
                        handler.parsePostRow(startElement);
                    }
                }
            }
            double average = (double) handler.cumulativeScore / handler.posts;
            return Optional.of(new AnalysisDetails(handler.firstPost, handler.lastPost, handler.posts, handler.acceptedAnswers, average));
        } catch (XMLStreamException ex) {
            return Optional.empty();
        }
    }

    private void parsePostRow(StartElement startElement) {
        posts++;

        LocalDateTime creationDate = LocalDateTime.parse(startElement.getAttributeByName(new QName("CreationDate")).getValue());
        if (firstPost == null || creationDate.isBefore(firstPost)) {
            firstPost = creationDate;
        }
        if (lastPost == null || creationDate.isAfter(lastPost)) {
            lastPost = creationDate;
        }

        cumulativeScore += Long.parseLong(startElement.getAttributeByName(new QName("Score")).getValue());
        if (startElement.getAttributeByName(new QName("AcceptedAnswerId")) != null) {
            acceptedAnswers++;
        }
    }
}
