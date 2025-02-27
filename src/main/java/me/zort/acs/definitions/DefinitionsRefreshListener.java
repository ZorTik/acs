package me.zort.acs.definitions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DefinitionsRefreshListener implements ApplicationListener<ApplicationReadyEvent> {
    private final DefinitionsService definitionsService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        definitionsService.refresh();
    }
}
