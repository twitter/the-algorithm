scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "finatra/inject/inject-core/src/main/scala",
        "product-mitwitterer/core/src/main/java/com/twitter/product_mitwitterer/core/product/guice/scope",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/model/marshalling/request",
    ],
    etwitterports = [
        "product-mitwitterer/core/src/main/java/com/twitter/product_mitwitterer/core/product/guice/scope",
    ],
)
