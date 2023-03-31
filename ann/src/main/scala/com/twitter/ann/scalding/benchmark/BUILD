scala_library(
    name = "benchmark",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":user_item_knn-scala",
        "3rdparty/src/jvm/com/twitter/scalding:args",
        "3rdparty/src/jvm/com/twitter/scalding:core",
        "3rdparty/src/jvm/com/twitter/scalding:date",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/scala/com/twitter/ann/scalding/offline",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/multiformat/format",
    ],
)

hadoop_binary(
    name = "benchmark-adhoc",
    main = "com.twitter.scalding.Tool",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":benchmark",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
    ],
)

create_datasets(
    base_name = "user_item_knn",
    description = "List of the top recommendations per search entity (user)",
    java_schema = "com.twitter.ann.knn.thriftjava.Knn",
    platform = "java8",
    role = "cortex-mlx",
    scala_schema = "com.twitter.ann.knn.thriftscala.Knn",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "ann/src/main/thrift/com/twitter/ann/knn:thrift-java",
    ],
    scala_dependencies = [
        "ann/src/main/thrift/com/twitter/ann/knn:thrift-scala",
    ],
)
