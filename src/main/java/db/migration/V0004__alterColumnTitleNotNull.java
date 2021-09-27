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
public class V0004__alterColumnTitleNotNull extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        // makes all the requests sql en Jooq
        final DSLContext dsl = DSL.using(context.getConnection());
        final Table<Record> messageTable = DSL.table("message");

        dsl.alterTable(messageTable)
                .alterColumn("title")
                .setNotNull()
                .execute();
    }

}
