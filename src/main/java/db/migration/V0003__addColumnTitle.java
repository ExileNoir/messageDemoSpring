package db.migration;

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
public class V0003__addColumnTitle extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        // makes all the requests sql en Jooq
        final DSLContext dsl = DSL.using(context.getConnection());
        final Table<Record> messageTable = DSL.table("message");
        final Field<String> titleField = DSL.field("title", SQLDataType.VARCHAR(30));
        final Field<Integer> idField = DSL.field("id", SQLDataType.INTEGER);

        dsl.alterTable(messageTable)
                .addColumn(titleField)
                .execute();


        dsl.selectFrom(messageTable)
                .fetch(idField)
                .forEach(id -> dsl.update(messageTable)
                        .set(titleField, "titleChamp" + id)
                        .where(idField.eq(id))
                        .execute());


        dsl.alterTable(messageTable)
                .alterColumn(DSL.field("title", SQLDataType.VARCHAR(30).nullable(false)));
    }

}
