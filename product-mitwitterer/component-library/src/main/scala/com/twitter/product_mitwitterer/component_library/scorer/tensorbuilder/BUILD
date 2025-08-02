scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/scorer/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featurestorev1",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
        "src/thrift/com/twitter/ml/api:embedding-scala",
    ],
    etwitterports = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/scorer/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
    ],
)
