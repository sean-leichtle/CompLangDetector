# CompLangDetector

CompLangDetector uses the zlib compression library by way of java.util.zip.Deflater to provide a simple, elegant means of language detection.

Parallel multilingual corpora, in this case versions of the UN Universal Declaration of Human Rights, are used to provide "fingerprints" of various languages. Each "fingerprint" is compressed and the size of the compressed artefact is noted.

A candidate for language detection is then appended to each "fingerprint". The resulting object is compressed and the size of the compressed artefact is noted.

Language detection is then a function of the least difference between corresponding pairs of compressed artefacts comp(fingerprint(language_i) + candidate) and comp(fingerprint(language_i).

<i>Note:</i> The current implementation only supports detection of German and English.
