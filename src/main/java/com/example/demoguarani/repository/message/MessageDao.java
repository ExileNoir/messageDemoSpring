package com.example.demoguarani.repository.message;

import ch.rasc.sbjooqflyway.db.tables.Message;
import com.example.demoguarani.repository.mapper.MapperMessageDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Repository
@RequiredArgsConstructor
public class MessageDao {

    private final JdbcTemplate pg13;
    private final DSLContext dslContext;
    private final MapperMessageDto mapper;

    private final Message messageTable = Message.MESSAGE;

    public Optional<MessageDto> findMessage(final Long id) {
        return dslContext.selectFrom(messageTable)
                .where(messageTable.ID.eq(id.intValue()))
                .fetch()
                .stream()
                .map(mapper::toMessageDto)
                .filter(messageDto -> Objects.nonNull(messageDto.getId()))
                .findFirst();

//        final List<MessageDto> listMessageDto = pg13.query(
//                "SELECT id, message FROM message WHERE id = ?", (resultSet, rowNumber) -> {
//
//                    Long idTable = resultSet.getLong("id");
//                    if (wasNull(resultSet)) {
//                        idTable = null;
//                    }
//
//                    String messageTable = resultSet.getString("message");
//                    if (wasNull(resultSet)) {
//                        messageTable = null;
//                    }
//                    return new MessageDto(idTable, messageTable);
//                }
//                , id);
//
//        return listMessageDto.stream()
//                .filter(messageDto -> Objects.nonNull(messageDto.getId()))
//                .findFirst();
    }

//    @SneakyThrows
//    private boolean wasNull(final ResultSet resultSet) {
//        return resultSet.wasNull();
//    }

//    public MessageDto createMessage(final String message) {
//        return pg13.execute(buildConnectionCallback(message));
//    }

//    private ConnectionCallback<MessageDto> buildConnectionCallback(final String message) {
//        return (Connection con) -> executeInsert(message, con);
//    }

//    private MessageDto executeInsert(final String message, final Connection conn) throws SQLException {
//        try (final PreparedStatement ps = conn.prepareStatement("INSERT INTO message (message) values(?)",
//                Statement.RETURN_GENERATED_KEYS)) {
//            ps.setString(1, message);
//            ps.execute();
//            Long id = null;
//            try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    id = generatedKeys.getLong(1);
//                }
//            }
//            return new MessageDto(id, message);
//        }
//    }

    public MessageDto createMessage(final String message, final String title) {
        return dslContext.insertInto(messageTable, messageTable.MESSAGE_, messageTable.TITLE)
                .values(message, title)
                .returning()
                .fetchOptional()
                .map(mapper::toMessageDto)
                .orElseThrow(() -> new RuntimeException(
                        "No message Created with; data message: '" + message + "' and title: '" + title + "'"));

    }

    public boolean updateMessage(final MessageDto message) {
        return dslContext.update(messageTable)
                .set(messageTable.MESSAGE_, message.getMessage())
                .set(messageTable.TITLE, message.getTitle())
                .set(messageTable.LEVEL, message.getLevel().name())
                .where(messageTable.ID.eq(message.getId()
                        .intValue()))
                .execute() == 1;

//        return pg13.update("UPDATE message SET message = ? WHERE id = ?", message.getMessage(), message.getId()) == 1;
    }

    public boolean deleteMessage(final Long id) {
        return dslContext.deleteFrom(messageTable)
                .where(messageTable.ID.eq(id.intValue()))
                .execute() == 1;

//        return pg13.update("DELETE FROM message WHERE id = ?", id) == 1;
    }
}
