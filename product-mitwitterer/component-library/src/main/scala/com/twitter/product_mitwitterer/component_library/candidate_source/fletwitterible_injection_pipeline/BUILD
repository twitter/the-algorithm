scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-core/src/main/scala/com/twitter/inject",
        "onboarding/service/thrift/src/main/thrift:thrift-scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "onboarding/service/thrift/src/main/thrift:thrift-scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
    ],
)
