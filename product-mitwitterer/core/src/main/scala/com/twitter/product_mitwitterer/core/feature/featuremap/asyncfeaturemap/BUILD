scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/identifier",
        "stitch/stitch-core",
        "util/util-jackson/src/main/scala/com/twitter/util/jackson",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/feature/featuremap",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common/identifier",
        "stitch/stitch-core",
    ],
)
