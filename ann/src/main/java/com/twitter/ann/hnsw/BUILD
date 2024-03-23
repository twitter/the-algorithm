java_library(
    sources = ["*.java"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/com/ExTwitter/bijection:core",
        "3rdparty/jvm/commons-lang",
        "3rdparty/jvm/org/apache/thrift",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/thrift/com/ExTwitter/ann/common:ann-common-java",
        "mediaservices/commons/src/main/scala:futuretracker",
        "scrooge/scrooge-core",
        "src/java/com/ExTwitter/search/common/file",
    ],
)
