scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "people-discovery/api/thrift:thrift-scala",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
        "src/thrift/com/twitter/hermit:hermit-scala",
        "stitch/stitch-core",
    ],
    exports = [
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "people-discovery/api/thrift:thrift-scala",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source",
        "stitch/stitch-core",
    ],
)
