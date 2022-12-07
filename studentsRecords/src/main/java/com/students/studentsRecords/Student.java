package com.students.studentsRecords;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "students")
@Data
public class Student {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "sem")
    private String sem;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Text, name = "subject")
    private String subject;

    @Field(type = FieldType.Double, name = "marks")
    private Double marks;
}
