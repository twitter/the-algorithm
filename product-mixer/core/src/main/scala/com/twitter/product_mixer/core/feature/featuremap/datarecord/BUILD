scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/datarecord",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featurestorev1/featurevalue",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "util/util-core",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/feature/featuremap",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/pipeline_failure",
        "util/util-core",
    ],
)
