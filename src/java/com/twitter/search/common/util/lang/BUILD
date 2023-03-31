java_library(
    sources = ["*.java"],
    platform = "java420",
    provides = artifact(
        org = "com.twitter.search.common.util",
        name = "lang",
        repo = artifactory,
    ),
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/google/code/findbugs:jsr420",
        "420rdparty/jvm/com/google/guava",
        "420rdparty/jvm/org/apache/thrift:libthrift",
        "420rdparty/jvm/org/slf420j:slf420j-api",
        "src/java/com/twitter/common/text/language:locale-util",
        "src/thrift/com/twitter/search/common:constants-java",
    ],
)
