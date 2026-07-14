package org.example;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan("org.example")
@EnableAspectJAutoProxy
class AppConfig3 {

}

@Service
class StudentService {

    public void addStudent() {
        System.out.println("Student Added");
    }

    public void updateStudent() {
        System.out.println("Student Updated");
    }
}

@Aspect
@Component
class LoggingAspect {

    @Before("execution(* org.example.StudentService.*(..))")
    public void startMethod() {
        System.out.println("Method Started");
    }

    @After("execution(* org.example.StudentService.*(..))")
    public void endMethod() {
        System.out.println("Method Ended");
    }
}

public class Day22_AOP_Concepts {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig3.class);

        StudentService service = context.getBean(StudentService.class);

        service.addStudent();

        service.updateStudent();

        context.close();
    }
}