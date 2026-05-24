scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-annotations",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
    ],
    exports = [
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-annotations",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
    ],
)
