scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "stitch/stitch-core",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "strato/src/main/scala/com/twitter/strato/client",
        "strato/src/main/scala/com/twitter/strato/response",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/candidate_source",
        "stitch/stitch-core",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "strato/src/main/scala/com/twitter/strato/client",
    ],
)
