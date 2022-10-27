# CompLangDetector

Implementing a strategy first proposed by Benedetto, Caglioti, and Loreto,[^1] CompLangDetector uses the zlib compression library by way of java.util.zip.Deflater[^2] to provide a simple, elegant means of language detection.

A parallel multilingual corpus, in this case versions of the UN Universal Declaration of Human Rights,[^3] is used to provide "fingerprints" of various languages. Each "fingerprint" is compressed and the size of the compressed artefact is noted.

A candidate for language detection is then appended to each "fingerprint". The resulting object is compressed and the size of the compressed artefact is noted.

Language detection is then a function of the least difference between corresponding pairs of compressed artefacts ***comp(fingerprint(language<sub>1</sub>...<sub>n</sub>) + candidate)*** and ***comp(fingerprint(language<sub>1</sub>...<sub>n</sub>))***.

<i>Note:</i> The current implementation only supports detection of English, Dutch, French, German and Spanish. Given that it employs a compression algorithm, CompLangDetector cannot reliably detect the language of shorter texts.

[^1]: BENEDETTO, Dario; CAGLIOTI, Emanuele; LORETO, Vittorio. Language trees and zipping. Physical Review Letters, 2002, 88. Jg., Nr. 4, S. 048702. (DOI: https://doi.org/10.1103/PhysRevLett.88.048702)
[^2]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/zip/Deflater.html
[^3]: https://www.ohchr.org/en/human-rights/universal-declaration/universal-declaration-human-rights/about-universal-declaration-human-rights-translation-project