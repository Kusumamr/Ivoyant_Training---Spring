package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class StudentController {

    @GetMapping("/student")
    public String showForm(Model model){
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student,Model model){
        System.out.println(student.getId());
        System.out.println(student.getName());
        System.out.println(student.getCourse());

        model.addAttribute("student", student);

        return "result";
    }
}
