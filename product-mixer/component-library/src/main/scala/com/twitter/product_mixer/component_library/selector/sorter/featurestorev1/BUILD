scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/selector/sorter",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/selector/sorter",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1",
    ],
)
