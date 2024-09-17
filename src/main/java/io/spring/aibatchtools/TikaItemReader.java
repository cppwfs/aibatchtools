package io.spring.aibatchtools;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.Resource;

public class TikaItemReader implements ItemReader<List<Document>> {

    private Resource resource;

    private boolean readComplete = false;

    @Override
    public List<Document> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        TikaDocumentReader tikaDocumentReader =  new TikaDocumentReader(resource);
        System.out.println("Tika Created");
        List<Document> documents = tikaDocumentReader.read();
        System.out.println("Tika Read Complete");
        if (readComplete) {
            return null;
        }
        readComplete = true;
//        System.out.println(documents);
        return documents;
    }


    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
