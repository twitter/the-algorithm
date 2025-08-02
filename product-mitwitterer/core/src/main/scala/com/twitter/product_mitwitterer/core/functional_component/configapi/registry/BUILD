scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "configapi/configapi-core",
        "configapi/configapi-decider",
        "servo/decider",
        "util/util-core:util-core-util",
        "util/util-logging",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "configapi/configapi-core",
        "configapi/configapi-decider",
        "servo/decider",
        "util/util-core:util-core-util",
        "util/util-logging",
    ],
)
