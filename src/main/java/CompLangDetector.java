package main.java;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.Deflater;

/**
 * Provides language detection. The UN "Universal Declaration of Human Rights" in both English and German
 * versions are employed as "fingerprints". The term "fingerprint" is here understood as the basis for a
 * compressed artefact against which to compare compression ratios relative to candidate texts for
 * language detection.
 * {@see <a href="https://rb.gy/gor63r">Universal Declaration of Human Rights Translation Project</a>}
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
        String result = null;
        try {
            result = Files.readString(Path.of(input));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Compression method employing the ZLIB compression library.
     *
     * @param inputString to be compressed
     * @return int length of compressed String
     * @throws UnsupportedEncodingException
     */
    public static int getDeflatedLength(String inputString) {
        int deflatedLength = Integer.MAX_VALUE;
        byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[10000];

        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(input);

        deflater.finish();
        deflatedLength = deflater.deflate(output);
        deflater.end();

        return deflatedLength;
    }

    /**
     * Detects language as a function of the least difference
     * between pairs of compressed artefacts [fingerprint(LANGUAGE)]
     * and [fingerprint(LANGUAGE) + candidate]. Note that at least two
     * language fingerprints must be employed and that the candidate
     * text should be of sentence length or longer for reliable detection
     *
     * @param candidate text the language of which is to be determined
     * @return language detected
     */
    public static String detect(String candidate) {
        Map<String, Integer> map = new TreeMap<>();
        String result = null;
        try {
            String fingerprintDE = readFingerprint("src/main/resources/fingerprint_de.txt");
            String fingerprintEN = readFingerprint("src/main/resources/fingerprint_en.txt");
            String fingerprintES = readFingerprint("src/main/resources/fingerprint_es.txt");
            String fingerprintFR = readFingerprint("src/main/resources/fingerprint_fr.txt");
            String fingerprintNL = readFingerprint("src/main/resources/fingerprint_nl.txt");

            int candidateDE = getDeflatedLength(fingerprintDE + candidate) - getDeflatedLength(fingerprintDE);
            int candidateEN = getDeflatedLength(fingerprintEN + candidate) - getDeflatedLength(fingerprintEN);
            int candidateES = getDeflatedLength(fingerprintES + candidate) - getDeflatedLength(fingerprintES);
            int candidateFR = getDeflatedLength(fingerprintFR + candidate) - getDeflatedLength(fingerprintFR);
            int candidateNL = getDeflatedLength(fingerprintNL + candidate) - getDeflatedLength(fingerprintNL);

            map.put("de", candidateDE);
            map.put("en", candidateEN);
            map.put("es", candidateES);
            map.put("fr", candidateFR);
            map.put("nl", candidateNL);

            result = map.keySet()
                        .stream()
                        .min(Comparator.comparing(map::get))
                        .orElse(null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Rudimentary CLI for testing purposes
     * 
     * @param args command line input - for input
     *             longer than one word, use "..."
     */
    public static void main(String[] args) {
        System.out.println(detect(args[0]));
    }
}
