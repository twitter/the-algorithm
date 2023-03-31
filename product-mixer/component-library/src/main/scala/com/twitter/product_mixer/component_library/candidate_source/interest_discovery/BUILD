scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "interests_discovery/thrift/src/main/thrift:service-thrift-scala",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "stitch/stitch-core",
    ],
    exports = [
        "interests-service/thrift/src/main/thrift:thrift-scala",
        "interests_discovery/thrift/src/main/thrift:service-thrift-scala",
    ],
)
