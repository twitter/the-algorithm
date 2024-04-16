package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateGroup
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateS ce
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateStore
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron.Onl neAggregat onConf gTra 
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Count tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Sum tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.B naryUn on
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.DownsampleTransform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms. sNewUserTransform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms. sPos  onTransform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.LogTransform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.Pos  onCase
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.R ch Transform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.R chRemoveUnver f edUserTransform
 mport com.tw ter.t  l nes.pred ct on.features.cl ent_log_event.Cl entLogEventDataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features.common.Comb nedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.common.Comb nedFeatures._
 mport com.tw ter.t  l nes.pred ct on.features.common.Prof leLabelFeatures
 mport com.tw ter.t  l nes.pred ct on.features.common.SearchLabelFeatures
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures. S_TOP_F VE
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures. S_TOP_ONE
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures. S_TOP_TEN
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures.LOG_POS T ON
 mport com.tw ter.t  l nes.pred ct on.features.l st_features.L stFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.ut l.Durat on
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Long => JLong}
 mport scala. o.S ce

object T  l nesOnl neAggregat onUt ls {
  val T etLabels: Set[Feature[JBoolean]] = Comb nedFeatures.Engage ntsRealT  
  val T etCoreLabels: Set[Feature[JBoolean]] = Comb nedFeatures.CoreEngage nts
  val T etD llLabels: Set[Feature[JBoolean]] = Comb nedFeatures.D llEngage nts
  val T etCoreAndD llLabels: Set[Feature[JBoolean]] = T etCoreLabels ++ T etD llLabels
  val Pr vateEngage ntLabelsV2: Set[Feature[JBoolean]] = Comb nedFeatures.Pr vateEngage ntsV2
  val Prof leCoreLabels: Set[Feature[JBoolean]] = Prof leLabelFeatures.CoreEngage nts
  val Prof leNegat veEngage ntLabels: Set[Feature[JBoolean]] =
    Prof leLabelFeatures.Negat veEngage nts
  val Prof leNegat veEngage ntUn onLabels: Set[Feature[JBoolean]] = Set(
    Prof leLabelFeatures. S_NEGAT VE_FEEDBACK_UN ON)
  val SearchCoreLabels: Set[Feature[JBoolean]] = SearchLabelFeatures.CoreEngage nts
  val T etNegat veEngage ntLabels: Set[Feature[JBoolean]] =
    Comb nedFeatures.Negat veEngage ntsRealT  
  val T etNegat veEngage ntDontL keLabels: Set[Feature[JBoolean]] =
    Comb nedFeatures.Negat veEngage ntsRealT  DontL ke
  val T etNegat veEngage ntSecondaryLabels: Set[Feature[JBoolean]] =
    Comb nedFeatures.Negat veEngage ntsSecondary
  val AllT etNegat veEngage ntLabels: Set[Feature[JBoolean]] =
    T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels ++ T etNegat veEngage ntSecondaryLabels
  val UserAuthorEngage ntLabels: Set[Feature[JBoolean]] = Comb nedFeatures.UserAuthorEngage nts
  val ShareEngage ntLabels: Set[Feature[JBoolean]] = Comb nedFeatures.ShareEngage nts
  val BookmarkEngage ntLabels: Set[Feature[JBoolean]] = Comb nedFeatures.BookmarkEngage nts
  val AllBCED llLabels: Set[Feature[JBoolean]] =
    Comb nedFeatures.T etDeta lD llEngage nts ++ Comb nedFeatures.Prof leD llEngage nts ++ Comb nedFeatures.FullscreenV deoD llEngage nts
  val AllT etUn onLabels: Set[Feature[JBoolean]] = Set(
    Comb nedFeatures. S_ MPL C T_POS T VE_FEEDBACK_UN ON,
    Comb nedFeatures. S_EXPL C T_POS T VE_FEEDBACK_UN ON,
    Comb nedFeatures. S_ALL_NEGAT VE_FEEDBACK_UN ON
  )
  val AllT etLabels: Set[Feature[JBoolean]] =
    T etLabels ++ T etCoreAndD llLabels ++ AllT etNegat veEngage ntLabels ++ Prof leCoreLabels ++ Prof leNegat veEngage ntLabels ++ Prof leNegat veEngage ntUn onLabels ++ UserAuthorEngage ntLabels ++ SearchCoreLabels ++ ShareEngage ntLabels ++ BookmarkEngage ntLabels ++ Pr vateEngage ntLabelsV2 ++ AllBCED llLabels ++ AllT etUn onLabels

  def addFeatureF lterFromRes ce(
    prodGroup: AggregateGroup,
    aggRemovalPath: Str ng
  ): AggregateGroup = {
    val res ce = So (S ce.fromRes ce(aggRemovalPath))
    val l nes = res ce.map(_.getL nes.toSeq)
    l nes match {
      case So (value) => prodGroup.copy(aggExclus onRegex = value)
      case _ => prodGroup
    }
  }
}

tra  T  l nesOnl neAggregat onDef n  onsTra  extends Onl neAggregat onConf gTra  {
   mport T  l nesOnl neAggregat onUt ls._

  def  nputS ce: AggregateS ce
  def Product onStore: AggregateStore
  def Stag ngStore: AggregateStore

  val T etFeatures: Set[Feature[_]] = Set(
    Cl entLogEventDataRecordFeatures.HasConsu rV deo,
    Cl entLogEventDataRecordFeatures.PhotoCount
  )
  val Cand dateT etS ceFeatures: Set[Feature[_]] = Set(
    Cl entLogEventDataRecordFeatures.FromRecap,
    Cl entLogEventDataRecordFeatures.FromRecycled,
    Cl entLogEventDataRecordFeatures.FromAct v y,
    Cl entLogEventDataRecordFeatures.FromS mcluster,
    Cl entLogEventDataRecordFeatures.FromErg,
    Cl entLogEventDataRecordFeatures.FromCroon,
    Cl entLogEventDataRecordFeatures.FromL st,
    Cl entLogEventDataRecordFeatures.FromRecTop c
  )

  def createStag ngGroup(prodGroup: AggregateGroup): AggregateGroup =
    prodGroup.copy(
      outputStore = Stag ngStore
    )

  // Aggregate user engage nts/features by t et  d.
  val t etEngage nt30M nuteCountsProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate user engage nts/features by t et  d.
  val t etVer f edDontL keEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v6",
      preTransforms = Seq(R chRemoveUnver f edUserTransform),
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val t etNegat veEngage nt6H Counts =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v2",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val t etVer f edNegat veEngage ntCounts =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v7",
      preTransforms = Seq(R chRemoveUnver f edUserTransform),
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val promotedT etEngage ntRealT  Counts =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v3. s_promoted",
      preTransforms = Seq(
        DownsampleTransform(
          negat veSampl ngRate = 0.0,
          keepLabels = Set(Cl entLogEventDataRecordFeatures. sPromoted))),
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etCoreAndD llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(2.h s, 24.h s),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate total engage nt counts by t et  d for non-publ c
   * engage nts. S m lar to EB's publ c engage nt counts.
   */
  val t etEngage ntTotalCountsProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val t etNegat veEngage ntTotalCounts =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v2",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = T etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by v e r's user  d.
   */
  val userEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D),
      features = T etFeatures,
      labels = T etLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by v e r's user  d.
   */
  val userEngage ntRealT  AggregatesV2 =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_aggregates_v2",
      keys = Set(SharedFeatures.USER_ D),
      features = Cl entLogEventDataRecordFeatures.T etFeaturesV2,
      labels = T etCoreAndD llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate author's user state features grouped by v e r's user  d.
   */
  val userEngage ntAuthorUserStateRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_aggregates_v3",
      preTransforms = Seq.empty,
      keys = Set(SharedFeatures.USER_ D),
      features = AuthorFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etCoreAndD llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate author's user state features grouped by v e r's user  d.
   */
  val userNegat veEngage ntAuthorUserStateRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_aggregates_v4",
      preTransforms = Seq.empty,
      keys = Set(SharedFeatures.USER_ D),
      features = AuthorFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by v e r's user  d, w h 48 h  halfL fe.
   */
  val userEngage nt48H RealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_aggregates_v5",
      keys = Set(SharedFeatures.USER_ D),
      features = T etFeatures,
      labels = T etLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(48.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate author's user state features grouped by v e r's user  d.
   */
  val userNegat veEngage ntAuthorUserState72H RealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_aggregates_v6",
      preTransforms = Seq.empty,
      keys = Set(SharedFeatures.USER_ D),
      features = AuthorFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(72.h s),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate features grouped by s ce author  d: for each author, aggregate features are created
   * to quant fy engage nts (fav, reply, etc.) wh ch t ets of t  author has rece ved.
   */
  val authorEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _author_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = T etLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate features grouped by s ce author  d: for each author, aggregate features are created
   * to quant fy negat ve engage nts (mute, block, etc.) wh ch t ets of t  author has rece ved.
   *
   * T  aggregate group  s not used  n Ho , but    s used  n Follow Recom ndat on Serv ce so need to keep   for now.
   *
   */
  val authorNegat veEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _author_aggregates_v2",
      keys = Set(T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = T etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate features grouped by s ce author  d: for each author, aggregate features are created
   * to quant fy negat ve engage nts (don't l ke) wh ch t ets of t  author has rece ved from
   * ver f ed users.
   */
  val authorVer f edNegat veEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _author_aggregates_v3",
      preTransforms = Seq(R chRemoveUnver f edUserTransform),
      keys = Set(T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by top c  d.
   */
  val top cEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _top c_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.TOP C_ D),
      features = Set.empty,
      labels = T etLabels ++ AllT etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate user engage nts / user state by top c  d.
   */
  val top cEngage ntUserStateRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _top c_aggregates_v2",
      keys = Set(T  l nesSharedFeatures.TOP C_ D),
      features = UserFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etCoreAndD llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate user negat ve engage nts / user state by top c  d.
   */
  val top cNegat veEngage ntUserStateRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _top c_aggregates_v3",
      keys = Set(T  l nesSharedFeatures.TOP C_ D),
      features = UserFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by top c  d l ke real_t  _top c_aggregates_v1 but 24h  halfL fe
   */
  val top cEngage nt24H RealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _top c_aggregates_v4",
      keys = Set(T  l nesSharedFeatures.TOP C_ D),
      features = Set.empty,
      labels = T etLabels ++ AllT etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate user engage nts / user state by t et  d.
  val t etEngage ntUserStateRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v3",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = UserFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etCoreAndD llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate user engage nts / user gender by t et  d.
  val t etEngage ntGenderRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v4",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = UserFeaturesAdapter.GenderBooleanFeatures,
      labels =
        T etCoreAndD llLabels ++ T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate user negat ve engage nts / user state by t et  d.
  val t etNegat veEngage ntUserStateRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v5",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = UserFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate user negat ve engage nts / user state by t et  d.
  val t etVer f edNegat veEngage ntUserStateRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_aggregates_v8",
      preTransforms = Seq(R chRemoveUnver f edUserTransform),
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = UserFeaturesAdapter.UserStateBooleanFeatures,
      labels = T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et engage nt labels and cand date t et s ce features grouped by user  d.
   */
  val userCand dateT etS ceEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_cand date_t et_s ce_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D),
      features = Cand dateT etS ceFeatures,
      labels = T etCoreAndD llLabels ++ Negat veEngage ntsRealT  DontL ke,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et engage nt labels and cand date t et s ce features grouped by user  d.
   */
  val userCand dateT etS ceEngage nt48H RealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_cand date_t et_s ce_aggregates_v2",
      keys = Set(SharedFeatures.USER_ D),
      features = Cand dateT etS ceFeatures,
      labels = T etCoreAndD llLabels ++ Negat veEngage ntsRealT  DontL ke,
       tr cs = Set(Count tr c),
      halfL ves = Set(48.h s),
      outputStore = Product onStore,
       ncludeAnyFeature = false,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by v e r's user  d on Prof le engage nts
   */
  val userProf leEngage ntRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "prof le_real_t  _user_aggregates_v1",
      preTransforms = Seq( sNewUserTransform),
      keys = Set(SharedFeatures.USER_ D),
      features = T etFeatures,
      labels = Prof leCoreLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val Negat veEngage ntsUn onTransform = R ch Transform(
    B naryUn on(
      featuresToUn fy = Prof leNegat veEngage ntLabels,
      outputFeature = Prof leLabelFeatures. S_NEGAT VE_FEEDBACK_UN ON
    ))

  /**
   * Aggregate t et features grouped by v e r's user  d on Prof le negat ve engage nts.
   */
  val userProf leNegat veEngage ntRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "prof le_negat ve_engage nt_real_t  _user_aggregates_v1",
      preTransforms = Seq(Negat veEngage ntsUn onTransform),
      keys = Set(SharedFeatures.USER_ D),
      features = Set.empty,
      labels = Prof leNegat veEngage ntLabels ++ Prof leNegat veEngage ntUn onLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 72.h s, 14.day),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by v e r's and author's user  ds and on Prof le engage nts
   */
  val userAuthorProf leEngage ntRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "user_author_prof le_real_t  _aggregates_v1",
      keys = Set(SharedFeatures.USER_ D, T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = Prof leCoreLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s, 72.h s),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate t et features grouped by v e r's and author's user  ds and on negat ve Prof le engage nts
   */
  val userAuthorProf leNegat veEngage ntRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "user_author_prof le_negat ve_engage nt_real_t  _aggregates_v1",
      preTransforms = Seq(Negat veEngage ntsUn onTransform),
      keys = Set(SharedFeatures.USER_ D, T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = Prof leNegat veEngage ntUn onLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 72.h s, 14.day),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val newUserAuthorEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _new_user_author_aggregates_v1",
      preTransforms = Seq( sNewUserTransform),
      keys = Set(SharedFeatures.USER_ D, T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = T etCoreAndD llLabels ++ Set(
         S_CL CKED,
         S_PROF LE_CL CKED,
         S_PHOTO_EXPANDED
      ),
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val userAuthorEngage ntRealT  AggregatesProd = {
    // Comput ng user-author real-t   aggregates  s very expens ve so  
    // take t  un on of all major negat ve feedback engage nts to create
    // a s ngle negt ve label for aggregat on.   also  nclude a number of
    // core pos  ve engage nts.
    val B naryUn onNegat veEngage nts =
      B naryUn on(
        featuresToUn fy = AllT etNegat veEngage ntLabels,
        outputFeature =  S_NEGAT VE_FEEDBACK_UN ON
      )
    val B naryUn onNegat veEngage ntsTransform = R ch Transform(B naryUn onNegat veEngage nts)

    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_author_aggregates_v1",
      preTransforms = Seq(B naryUn onNegat veEngage ntsTransform),
      keys = Set(SharedFeatures.USER_ D, T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = UserAuthorEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 1.day),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )
  }

  /**
   * Aggregate t et features grouped by l st  d.
   */
  val l stEngage ntRealT  AggregatesProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _l st_aggregates_v1",
      keys = Set(L stFeatures.L ST_ D),
      features = Set.empty,
      labels =
        T etCoreAndD llLabels ++ T etNegat veEngage ntLabels ++ T etNegat veEngage ntDontL keLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate features grouped by top c of t et and country from user's locat on
  val top cCountryRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _top c_country_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.TOP C_ D, UserFeaturesAdapter.USER_COUNTRY_ D),
      features = Set.empty,
      labels =
        T etCoreAndD llLabels ++ AllT etNegat veEngage ntLabels ++ Pr vateEngage ntLabelsV2 ++ ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 72.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate features grouped by T et d_Country from user's locat on
  val t etCountryRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_country_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D, UserFeaturesAdapter.USER_COUNTRY_ D),
      features = Set.empty,
      labels = T etCoreAndD llLabels ++ AllT etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = true,
       ncludeT  stampFeature = false,
    )

  // Add  onal aggregate features grouped by T et d_Country from user's locat on
  val t etCountryPr vateEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_country_aggregates_v2",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D, UserFeaturesAdapter.USER_COUNTRY_ D),
      features = Set.empty,
      labels = Pr vateEngage ntLabelsV2 ++ ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 72.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Aggregate features grouped by T et d_Country from user's locat on
  val t etCountryVer f edNegat veEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_country_aggregates_v3",
      preTransforms = Seq(R chRemoveUnver f edUserTransform),
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D, UserFeaturesAdapter.USER_COUNTRY_ D),
      features = Set.empty,
      labels = AllT etNegat veEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, Durat on.Top),
      outputStore = Product onStore,
       ncludeAnyLabel = true,
       ncludeT  stampFeature = false,
    )

  object pos  onTranforms extends  sPos  onTransform {
    overr de val  s nPos  onRangeFeature: Seq[Pos  onCase] =
      Seq(Pos  onCase(1,  S_TOP_ONE), Pos  onCase(5,  S_TOP_F VE), Pos  onCase(10,  S_TOP_TEN))
    overr de val decodedPos  onFeature: Feature.D screte =
      Cl entLogEventDataRecordFeatures. njectedPos  on
  }

  val userPos  onEngage ntsCountsProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _pos  on_based_user_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D),
      features = Set( S_TOP_ONE,  S_TOP_F VE,  S_TOP_TEN),
      labels = T etCoreAndD llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
      preTransforms = Seq(pos  onTranforms),
       ncludeAnyLabel = false,
       ncludeAnyFeature = false,
       ncludeT  stampFeature = false,
    )

  val userPos  onEngage ntsSumProd =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _pos  on_based_user_sum_aggregates_v2",
      keys = Set(SharedFeatures.USER_ D),
      features = Set(LOG_POS T ON),
      labels = T etCoreAndD llLabels,
       tr cs = Set(Sum tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
      preTransforms =
        Seq(new LogTransform(Cl entLogEventDataRecordFeatures. njectedPos  on, LOG_POS T ON)),
       ncludeAnyLabel = false,
       ncludeAnyFeature = false,
       ncludeT  stampFeature = false,
    )

  // Aggregates for share engage nts
  val t etShareEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_share_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val userShareEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_share_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D),
      features = Set.empty,
      labels = ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val userAuthorShareEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_author_share_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D, T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val top cShareEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _top c_share_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.TOP C_ D),
      features = Set.empty,
      labels = ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val authorShareEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _author_share_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = ShareEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  // Bookmark RTAs
  val t etBookmarkEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_bookmark_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = BookmarkEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val userBookmarkEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_bookmark_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D),
      features = Set.empty,
      labels = BookmarkEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val userAuthorBookmarkEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_author_bookmark_aggregates_v1",
      keys = Set(SharedFeatures.USER_ D, T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = BookmarkEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyFeature = true,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val authorBookmarkEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _author_bookmark_aggregates_v1",
      keys = Set(T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features = Set.empty,
      labels = BookmarkEngage ntLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate on user level d ll labels from BCE
   */
  val userBCED llEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _user_bce_d ll_aggregates",
      keys = Set(SharedFeatures.USER_ D),
      features = Set.empty,
      labels = AllBCED llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  /**
   * Aggregate on t et level d ll labels from BCE
   */
  val t etBCED llEngage ntsRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _t et_bce_d ll_aggregates",
      keys = Set(T  l nesSharedFeatures.SOURCE_TWEET_ D),
      features = Set.empty,
      labels = AllBCED llLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(30.m nutes, 24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeT  stampFeature = false,
    )

  val  mpl c Pos  veEngage ntsUn onTransform = R ch Transform(
    B naryUn on(
      featuresToUn fy = Comb nedFeatures. mpl c Pos  veEngage nts,
      outputFeature = Comb nedFeatures. S_ MPL C T_POS T VE_FEEDBACK_UN ON
    )
  )

  val Expl c Pos  veEngage ntsUn onTransform = R ch Transform(
    B naryUn on(
      featuresToUn fy = Comb nedFeatures.Expl c Pos  veEngage nts,
      outputFeature = Comb nedFeatures. S_EXPL C T_POS T VE_FEEDBACK_UN ON
    )
  )

  val AllNegat veEngage ntsUn onTransform = R ch Transform(
    B naryUn on(
      featuresToUn fy = Comb nedFeatures.AllNegat veEngage nts,
      outputFeature = Comb nedFeatures. S_ALL_NEGAT VE_FEEDBACK_UN ON
    )
  )

  /**
   * Aggregate features for author content preference
   */
  val authorContentPreferenceRealT  Aggregates =
    AggregateGroup(
       nputS ce =  nputS ce,
      aggregatePref x = "real_t  _author_content_preference_aggregates",
      preTransforms = Seq(
         mpl c Pos  veEngage ntsUn onTransform,
        Expl c Pos  veEngage ntsUn onTransform,
        AllNegat veEngage ntsUn onTransform),
      keys = Set(T  l nesSharedFeatures.SOURCE_AUTHOR_ D),
      features =
        Cl entLogEventDataRecordFeatures.AuthorContentPreferenceT etTypeFeatures ++ AuthorFeaturesAdapter.UserStateBooleanFeatures,
      labels = AllT etUn onLabels,
       tr cs = Set(Count tr c),
      halfL ves = Set(24.h s),
      outputStore = Product onStore,
       ncludeAnyLabel = false,
       ncludeAnyFeature = false,
    )

  val FeaturesGeneratedByPreTransforms = Set(LOG_POS T ON,  S_TOP_TEN,  S_TOP_F VE,  S_TOP_ONE)

  val ProdAggregateGroups = Set(
    t etEngage nt30M nuteCountsProd,
    t etEngage ntTotalCountsProd,
    t etNegat veEngage nt6H Counts,
    t etNegat veEngage ntTotalCounts,
    userEngage ntRealT  AggregatesProd,
    userEngage nt48H RealT  AggregatesProd,
    userNegat veEngage ntAuthorUserStateRealT  Aggregates,
    userNegat veEngage ntAuthorUserState72H RealT  Aggregates,
    authorEngage ntRealT  AggregatesProd,
    top cEngage ntRealT  AggregatesProd,
    top cEngage nt24H RealT  AggregatesProd,
    t etEngage ntUserStateRealT  AggregatesProd,
    t etNegat veEngage ntUserStateRealT  Aggregates,
    userProf leEngage ntRealT  Aggregates,
    newUserAuthorEngage ntRealT  AggregatesProd,
    userAuthorEngage ntRealT  AggregatesProd,
    l stEngage ntRealT  AggregatesProd,
    t etCountryRealT  Aggregates,
    t etShareEngage ntsRealT  Aggregates,
    userShareEngage ntsRealT  Aggregates,
    userAuthorShareEngage ntsRealT  Aggregates,
    top cShareEngage ntsRealT  Aggregates,
    authorShareEngage ntsRealT  Aggregates,
    t etBookmarkEngage ntsRealT  Aggregates,
    userBookmarkEngage ntsRealT  Aggregates,
    userAuthorBookmarkEngage ntsRealT  Aggregates,
    authorBookmarkEngage ntsRealT  Aggregates,
    top cCountryRealT  Aggregates,
    t etCountryPr vateEngage ntsRealT  Aggregates,
    userBCED llEngage ntsRealT  Aggregates,
    t etBCED llEngage ntsRealT  Aggregates,
    authorContentPreferenceRealT  Aggregates,
    authorVer f edNegat veEngage ntRealT  AggregatesProd,
    t etVer f edDontL keEngage ntRealT  AggregatesProd,
    t etVer f edNegat veEngage ntCounts,
    t etVer f edNegat veEngage ntUserStateRealT  Aggregates,
    t etCountryVer f edNegat veEngage ntsRealT  Aggregates
  ).map(
    addFeatureF lterFromRes ce(
      _,
      "com/tw ter/t  l nes/pred ct on/common/aggregates/real_t  /aggregates_to_drop.txt"))

  val Stag ngAggregateGroups = ProdAggregateGroups.map(createStag ngGroup)

  /**
   * Conta ns t  fully typed aggregate groups from wh ch  mportant
   * values can be der ved e.g. t  features to be computed, halfl ves etc.
   */
  overr de val ProdAggregates = ProdAggregateGroups.flatMap(_.bu ldTypedAggregateGroups())

  overr de val Stag ngAggregates = Stag ngAggregateGroups.flatMap(_.bu ldTypedAggregateGroups())


  overr de val ProdCommonAggregates = ProdAggregates
    .f lter(_.keysToAggregate == Set(SharedFeatures.USER_ D))

  /**
   * T  def nes t  set of selected features from a cand date
   * that  'd l ke to send to t  served features cac  by TLM.
   * T se should  nclude   nterest ng and necessary features that
   * cannot be extracted from LogEvents only by t  real-t   aggregates
   * job.  f   are add ng new AggregateGroups requ r ng TLM-s de
   * cand date features, make sure to add t m  re.
   */
  val cand dateFeaturesToCac : Set[Feature[_]] = Set(
    T  l nesSharedFeatures.SOURCE_AUTHOR_ D,
    RecapFeatures.HASHTAGS,
    RecapFeatures.MENT ONED_SCREEN_NAMES,
    RecapFeatures.URL_DOMA NS
  )
}

/**
 * T  conf g should only be used to access t  aggregate features constructed by t 
 * aggregat on conf g, and not for  mple nt ng an onl ne real-t   aggregates job.
 */
object T  l nesOnl neAggregat onFeaturesOnlyConf g
    extends T  l nesOnl neAggregat onDef n  onsTra  {

  pr vate[real_t  ] case class Dum AggregateS ce(na : Str ng, t  stampFeature: Feature[JLong])
      extends AggregateS ce

  pr vate[real_t  ] case class Dum AggregateStore(na : Str ng) extends AggregateStore

  overr de lazy val  nputS ce = Dum AggregateS ce(
    na  = "t  l nes_rta",
    t  stampFeature = SharedFeatures.T MESTAMP
  )
  overr de lazy val Product onStore = Dum AggregateStore("t  l nes_rta")
  overr de lazy val Stag ngStore = Dum AggregateStore("t  l nes_rta")

  overr de lazy val AggregatesToCompute = ProdAggregates ++ Stag ngAggregates
}
