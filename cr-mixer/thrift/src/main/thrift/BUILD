create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "product-mixer/core/src/main/thrift/com/X/product_mixer/core:thrift",
        "src/thrift/com/X/ads/schema:common",
        "src/thrift/com/X/ml/api:data",
        "src/thrift/com/X/recos:recos-common",
        "src/thrift/com/X/simclusters_v2:simclusters_v2-thrift",
        "src/thrift/com/X/timelines/render:thrift",
        "strato/config/src/thrift/com/X/strato/graphql",
        "strato/config/src/thrift/com/X/strato/graphql:api-media-graphql",
        "strato/config/src/thrift/com/X/strato/graphql:topics-graphql",
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
        "product-mixer/core/src/main/thrift/com/X/product_mixer/core:thrift",
        "src/thrift/com/X/ads/schema:common",
        "src/thrift/com/X/ml/api:data",
        "src/thrift/com/X/recos:recos-common",
        "src/thrift/com/X/simclusters_v2:simclusters_v2-thrift",
        "src/thrift/com/X/timelines/render:thrift",
        "strato/config/src/thrift/com/X/strato/graphql",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "cr-mixer-scribe-java",
    provides_scala_name = "cr-mixer-scribe-scala",
)
