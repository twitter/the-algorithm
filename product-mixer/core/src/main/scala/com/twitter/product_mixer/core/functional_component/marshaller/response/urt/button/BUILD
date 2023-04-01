scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt/icon",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt/metadata",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urt/button",
        "src/thrift/com/twitter/timelines/render:thrift-scala",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt/icon",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt/metadata",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urt/button",
        "src/thrift/com/twitter/timelines/render:thrift-scala",
    ],
)
