scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/twitter/bijection:core",
        "3rdparty/jvm/com/twitter/storehaus:core",
        "3rdparty/jvm/org/apache/beam:beam-sdks-java-io-google-cloud-platform",
        "ann/src/main/thrift/com/twitter/ann/common:ann-common-scala",
        "finatra/inject/inject-mdc/src/main/scala",
        "mediaservices/commons/src/main/scala:futuretracker",
        "src/java/com/twitter/search/common/file",
        "src/scala/com/twitter/ml/api/embedding",
        "stitch/stitch-core",
    ],
    exports = [
        "3rdparty/jvm/com/twitter/bijection:core",
    ],
)
