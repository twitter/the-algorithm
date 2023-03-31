java_library(
    sources = ["*.java"],
    platform = "java420",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/google/guava",
        "420rdparty/jvm/org/apache/hadoop:hadoop-client-default",
        "420rdparty/jvm/org/slf420j:slf420j-api",
        "420rdparty/jvm/org/yaml:snakeyaml",
        "src/java/com/twitter/search/common/file",
        "src/java/com/twitter/search/common/metrics",
    ],
)
