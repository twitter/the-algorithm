create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/ExTwitter/ads/adserver:adserver_common",
        "src/thrift/com/ExTwitter/ml/api:data",
        "src/thrift/com/ExTwitter/suggests/controller_data",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "follow-recommendations-logging-java",
    provides_scala_name = "follow-recommendations-logging-scala",
)
