target(
    name = "faiss",
    dependencies = [
        "ann/src/main/java/com/twitter/ann/faiss/swig:swig-artifactory",
    ],
)

java_library(
    name = "swig-native-utils",
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [],
)
