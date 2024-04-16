package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ml.ap .constant.SharedFeatures.AUTHOR_ D
 mport com.tw ter.ml.ap .constant.SharedFeatures.USER_ D
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.DownsampleTransform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.R chRemoveAuthor dZero
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.R chRemoveUser dZero
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.engage nt_features.Engage ntDataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features.engage nt_features.Engage ntDataRecordFeatures.R chUn fyPubl cEngagersTransform
 mport com.tw ter.t  l nes.pred ct on.features.l st_features.L stFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.t  l nes.pred ct on.features.request_context.RequestContextFeatures
 mport com.tw ter.t  l nes.pred ct on.features.semant c_core_features.Semant cCoreFeatures
 mport com.tw ter.t  l nes.pred ct on.transform.f lter.F lter nNetworkTransform
 mport com.tw ter.t  l nes.pred ct on.transform.f lter.F lter mageT etTransform
 mport com.tw ter.t  l nes.pred ct on.transform.f lter.F lterV deoT etTransform
 mport com.tw ter.t  l nes.pred ct on.transform.f lter.F lterOut mageV deoT etTransform
 mport com.tw ter.ut l.Durat on

tra  T  l nesAggregat onConf gDeta ls extends Ser al zable {

   mport T  l nesAggregat onS ces._

  def outputHdfsPath: Str ng

  /**
   * Converts t  g ven log cal store to a phys cal store. T  reason   do not spec fy t 
   * phys cal store d rectly w h t  [[AggregateGroup]]  s because of a cycl c dependency w n
   * create phys cal stores that are DalDataset w h PersonalDataType annotat ons der ved from
   * t  [[AggregateGroup]].
   *
   */
  def mkPhys calStore(store: AggregateStore): AggregateStore

  def defaultMaxKvS ceFa lures:  nt = 100

  val t  l nesOffl neAggregateS nk = new Offl neStoreCommonConf g {
    overr de def apply(startDate: Str ng) = Offl neAggregateStoreCommonConf g(
      outputHdfsPathPref x = outputHdfsPath,
      dum App d = "t  l nes_aggregates_v2_ro",
      dum DatasetPref x = "t  l nes_aggregates_v2_ro",
      startDate = startDate
    )
  }

  val UserAggregateStore = "user_aggregates"
  val UserAuthorAggregateStore = "user_author_aggregates"
  val UserOr g nalAuthorAggregateStore = "user_or g nal_author_aggregates"
  val Or g nalAuthorAggregateStore = "or g nal_author_aggregates"
  val UserEngagerAggregateStore = "user_engager_aggregates"
  val User nt onAggregateStore = "user_ nt on_aggregates"
  val Tw terW deUserAggregateStore = "tw ter_w de_user_aggregates"
  val Tw terW deUserAuthorAggregateStore = "tw ter_w de_user_author_aggregates"
  val UserRequestH AggregateStore = "user_request_h _aggregates"
  val UserRequestDowAggregateStore = "user_request_dow_aggregates"
  val UserL stAggregateStore = "user_l st_aggregates"
  val AuthorTop cAggregateStore = "author_top c_aggregates"
  val UserTop cAggregateStore = "user_top c_aggregates"
  val User nferredTop cAggregateStore = "user_ nferred_top c_aggregates"
  val User d aUnderstand ngAnnotat onAggregateStore =
    "user_ d a_understand ng_annotat on_aggregates"
  val AuthorCountryCodeAggregateStore = "author_country_code_aggregates"
  val Or g nalAuthorCountryCodeAggregateStore = "or g nal_author_country_code_aggregates"

  /**
   * Step 3: Conf gure all aggregates to compute.
   * Note that d fferent subsets of aggregates  n t  l st
   * can be launc d by d fferent summ ngb rd job  nstances.
   * Any g ven job can be respons ble for a set of AggregateGroup
   * conf gs whose outputStores share t  sa  exact startDate.
   * AggregateGroups that do not share t  sa   nputS ce,
   * outputStore or startDate MUST be launc d us ng d fferent
   * summ ngb rd jobs and passed  n a d fferent --start-t   argu nt
   * See sc ence/scald ng/ sos/t  l nes/prod.yaml for an example
   * of how to conf gure y  own job.
   */
  val negat veDownsampleTransform =
    DownsampleTransform(
      negat veSampl ngRate = 0.03,
      keepLabels = RecapUserFeatureAggregat on.LabelsV2)
  val negat veRecT etDownsampleTransform = DownsampleTransform(
    negat veSampl ngRate = 0.03,
    keepLabels = Rect etUserFeatureAggregat on.Rect etLabelsForAggregat on
  )

  val userAggregatesV2: AggregateGroup =
    AggregateGroup(
       nputS ce = t  l nesDa lyRecapM n malS ce,
      aggregatePref x = "user_aggregate_v2",
      preTransforms = Seq(R chRemoveUser dZero), /* El m nates reducer skew */
      keys = Set(USER_ D),
      features = RecapUserFeatureAggregat on.UserFeaturesV2,
      labels = RecapUserFeatureAggregat on.LabelsV2,
       tr cs = Set(Count tr c, Sum tr c),
      halfL ves = Set(50.days),
      outputStore = mkPhys calStore(
        Offl neAggregateDataRecordStore(
          na  = UserAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConf g = t  l nesOffl neAggregateS nk,
          maxKvS ceFa lures = defaultMaxKvS ceFa lures
        ))
    )

  val userAuthorAggregatesV2: Set[AggregateGroup] = {

    /**
     * NOTE:   need to remove records from out-of-network authors from t  recap  nput
     * records (wh ch now  nclude out-of-network records as  ll after  rg ng recap and
     * rect et models) that are used to compute user-author aggregates. T   s necessary
     * to l m  t  growth rate of user-author aggregates.
     */
    val allFeatureAggregates = Set(
      AggregateGroup(
         nputS ce = t  l nesDa lyRecapM n malS ce,
        aggregatePref x = "user_author_aggregate_v2",
        preTransforms = Seq(F lter nNetworkTransform, R chRemoveUser dZero),
        keys = Set(USER_ D, AUTHOR_ D),
        features = RecapUserFeatureAggregat on.UserAuthorFeaturesV2,
        labels = RecapUserFeatureAggregat on.LabelsV2,
         tr cs = Set(Sum tr c),
        halfL ves = Set(50.days),
        outputStore = mkPhys calStore(
          Offl neAggregateDataRecordStore(
            na  = UserAuthorAggregateStore,
            startDate = "2016-07-15 00:00",
            commonConf g = t  l nesOffl neAggregateS nk,
            maxKvS ceFa lures = defaultMaxKvS ceFa lures
          ))
      )
    )

    val countAggregates: Set[AggregateGroup] = Set(
      AggregateGroup(
         nputS ce = t  l nesDa lyRecapM n malS ce,
        aggregatePref x = "user_author_aggregate_v2",
        preTransforms = Seq(F lter nNetworkTransform, R chRemoveUser dZero),
        keys = Set(USER_ D, AUTHOR_ D),
        features = RecapUserFeatureAggregat on.UserAuthorFeaturesV2Count,
        labels = RecapUserFeatureAggregat on.LabelsV2,
         tr cs = Set(Count tr c),
        halfL ves = Set(50.days),
        outputStore = mkPhys calStore(
          Offl neAggregateDataRecordStore(
            na  = UserAuthorAggregateStore,
            startDate = "2016-07-15 00:00",
            commonConf g = t  l nesOffl neAggregateS nk,
            maxKvS ceFa lures = defaultMaxKvS ceFa lures
          ))
      )
    )

    allFeatureAggregates ++ countAggregates
  }

  val userAggregatesV5Cont nuous: AggregateGroup =
    AggregateGroup(
       nputS ce = t  l nesDa lyRecapM n malS ce,
      aggregatePref x = "user_aggregate_v5.cont nuous",
      preTransforms = Seq(R chRemoveUser dZero),
      keys = Set(USER_ D),
      features = RecapUserFeatureAggregat on.UserFeaturesV5Cont nuous,
      labels = RecapUserFeatureAggregat on.LabelsV2,
       tr cs = Set(Count tr c, Sum tr c, SumSq tr c),
      halfL ves = Set(50.days),
      outputStore = mkPhys calStore(
        Offl neAggregateDataRecordStore(
          na  = UserAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConf g = t  l nesOffl neAggregateS nk,
          maxKvS ceFa lures = defaultMaxKvS ceFa lures
        ))
    )

  val userAuthorAggregatesV5: AggregateGroup =
    AggregateGroup(
       nputS ce = t  l nesDa lyRecapM n malS ce,
      aggregatePref x = "user_author_aggregate_v5",
      preTransforms = Seq(F lter nNetworkTransform, R chRemoveUser dZero),
      keys = Set(USER_ D, AUTHOR_ D),
      features = RecapUserFeatureAggregat on.UserAuthorFeaturesV5,
      labels = RecapUserFeatureAggregat on.LabelsV2,
       tr cs = Set(Count tr c),
      halfL ves = Set(50.days),
      outputStore = mkPhys calStore(
        Offl neAggregateDataRecordStore(
          na  = UserAuthorAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConf g = t  l nesOffl neAggregateS nk,
          maxKvS ceFa lures = defaultMaxKvS ceFa lures
        ))
    )

  val t etS ceUserAuthorAggregatesV1: AggregateGroup =
    AggregateGroup(
       nputS ce = t  l nesDa lyRecapM n malS ce,
      aggregatePref x = "user_author_aggregate_t ets ce_v1",
      preTransforms = Seq(F lter nNetworkTransform, R chRemoveUser dZero),
      keys = Set(USER_ D, AUTHOR_ D),
      features = RecapUserFeatureAggregat on.UserAuthorT etS ceFeaturesV1,
      labels = RecapUserFeatureAggregat on.LabelsV2,
       tr cs = Set(Count tr c, Sum tr c),
      halfL ves = Set(50.days),
      outputStore = mkPhys calStore(
        Offl neAggregateDataRecordStore(
          na  = UserAuthorAggregateStore,
          startDate = "2016-07-15 00:00",
          commonConf g = t  l nesOffl neAggregateS nk,
          maxKvS ceFa lures = defaultMaxKvS ceFa lures
        ))
    )

  val userEngagerAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_engager_aggregate",
    keys = Set(USER_ D, Engage ntDataRecordFeatures.Publ cEngage ntUser ds),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserEngagerAggregateStore,
        startDate = "2016-09-02 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
    preTransforms = Seq(
      R chRemoveUser dZero,
      R chUn fyPubl cEngagersTransform
    )
  )

  val user nt onAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    preTransforms = Seq(R chRemoveUser dZero), /* El m nates reducer skew */
    aggregatePref x = "user_ nt on_aggregate",
    keys = Set(USER_ D, RecapFeatures.MENT ONED_SCREEN_NAMES),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = User nt onAggregateStore,
        startDate = "2017-03-01 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
     ncludeAnyLabel = false
  )

  val tw terW deUserAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyTw terW deS ce,
    preTransforms = Seq(R chRemoveUser dZero), /* El m nates reducer skew */
    aggregatePref x = "tw ter_w de_user_aggregate",
    keys = Set(USER_ D),
    features = RecapUserFeatureAggregat on.Tw terW deFeatures,
    labels = RecapUserFeatureAggregat on.Tw terW deLabels,
     tr cs = Set(Count tr c, Sum tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = Tw terW deUserAggregateStore,
        startDate = "2016-12-28 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val tw terW deUserAuthorAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyTw terW deS ce,
    preTransforms = Seq(R chRemoveUser dZero), /* El m nates reducer skew */
    aggregatePref x = "tw ter_w de_user_author_aggregate",
    keys = Set(USER_ D, AUTHOR_ D),
    features = RecapUserFeatureAggregat on.Tw terW deFeatures,
    labels = RecapUserFeatureAggregat on.Tw terW deLabels,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = Tw terW deUserAuthorAggregateStore,
        startDate = "2016-12-28 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
     ncludeAnyLabel = false
  )

  /**
   * User-H OfDay and User-DayOf ek aggregat ons, both for recap and rect et
   */
  val userRequestH Aggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_request_context_aggregate.h ",
    preTransforms = Seq(R chRemoveUser dZero, negat veDownsampleTransform),
    keys = Set(USER_ D, RequestContextFeatures.T MESTAMP_GMT_HOUR),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserRequestH AggregateStore,
        startDate = "2017-08-01 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val userRequestDowAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_request_context_aggregate.dow",
    preTransforms = Seq(R chRemoveUser dZero, negat veDownsampleTransform),
    keys = Set(USER_ D, RequestContextFeatures.T MESTAMP_GMT_DOW),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserRequestDowAggregateStore,
        startDate = "2017-08-01 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val authorTop cAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "author_top c_aggregate",
    preTransforms = Seq(R chRemoveUser dZero),
    keys = Set(AUTHOR_ D, T  l nesSharedFeatures.TOP C_ D),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = AuthorTop cAggregateStore,
        startDate = "2020-05-19 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val userTop cAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_top c_aggregate",
    preTransforms = Seq(R chRemoveUser dZero),
    keys = Set(USER_ D, T  l nesSharedFeatures.TOP C_ D),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserTop cAggregateStore,
        startDate = "2020-05-23 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val userTop cAggregatesV2 = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_top c_aggregate_v2",
    preTransforms = Seq(R chRemoveUser dZero),
    keys = Set(USER_ D, T  l nesSharedFeatures.TOP C_ D),
    features = RecapUserFeatureAggregat on.UserTop cFeaturesV2Count,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     ncludeAnyFeature = false,
     ncludeAnyLabel = false,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserTop cAggregateStore,
        startDate = "2020-05-23 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val user nferredTop cAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_ nferred_top c_aggregate",
    preTransforms = Seq(R chRemoveUser dZero),
    keys = Set(USER_ D, T  l nesSharedFeatures. NFERRED_TOP C_ DS),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = User nferredTop cAggregateStore,
        startDate = "2020-09-09 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val user nferredTop cAggregatesV2 = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_ nferred_top c_aggregate_v2",
    preTransforms = Seq(R chRemoveUser dZero),
    keys = Set(USER_ D, T  l nesSharedFeatures. NFERRED_TOP C_ DS),
    features = RecapUserFeatureAggregat on.UserTop cFeaturesV2Count,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     ncludeAnyFeature = false,
     ncludeAnyLabel = false,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = User nferredTop cAggregateStore,
        startDate = "2020-09-09 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val userRec procalEngage ntAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_aggregate_v6",
    preTransforms = Seq(R chRemoveUser dZero),
    keys = Set(USER_ D),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.Rec procalLabels,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserAggregateStore,
        startDate = "2016-07-15 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
     ncludeAnyLabel = false
  )

  val userOr g nalAuthorRec procalEngage ntAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_or g nal_author_aggregate_v1",
    preTransforms = Seq(R chRemoveUser dZero, R chRemoveAuthor dZero),
    keys = Set(USER_ D, T  l nesSharedFeatures.OR G NAL_AUTHOR_ D),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.Rec procalLabels,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserOr g nalAuthorAggregateStore,
        startDate = "2018-12-26 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
     ncludeAnyLabel = false
  )

  val or g nalAuthorRec procalEngage ntAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "or g nal_author_aggregate_v1",
    preTransforms = Seq(R chRemoveUser dZero, R chRemoveAuthor dZero),
    keys = Set(T  l nesSharedFeatures.OR G NAL_AUTHOR_ D),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.Rec procalLabels,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = Or g nalAuthorAggregateStore,
        startDate = "2023-02-25 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
     ncludeAnyLabel = false
  )

  val or g nalAuthorNegat veEngage ntAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "or g nal_author_aggregate_v2",
    preTransforms = Seq(R chRemoveUser dZero, R chRemoveAuthor dZero),
    keys = Set(T  l nesSharedFeatures.OR G NAL_AUTHOR_ D),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.Negat veEngage ntLabels,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = Or g nalAuthorAggregateStore,
        startDate = "2023-02-25 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
     ncludeAnyLabel = false
  )

  val userL stAggregates: AggregateGroup =
    AggregateGroup(
       nputS ce = t  l nesDa lyRecapM n malS ce,
      aggregatePref x = "user_l st_aggregate",
      keys = Set(USER_ D, L stFeatures.L ST_ D),
      features = Set.empty,
      labels = RecapUserFeatureAggregat on.LabelsV2,
       tr cs = Set(Count tr c),
      halfL ves = Set(50.days),
      outputStore = mkPhys calStore(
        Offl neAggregateDataRecordStore(
          na  = UserL stAggregateStore,
          startDate = "2020-05-28 00:00",
          commonConf g = t  l nesOffl neAggregateS nk,
          maxKvS ceFa lures = defaultMaxKvS ceFa lures
        )),
      preTransforms = Seq(R chRemoveUser dZero)
    )

  val user d aUnderstand ngAnnotat onAggregates: AggregateGroup = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_ d a_annotat on_aggregate",
    preTransforms = Seq(R chRemoveUser dZero),
    keys =
      Set(USER_ D, Semant cCoreFeatures. d aUnderstand ngH ghRecallNonSens  veEnt y dsFeature),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.LabelsV2,
     tr cs = Set(Count tr c),
    halfL ves = Set(50.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = User d aUnderstand ngAnnotat onAggregateStore,
        startDate = "2021-03-20 00:00",
        commonConf g = t  l nesOffl neAggregateS nk
      ))
  )

  val userAuthorGoodCl ckAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_author_good_cl ck_aggregate",
    preTransforms = Seq(F lter nNetworkTransform, R chRemoveUser dZero),
    keys = Set(USER_ D, AUTHOR_ D),
    features = RecapUserFeatureAggregat on.UserAuthorFeaturesV2,
    labels = RecapUserFeatureAggregat on.GoodCl ckLabels,
     tr cs = Set(Sum tr c),
    halfL ves = Set(14.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserAuthorAggregateStore,
        startDate = "2016-07-15 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      ))
  )

  val userEngagerGoodCl ckAggregates = AggregateGroup(
     nputS ce = t  l nesDa lyRecapM n malS ce,
    aggregatePref x = "user_engager_good_cl ck_aggregate",
    keys = Set(USER_ D, Engage ntDataRecordFeatures.Publ cEngage ntUser ds),
    features = Set.empty,
    labels = RecapUserFeatureAggregat on.GoodCl ckLabels,
     tr cs = Set(Count tr c),
    halfL ves = Set(14.days),
    outputStore = mkPhys calStore(
      Offl neAggregateDataRecordStore(
        na  = UserEngagerAggregateStore,
        startDate = "2016-09-02 00:00",
        commonConf g = t  l nesOffl neAggregateS nk,
        maxKvS ceFa lures = defaultMaxKvS ceFa lures
      )),
    preTransforms = Seq(
      R chRemoveUser dZero,
      R chUn fyPubl cEngagersTransform
    )
  )

}
