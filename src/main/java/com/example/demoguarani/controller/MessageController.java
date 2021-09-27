package com.example.demoguarani.controller;

import com.example.demoguarani.controller.dataRest.MessageRest;
import com.example.demoguarani.controller.dataRest.ResultCreateMessageRest;
import com.example.demoguarani.controller.mapper.MapperMessageDataRest;
import com.example.demoguarani.service.MessageService;
import com.example.demoguarani.service.dataBusiness.MessageBusiness;
import com.example.demoguarani.service.dataBusiness.MessageBusinessWithoutId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 21/09/2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService service;
    private final MapperMessageDataRest mapper;


    @GetMapping("/findMessage/{id}")
    public ResponseEntity<MessageRest> findMessage(@PathVariable("id") final Long id) {
        return service.findMessage(id)
                .map(mapper::mapToMessageDataRest)
                .map(messageRest -> new ResponseEntity<>(messageRest, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/createMessage")
    public ResponseEntity<ResultCreateMessageRest> createMessage(@RequestBody final MessageRest messageRestToCreate) {
        ResponseEntity<ResultCreateMessageRest> resultRest;
        try {
            final MessageBusinessWithoutId messageBusinessWithoutIdToCreate = mapper.mapToMessageBusinessWithoutId(messageRestToCreate);
            final MessageBusiness messageBusinessResult = service.createMessage(messageBusinessWithoutIdToCreate);
            final ResultCreateMessageRest messageRestResult = mapper.mapToResultCreateMessageRest(messageBusinessResult);

            resultRest = new ResponseEntity<>(messageRestResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resultRest = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resultRest;
    }

    @PutMapping("/updateMessage/{id}")
    public ResponseEntity<MessageRest> updateMessage(@PathVariable("id") final Long id,
                                                     @RequestBody final MessageRest messageRestToModify) {
        ResponseEntity<MessageRest> resultRest;
        try {
            final MessageBusiness messageBusinessToUpdate = mapper.mapToMessageBusiness(messageRestToModify, id);
            resultRest = service.updateMessage(messageBusinessToUpdate)
                    .map(mapper::mapToMessageDataRest)
                    .map(messageRest -> new ResponseEntity<>(messageRest, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            e.printStackTrace();
            resultRest = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resultRest;
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<MessageRest> deleteMessage(@PathVariable("id") final Long id) {
        ResponseEntity<MessageRest> resultRest;
        try {
            resultRest = service.deleteMessage(id)
                    .map(mapper::mapToMessageDataRest)
                    .map(messageRest -> new ResponseEntity<>(messageRest, HttpStatus.FOUND))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            e.printStackTrace();
            resultRest = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resultRest;
    }
}
