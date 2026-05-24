create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "follow-recommendations-service/thrift/src/main/thrift/logging:thrift",
        "product-mixer/core/src/main/thrift/com/twitter/product_mixer/core:thrift",
        "src/thrift/com/twitter/ads/adserver:adserver_common",
        "src/thrift/com/twitter/ml/api:data",
        "src/thrift/com/twitter/suggests/controller_data",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "follow-recommendations-java",
    provides_scala_name = "follow-recommendations-scala",
)
