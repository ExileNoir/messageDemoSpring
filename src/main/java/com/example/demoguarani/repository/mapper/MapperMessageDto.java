package com.example.demoguarani.repository.mapper;

import ch.rasc.sbjooqflyway.db.tables.records.MessageRecord;
import com.example.demoguarani.repository.message.LevelOfMessageDto;
import com.example.demoguarani.repository.message.MessageDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 23/09/2021
 */

@Component
public class MapperMessageDto {

    public MessageDto toMessageDto(final MessageRecord messageRecord) {
        return new MessageDto(
                messageRecord.getId()
                        .longValue(),
                messageRecord.getMessage(),
                messageRecord.getTitle(),
                map(messageRecord.getLevel())
        );
    }

    private LevelOfMessageDto map(final String level) {
        return Arrays.stream(LevelOfMessageDto.values())
                .filter(levelDto -> Objects.equals(levelDto.name(), level))
                .findFirst()
                .orElseGet(() -> LevelOfMessageDto.USER);
    }
}
