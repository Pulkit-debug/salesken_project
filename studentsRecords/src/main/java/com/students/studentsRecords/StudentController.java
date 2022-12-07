package com.students.studentsRecords;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository repo;

    @GetMapping("/")
    public String viewHomePage(Model model){
        return "home";
    }

    @GetMapping("/listRecord")
    public String viewlistRecordPage(Model model) throws IOException {
        model.addAttribute("listRecordDocuments",repo.getAllRecords());
        return "listRecord";
    }

    @PostMapping("/saveRecord")
    public String saveRecord(@ModelAttribute("record") Student record) throws IOException {
        repo.createOrUpdateRecord(record);
        return "redirect:/listRecord";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {
        Student record = repo.getRecordById(id);
        model.addAttribute("record", record);
        return "updateRecord";
    }

    @GetMapping("/showNewRecordForm")
    public String showNewRecordForm(Model model) {
        // creating model attribute to bind form data
        Student record = new Student();
        model.addAttribute("record", record);
        return "addRecord";
    }

    @GetMapping("/deleteRecord/{id}")
    public String deleteRecord(@PathVariable(value = "id") String id) throws IOException {
        this.repo.deleteRecordById(id);
        return "redirect:/listRecord";
    }
}
