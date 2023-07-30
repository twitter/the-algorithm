scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "finatra/inject/inject-thrift-client",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/clients/common",
        "follow-recommendations-service/common/src/main/scala/com/X/follow_recommendations/common/models",
        "phonestorage/server/src/main/thrift/com/X/phonestorage/api:phone-storage-service-scala",
        "stitch/stitch-core",
    ],
)
