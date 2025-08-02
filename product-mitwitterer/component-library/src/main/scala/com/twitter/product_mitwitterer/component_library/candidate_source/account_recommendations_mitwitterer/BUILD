scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "account-recommendations-mitwitterer/thrift/src/main/thrift:thrift-scala",
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "src/thrift/com/twitter/ads/adserver:adserver_common-scala",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "account-recommendations-mitwitterer/thrift/src/main/thrift:thrift-scala",
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "stitch/stitch-core",
    ],
)
