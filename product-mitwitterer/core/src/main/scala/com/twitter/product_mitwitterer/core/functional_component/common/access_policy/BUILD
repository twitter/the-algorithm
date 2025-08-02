scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fastertwitterml/jackson/core:jackson-annotations",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common",
    ],
    etwitterports = [
        "3rdparty/jvm/com/fastertwitterml/jackson/core:jackson-annotations",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common",
    ],
)
