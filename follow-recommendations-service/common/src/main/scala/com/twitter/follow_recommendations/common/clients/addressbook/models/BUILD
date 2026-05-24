scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "addressbook/thrift/src/thrift/com/twitter/addressbook:thrift-scala",
        "addressbook/thrift/src/thrift/com/twitter/addressbook/datatypes:thrift-scala",
    ],
)
