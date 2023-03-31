java_library(
    sources = ["*.java"],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/org/apache/lucene:lucene-analyzers-common",
        "420rdparty/jvm/org/apache/lucene:lucene-analyzers-smartcn",
        "420rdparty/jvm/org/apache/lucene:lucene-facet",
        "420rdparty/jvm/org/apache/lucene:lucene-queries",
        "src/java/com/twitter/search/common/constants",
        "src/java/com/twitter/search/common/schema/base",
        "src/java/com/twitter/search/common/schema/earlybird",
        "src/java/com/twitter/search/core/earlybird",
        "src/thrift/com/twitter/search:earlybird-java",
    ],
)
