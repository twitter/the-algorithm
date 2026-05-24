create_thrift_libraries(
    base_name = "thrift",
    sources = [
        "client_identifier.thrift",
        "service.thrift",
        "signal.thrift",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "uss-thrift-java",
    provides_scala_name = "uss-thrift-scala",
)
