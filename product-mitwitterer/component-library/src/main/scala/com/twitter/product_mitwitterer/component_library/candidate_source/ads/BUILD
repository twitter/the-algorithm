scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source/strato",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
        "src/thrift/com/twitter/ads/adserver:adserver_rpc-scala",
        "strato/config/columns/ads/admitwitterer:admitwitterer-strato-client",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source/strato",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
        "src/thrift/com/twitter/ads/adserver:adserver_rpc-scala",
    ],
)
