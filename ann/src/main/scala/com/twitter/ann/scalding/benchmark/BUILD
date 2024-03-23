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
        "3rdparty/src/jvm/com/ExTwitter/scalding:args",
        "3rdparty/src/jvm/com/ExTwitter/scalding:core",
        "3rdparty/src/jvm/com/ExTwitter/scalding:date",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/scala/com/ExTwitter/ann/scalding/offline",
        "src/scala/com/ExTwitter/scalding_internal/dalv2",
        "src/scala/com/ExTwitter/scalding_internal/job",
        "src/scala/com/ExTwitter/scalding_internal/job/analytics_batch",
        "src/scala/com/ExTwitter/scalding_internal/multiformat/format",
    ],
)

hadoop_binary(
    name = "benchmark-adhoc",
    main = "com.ExTwitter.scalding.Tool",
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
    java_schema = "com.ExTwitter.ann.knn.thriftjava.Knn",
    platform = "java8",
    role = "cortex-mlx",
    scala_schema = "com.ExTwitter.ann.knn.thriftscala.Knn",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "ann/src/main/thrift/com/ExTwitter/ann/knn:thrift-java",
    ],
    scala_dependencies = [
        "ann/src/main/thrift/com/ExTwitter/ann/knn:thrift-scala",
    ],
)
