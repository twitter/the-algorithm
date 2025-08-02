create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "product-mitwitterer/core/src/main/thrift/com/twitter/product_mitwitterer/core:thrift",
        "src/thrift/com/twitter/ads/schema:common",
        "src/thrift/com/twitter/ml/api:data",
        "src/thrift/com/twitter/recos:recos-common",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift",
        "src/thrift/com/twitter/timelines/render:thrift",
        "strato/config/src/thrift/com/twitter/strato/graphql",
        "strato/config/src/thrift/com/twitter/strato/graphql:api-media-graphql",
        "strato/config/src/thrift/com/twitter/strato/graphql:topics-graphql",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "cr-mitwitterer-thrift-java",
    provides_scala_name = "cr-mitwitterer-thrift-scala",
)

create_thrift_libraries(
    base_name = "cr-mitwitterer-scribe",
    sources = ["*.thrift"],
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "product-mitwitterer/core/src/main/thrift/com/twitter/product_mitwitterer/core:thrift",
        "src/thrift/com/twitter/ads/schema:common",
        "src/thrift/com/twitter/ml/api:data",
        "src/thrift/com/twitter/recos:recos-common",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift",
        "src/thrift/com/twitter/timelines/render:thrift",
        "strato/config/src/thrift/com/twitter/strato/graphql",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "cr-mitwitterer-scribe-java",
    provides_scala_name = "cr-mitwitterer-scribe-scala",
)
