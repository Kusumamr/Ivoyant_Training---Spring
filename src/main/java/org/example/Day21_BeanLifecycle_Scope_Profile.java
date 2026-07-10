package org.example;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Configuration
@ComponentScan("org.example")
class AppConfig2{

}

//Bean lifecycle
@Component
class beanLifecycle{

    public beanLifecycle(){
        System.out.println("Constructor called");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("Post constructor called");
    }

    @PreDestroy
    public void predestroy(){
        System.out.println("Pre destroy is called");
    }
}

//singleton scope
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class SingletonBean{
    public SingletonBean(){
        System.out.println("Singleton bean created");
    }
}

//prototype scope
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PrototypeBean{
    public PrototypeBean(){
        System.out.println("Prototype bean created");
    }
}

//datasource interface
interface Datasource{
    void connect();
}

//development profile
@Component
@Profile("dev")
class DevDatasource implements Datasource{

    @Override
    public void connect(){
        System.out.println("Development Database connected");
    }
}

//production profile
@Component
@Profile("prod")
class ProdDatasource implements Datasource{

    @Override
    public void connect(){
        System.out.println("Production Database Connected");
    }
}

public class Day21_BeanLifecycle_Scope_Profile {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();

        //dev profile
        context.getEnvironment().setActiveProfiles("dev");

        //prod profile
        context.getEnvironment().setActiveProfiles("prod");

        context.register(AppConfig2.class);
        context.refresh();

        context.getBean(beanLifecycle.class);

        SingletonBean s=context.getBean(SingletonBean.class);

        PrototypeBean b=context.getBean(PrototypeBean.class);

//        Datasource datasource=context.getBean(DevDatasource.class);
//        datasource.connect();

        Datasource datasource=context.getBean(ProdDatasource.class);
        datasource.connect();

        context.close();
    }
}
