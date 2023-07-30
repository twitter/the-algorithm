scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "emailstorage/server/src/main/thrift/com/X/emailstorage/api:email-storage-service-scala",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "finatra/inject/inject-thrift-client",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/clients/common",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/models",
        "stitch/stitch-core",
    ],
)
