package com.students.studentsRecords;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentRestController {

    @Autowired
    private StudentRepository repo;

    @PostMapping("/createOrUpdateRecord")
    public ResponseEntity<Object> createOrUpdateRecord(@RequestBody Student record) throws IOException {
        String response = repo.createOrUpdateRecord(record);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRecord")
    public ResponseEntity<Object> getRecordById(@RequestParam String recordId) throws IOException {
        Student record = repo.getRecordById(recordId);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping("/getAllRecords")
    public ResponseEntity<Object> getAllRecords() throws IOException {
        List<Student> records = repo.getAllRecords();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRecord")
    public ResponseEntity<Object> deleteRecordById(@RequestParam String recordId) throws IOException {
        String response = repo.deleteRecordById(recordId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
