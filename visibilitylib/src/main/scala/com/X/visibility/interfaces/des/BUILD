scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/thrift/com/X/spam/rtf:tweet-rtf-event-scala",
        "src/thrift/com/X/tweetypie:events-scala",
        "src/thrift/com/X/tweetypie:tweet-scala",
        "stitch/stitch-core",
        "visibility/common/src/main/scala/com/X/visibility/common",
        "visibility/common/src/main/thrift/com/X/visibility:action-scala",
        "visibility/lib/src/main/resources/config",
        "visibility/lib/src/main/scala/com/X/visibility",
        "visibility/lib/src/main/scala/com/X/visibility/builder/tweets",
        "visibility/lib/src/main/scala/com/X/visibility/builder/users",
        "visibility/lib/src/main/scala/com/X/visibility/features",
        "visibility/lib/src/main/scala/com/X/visibility/interfaces/common/tweets",
    ],
    exports = [
        "visibility/lib/src/main/scala/com/X/visibility",
    ],
)
