create_thrift_libraries(
    base_name = "thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = ["src/thrift/com/twitter/ml/featurestore:ml-feature-store"],
    generate_languages = [
        "java",
        "scala",
    ],
)
