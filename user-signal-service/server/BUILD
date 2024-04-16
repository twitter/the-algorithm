jvm_b nary(
    na  = "b n",
    basena  = "user-s gnal-serv ce",
    ma n = "com.tw ter.users gnalserv ce.UserS gnalServ ceStratoFedServerMa n",
    runt  _platform = "java11",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/jvm/ch/qos/logback:logback-class c",
        "loglens/loglens-logback/src/ma n/scala/com/tw ter/loglens/logback",
        "strato/src/ma n/scala/com/tw ter/strato/logg ng/logback",
        "user-s gnal-serv ce/server/src/ma n/res ces",
        "user-s gnal-serv ce/server/src/ma n/scala/com/tw ter/users gnalserv ce",
    ],
)

#  Aurora Workflows bu ld phase convent on requ res a jvm_app na d w h ${project-na }-app
jvm_app(
    na  = "user-s gnal-serv ce-app",
    arch ve = "z p",
    b nary = ":b n",
)
