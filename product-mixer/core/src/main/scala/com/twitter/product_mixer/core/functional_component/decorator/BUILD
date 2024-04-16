scala_l brary(
    na  = "decorat on",
    s ces = ["Decorat on.scala"],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    str ct_deps = True,
    tags = ["bazel-compat ble"],
    dependenc es = [
        "product-m xer/core/src/ma n/scala/com/tw ter/product_m xer/core/model/common/presentat on",
    ],
    exports = [
        "product-m xer/core/src/ma n/scala/com/tw ter/product_m xer/core/model/common/presentat on",
    ],
)

scala_l brary(
    s ces = [
        "!Decorat on.scala",
        "*.scala",
    ],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    str ct_deps = True,
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":decorat on",
        "3rdparty/jvm/javax/ nject:javax. nject",
        "product-m xer/core/src/ma n/scala/com/tw ter/product_m xer/core/model/common/presentat on",
        "product-m xer/core/src/ma n/scala/com/tw ter/product_m xer/core/p pel ne",
    ],
    exports = [
        ":decorat on",
        "3rdparty/jvm/javax/ nject:javax. nject",
        "product-m xer/core/src/ma n/scala/com/tw ter/product_m xer/core/model/common/presentat on",
        "product-m xer/core/src/ma n/scala/com/tw ter/product_m xer/core/p pel ne",
    ],
)
