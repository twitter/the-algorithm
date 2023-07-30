scala_library(
    name = "user_session_inj",
    sources = ["UserSessionInjection.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/bijection:scrooge",
        "src/scala/com/X/scalding_internal/multiformat/format",
        "src/thrift/com/X/user_session_store:thrift-scala",
    ],
)

scala_library(
    name = "edge_list_injection",
    sources = ["EdgeListInjection.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/bijection:scrooge",
        "src/scala/com/X/scalding_internal/multiformat/format",
        "src/thrift/com/X/interaction_graph:interaction_graph-scala",
    ],
)
