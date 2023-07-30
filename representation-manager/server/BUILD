jvm_binary(
    name = "bin",
    basename = "representation-manager",
    main = "com.X.representation_manager.RepresentationManagerFedServerMain",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-logback/src/main/scala",
        "loglens/loglens-logback/src/main/scala/com/X/loglens/logback",
        "representation-manager/server/src/main/resources",
        "representation-manager/server/src/main/scala/com/X/representation_manager",
        "X-server/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "representation-manager-app",
    archive = "zip",
    binary = ":bin",
)
