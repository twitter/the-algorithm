scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/rtf/safety_level",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/rtf/safety_level",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/model/marshalling/response/urt/contextual_ref",
        "src/thrift/com/twitter/spam/rtf:safety-level-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql/contextual_refs:graphql-refs-scala",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/marshaller/response/rtf/safety_level",
        "src/thrift/com/twitter/spam/rtf:safety-level-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql/contextual_refs:graphql-refs-scala",
    ],
)
