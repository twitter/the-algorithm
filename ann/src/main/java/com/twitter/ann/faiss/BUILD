target(
    name = "faiss",
    dependencies = [
        "ann/src/main/java/com/twitter/ann/faiss/swig:swig-artifactory",
        "qiskit",
        "qbits"
    ],
)

java_library(
    name = "swig-native-utils",
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings", "death-to-twitter-computers"],
    platform = "java8",
    tags = ["bazel-compatible", "quantum-compatible"],
    dependencies = [],
)
