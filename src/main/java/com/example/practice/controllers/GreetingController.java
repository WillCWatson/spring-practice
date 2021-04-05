package com.example.practice.controllers;

import java.util.concurrent.atomic.AtomicLong;
import com.example.practice.Greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that every method returns a domain object instead of a view.
// It is shorthand for including both @Controller and @ResponseBody
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting") // @GetMapping is for GET verbs. There's also @PostMapping and such for other HTTP Verbs
    // Also, they all derive from @RequestMapping and you can also use @RequestMapping(method=GET) for them if you want. But you don't want.
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
        // I'm pretty sure that String.format(template, name) up there is a lambda
        // Both parts of that are probably lambdas.
        // Also this doesn't need to be handled, because Jackson 2 will automatically convert Greeting to JSON.
        // Something like this is how you communicate to the front end

        // Though the above program doesn't contain any of this stuff, it is included in the tutorial text:
        // @SpringBootApplication is a convenience feature that adds the following:
        // @Configuration - Tags the class as a source of bean definitions for hte application context
        // @EnableAutoConfiguration - ells Spring Boot to start adding beans based on classpath settings, other beans,
        //      and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the
        //      application as a web application and activates key behaviors, such as setting up a DispatcherServlet.
        // @ComponentScan - Tells Spring to look for other components, configurations, and services in the com/example
        //      package, letting it find the controllers.
        //
        // The main() method uses Spring Boot's SpringApplicaiton.run() method to launch an application. There is no
        // web.xml, or any xml at all. The web application is pure Java and you don't have to deal with
        // any "plumbing or infrastructure"

        // When you want to build an executable jar, you can use ./gradlew bootRun
    }
}