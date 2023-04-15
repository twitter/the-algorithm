scala_library(
    sources = [
        "UnifiedUserActionsSourceScrooge.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    # Our runtime is using Java 11, but for compatibility with other internal libraries that
    # are still on Java 8, we'll make our target platform to be Java 8 as well until everyone can
    # migrate.
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/src/jvm/com/twitter/summingbird:core",
        "3rdparty/src/jvm/com/twitter/summingbird:storm",
        "3rdparty/src/jvm/com/twitter/tormenta:core",
        "src/scala/com/twitter/summingbird_internal/sources/common",
        "src/scala/com/twitter/tormenta_internal/scheme",
        "src/scala/com/twitter/tormenta_internal/spout:kafka2",
        "unified_user_actions/client/src/main/scala/com/twitter/unified_user_actions/client/config",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
    ],
)
