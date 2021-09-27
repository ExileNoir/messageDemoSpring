package com.example.demoguarani.controller.dataRest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 23/09/2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultCreateMessageRest {

    private Long id;
    private MessageRest messageRest;
}
