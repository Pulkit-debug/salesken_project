package com.students.studentsRecords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Repository
public class StudentRepository {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "students";

    public String createOrUpdateRecord(Student record) throws IOException {

        IndexResponse response = elasticsearchClient.index(
                i -> i.index(indexName)
                        .id(record.getId())
                        .document(record)
        );
        if(response.result().name().equals("Created")){
            return new StringBuilder("Student Record document has been created successfully.").toString();
        }else if(response.result().name().equals("Updated")){
            return new StringBuilder("Student Record document has been updated successfully.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public Student getRecordById(String recordId) throws IOException{
        Student record = null;
        GetResponse<Student> response = elasticsearchClient.get(
                g -> g.index(indexName)
                        .id(recordId),
                Student.class
        );

        if (response.found()) {
            record = response.source();
            System.out.println("Invoice name is: " + record.getName());
        } else {
            System.out.println ("Invoice not found");
        }
        return record;
    }

    public String deleteRecordById(String recordId) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(recordId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Student Record with id : " + deleteResponse.id()+ " has been deleted successfully !.").toString();
        }
        System.out.println("Student Record not found");
        return new StringBuilder("Student Record with id : " + deleteResponse.id()+" does not exist.").toString();
    }

    public List<Student> getAllRecords() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse<Student> searchResponse = elasticsearchClient.search(searchRequest, Student.class);
        List<Hit<Student>> hits = searchResponse.hits().hits();
        List<Student> records = new ArrayList<>();
        for(Hit<Student> object : hits){
            System.out.print(((Student) object.source()));
            records.add((Student) object.source());
        }
        return records;
    }
}