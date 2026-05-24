create_thrift_libraries(
    base_name = "thrift",
    sources = ["**/*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "servo-repo-thrift-java",
    provides_scala_name = "servo-repo-thrift-scala",
)
