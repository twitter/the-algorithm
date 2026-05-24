scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
        "src/thrift/com/twitter/pages/render:thrift-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql/timelines:graphql-timelines-scala",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/urt",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urp",
        "src/thrift/com/twitter/pages/render:thrift-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql/timelines:graphql-timelines-scala",
    ],
)
