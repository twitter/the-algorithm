EXCEPT ON_SOURCES = [
    "com/tw ter/servo/ut l/Effect.scala",
    "com/tw ter/servo/ut l/Except onCounter.scala",
    "com/tw ter/servo/ut l/Gate.scala",
    "com/tw ter/servo/ut l/Throwable lper.scala",
    "com/tw ter/servo/ut l/package.scala",
]

scala_l brary(
    s ces = ["**/*.scala"] + exclude_globs(EXCEPT ON_SOURCES),
    platform = "java8",
    prov des = scala_art fact(
        org = "com.tw ter",
        na  = "servo-ut l",
        repo = art factory,
    ),
    str ct_deps = True,
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":except on",
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/google/ nject:gu ce",
        "f nagle/f nagle-core/src/ma n",
        "f nagle/f nagle-mux/src/ma n/scala",
        "scrooge/scrooge-core",
        "scrooge/scrooge-ser al zer",
        "ut l- nternal/scr be",
        "ut l/ut l-logg ng/src/ma n/scala/com/tw ter/logg ng",
        "ut l/ut l-stats/src/ma n/scala",
    ],
    exports = [
        ":except on",
        "ut l/ut l-logg ng/src/ma n/scala/com/tw ter/logg ng",
    ],
)

scala_l brary(
    na  = "except on",
    s ces = EXCEPT ON_SOURCES,
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    prov des = scala_art fact(
        org = "com.tw ter",
        na  = "servo-ut l-except on",
        repo = art factory,
    ),
    str ct_deps = True,
    tags = ["bazel-compat ble"],
    dependenc es = [
        "ut l/ut l-core:ut l-core-ut l",
        "ut l/ut l-stats/src/ma n/scala",
    ],
)
