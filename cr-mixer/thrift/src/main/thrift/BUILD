create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "product-mixer/core/src/main/thrift/com/ExTwitter/product_mixer/core:thrift",
        "src/thrift/com/ExTwitter/ads/schema:common",
        "src/thrift/com/ExTwitter/ml/api:data",
        "src/thrift/com/ExTwitter/recos:recos-common",
        "src/thrift/com/ExTwitter/simclusters_v2:simclusters_v2-thrift",
        "src/thrift/com/ExTwitter/timelines/render:thrift",
        "strato/config/src/thrift/com/ExTwitter/strato/graphql",
        "strato/config/src/thrift/com/ExTwitter/strato/graphql:api-media-graphql",
        "strato/config/src/thrift/com/ExTwitter/strato/graphql:topics-graphql",
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
        "product-mixer/core/src/main/thrift/com/ExTwitter/product_mixer/core:thrift",
        "src/thrift/com/ExTwitter/ads/schema:common",
        "src/thrift/com/ExTwitter/ml/api:data",
        "src/thrift/com/ExTwitter/recos:recos-common",
        "src/thrift/com/ExTwitter/simclusters_v2:simclusters_v2-thrift",
        "src/thrift/com/ExTwitter/timelines/render:thrift",
        "strato/config/src/thrift/com/ExTwitter/strato/graphql",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "cr-mixer-scribe-java",
    provides_scala_name = "cr-mixer-scribe-scala",
)
