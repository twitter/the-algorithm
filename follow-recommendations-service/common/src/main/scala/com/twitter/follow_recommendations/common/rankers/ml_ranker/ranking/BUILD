scala_l brary(
    comp ler_opt on_sets = ["fatal_warn ngs"],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "3rdparty/jvm/com/google/ nject:gu ce",
        "3rdparty/jvm/com/google/ nject/extens ons:gu ce-ass sted nject",
        "3rdparty/jvm/net/cod ng ll:scala-gu ce",
        "3rdparty/jvm/org/slf4j:slf4j-ap ",
        "f natra/ nject/ nject-core/src/ma n/scala",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/base",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/cl ents/deepb rdv2",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/constants",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/feature_hydrat on/s ces",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/models",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/rankers/common",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/rankers/ml_ranker/scor ng",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/rankers/ut ls",
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/ut ls",
        "src/java/com/tw ter/ml/ap :ap -base",
        "ut l/ut l-slf4j-ap /src/ma n/scala",
    ],
)

# T   s to  mport only t  params from MlRanker, for  nstance to get request-level  avy ranker.
scala_l brary(
    na  = "ml_ranker_params",
    s ces = [
        "MlRankerParams.scala",
    ],
    platform = "java8",
    tags = ["bazel-compat ble"],
    dependenc es = [
        "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/rankers/common",
        "t  l nes/src/ma n/scala/com/tw ter/t  l nes/conf g/conf gap ",
    ],
)
