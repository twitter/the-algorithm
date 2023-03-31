scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
        "stringcenter/client",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
        "stringcenter/client",
    ],
)
