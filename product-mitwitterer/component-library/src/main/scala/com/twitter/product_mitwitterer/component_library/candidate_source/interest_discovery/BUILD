scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "interests_discovery/thrift/src/main/thrift:service-thrift-scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "interests_discovery/thrift/src/main/thrift:service-thrift-scala",
    ],
)
