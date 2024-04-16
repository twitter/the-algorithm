# W hout t  al as, l brary :follow-recom ndat ons-serv ce_l b would confl ct w h :b n
al as(
    na  = "follow-recom ndat ons-serv ce",
    target = ":follow-recom ndat ons-serv ce_l b",
)

target(
    na  = "follow-recom ndat ons-serv ce_l b",
    dependenc es = [
        "follow-recom ndat ons-serv ce/server/src/ma n/scala/com/tw ter/follow_recom ndat ons",
        "follow-recom ndat ons-serv ce/server/src/ma n/scala/com/tw ter/follow_recom ndat ons/models",
    ],
)

jvm_b nary(
    na  = "b n",
    basena  = "follow-recom ndat ons-serv ce",
    ma n = "com.tw ter.follow_recom ndat ons.FollowRecom ndat onsServ ceThr ftServerMa n",
    runt  _platform = "java11",
    tags = ["bazel-compat ble"],
    dependenc es = [
        ":follow-recom ndat ons-serv ce",
        "3rdparty/jvm/ch/qos/logback:logback-class c",
        "f nagle/f nagle-z pk n-scr be/src/ma n/scala",
        "f natra/ nject/ nject-logback/src/ma n/scala",
        "loglens/loglens-logback/src/ma n/scala/com/tw ter/loglens/logback",
        "tw ter-server- nternal/src/ma n/scala",
        "tw ter-server/logback-class c/src/ma n/scala",
    ],
)

#  Aurora Workflows bu ld phase convent on requ res a jvm_app na d w h ${project-na }-app
jvm_app(
    na  = "follow-recom ndat ons-serv ce-app",
    arch ve = "z p",
    b nary = ":b n",
    bundles = [
        bundle(
            f leset = [
                "server/src/ma n/res ces/*",
                "server/src/ma n/res ces/**/*",
            ],
            own ng_target = "follow-recom ndat ons-serv ce/server/src/ma n/res ces:frs_res ces",
            relat ve_to = "server/src/ma n/res ces",
        ),
    ],
    tags = ["bazel-compat ble"],
)
