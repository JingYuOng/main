package modulo.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.fxml.FXML;
import modulo.MainApp;
import modulo.testutil.Assert;

public class UiPartTest {

    private static final String MISSING_FILE_PATH = "UiPartTest/missingFile.fxml";
    private static final String INVALID_FILE_PATH = "UiPartTest/invalidFile.fxml";
    private static final String VALID_FILE_PATH = "UiPartTest/validFile.fxml";
    private static final String VALID_FILE_WITH_FX_ROOT_PATH = "UiPartTest/validFileWithFxRoot.fxml";
    private static final TestFxmlObject VALID_FILE_ROOT = new TestFxmlObject("Hello World!");

    @TempDir
    public Path testFolder;

    @Test
    public void constructor_nullFileUrl_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TestUiPart<>((URL) null));
        Assert.assertThrows(NullPointerException.class, () -> new TestUiPart<>((URL) null, new Object()));
    }

    @Test
    public void constructor_missingFileUrl_throwsAssertionError() throws Exception {
        URL missingFileUrl = new URL(testFolder.toUri().toURL(), MISSING_FILE_PATH);
        Assert.assertThrows(AssertionError.class, () -> new TestUiPart<>(missingFileUrl));
        Assert.assertThrows(AssertionError.class, () -> new TestUiPart<>(missingFileUrl, new Object()));
    }

    @Test
    public void constructor_invalidFileUrl_throwsAssertionError() {
        URL invalidFileUrl = getTestFileUrl(INVALID_FILE_PATH);
        Assert.assertThrows(AssertionError.class, () -> new TestUiPart<>(invalidFileUrl));
        Assert.assertThrows(AssertionError.class, () -> new TestUiPart<>(invalidFileUrl, new Object()));
    }

    @Test
    public void constructor_validFileUrl_loadsFile() {
        URL validFileUrl = getTestFileUrl(VALID_FILE_PATH);
        assertEquals(VALID_FILE_ROOT, new TestUiPart<TestFxmlObject>(validFileUrl).getRoot());
    }

    @Test
    public void constructor_validFileUrlString_loadsFile() {
        assertEquals(VALID_FILE_ROOT, new TestUiPart<TestFxmlObject>(VALID_FILE_PATH).getRoot());
    }

    @Test
    public void constructor_validFileWithFxRootUrl_loadsFile() {
        URL validFileUrl = getTestFileUrl(VALID_FILE_WITH_FX_ROOT_PATH);
        TestFxmlObject root = new TestFxmlObject();
        assertEquals(VALID_FILE_ROOT, new TestUiPart<>(validFileUrl, root).getRoot());
    }

    @Test
    public void constructor_validFileStringWithFxRootUrl_loadsFile() {
        TestFxmlObject root = new TestFxmlObject();
        assertEquals(VALID_FILE_ROOT, new TestUiPart<>(VALID_FILE_WITH_FX_ROOT_PATH, root).getRoot());
    }

    @Test
    public void constructor_nullFileName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TestUiPart<>((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new TestUiPart<>((String) null, new Object()));
    }

    @Test
    public void constructor_missingFileName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TestUiPart<>(MISSING_FILE_PATH));
        Assert.assertThrows(NullPointerException.class, () -> new TestUiPart<>(MISSING_FILE_PATH, new Object()));
    }

    @Test
    public void constructor_invalidFileName_throwsAssertionError() {
        Assert.assertThrows(AssertionError.class, () -> new TestUiPart<>(INVALID_FILE_PATH));
        Assert.assertThrows(AssertionError.class, () -> new TestUiPart<>(INVALID_FILE_PATH, new Object()));
    }

    private URL getTestFileUrl(String testFilePath) {
        String testFilePathInView = "/view/" + testFilePath;
        URL testFileUrl = MainApp.class.getResource(testFilePathInView);
        assertNotNull(testFileUrl, testFilePathInView + " does not exist.");
        return testFileUrl;
    }

    /**
     * UiPart used for testing. It should only be used with invalid FXML files or the valid file located at the above
     * {@value VALID_FILE_PATH}.
     */
    private static class TestUiPart<T> extends UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestUiPart(URL fxmlFileUrl, T root) {
            super(fxmlFileUrl, root);
        }

        TestUiPart(String fxmlFileName, T root) {
            super(fxmlFileName, root);
        }

        TestUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

    }

}
