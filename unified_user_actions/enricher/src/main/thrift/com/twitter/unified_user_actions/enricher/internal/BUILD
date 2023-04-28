create_thrift_libraries(
    org = "com.twitter.unified_user_actions.enricher",
    base_name = "internal",
    sources = ["*.thrift"],
    tags = ["bazel-compatible"],
    dependency_roots = [
        "src/thrift/com/twitter/clientapp/gen:clientapp",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions",
    ],
    generate_languages = [
        "java",
        "scala",
    ],
    provides_java_name = "enricher_internal-thrift-java",
    provides_scala_name = "enricher_internal-thrift-scala",
)
