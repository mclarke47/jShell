package org.mclarke.command.ls;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LongListFormatterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testLongListPrint() throws Exception {

        LongListFormatter longListFormatter = new LongListFormatter();

        File createdFile = folder.newFile("myfile.txt");

        Instant now = Instant.now();
        DateTimeFormatter longListDateFormat = DateTimeFormatter.ofPattern("MMM dd HH:mm");

        LocalDateTime ldt = LocalDateTime.ofInstant(now, ZoneId.systemDefault());

        String date = longListDateFormat.format(ldt);

        FileListing fileListing = new FileListing("myfile.txt", createdFile);

        String longListLine = longListFormatter.format(fileListing).getFileName();

        assertThat(longListLine, is("-rwxrwx--- Matthew   admin          0 " + date + "                     myfile.txt\n"));


    }

}