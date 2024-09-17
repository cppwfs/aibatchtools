package io.spring.aibatchtools;
import java.util.Collections;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.core.io.Resource;

public class TikaItemReader extends AbstractItemStreamItemReader<List<Document>> {

    private Resource resource;

    TikaDocumentReader tikaDocumentReader;

    List<Document> documents;

    int docCount = 0;

    @Override
    public List<Document> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Tika Created");
        this.tikaDocumentReader =  new TikaDocumentReader(resource);

        if(this.documents == null) {
            this.documents = tikaDocumentReader.read();
            System.out.println(this.documents.size());
            TokenTextSplitter splitter = new TokenTextSplitter();
            this.documents = splitter.apply(documents);
            System.out.println(this.documents.size());
        }
        System.out.println("Tika Read Complete");
        if (docCount>=documents.size()) {
            return null;
        }
        List<Document> result = Collections.singletonList(documents.get(docCount++));
//        System.out.println(documents);
        return result;
    }


    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
