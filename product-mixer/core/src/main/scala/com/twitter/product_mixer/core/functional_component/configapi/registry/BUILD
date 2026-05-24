scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "configapi/configapi-core",
        "configapi/configapi-decider",
        "servo/decider",
        "util/util-core:util-core-util",
        "util/util-logging",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "configapi/configapi-core",
        "configapi/configapi-decider",
        "servo/decider",
        "util/util-core:util-core-util",
        "util/util-logging",
    ],
)
