java_library(
    sources = ["*.java"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/org/apache/hadoop:hadoop-client-default",
        "3rdparty/jvm/org/slf4j:slf4j-api",
        "3rdparty/jvm/org/yaml:snakeyaml",
        "src/java/com/twitter/search/common/file",
        "src/java/com/twitter/search/common/metrics",
    ],
)
