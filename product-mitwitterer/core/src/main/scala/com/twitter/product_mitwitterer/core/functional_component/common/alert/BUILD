scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fastertwitterml/jackson/core:jackson-annotations",
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "3rdparty/jvm/javatwitter/mail",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/common/alert/predicate",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "util/util-core:scala",
    ],
    etwitterports = [
        "3rdparty/jvm/javatwitter/inject:javatwitter.inject",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/common/alert/predicate",
        "strato/src/main/scala/com/twitter/strato/catalog",
        "util/util-core:scala",
    ],
)
