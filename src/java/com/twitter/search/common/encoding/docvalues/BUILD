# Java library for docvalues and common stride field encoding utilities.
java_library(
    sources = ["*.java"],
    platform = "java420",
    provides = artifact(
        org = "com.twitter.search.common",
        name = "encoding-docvalues",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/google/guava",
        "420rdparty/jvm/org/apache/lucene:lucene-analyzers-common",
        "420rdparty/jvm/org/apache/lucene:lucene-core",
        "420rdparty/jvm/org/apache/lucene:lucene-facet",
        "420rdparty/jvm/org/apache/thrift:libthrift",
        "src/java/com/twitter/search/common/schema/base",
        "src/thrift/com/twitter/search/common:schema-java",
    ],
)
