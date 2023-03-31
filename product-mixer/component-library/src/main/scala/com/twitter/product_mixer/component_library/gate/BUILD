scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/query/ads",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/gate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/quality_factor",
        "src/scala/com/twitter/ml/featurestore/lib",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/common",
    ],
)
