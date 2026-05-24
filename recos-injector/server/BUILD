target(
    name = "server",
    tags = ["bazel-compatible"],
    dependencies = [
        "recos-injector/server/src/main/scala/com/twitter/recosinjector",
    ],
)

test_suite(
    name = "tests",
    tags = ["bazel-compatible"],
    dependencies = [
        "recos-injector/server/src/test/scala/com/twitter/recosinjector",
    ],
)

jvm_binary(
    name = "bin",
    basename = "recos-injector-server",
    main = "com.twitter.recosinjector.Main",
    platform = "java11",
    runtime_platform = "java11",
    tags = [
        "bazel-compatible:migrated",
    ],
    dependencies = [
        ":server",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
    ],
)

jvm_app(
    name = "bundle",
    basename = "recos-injector",
    binary = ":bin",
    bundles = [bundle(
        fileset = ["config/*"],
        owning_target = "recos-injector/server/config:files",
    )],
    tags = [
        "bazel-compatible:migrated",
    ],
)
