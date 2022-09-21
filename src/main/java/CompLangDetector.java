import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Deflater;

/**
 * Provides language detection. The UN "Universal Declaration of Human Rights" in both English and German
 * versions are employed as "fingerprints". The term "fingerprint" is here understood as the basis for a
 * compressed artefact against which to compare compression ratios relative to candidate texts for
 * language detection.
 * {@see <a href="https://rb.gy/gor63r">Universal Declaration of Human Rights Translation Project</a>}
 *
 */
public class CompLangDetector {

    /**
     * Used to import "fingerprint" files.
     *
     * @param input String of path to .txt file
     * @return String of imported .txt file
     * @throws IOException if {@param input} is null or I/O operations
     *          are interrupted
     */
    public static String readFingerprint(String input) throws IOException {
        return Files.readString(Path.of(input));
    }

    /**
     * Compression method employing the ZLIB compression library.
     *
     * @param inputString to be compressed
     * @return int length of compressed String
     * @throws UnsupportedEncodingException
     */
    public static int getDeflatedLength(String inputString) throws UnsupportedEncodingException {
        byte[] input = inputString.getBytes("UTF-8");
        byte[] output = new byte[10000];

        Deflater deflater = new Deflater();
        deflater.setInput(input);

        deflater.finish();
        int deflatedLength = deflater.deflate(output);
        deflater.end();

        return deflatedLength;
    }

    /**
     * Detects language as a function of the least difference
     * between pairs of compressed artefacts [fingerprint(LANGUAGE) + candidate]
     * and [fingerprint]. Note that at least two language fingerprints
     * must be employed and that the candidate text should include
     * more than one word for reliable detection
     *
     * @param candidate text the language of which is to be determined
     * @return language detected
     */
    public static String detect(String candidate) {
        String result = null;
        try {
            String fingerprintDE = readFingerprint("src/main/java/org/jabref/logic/formatter/detector/fingerprint_de.txt");
            String fingerprintEN = readFingerprint("src/main/java/org/jabref/logic/formatter/detector/fingerprint_en.txt");

            int candidateDE = getDeflatedLength(fingerprintDE + candidate) - getDeflatedLength(fingerprintDE);
            int candidateEN = getDeflatedLength(fingerprintEN + candidate) - getDeflatedLength(fingerprintEN);

            if (candidateDE < candidateEN) {
                result = "DE";
            } else {
                result = "EN";
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
