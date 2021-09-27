package db.migration;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 23/09/2021
 */

@Component
public class V0002__initial_import extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        final ClassPathResource classPathResource = new ClassPathResource("message.csv");

        try (final InputStream is = classPathResource.getInputStream()) {

            final CsvParserSettings settings = new CsvParserSettings();
            settings.getFormat().setDelimiter(',');
            settings.getFormat().setQuote('"');
            final CsvParser parser = new CsvParser(settings);

            final List<String[]> rows = parser.parseAll(is);

            // makes all the requests sql en Jooq
            final DSLContext dsl = DSL.using(context.getConnection());
            rows.forEach(
                    row -> dsl.insertInto(
                            DSL.table("message"),
                            DSL.field("message")
                    ).values(row[0]).execute()
            );
        }
    }
}
