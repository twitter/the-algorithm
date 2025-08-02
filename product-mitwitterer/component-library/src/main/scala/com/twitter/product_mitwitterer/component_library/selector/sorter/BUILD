scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/presentation",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/presentation",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline",
    ],
)
