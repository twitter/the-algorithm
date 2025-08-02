scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "configapi/configapi-core",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/pipeline/product",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/product",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/product/registry",
        "stitch/stitch-core",
        "strato/config/src/thrift/com/twitter/strato/graphql:api-media-graphql-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql:graphql-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql:topics-graphql-scala",
        "util/util-core:scala",
    ],
    etwitterports = [
        "strato/config/src/thrift/com/twitter/strato/graphql:graphql-scala",
    ],
)
