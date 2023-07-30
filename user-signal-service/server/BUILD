jvm_binary(
    name = "bin",
    basename = "user-signal-service",
    main = "com.X.usersignalservice.UserSignalServiceStratoFedServerMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "loglens/loglens-logback/src/main/scala/com/X/loglens/logback",
        "strato/src/main/scala/com/X/strato/logging/logback",
        "user-signal-service/server/src/main/resources",
        "user-signal-service/server/src/main/scala/com/X/usersignalservice",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "user-signal-service-app",
    archive = "zip",
    binary = ":bin",
)
