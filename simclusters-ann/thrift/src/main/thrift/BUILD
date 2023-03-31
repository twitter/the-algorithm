create_thrift_libraries(
    base_name = "thrift",
    sources = ["**/*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "finatra-internal/thrift/src/main/thrift",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift",
    ],
    generate_languages = [
        "java",
        "scala",
    ],
    provides_java_name = "simclusters-ann-thrift-java",
    provides_scala_name = "simclusters-ann-thrift-scala",
)
