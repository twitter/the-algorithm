scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authentication",
        "finagle/finagle-core/src/main",
        "tweetypie/servo/util/src/main/scala",
        "src/thrift/com/twitter/consumer_privacy/mention_controls:thrift-scala",
        "src/thrift/com/twitter/gizmoduck:thrift-scala",
        "src/thrift/com/twitter/spam/rtf:safety-level-scala",
        "src/thrift/com/twitter/spam/rtf:safety-result-scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:tweet-scala",
        "stitch/stitch-compat/src/main/scala/com/twitter/stitch/compat",
        "stitch/stitch-core/src/main/scala/com/twitter/stitch",
        "stitch/stitch-gizmoduck",
        "strato/config/src/thrift/com/twitter/strato/graphql:api-media-graphql-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql:graphql-scala",
        "strato/config/src/thrift/com/twitter/strato/graphql:topics-graphql-scala",
        "strato/src/main/scala/com/twitter/strato/rpc",
        "tweetypie/server/src/main/scala/com/twitter/tweetypie",
        "tweetypie/server/src/main/scala/com/twitter/tweetypie/backends",
        "vibes/src/main/thrift/com/twitter/vibes:vibes-scala",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/common",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/converters/common",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/converters/tweet",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/hydrators",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/mappers",
        "weaverbird/common/src/main/scala/com/twitter/weaverbird/repositories",
    ],
)
