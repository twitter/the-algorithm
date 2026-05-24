scala_library(
    name = "decoration",
    sources = ["Decoration.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
    ],
    exports = [
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
    ],
)

scala_library(
    sources = [
        "!Decoration.scala",
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        ":decoration",
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
    exports = [
        ":decoration",
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common/presentation",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline",
    ],
)
