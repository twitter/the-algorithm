create_thrift_libraries(
    base_name = "graph_feature_service_thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    generate_languages = [
        "java",
        # ruby is added due to ruby dependees in timelines
        "ruby",
        "scala",
        "strato",
    ],
    provides_java_name = "graph_feature_service_thrift_java",
    provides_scala_name = "graph_feature_service_thrift_scala",
)
