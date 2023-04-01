java_library(
    name = "swig-local",
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-only",
    ],
    dependencies = [
        "ann/src/main/java/com/twitter/ann/faiss:swig-native-utils",
        "ann/src/main/java/com/twitter/ann/faiss/swig/resources",
    ],
)

java_library(
    name = "swig-artifactory",
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/ann/faiss/swig:resources",
        "ann/src/main/java/com/twitter/ann/faiss:swig-native-utils",
    ],
)
