scala_library(
    name = "common_types",
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/twitter/algebird:bijection",
        "3rdparty/jvm/com/twitter/algebird:core",
        "3rdparty/jvm/com/twitter/algebird:util",
        "3rdparty/jvm/com/twitter/bijection:core",
        "3rdparty/jvm/com/twitter/bijection:json",
        "3rdparty/jvm/com/twitter/bijection:macros",
        "3rdparty/jvm/com/twitter/bijection:netty",
        "3rdparty/jvm/com/twitter/bijection:scrooge",
        "3rdparty/jvm/com/twitter/bijection:thrift",
        "3rdparty/jvm/com/twitter/bijection:util",
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "3rdparty/src/jvm/com/twitter/scalding:date",
        "3rdparty/src/jvm/com/twitter/summingbird:batch",
        "src/java/com/twitter/ml/api:api-base",
        "src/java/com/twitter/ml/api/constant",
        "src/scala/com/twitter/dal/client/dataset",
        "src/scala/com/twitter/ml/api/util:datarecord",
        "src/scala/com/twitter/scalding_internal/dalv2/vkvs",
        "src/scala/com/twitter/scalding_internal/multiformat/format/keyval",
        "src/scala/com/twitter/storehaus_internal/manhattan/config",
        "src/scala/com/twitter/storehaus_internal/offline",
        "src/scala/com/twitter/storehaus_internal/util",
        "src/scala/com/twitter/summingbird_internal/bijection:bijection-implicits",
        "src/scala/com/twitter/summingbird_internal/runner/store_config",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/dal/personal_data:personal_data-scala",
        "src/thrift/com/twitter/ml/api:data-java",
        "timelines/data_processing/ml_util/aggregation_framework/metrics",
        "timelines/data_processing/ml_util/transforms",
        "util/util-core:util-core-util",
    ],
)

target(
    name = "common_online_stores",
    dependencies = [
        "src/scala/com/twitter/storehaus_internal/memcache",
    ],
)

target(
    name = "common_offline_stores",
    dependencies = [
        "src/scala/com/twitter/storehaus_internal/manhattan",
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
