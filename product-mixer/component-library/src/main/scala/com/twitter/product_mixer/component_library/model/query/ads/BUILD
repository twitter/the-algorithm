scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "dspbidder/thrift/src/main/thrift/com/ExTwitter/dspbidder/commons:thrift-scala",
        "product-mixer/core/src/main/scala/com/ExTwitter/product_mixer/core/pipeline",
        "src/thrift/com/ExTwitter/ads/adserver:adserver_rpc-scala",
    ],
    exports = [
        "dspbidder/thrift/src/main/thrift/com/ExTwitter/dspbidder/commons:thrift-scala",
        "product-mixer/core/src/main/scala/com/ExTwitter/product_mixer/core/pipeline",
        "src/thrift/com/ExTwitter/ads/adserver:adserver_rpc-scala",
    ],
)
