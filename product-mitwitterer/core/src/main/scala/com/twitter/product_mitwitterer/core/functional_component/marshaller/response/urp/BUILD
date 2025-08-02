scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/marshaller",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/marshaller/response/urt",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/urp",
        "src/thrift/com/twitter/pages/render:thrift-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql/timelines:graphql-timelines-scala",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/marshaller",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/marshaller/response/urt",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/response/urp",
        "src/thrift/com/twitter/pages/render:thrift-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql/timelines:graphql-timelines-scala",
    ],
)
