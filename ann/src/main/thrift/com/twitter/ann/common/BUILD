create_thrift_libraries(
    base_name = "ann-common",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "mediaservices/commons/src/main/thrift",
        "src/thrift/com/twitter/ml/api:embedding",
    ],
    generate_languages = [
        "java",
        "python",
        "scala",
        "strato",
    ],
    provides_java_name = "ann-common-thrift-java",
    provides_scala_name = "ann-common-thrift-scala",
)
