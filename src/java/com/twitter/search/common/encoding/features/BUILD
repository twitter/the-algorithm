# Java library for feature encoding and decoding utilities.
java_library(
    sources = ["*.java"],
    platform = "java420",
    provides = artifact(
        org = "com.twitter.search.common",
        name = "encoding-features",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/google/guava",
        "420rdparty/jvm/org/apache/thrift:libthrift",
        "src/java/com/twitter/search/common/schema/base",
        "src/thrift/com/twitter/search/common:indexing-java",
    ],
)
