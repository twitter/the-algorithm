scala_library(
    name = "insert_append_results",
    sources = ["InsertAppendResults.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/selector",
    ],
    exports = [
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/common",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/selector",
    ],
)

scala_library(
    sources = [
        "!InsertAppendResults.scala",
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        ":insert_append_results",
        "3rdparty/jvm/com/google/inject:guice",
        "finatra/inject/inject-slf4j/src/main/scala/com/twitter/inject",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/query/ads",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/selector/sorter",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/configapi",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/selector",
    ],
    exports = [
        ":insert_append_results",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/selector/sorter",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/functional_component/selector",
    ],
)
