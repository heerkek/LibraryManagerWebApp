package com.hasanemreerkek.config;

/**
 * Created by Emre on 13.10.2015.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig {

    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
        UserCredentials credentials = new UserCredentials("books", "123456");
        String textUri="mongodb://books:123456@ds055842.mongolab.com:55842/heroku_p03xvq96";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient client = new MongoClient(uri);


        return new SimpleMongoDbFactory(client,"books");
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }

}
