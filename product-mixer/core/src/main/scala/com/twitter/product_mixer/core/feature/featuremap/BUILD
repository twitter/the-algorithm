scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1/featurevalue",
        "util/util-core",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "util/util-core",
    ],
)
