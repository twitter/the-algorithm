create_thrift_libraries(
    org = "com.twitter",
    base_name = "unified_user_actions",
    sources = ["*.thrift"],
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/twitter/clientapp/gen:clientapp",
        "src/thrift/com/twitter/gizmoduck:thrift",
        "src/thrift/com/twitter/gizmoduck:user-thrift",
        "src/thrift/com/twitter/search/common:constants",
        "src/thrift/com/twitter/socialgraph:thrift",
    ],
    generate_languages = [
        "java",
        "scala",
        "strato",
    ],
    provides_java_name = "unified_user_actions-thrift-java",
    provides_scala_name = "unified_user_actions-thrift-scala",
)
