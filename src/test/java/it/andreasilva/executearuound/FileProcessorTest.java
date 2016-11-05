package it.andreasilva.executearuound;

import it.andreasilva.executearuound.FileProcessor;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileProcessorTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private File file;

    @Before
    public void setUp() throws Exception {
        file = testFolder.newFile();
        FileUtils.write(file, "uno\ndue\ntre", "UTF-8");

    }

    @Test
    public void testFirstLineProcessor() throws Exception {
        String output = FileProcessor.process(file.getAbsolutePath(), BufferedReader::readLine);
        assertThat(output, equalTo("uno"));
    }

    @Test
    public void testTwoLinesProcessor() throws Exception {
        String output = FileProcessor.process(file.getAbsolutePath(), reader -> reader.readLine() + reader.readLine());
        assertThat(output, equalTo("unodue"));
    }
}