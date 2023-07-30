java_library(
    sources = ["*.java"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/lucene:lucene-analyzers-common",
        "3rdparty/jvm/org/apache/lucene:lucene-analyzers-smartcn",
        "3rdparty/jvm/org/apache/lucene:lucene-facet",
        "3rdparty/jvm/org/apache/lucene:lucene-queries",
        "src/java/com/X/search/common/constants",
        "src/java/com/X/search/common/schema/base",
        "src/java/com/X/search/common/schema/earlybird",
        "src/java/com/X/search/core/earlybird",
        "src/thrift/com/X/search:earlybird-java",
    ],
)
