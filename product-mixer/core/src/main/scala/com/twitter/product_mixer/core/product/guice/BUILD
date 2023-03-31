scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/javax/inject:javax.inject",
        "finatra/inject/inject-core/src/main/scala",
        "product-mixer/core/src/main/java/com/twitter/product_mixer/core/product/guice/scope",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/request",
    ],
    exports = [
        "product-mixer/core/src/main/java/com/twitter/product_mixer/core/product/guice/scope",
    ],
)
