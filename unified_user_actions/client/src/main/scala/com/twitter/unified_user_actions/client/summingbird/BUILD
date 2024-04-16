scala_l brary(
    s ces = [
        "Un f edUserAct onsS ceScrooge.scala",
    ],
    comp ler_opt on_sets = ["fatal_warn ngs"],
    #   runt    s us ng Java 11, but for compat b l y w h ot r  nternal l brar es that
    # are st ll on Java 8,  'll make   target platform to be Java 8 as  ll unt l everyone can
    # m grate.
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/src/jvm/com/tw ter/summ ngb rd:core",
        "3rdparty/src/jvm/com/tw ter/summ ngb rd:storm",
        "3rdparty/src/jvm/com/tw ter/tor nta:core",
        "src/scala/com/tw ter/summ ngb rd_ nternal/s ces/common",
        "src/scala/com/tw ter/tor nta_ nternal/sc  ",
        "src/scala/com/tw ter/tor nta_ nternal/spout:kafka2",
        "un f ed_user_act ons/cl ent/src/ma n/scala/com/tw ter/un f ed_user_act ons/cl ent/conf g",
        "un f ed_user_act ons/thr ft/src/ma n/thr ft/com/tw ter/un f ed_user_act ons:un f ed_user_act ons-scala",
    ],
)
