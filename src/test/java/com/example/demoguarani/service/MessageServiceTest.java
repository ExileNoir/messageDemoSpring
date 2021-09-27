package com.example.demoguarani.service;

import com.example.demoguarani.repository.message.LevelOfMessageDto;
import com.example.demoguarani.repository.message.MessageDao;
import com.example.demoguarani.repository.message.MessageDto;
import com.example.demoguarani.service.dataBusiness.LevelOfMessageBusiness;
import com.example.demoguarani.service.dataBusiness.MessageBusiness;
import com.example.demoguarani.service.mapper.MapperMessageBusiness;
import com.example.demoguarani.utilities.MapperEnum;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 24/09/2021
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageServiceTest {

    private MessageDao messageDao = Mockito.mock(MessageDao.class);

    private MapperEnum mapperEnum = new MapperEnum();
    private MapperMessageBusiness mapper = new MapperMessageBusiness(mapperEnum);
    private MessageService messageService = new MessageService(messageDao, mapper);


    @BeforeEach
    public void init() {
        final MessageDto messageDto =
                new MessageDto(1L, "Hello World", "Title Hello", LevelOfMessageDto.USER);
        Mockito.when(messageDao.findMessage(1L))
                .thenReturn(Optional.of(messageDto));

        final MessageDto messageDto01 =
                new MessageDto(2L, "Hello World01", "Title Hello", LevelOfMessageDto.MANAGER);
        Mockito.when(messageDao.findMessage(2L))
                .thenReturn(Optional.of(messageDto01));

        final MessageDto messageDto02 =
                new MessageDto(3L, "Hello World02", "Title Hello", LevelOfMessageDto.DIRECTOR);
        Mockito.when(messageDao.findMessage(3L))
                .thenReturn(Optional.of(messageDto02));
    }

//    @Test
//    public void checkCallUpdateDao() {
//        final Long id = 1L;
//        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);
//
//        Mockito.verify(messageDao, Mockito.times(1))
//                .updateMessage(Mockito.any());
//    }

    // Level 1 User
    // Level 2 Manager
    // Level 3 Director
//    @Test
//    public void testUpLevelToManager() {
//        final Long id = 1L;
//        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);
//        final MessageBusiness messageBusiness = messageBusinessOpt
//                .orElseThrow(() -> new RuntimeException("No User available with id: '" + id + "'"));
//
//        Assertions.assertEquals(id, messageBusiness.getId(), "Different Id's");
//        Assertions.assertEquals(LevelOfMessageBusiness.MANAGER, messageBusiness.getLevel());
//    }

    // FAKE FALSE BECAUSE MOCK FIND MESSAGE DUE TO UPDATE
//    @Test
//    public void testUpLevelToDirector() {
//        final Long id = 2L;
//        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);
//        final MessageBusiness messageBusiness = messageBusinessOpt
//                .orElseThrow(() -> new RuntimeException("No User available with id: '" + id + "'"));
//
//        Assertions.assertEquals(id, messageBusiness.getId(), "Different Id's");
//        Assertions.assertEquals(LevelOfMessageBusiness.DIRECTOR, messageBusiness.getLevel());
//    }

    @Test
    public void testUpLevelAlreadyDirector() {
        final Long id = 3L;
        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);
        final MessageBusiness messageBusiness = messageBusinessOpt
                .orElseThrow(() -> new RuntimeException("No User available with id: '" + id + "'"));

        Assertions.assertEquals(id, messageBusiness.getId(), "Different Id's");
        Assertions.assertEquals(LevelOfMessageBusiness.DIRECTOR, messageBusiness.getLevel());
    }

    @Test
    public void testUpLevelNoDirector() {
        final Long id = 1L;
        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);
        final MessageBusiness messageBusiness = messageBusinessOpt
                .orElseThrow(() -> new RuntimeException("No User available with id: '" + id + "'"));

        Assertions.assertEquals(id, messageBusiness.getId(), "Different Id's");
        Assertions.assertNotEquals(LevelOfMessageBusiness.DIRECTOR, messageBusiness.getLevel());
    }

//    @Test
//    public void testUpLevelWithNoChange() {
//        final Long id = 1L;
//        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);
//        final MessageBusiness messageBusiness = messageBusinessOpt
//                .orElseThrow(() -> new RuntimeException("No User available with id: '" + id + "'"));
//
//        Assertions.assertEquals(id, messageBusiness.getId(), "Different Id's");
//        Assertions.assertNotEquals(LevelOfMessageBusiness.USER, messageBusiness.getLevel());
//    }



    @Test
    public void checkNoCallUpdateDao() {
        final Long id = 4L;
        Optional<MessageBusiness> messageBusinessOpt = messageService.upLevel(id);

        Mockito.verify(messageDao, Mockito.times(0))
                .updateMessage(Mockito.any());
    }
}