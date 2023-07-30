scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/X/bijection:core",
        "3rdparty/jvm/com/X/storehaus:core",
        "3rdparty/jvm/org/apache/beam:beam-sdks-java-io-google-cloud-platform",
        "ann/src/main/thrift/com/X/ann/common:ann-common-scala",
        "finatra/inject/inject-mdc/src/main/scala",
        "mediaservices/commons/src/main/scala:futuretracker",
        "src/java/com/X/search/common/file",
        "src/scala/com/X/ml/api/embedding",
        "stitch/stitch-core",
    ],
    exports = [
        "3rdparty/jvm/com/X/bijection:core",
    ],
)
