package me.zort.acs.config;

import me.zort.acs.subject.Subject;
import me.zort.acs.subject.SubjectImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SubjectConfig {

    @SuppressWarnings("all")
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Subject subject(String id, me.zort.acs.scope.Scope scope) {
        return new SubjectImpl(id, scope);
    }
}
