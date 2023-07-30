scala_library(
    name = "common_types",
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/X/algebird:bijection",
        "3rdparty/jvm/com/X/algebird:core",
        "3rdparty/jvm/com/X/algebird:util",
        "3rdparty/jvm/com/X/bijection:core",
        "3rdparty/jvm/com/X/bijection:json",
        "3rdparty/jvm/com/X/bijection:macros",
        "3rdparty/jvm/com/X/bijection:netty",
        "3rdparty/jvm/com/X/bijection:scrooge",
        "3rdparty/jvm/com/X/bijection:thrift",
        "3rdparty/jvm/com/X/bijection:util",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "3rdparty/src/jvm/com/X/scalding:date",
        "3rdparty/src/jvm/com/X/summingbird:batch",
        "src/java/com/X/ml/api:api-base",
        "src/java/com/X/ml/api/constant",
        "src/scala/com/X/dal/client/dataset",
        "src/scala/com/X/ml/api/util:datarecord",
        "src/scala/com/X/scalding_internal/dalv2/vkvs",
        "src/scala/com/X/scalding_internal/multiformat/format/keyval",
        "src/scala/com/X/storehaus_internal/manhattan/config",
        "src/scala/com/X/storehaus_internal/offline",
        "src/scala/com/X/storehaus_internal/util",
        "src/scala/com/X/summingbird_internal/bijection:bijection-implicits",
        "src/scala/com/X/summingbird_internal/runner/store_config",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/dal/personal_data:personal_data-scala",
        "src/thrift/com/X/ml/api:data-java",
        "timelines/data_processing/ml_util/aggregation_framework/metrics",
        "timelines/data_processing/ml_util/transforms",
        "util/util-core:util-core-util",
    ],
)

target(
    name = "common_online_stores",
    dependencies = [
        "src/scala/com/X/storehaus_internal/memcache",
    ],
)

target(
    name = "common_offline_stores",
    dependencies = [
        "src/scala/com/X/storehaus_internal/manhattan",
    ],
)

target(
    name = "user_job",
    dependencies = [
        "timelines/data_processing/ml_util/aggregation_framework/job",
    ],
)

target(
    name = "scalding",
    dependencies = [
        "timelines/data_processing/ml_util/aggregation_framework/scalding",
    ],
)

target(
    name = "conversion",
    dependencies = [
        "timelines/data_processing/ml_util/aggregation_framework/conversion",
    ],
)

target(
    name = "query",
    dependencies = [
        "timelines/data_processing/ml_util/aggregation_framework/query",
    ],
)

target(
    name = "heron",
    dependencies = [
        "timelines/data_processing/ml_util/aggregation_framework/heron",
    ],
)

target(
    dependencies = [
        ":common_offline_stores",
        ":common_online_stores",
        ":common_types",
        ":conversion",
        ":heron",
        ":query",
        ":scalding",
    ],
)
