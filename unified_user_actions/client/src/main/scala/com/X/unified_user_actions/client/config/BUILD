scala_library(
    sources = [
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    # Our runtime is using Java 11, but for compatibility with other internal libraries that
    # are still on Java 8, we'll make our target platform to be Java 8 as well until everyone can
    # migrate.
    platform = "java8",
    tags = ["bazel-compatible"],
)
