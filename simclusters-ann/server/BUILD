jvm_b nary(
    na  = "b n",
    basena  = "s mclusters-ann",
    ma n = "com.tw ter.s mclustersann.S mClustersAnnServerMa n",
    runt  _platform = "java11",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "f nagle/f nagle-z pk n-scr be/src/ma n/scala",
        "f natra/ nject/ nject-logback/src/ma n/scala",
        "loglens/loglens-logback/src/ma n/scala/com/tw ter/loglens/logback",
        "s mclusters-ann/server/src/ma n/scala/com/tw ter/s mclustersann",
        "tw ter-server- nternal/src/ma n/scala",
        "tw ter-server/logback-class c/src/ma n/scala",
    ],
)

#  Aurora Workflows bu ld phase convent on requ res a jvm_app na d w h ${project-na }-app
jvm_app(
    na  = "s mclusters-ann-app",
    arch ve = "z p",
    b nary = ":b n",
    tags = ["bazel-compat ble"],
)
