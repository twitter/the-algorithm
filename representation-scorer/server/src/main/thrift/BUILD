create_thrift_libraries(
    base_name = "thrift",
    sources = [
        "com/twitter/representationscorer/service.thrift",
    ],
    platform = "java8",
    tags = [
        "bazel-compatible",
    ],
    dependency_roots = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "representationscorer-service-thrift-java",
    provides_scala_name = "representationscorer-service-thrift-scala",
)
