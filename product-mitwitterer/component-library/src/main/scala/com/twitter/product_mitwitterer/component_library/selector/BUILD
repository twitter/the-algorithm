scala_library(
    name = "insert_append_results",
    sources = ["InsertAppendResults.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/selector",
    ],
    etwitterports = [
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/common",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/selector",
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
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate/ads",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/query/ads",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/selector/sorter",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/configapi",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/selector",
    ],
    etwitterports = [
        ":insert_append_results",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/model/candidate",
        "product-mitwitterer/component-library/src/main/scala/com/twitter/product_mitwitterer/component_library/selector/sorter",
        "product-mitwitterer/core/src/main/scala/com/twitter/product_mitwitterer/core/functional_component/selector",
    ],
)
