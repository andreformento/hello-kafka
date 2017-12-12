package com.formento.hellokafka.producer;

import com.formento.hellokafka.domain.WorkUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleKafkaMessageController {

    @Autowired
    private WorkUnitDispatcher workUnitDispatcher;

    @PostMapping("/generate-work")
    public boolean sendMessage(@RequestBody final WorkUnit workUnit) {
        return this.workUnitDispatcher.dispatch(workUnit);
    }

}
