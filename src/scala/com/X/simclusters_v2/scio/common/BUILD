scala_library(
    sources = [
        "*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "beam-internal/src/main/scala/com/X/beam/io/dal",
        "beam-internal/src/main/scala/com/X/scio_internal/runner/dataflow",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-follows-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        "iesource/processing/events/src/main/scala/com/X/iesource/processing/events/batch:server_engagements-scala",
        "src/scala/com/X/simclusters_v2/scalding",
        "src/thrift/com/X/twadoop/user/gen:gen-scala",
        "tweetsource/public_tweets/src/main/scala/com/X/tweetsource/public_tweets:public_tweets-scala",
        "usersource/snapshot/src/main/scala/com/X/usersource/snapshot/flat:usersource_flat-scala",
        "usersource/snapshot/src/main/thrift/com/X/usersource/snapshot/flat:flat-scala",
    ],
)
