/*
 * Copyright 2015 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package ec.util.spreadsheet.od;

import static ec.util.spreadsheet.Assertions.assertThat;
import ec.util.spreadsheet.helpers.ArrayBook;
import ec.util.spreadsheet.helpers.ArraySheet;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author Philippe Charles
 */
public class OpenDocumentBookFactoryTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void testCompliance() throws IOException {
        File valid = createContent(temp.newFile("valid.ods"));
        // FIXME: find a way to detect invalid files
//        File invalid = temp.newFile("invalid.ods");
//        Files.write(invalid.toPath(), Arrays.asList("..."));
        assertThat(new OpenDocumentBookFactory()).isCompliant(valid);
    }

    private static File createContent(File file) throws IOException {
        Date jan2012 = new GregorianCalendar(2012, Calendar.JANUARY, 1).getTime();

        ArraySheet.Builder sheetBuilder = ArraySheet.builder();

        ArrayBook input = ArrayBook.builder()
                .sheet(sheetBuilder.clear().name("first").row(1, 1, "hello", 3.14d, jan2012).build())
                .sheet(sheetBuilder.clear().name("second").row(3, 0, "world", 123, jan2012).build())
                .build();

        new OpenDocumentBookFactory().store(file, input);
        return file;
    }
}
