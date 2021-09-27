package com.example.demoguarani.service.dataBusiness;

import lombok.Value;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Value
public class MessageBusiness {

    Long id;
    String message;
    String title;
    LevelOfMessageBusiness level;

}

