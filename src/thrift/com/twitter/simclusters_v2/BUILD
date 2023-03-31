create_thrift_libraries(
    base_name = "simclusters_v2-thrift",
    sources = ["*.thrift"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/twitter/algebird_internal",
    ],
    export_roots = [
        "src/thrift/com/twitter/algebird_internal:algebird_internal",
    ],
    generate_languages = [
        "go",
        "java",
        "lua",
        "python",
        "ruby",
        "scala",
        "strato",
    ],
    provides_java_name = "simclusters_v2-thrift-java",
    provides_scala_name = "simclusters_v2-thrift-scala",
)
