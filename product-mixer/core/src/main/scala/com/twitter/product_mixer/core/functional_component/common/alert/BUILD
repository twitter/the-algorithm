scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-annotations",
        "3rdparty/jvm/javax/inject:javax.inject",
        "3rdparty/jvm/javax/mail",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/common/alert/predicate",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "util/util-core:scala",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/common/alert/predicate",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "util/util-core:scala",
    ],
)
