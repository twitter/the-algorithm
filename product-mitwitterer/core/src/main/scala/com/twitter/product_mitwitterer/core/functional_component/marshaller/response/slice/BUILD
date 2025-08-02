scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/component-library/src/main/thrift/com/twitter/product_mitwitterer/component_library:thrift-scala",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/marshaller",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/slice",
        "strato/config/src/thrift/com/twitter/strato/graphql:api-media-graphql-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql:graphql-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql:topics-graphql-scala",
    ],
)
