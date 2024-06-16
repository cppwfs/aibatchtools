package io.spring.aibatchtools;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;

public class VectorStoreWriter<T> implements ItemWriter<T>, InitializingBean {

    private VectorStore vectorStore;

    private String name;

    private String metadataFieldName = null;

    private String contentFieldName = null;

    @Override
    public void write(Chunk<? extends T> chunk) {
        List<Document> documents = new ArrayList<>();
        for(T item : chunk.getItems()) {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
            documents.add(new Document((String) beanWrapper.getPropertyValue(contentFieldName), (java.util.Map<String,Object>)beanWrapper.getPropertyValue(metadataFieldName)));
//            vectorStore.accept((List<Document>) chunk.getItems());
            System.out.println("******** " + beanWrapper.getPropertyValue(contentFieldName));
        }
        vectorStore.accept(documents);

    }

    public VectorStore getVectorStore() {
        return vectorStore;
    }

    public void setVectorStore(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetadataFieldName() {
        return metadataFieldName;
    }

    public void setMetadataFieldName(String metadataFieldName) {
        this.metadataFieldName = metadataFieldName;
    }

    public String getContentFieldName() {
        return contentFieldName;
    }

    public void setContentFieldName(String contentFieldName) {
        this.contentFieldName = contentFieldName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
