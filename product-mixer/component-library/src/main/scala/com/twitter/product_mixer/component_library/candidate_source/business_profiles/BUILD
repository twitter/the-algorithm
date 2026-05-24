scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/cursor",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/candidate_source/strato",
        "strato/config/columns/consumer-identity/business-profiles:business-profiles-strato-client",
        "strato/config/src/thrift/com/twitter/strato/graphql:graphql-scala",
        "strato/src/main/scala/com/twitter/strato/client",
    ],
)
