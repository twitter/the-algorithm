scala_l brary(
    na  = "common",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/common",
    ],
)

scala_l brary(
    na  = "stores",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/stores",
    ],
)

scala_l brary(
    na  = " bserv ce",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/ bserv ce",
        "tw ter-server/slf4j-jdk14/src/ma n/scala/com/tw ter/server/logg ng",
    ],
)

 ron_b nary(
    na  = "t et-s mclusters-storm-b nary",
    ma n = "com.tw ter.s mclusters_v2.summ ngb rd.storm.T etJobRunner",
    platform = "java8",
    runt  _platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/storm",
    ],
)

jvm_app(
    na  = "t et-s mclusters-storm-job",
    b nary = ":t et-s mclusters-storm-b nary",
    bundles = [
        bundle(
            f leset = ["conf g/jaas.conf"],
        ),
    ],
    tags = ["bazel-compat ble"],
)

 ron_b nary(
    na  = "pers stent-t et-s mclusters-storm-b nary",
    ma n = "com.tw ter.s mclusters_v2.summ ngb rd.storm.Pers stentT etJobRunner",
    platform = "java8",
    runt  _platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/storm",
    ],
)

jvm_app(
    na  = "pers stent-t et-s mclusters-storm-job",
    b nary = ":pers stent-t et-s mclusters-storm-b nary",
    bundles = [
        bundle(
            f leset = ["conf g/jaas.conf"],
        ),
    ],
    tags = ["bazel-compat ble"],
)

 ron_b nary(
    na  = "mult -model-t et-s mclusters-storm-b nary",
    ma n = "com.tw ter.s mclusters_v2.summ ngb rd.storm.Mult ModelT etJobRunner",
    platform = "java8",
    runt  _platform = "java8",
    dependenc es = [
        ":common",
        "3rdparty/jvm/org/slf4j:slf4j-jdk14",
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/storm",
    ],
)

jvm_app(
    na  = "mult -model-t et-s mclusters-storm-job",
    b nary = ":mult -model-t et-s mclusters-storm-b nary",
    bundles = [
        bundle(
            f leset = ["conf g/jaas.conf"],
        ),
    ],
)

jvm_b nary(
    na  = "repl",
    basena  = "repl-s mclusters_v2",
    ma n = "scala.tools.nsc.Ma nGener cRunner",
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":common",
        "3rdparty/jvm/org/scala-lang:scala-comp ler",
    ],
)

target(
    dependenc es = [
        ":common",
        ":repl",
        ":stores",
        ": bserv ce",
        "src/scala/com/tw ter/s mclusters_v2/summ ngb rd/storm",
    ],
)
