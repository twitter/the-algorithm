scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "dspbidder/thrift/src/main/thrift/com/twitter/dspbidder/commons:thrift-scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
        "src/thrift/com/twitter/ads/adserver:adserver_rpc-scala",
    ],
    etwitterports = [
        "dspbidder/thrift/src/main/thrift/com/twitter/dspbidder/commons:thrift-scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
        "src/thrift/com/twitter/ads/adserver:adserver_rpc-scala",
    ],
)
