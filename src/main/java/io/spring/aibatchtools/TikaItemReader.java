package io.spring.aibatchtools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.core.io.Resource;

public class TikaItemReader extends AbstractItemStreamItemReader<Document> {

    private final Logger logger =  LoggerFactory.getLogger(this.getClass());

    private Resource resource;

    TikaDocumentReader tikaDocumentReader;

    List<Document> documents;

    int docCount = 0;

    @Override
    public Document read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Tika Created");
        this.tikaDocumentReader = new TikaDocumentReader(resource);

        if (this.documents == null) {
            this.documents = tikaDocumentReader.read();
            TokenTextSplitter splitter = new TokenTextSplitter();
            this.documents = splitter.apply(documents);
        }
        logger.info("Tika Read Complete");
        if (docCount >= documents.size()) {
            return null;
        }
        return documents.get(docCount++);
    }


    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
