create_thrift_libraries(
    base_name = "serialization",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/twitter/ml/api:embedding",
    ],
    generate_languages = [
        "java",
        "scala",
    ],
)
