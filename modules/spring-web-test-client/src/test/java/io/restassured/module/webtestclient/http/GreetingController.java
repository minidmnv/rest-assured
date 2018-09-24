/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.restassured.module.webtestclient.http;

import java.util.concurrent.atomic.AtomicLong;

import io.restassured.response.Response;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * This controller has been copied from <a href="http://spring.io/guides/gs/rest-service/">Spring Guides</a>.
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = GET)
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(method = GET, produces = "application/json")
    public Mono<ResponseEntity> simpleGreeting() {
        ResponseEntity responseEntity = ResponseEntity.ok(new Greeting(counter.incrementAndGet(),
                String.format(template, "World")));
        return Mono.justOrEmpty(responseEntity);
    }

    @RequestMapping(value = "/greeting", method = POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Greeting greetingWithRequiredContentType(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return greeting(name);
    }
}