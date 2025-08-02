scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/datarecord",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1/featurevalue",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/feature_hydrator",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "src/scala/com/twitter/ml/featurestore/lib/online",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1/featurevalue",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/feature_hydrator",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "src/scala/com/twitter/ml/featurestore/lib/online",
    ],
)
