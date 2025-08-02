scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source/strato",
        "src/thrift/com/twitter/timelines/impression:thrift-scala",
        "strato/config/columns/timelines/impression-store:impression-store-strato-client",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source/strato",
        "strato/config/columns/timelines/impression-store:impression-store-strato-client",
    ],
)
