scala_l brary(
    na  = "model_evaluat on",
    s ces = ["*.scala"],
    platform = "java8",
    str ct_deps = False,
    dependenc es = [
        "3rdparty/src/jvm/com/tw ter/scald ng:json",
        "src/scala/com/tw ter/ml/ap :ap -base",
        "src/scala/com/tw ter/ml/ap /pred ct on_eng ne",
        "src/scala/com/tw ter/ml/ap /ut l",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/t  l nes/pred ct on/adapters/recap",
        "src/scala/com/tw ter/t  l nes/pred ct on/features/recap",
        "t  l nes/data_process ng/ad_hoc/earlyb rd_rank ng/common",
        "t  l nes/data_process ng/ut l:r ch-request",
        "t  l nes/data_process ng/ut l/example",
        "t  l nes/data_process ng/ut l/execut on",
        "twadoop_conf g/conf gurat on/log_categor es/group/t  l nes:t  l neserv ce_ nject on_request_log-scala",
    ],
)

hadoop_b nary(
    na  = "b n",
    basena  = "earlyb rd_model_evaluat on-deploy",
    ma n = "com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.model_evaluat on.Earlyb rdModelEvaluat onJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":model_evaluat on",
    ],
)
