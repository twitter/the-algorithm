scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "account-recommendations-mixer/thrift/src/main/thrift:thrift-scala",
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
        "stitch/stitch-core",
    ],
    exports = [
        "account-recommendations-mixer/thrift/src/main/thrift:thrift-scala",
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "stitch/stitch-core",
    ],
)
