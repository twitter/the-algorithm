java_library(
    sources = ["*.java"],
    platform = "java8",
    provides = artifact(
        org = "com.twitter.search.common.util",
        name = "lang",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/code/findbugs:jsr305",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "3rdparty/jvm/org/slf4j:slf4j-api",
        "src/java/com/twitter/common/text/language:locale-util",
        "src/thrift/com/twitter/search/common:constants-java",
    ],
)
