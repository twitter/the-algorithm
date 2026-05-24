scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/scorer/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
        "src/thrift/com/twitter/ml/api:embedding-scala",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/scorer/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
)
