# Library for Schema.java and other utilities with minimal dependencies.
java_library(
    name = "base",
    sources = ["*.java"],
    platform = "java8",
    provides = artifact(
        org = "com.X.search.common",
        name = "schema-base",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/code/findbugs:jsr305",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/commons-lang",
        "3rdparty/jvm/org/apache/lucene:lucene-analyzers-common",
        "3rdparty/jvm/org/apache/lucene:lucene-core",
        "3rdparty/jvm/org/apache/lucene:lucene-facet",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "src/java/com/X/common/base",
        "src/java/com/X/common/text/util:token-util",
        "src/thrift/com/X/search/common:features-java",
        "src/thrift/com/X/search/common:schema-java",
    ],
)
