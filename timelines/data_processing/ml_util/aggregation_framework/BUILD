scala_l brary(
    na  = "common_types",
    s ces = ["*.scala"],
    platform = "java8",
    str ct_deps = True,
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/jvm/com/google/guava",
        "3rdparty/jvm/com/tw ter/algeb rd:b ject on",
        "3rdparty/jvm/com/tw ter/algeb rd:core",
        "3rdparty/jvm/com/tw ter/algeb rd:ut l",
        "3rdparty/jvm/com/tw ter/b ject on:core",
        "3rdparty/jvm/com/tw ter/b ject on:json",
        "3rdparty/jvm/com/tw ter/b ject on:macros",
        "3rdparty/jvm/com/tw ter/b ject on:netty",
        "3rdparty/jvm/com/tw ter/b ject on:scrooge",
        "3rdparty/jvm/com/tw ter/b ject on:thr ft",
        "3rdparty/jvm/com/tw ter/b ject on:ut l",
        "3rdparty/jvm/org/apac /thr ft:l bthr ft",
        "3rdparty/src/jvm/com/tw ter/scald ng:date",
        "3rdparty/src/jvm/com/tw ter/summ ngb rd:batch",
        "src/java/com/tw ter/ml/ap :ap -base",
        "src/java/com/tw ter/ml/ap /constant",
        "src/scala/com/tw ter/dal/cl ent/dataset",
        "src/scala/com/tw ter/ml/ap /ut l:datarecord",
        "src/scala/com/tw ter/scald ng_ nternal/dalv2/vkvs",
        "src/scala/com/tw ter/scald ng_ nternal/mult format/format/keyval",
        "src/scala/com/tw ter/storehaus_ nternal/manhattan/conf g",
        "src/scala/com/tw ter/storehaus_ nternal/offl ne",
        "src/scala/com/tw ter/storehaus_ nternal/ut l",
        "src/scala/com/tw ter/summ ngb rd_ nternal/b ject on:b ject on- mpl c s",
        "src/scala/com/tw ter/summ ngb rd_ nternal/runner/store_conf g",
        "src/thr ft/com/tw ter/dal/personal_data:personal_data-java",
        "src/thr ft/com/tw ter/dal/personal_data:personal_data-scala",
        "src/thr ft/com/tw ter/ml/ap :data-java",
        "t  l nes/data_process ng/ml_ut l/aggregat on_fra work/ tr cs",
        "t  l nes/data_process ng/ml_ut l/transforms",
        "ut l/ut l-core:ut l-core-ut l",
    ],
)

target(
    na  = "common_onl ne_stores",
    dependenc es = [
        "src/scala/com/tw ter/storehaus_ nternal/ mcac ",
    ],
)

target(
    na  = "common_offl ne_stores",
    dependenc es = [
        "src/scala/com/tw ter/storehaus_ nternal/manhattan",
    ],
)

target(
    na  = "user_job",
    dependenc es = [
        "t  l nes/data_process ng/ml_ut l/aggregat on_fra work/job",
    ],
)

target(
    na  = "scald ng",
    dependenc es = [
        "t  l nes/data_process ng/ml_ut l/aggregat on_fra work/scald ng",
    ],
)

target(
    na  = "convers on",
    dependenc es = [
        "t  l nes/data_process ng/ml_ut l/aggregat on_fra work/convers on",
    ],
)

target(
    na  = "query",
    dependenc es = [
        "t  l nes/data_process ng/ml_ut l/aggregat on_fra work/query",
    ],
)

target(
    na  = " ron",
    dependenc es = [
        "t  l nes/data_process ng/ml_ut l/aggregat on_fra work/ ron",
    ],
)

target(
    dependenc es = [
        ":common_offl ne_stores",
        ":common_onl ne_stores",
        ":common_types",
        ":convers on",
        ": ron",
        ":query",
        ":scald ng",
    ],
)
