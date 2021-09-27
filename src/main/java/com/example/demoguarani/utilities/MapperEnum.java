package com.example.demoguarani.utilities;

import com.example.demoguarani.repository.message.LevelOfMessageDto;
import com.example.demoguarani.service.dataBusiness.LevelOfMessageBusiness;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 24/09/2021
 */

@Component
public class MapperEnum {
    
    public LevelOfMessageBusiness map(final LevelOfMessageDto element){
        return LevelOfMessageBusiness.valueOf(element.name());
    }

    public LevelOfMessageDto map(final LevelOfMessageBusiness element){
        return LevelOfMessageDto.valueOf(element.name());
    }
}
