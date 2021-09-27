package com.example.demoguarani.controller.mapper;

import com.example.demoguarani.controller.dataRest.MessageRest;
import com.example.demoguarani.controller.dataRest.ResultCreateMessageRest;
import com.example.demoguarani.service.dataBusiness.MessageBusiness;
import com.example.demoguarani.service.dataBusiness.MessageBusinessWithoutId;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Component
public class MapperMessageDataRest {

    public MessageRest mapToMessageDataRest(final MessageBusiness messageBusiness) {
        return new MessageRest(messageBusiness.getMessage(), messageBusiness.getTitle());
    }

    public MessageBusiness mapToMessageBusiness(final MessageRest messageRest, final Long id) {
        return new MessageBusiness(id, messageRest.getMessage(), messageRest.getTitle(),null);
    }

    public MessageBusinessWithoutId mapToMessageBusinessWithoutId(final MessageRest messageRest) {
        return new MessageBusinessWithoutId(messageRest.getMessage(), messageRest.getTitle());
    }

    public ResultCreateMessageRest mapToResultCreateMessageRest(final MessageBusiness messageBusiness) {
        return new ResultCreateMessageRest(messageBusiness.getId(), mapToMessageDataRest(messageBusiness));
    }

}
