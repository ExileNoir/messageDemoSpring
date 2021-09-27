package com.example.demoguarani.service.mapper;

import com.example.demoguarani.repository.message.MessageDto;
import com.example.demoguarani.service.dataBusiness.MessageBusiness;
import com.example.demoguarani.utilities.MapperEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Component
@RequiredArgsConstructor
public class MapperMessageBusiness {

    private final MapperEnum mapper;

    public MessageBusiness mapToMessageBusiness(final MessageDto messageDto) {
        return new MessageBusiness(messageDto.getId(), messageDto.getMessage(), messageDto.getTitle(),
                mapper.map(messageDto.getLevel()));
    }

    public MessageDto mapToMessageDto(final MessageBusiness messageBusiness) {
        return new MessageDto(messageBusiness.getId(), messageBusiness.getMessage(), messageBusiness.getTitle(),
                mapper.map(messageBusiness.getLevel()));
    }
}
