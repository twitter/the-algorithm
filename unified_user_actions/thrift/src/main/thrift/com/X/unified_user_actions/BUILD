create_thrift_libraries(
    org = "com.X",
    base_name = "unified_user_actions",
    sources = ["*.thrift"],
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/X/clientapp/gen:clientapp",
        "src/thrift/com/X/gizmoduck:thrift",
        "src/thrift/com/X/gizmoduck:user-thrift",
        "src/thrift/com/X/search/common:constants",
        "src/thrift/com/X/socialgraph:thrift",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "unified_user_actions-thrift-java",
    provides_scala_name = "unified_user_actions-thrift-scala",
)
