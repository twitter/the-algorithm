scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/selector/sorter",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1",
    ],
    etwitterports = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/selector/sorter",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1",
    ],
)
