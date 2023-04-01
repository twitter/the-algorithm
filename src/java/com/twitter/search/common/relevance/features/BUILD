# Java library for tweet features and utilities.
java_library(
    sources = ["*.java"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/com/twitter/elephantbird:core",
        "3rdparty/jvm/org/apache/hadoop:hadoop-client-default",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "src/java/com/twitter/common/base",
        "src/java/com/twitter/common/text/token",
        "src/java/com/twitter/search/common/encoding/features",
        "src/java/com/twitter/search/common/features",
        "src/java/com/twitter/search/common/metrics",
        "src/java/com/twitter/search/common/schema/base",
        "src/java/com/twitter/search/common/schema/earlybird",
        "src/java/com/twitter/search/common/util/lang",
        "src/thrift/com/twitter/search/common:constants-java",
        "src/thrift/com/twitter/search/common:features-java",
        "src/thrift/com/twitter/search/common:schema-java",
    ],
)
