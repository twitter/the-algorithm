scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "finatra-internal/tfe",
        "passbird/bitfields-thrift/src/main/thrift:thrift-scala",
        "src/scala/com/twitter/common/ip_address_utils",
        "src/thrift/com/twitter/context:feature-context-scala",
        "src/thrift/com/twitter/context:twitter-context-scala",
        "src/thrift/com/twitter/ip_address_utils:ip-address-utils-thrift-scala",
        "src/thrift/com/twitter/spam/rtf:safety-level-scala",
        "strato/src/main/scala/com/twitter/strato/access",
        "strato/src/main/scala/com/twitter/strato/config",
        "strato/src/main/scala/com/twitter/strato/context",
        "strato/src/main/scala/com/twitter/strato/data",
        "strato/src/main/scala/com/twitter/strato/opcontext",
        "strato/src/main/scala/com/twitter/strato/response",
        "strato/src/main/scala/com/twitter/strato/thrift",
        "strato/src/main/thrift/com/twitter/strato/context:thrift-scala",
        "tweetypie/server/src/main/scala/com/twitter/tweetypie",
        "twitter-context/src/main/scala",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/common",
    ],
)
