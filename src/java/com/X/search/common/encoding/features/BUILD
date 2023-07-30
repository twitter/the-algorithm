# Java library for feature encoding and decoding utilities.
java_library(
    sources = ["*.java"],
    platform = "java8",
    provides = artifact(
        org = "com.X.search.common",
        name = "encoding-features",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "src/java/com/X/search/common/schema/base",
        "src/thrift/com/X/search/common:indexing-java",
    ],
)
