scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/configapi/registry",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/request",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/mitwitterer",
        "stitch/stitch-core",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/configapi/registry",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/request",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/mitwitterer",
        "stitch/stitch-core",
    ],
)
