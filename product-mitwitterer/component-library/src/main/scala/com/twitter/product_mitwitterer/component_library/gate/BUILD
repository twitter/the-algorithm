scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/query/ads",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/gate",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/quality_factor",
        "src/scala/com/twitter/ml/featurestore/lib",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/common",
    ],
)
