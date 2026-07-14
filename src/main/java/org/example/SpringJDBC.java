package org.example;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

class Employee{
    private int id;
    private String name;
    private double salary;

    public Employee(){

    }

    public Employee(int id,String name,double salary){
        this.id=id;
        this.name=name;
        this.salary=salary;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public void setSalary(double salary){
        this.salary=salary;
    }
    public double getSalary(){
        return salary;
    }

    @Override
    public String toString(){
        return "Employee id = " + id + ", name = " + name + ", salary = " + salary;
    }
}
@Component
class EmployeeRowMapper implements RowMapper<Employee>{
    @Override
    public Employee mapRow(ResultSet rs,int rowNum) throws SQLException {
        Employee emp=new Employee();

        emp.setId(rs.getInt("id"));
        emp.setName(rs.getString("name"));
        emp.setSalary(rs.getDouble("salary"));

        return emp;
    }
}

@Service
class EmployeeService{
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Employee> rowMapper;

    public EmployeeService(JdbcTemplate jdbcTemplate,RowMapper<Employee> rowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Transactional
    public void saveEmployee(Employee employee){
        String sql="INSERT INTO employee(id,name,salary) VALUES(?,?,?)";
        jdbcTemplate.update(sql,
                employee.getId(),
                employee.getName(),
                employee.getSalary());

        System.out.println("Employee Saved Successfully");
    }
    @Transactional
    public void updateEmployee(Employee employee) {

        String sql =
                "UPDATE employee SET name=?,salary=? WHERE id=?";

        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getSalary(),
                employee.getId());

        System.out.println("Employee Updated");
    }
    @Transactional
    public void deleteEmployee(int id) {

        String sql =
                "DELETE FROM employee WHERE id=?";

        jdbcTemplate.update(sql, id);

        System.out.println("Employee Deleted");
    }
    public Employee getEmployee(int id) {

        String sql =
                "SELECT * FROM employee WHERE id=?";

        return jdbcTemplate.queryForObject(sql,
                rowMapper,
                id);
    }
    public List<Employee> getAllEmployees() {

        String sql =
                "SELECT * FROM employee";

        return jdbcTemplate.query(sql, rowMapper);
    }
    @Transactional
    public void saveEmployeeWithError(Employee employee) {

        jdbcTemplate.update(
                "INSERT INTO employee VALUES(?,?,?)",
                employee.getId(),
                employee.getName(),
                employee.getSalary());

        System.out.println("Employee Inserted");
        throw new RuntimeException("Database Connection Lost");

    }
}
@Aspect
@Component
class ExceptionAspect {

    @AfterThrowing(
            pointcut = "execution(* org.example.EmployeeService.*(..))",
            throwing = "exception")
    public void databaseAlert(Exception exception) {

        System.out.println("ALERT");
        System.out.println("Database Error Detected");
        System.out.println(exception.getMessage());
        System.out.println("Admin Notified");
    }
}
@SpringBootApplication
public class SpringJDBC {
    public static void main(String[] args) {
        SpringApplication.run(SpringJDBC.class, args);
    }
    @Bean
    CommandLineRunner run(EmployeeService employeeService) {
        return args -> {

            Employee emp = new Employee(101, "Kusuma MR", 50000);

            employeeService.saveEmployee(emp);

            System.out.println(employeeService.getEmployee(101));

            employeeService.updateEmployee(
                    new Employee(101, "Kusuma M R", 60000));

            System.out.println(employeeService.getAllEmployees());

            employeeService.deleteEmployee(101);
        };
    }
}
