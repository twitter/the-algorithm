target(
    na  = "server",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "recos- njector/server/src/ma n/scala/com/tw ter/recos njector",
    ],
)

test_su e(
    na  = "tests",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "recos- njector/server/src/test/scala/com/tw ter/recos njector",
    ],
)

jvm_b nary(
    na  = "b n",
    basena  = "recos- njector-server",
    ma n = "com.tw ter.recos njector.Ma n",
    platform = "java11",
    runt  _platform = "java11",
    tags = [
        "bazel-compat ble:m grated",
    ],
    dependenc es = [
        ":server",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
    ],
)

jvm_app(
    na  = "bundle",
    basena  = "recos- njector",
    b nary = ":b n",
    bundles = [bundle(
        f leset = ["conf g/*"],
        own ng_target = "recos- njector/server/conf g:f les",
    )],
    tags = [
        "bazel-compat ble:m grated",
    ],
)
