scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/datarecord",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1/featurevalue",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/feature_hydrator",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "src/scala/com/twitter/ml/featurestore/lib/online",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1/featurevalue",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/feature_hydrator",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "src/scala/com/twitter/ml/featurestore/lib/online",
    ],
)
