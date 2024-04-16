jvm_b nary(
    na  = "b n",
    basena  = "representat on-manager",
    ma n = "com.tw ter.representat on_manager.Representat onManagerFedServerMa n",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "f natra/ nject/ nject-logback/src/ma n/scala",
        "loglens/loglens-logback/src/ma n/scala/com/tw ter/loglens/logback",
        "representat on-manager/server/src/ma n/res ces",
        "representat on-manager/server/src/ma n/scala/com/tw ter/representat on_manager",
        "tw ter-server/logback-class c/src/ma n/scala",
    ],
)

#  Aurora Workflows bu ld phase convent on requ res a jvm_app na d w h ${project-na }-app
jvm_app(
    na  = "representat on-manager-app",
    arch ve = "z p",
    b nary = ":b n",
)
