package db.migration;

import com.example.demoguarani.repository.message.LevelOfMessageDto;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 23/09/2021
 */

@Component
public class V0005__addColumnLevel extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        // makes all the requests sql en Jooq
        final DSLContext dsl = DSL.using(context.getConnection());
        final Table<Record> messageTable = DSL.table("message");
        final Field<String> levelField = DSL.field("level", SQLDataType.VARCHAR(15)
                .nullable(false)
                .defaultValue(LevelOfMessageDto.USER.name()));

        dsl.alterTable(messageTable)
                .addColumn(levelField)
                .execute();
    }

}
