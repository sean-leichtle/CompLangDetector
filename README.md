# CompLangDetector

CompLangDetector uses the zlib compression library by way of java.util.zip.Deflater to provide a simple, elegant means of language detection.

A parallel multilingual corpus, in this case versions of the UN Universal Declaration of Human Rights, is used to provide "fingerprints" of various languages. Each "fingerprint" is compressed and the size of the compressed artefact is noted.

A candidate for language detection is then appended to each "fingerprint". The resulting object is compressed and the size of the compressed artefact is noted.

Language detection is then a function of the least difference between corresponding pairs of compressed artefacts <b>comp(fingerprint(language<sub>1</sub>...<sub>n</sub>) + candidate)</b> and <b>comp(fingerprint(language<sub>1</sub>...<sub>n</sub>)</b>.

<i>Note:</i> The current implementation only supports detection of German and English.
