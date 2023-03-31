create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "product-mixer/core/src/main/thrift/com/twitter/product_mixer/core:thrift",
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
    provides_java_name = "cr-mixer-thrift-java",
    provides_scala_name = "cr-mixer-thrift-scala",
)

create_thrift_libraries(
    base_name = "cr-mixer-scribe",
    sources = ["*.thrift"],
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "product-mixer/core/src/main/thrift/com/twitter/product_mixer/core:thrift",
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
    provides_java_name = "cr-mixer-scribe-java",
    provides_scala_name = "cr-mixer-scribe-scala",
)
