scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/datarecord",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1/featurevalue",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "util/util-core",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/pipeline_failure",
        "util/util-core",
    ],
)
