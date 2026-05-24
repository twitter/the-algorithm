scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/request",
        "product-mixer/core/src/main/thrift/com/twitter/product_mixer/core:thrift-scala",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/request",
        "product-mixer/core/src/main/thrift/com/twitter/product_mixer/core:thrift-scala",
    ],
)
