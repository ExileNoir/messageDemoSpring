package com.example.demoguarani.repository.message;

import lombok.Value;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Value
public class MessageDto {

    Long id;
    String message;
    String title;
    LevelOfMessageDto level;
}
