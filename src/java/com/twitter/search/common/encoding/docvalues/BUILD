# Java library for docvalues and common stride field encoding utilities.
java_library(
    sources = ["*.java"],
    platform = "java8",
    provides = artifact(
        org = "com.twitter.search.common",
        name = "encoding-docvalues",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/org/apache/lucene:lucene-analyzers-common",
        "3rdparty/jvm/org/apache/lucene:lucene-core",
        "3rdparty/jvm/org/apache/lucene:lucene-facet",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "src/java/com/twitter/search/common/schema/base",
        "src/thrift/com/twitter/search/common:schema-java",
    ],
)
