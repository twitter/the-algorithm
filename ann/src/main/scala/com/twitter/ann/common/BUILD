scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/ExTwitter/bijection:core",
        "3rdparty/jvm/com/ExTwitter/storehaus:core",
        "3rdparty/jvm/org/apache/beam:beam-sdks-java-io-google-cloud-platform",
        "ann/src/main/thrift/com/ExTwitter/ann/common:ann-common-scala",
        "finatra/inject/inject-mdc/src/main/scala",
        "mediaservices/commons/src/main/scala:futuretracker",
        "src/java/com/ExTwitter/search/common/file",
        "src/scala/com/ExTwitter/ml/api/embedding",
        "stitch/stitch-core",
    ],
    exports = [
        "3rdparty/jvm/com/ExTwitter/bijection:core",
    ],
)
