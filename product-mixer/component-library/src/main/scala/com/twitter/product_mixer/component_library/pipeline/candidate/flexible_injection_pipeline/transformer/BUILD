scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    scalac_plugins = ["no-roomba"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "onboarding/service/thrift/src/main/thrift:thrift-scala",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/candidate_source/flexible_injection_pipeline",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/candidate",
    ],
    exports = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "onboarding/service/thrift/src/main/thrift:thrift-scala",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/candidate_source/flexible_injection_pipeline",
        "product-mixer/component-library/src/main/scala/com/twitter/product_mixer/component_library/model/candidate",
        "product-mixer/core/src/main/scala/com/twitter/product_mixer/core/pipeline/candidate",
    ],
)
