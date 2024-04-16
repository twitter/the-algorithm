create_datarecord_datasets(
    base_na  = "earlyb rd_recap_data_records",
    platform = "java8",
    role = "t  l nes",
    seg nt_type = "part  oned",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
    ],
)

create_datarecord_datasets(
    base_na  = "earlyb rd_rect et_data_records",
    platform = "java8",
    role = "t  l nes",
    seg nt_type = "part  oned",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
    ],
)

scala_l brary(
    na  = "tra n ng_data_generat on",
    s ces = ["*.scala"],
    platform = "java8",
    str ct_deps = True,
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
    ],
    dependenc es = [
        ":earlyb rd_recap_data_records-java",
        ":earlyb rd_rect et_data_records-java",
        "3rdparty/jvm/com/ bm/ cu: cu4j",
        "3rdparty/src/jvm/com/tw ter/scald ng:json",
        "src/java/com/tw ter/ml/ap :ap -base",
        "src/java/com/tw ter/ml/ap /constant",
        "src/java/com/tw ter/ml/ap /matc r",
        "src/java/com/tw ter/search/common/features",
        "src/scala/com/tw ter/ml/ap :ap -base",
        "src/scala/com/tw ter/ml/ap /analyt cs",
        "src/scala/com/tw ter/ml/ap /ut l",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2/dataset",
        "src/scala/com/tw ter/scald ng_ nternal/job",
        "src/scala/com/tw ter/scald ng_ nternal/job/analyt cs_batch",
        "src/scala/com/tw ter/t  l nes/pred ct on/features/common",
        "src/scala/com/tw ter/t  l nes/pred ct on/features/recap",
        "src/thr ft/com/tw ter/ml/ap :data-java",
        "src/thr ft/com/tw ter/ml/ap :dataset-analyt cs-java",
        "t  l nes/data_process ng/ad_hoc/earlyb rd_rank ng/common",
        "t  l nes/data_process ng/ad_hoc/recap/dataset_ut ls",
        "t  l nes/data_process ng/ad_hoc/recap/offl ne_execut on",
        "t  l nes/data_process ng/ut l/execut on",
    ],
)

hadoop_b nary(
    na  = "b n",
    basena  = "earlyb rd_tra n ng_data_generat on-deploy",
    ma n = "com.tw ter.scald ng.Tool",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":tra n ng_data_generat on",
    ],
)

hadoop_b nary(
    na  = "earlyb rd_tra n ng_data_generat on_prod",
    basena  = "earlyb rd_tra n ng_data_generat on_prod-deploy",
    ma n = "com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.tra n ng_data_generat on.Earlyb rdTra n ngDataProdJob",
    platform = "java8",
    runt  _platform = "java8",
    tags = [
        "bazel-compat ble",
        "bazel-compat ble:m grated",
        "bazel-only",
    ],
    dependenc es = [
        ":tra n ng_data_generat on",
    ],
)
