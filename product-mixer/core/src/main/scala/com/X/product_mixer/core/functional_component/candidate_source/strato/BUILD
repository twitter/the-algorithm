scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/functional_component/candidate_source",
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/pipeline/pipeline_failure",
        "stitch/stitch-core",
        "strato/src/main/scala/com/X/strato/catalog",
        "strato/src/main/scala/com/X/strato/client",
        "strato/src/main/scala/com/X/strato/response",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/X/product_mixer/core/functional_component/candidate_source",
        "stitch/stitch-core",
        "strato/src/main/scala/com/X/strato/catalog",
        "strato/src/main/scala/com/X/strato/client",
    ],
)
