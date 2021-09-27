package com.example.demoguarani.service;

import com.example.demoguarani.repository.message.MessageDao;
import com.example.demoguarani.repository.message.MessageDto;
import com.example.demoguarani.service.dataBusiness.LevelOfMessageBusiness;
import com.example.demoguarani.service.dataBusiness.MessageBusiness;
import com.example.demoguarani.service.dataBusiness.MessageBusinessWithoutId;
import com.example.demoguarani.service.mapper.MapperMessageBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.out;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao messageDao;
    private final MapperMessageBusiness mapper;

    public Optional<MessageBusiness> findMessage(final Long id) {
        return messageDao
                .findMessage(id)
                .map(mapper::mapToMessageBusiness);
    }

    public MessageBusiness createMessage(final MessageBusinessWithoutId messageBusiness) {
        final MessageDto message = messageDao.createMessage(messageBusiness.getMessage(), messageBusiness.getTitle());
        return mapper.mapToMessageBusiness(message);
    }

    public Optional<MessageBusiness> updateMessage(final MessageBusiness messageBusinessToUpdate) {
        final MessageDto messageDto = mapper.mapToMessageDto(messageBusinessToUpdate);
        final boolean updated = messageDao.updateMessage(messageDto);

        if (!updated) {
            out.println("Message not found, Not updated, Data to update: " + messageDto);
        }

        return findMessage(messageBusinessToUpdate.getId());
    }

    public Optional<MessageBusiness> deleteMessage(final Long id) {
        Optional<MessageBusiness> messageToDelete = findMessage(id);
        final AtomicBoolean deleted = new AtomicBoolean(false);

        messageToDelete.ifPresent(msg -> {
            out.println("Message Found to delete by id: " + id);
            deleted.set(messageDao.deleteMessage(id));
        });

        if (!deleted.get()) {
            out.println("Message not deleted by id: " + id);
        }
        return messageToDelete;
    }

    public Optional<MessageBusiness> upLevel(final Long id) {
        return findMessage(id)
                .map(mb -> new MessageBusiness(
                        mb.getId(), mb.getMessage(), mb.getTitle(), findLevelUp(mb.getLevel())))
                .flatMap(this::updateMessage);
    }

    private LevelOfMessageBusiness findLevelUp(final LevelOfMessageBusiness level) {
        LevelOfMessageBusiness newLevel = level;
        switch (level) {
            case USER:
                newLevel = LevelOfMessageBusiness.MANAGER;
                break;
            case MANAGER:
            case DIRECTOR:
                newLevel = LevelOfMessageBusiness.DIRECTOR;
                break;
        }
        return newLevel;
    }
}
