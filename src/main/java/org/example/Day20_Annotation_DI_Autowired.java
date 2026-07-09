package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:application.properties")
class AppConfig{

}

interface PaymentService{
    void payment();
}

@Component
class PhonePeService implements PaymentService{
    @Override
    public void payment(){
        System.out.println("Payment done via PhonePe");
    }
}

@Component
class GooglePeService implements PaymentService{
    @Override
    public void payment(){
        System.out.println("Payment done via GooglePe");
    }
}

@Component
class OrderService{
    private PaymentService paymentService;

    @Autowired
    OrderService(@Qualifier("phonePeService") PaymentService paymentService){
        this.paymentService=paymentService;

    }

    void placeOrder(){
        paymentService.payment();
        System.out.println("Order placed successfully");
    }
}

@Component
class StudentInfo{
    @Value("${student.name}")
    private String name;

    void display(){
        System.out.println("Student name is " + name);
    }
}

@Component
class Student{

    @Autowired
    @Lazy
    Teacher teacher;

    void show(){
        System.out.println("Student bean");
    }
}

@Component
class Teacher{

    @Autowired
    @Lazy

    Student student;
}
public class Day20_Annotation_DI_Autowired {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        //@qualifier example
        OrderService orderService=context.getBean(OrderService.class);
        orderService.placeOrder();

        //@value example
        StudentInfo studentInfo=context.getBean(StudentInfo.class);
        studentInfo.display();

        //circular dependency
        Student student=context.getBean(Student.class);
        student.show();
    }
}
