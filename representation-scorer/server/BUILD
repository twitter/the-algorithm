jvm_b nary(
    na  = "b n",
    basena  = "representat on-scorer",
    ma n = "com.tw ter.representat onscorer.Representat onScorerFedServerMa n",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "f natra/ nject/ nject-logback/src/ma n/scala",
        "loglens/loglens-logback/src/ma n/scala/com/tw ter/loglens/logback",
        "representat on-scorer/server/src/ma n/res ces",
        "representat on-scorer/server/src/ma n/scala/com/tw ter/representat onscorer",
        "tw ter-server/logback-class c/src/ma n/scala",
    ],
)

#  Aurora Workflows bu ld phase convent on requ res a jvm_app na d w h ${project-na }-app
jvm_app(
    na  = "representat on-scorer-app",
    arch ve = "z p",
    b nary = ":b n",
    tags = ["bazel-compat ble"],
)
