scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt/metadata",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt/metadata",
    ],
)
