scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "mediaservices/commons/src/main/thrift:thrift-scala",
        "scrooge/scrooge-core",
        "src/thrift/com/twitter/escherbird:media-annotation-structs-scala",
        "src/thrift/com/twitter/spam/rtf:safety-label-scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:tweet-scala",
    ],
)
