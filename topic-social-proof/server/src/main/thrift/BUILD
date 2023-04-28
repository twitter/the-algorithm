create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = [
        "bazel-compatible",
    ],
    dependency_roots = [
        "content-recommender/thrift/src/main/thrift",
        "content-recommender/thrift/src/main/thrift:content-recommender-common",
        "interests-service/thrift/src/main/thrift",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "tsp-thrift-java",
    provides_scala_name = "tsp-thrift-scala",
)
