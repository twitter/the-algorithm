package com.tw ter.ho _m xer.model

 mport com.tw ter.core_workflows.user_model.{thr ftscala => um}
 mport com.tw ter.dal.personal_data.{thr ftjava => pd}
 mport com.tw ter.g zmoduck.{thr ftscala => gt}
 mport com.tw ter.ho _m xer.{thr ftscala => hmt}
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.BoolDataRecordCompat ble
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecordFeature
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecordOpt onalFeature
 mport com.tw ter.product_m xer.core.feature.datarecord.DoubleDataRecordCompat ble
 mport com.tw ter.product_m xer.core.feature.datarecord.LongD screteDataRecordCompat ble
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContextFunct onal yType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.search.common.features.{thr ftscala => sc}
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l nem xer.cl ents.manhattan.D sm ss nfo
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseV3
 mport com.tw ter.t  l nem xer. nject on.model.cand date.Aud oSpace taData
 mport com.tw ter.t  l nes.conversat on_features.v1.thr ftscala.Conversat onFeatures
 mport com.tw ter.t  l nes. mpress on.{thr ftscala =>  mp}
 mport com.tw ter.t  l nes. mpress onbloomf lter.{thr ftscala => blm}
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.engage nt_features.Engage ntDataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.t  l nes.pred ct on.features.request_context.RequestContextFeatures
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => tst}
 mport com.tw ter.t  l neserv ce.model.FeedbackEntry
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.{thr ftscala => cts}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport com.tw ter.tsp.{thr ftscala => tsp}
 mport com.tw ter.t etconvosvc.t et_ancestor.{thr ftscala => ta}
 mport com.tw ter.ut l.T  

object Ho Features {
  // Cand date Features
  object AncestorsFeature extends Feature[T etCand date, Seq[ta.T etAncestor]]
  object Aud oSpace taDataFeature extends Feature[T etCand date, Opt on[Aud oSpace taData]]
  object Tw terL st dFeature extends Feature[T etCand date, Opt on[Long]]

  /**
   * For Ret ets, t  should refer to t  ret et ng user. Use [[S ceUser dFeature]]  f   want to know
   * who created t  T et that was ret eted.
   */
  object Author dFeature
      extends DataRecordOpt onalFeature[T etCand date, Long]
      w h LongD screteDataRecordCompat ble {
    overr de val featureNa : Str ng = SharedFeatures.AUTHOR_ D.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.User d)
  }

  object Author sBlueVer f edFeature extends Feature[T etCand date, Boolean]
  object Author sGoldVer f edFeature extends Feature[T etCand date, Boolean]
  object Author sGrayVer f edFeature extends Feature[T etCand date, Boolean]
  object Author sLegacyVer f edFeature extends Feature[T etCand date, Boolean]
  object Author sCreatorFeature extends Feature[T etCand date, Boolean]
  object Author sProtectedFeature extends Feature[T etCand date, Boolean]

  object AuthoredByContextualUserFeature extends Feature[T etCand date, Boolean]
  object Cac dCand dateP pel ne dent f erFeature extends Feature[T etCand date, Opt on[Str ng]]
  object Cand dateS ce dFeature
      extends Feature[T etCand date, Opt on[cts.Cand dateT etS ce d]]
  object Conversat onFeature extends Feature[T etCand date, Opt on[Conversat onFeatures]]

  /**
   * T  f eld should be set to t  focal T et's t et d for all t ets wh ch are expected to
   * be rendered  n t  sa  convo module. For non-convo module T ets, t  w ll be
   * set to None. Note t   s d fferent from how T etyP e def nes Conversat on d wh ch  s def ned
   * on all T ets and po nts to t  root t et. T  feature  s used for group ng convo modules toget r.
   */
  object Conversat onModuleFocalT et dFeature extends Feature[T etCand date, Opt on[Long]]

  /**
   * T  f eld should always be set to t  root T et  n a conversat on for all T ets. For repl es, t  w ll
   * po nt back to t  root T et. For non-repl es, t  w ll be t  cand date's T et  d. T   s cons stent w h
   * t  T etyP e def n  on of Conversat onModule d.
   */
  object Conversat onModule dFeature extends Feature[T etCand date, Opt on[Long]]
  object D rectedAtUser dFeature extends Feature[T etCand date, Opt on[Long]]
  object Earlyb rdFeature extends Feature[T etCand date, Opt on[sc.Thr ftT etFeatures]]
  object Earlyb rdScoreFeature extends Feature[T etCand date, Opt on[Double]]
  object Earlyb rdSearchResultFeature extends Feature[T etCand date, Opt on[eb.Thr ftSearchResult]]
  object Ent yTokenFeature extends Feature[T etCand date, Opt on[Str ng]]
  object Exclus veConversat onAuthor dFeature extends Feature[T etCand date, Opt on[Long]]
  object Favor edByCountFeature
      extends DataRecordFeature[T etCand date, Double]
      w h DoubleDataRecordCompat ble {
    overr de val featureNa : Str ng =
      Engage ntDataRecordFeatures. nNetworkFavor esCount.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPr vateL kes, pd.PersonalDataType.CountOfPubl cL kes)
  }
  object Favor edByUser dsFeature extends Feature[T etCand date, Seq[Long]]
  object Feedback toryFeature extends Feature[T etCand date, Seq[FeedbackEntry]]
  object Ret etedByCountFeature
      extends DataRecordFeature[T etCand date, Double]
      w h DoubleDataRecordCompat ble {
    overr de val featureNa : Str ng =
      Engage ntDataRecordFeatures. nNetworkRet etsCount.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPr vateRet ets, pd.PersonalDataType.CountOfPubl cRet ets)
  }
  object Ret etedByEngager dsFeature extends Feature[T etCand date, Seq[Long]]
  object Repl edByCountFeature
      extends DataRecordFeature[T etCand date, Double]
      w h DoubleDataRecordCompat ble {
    overr de val featureNa : Str ng =
      Engage ntDataRecordFeatures. nNetworkRepl esCount.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPr vateRepl es, pd.PersonalDataType.CountOfPubl cRepl es)
  }
  object Repl edByEngager dsFeature extends Feature[T etCand date, Seq[Long]]
  object Follo dByUser dsFeature extends Feature[T etCand date, Seq[Long]]

  object Top c dSoc alContextFeature extends Feature[T etCand date, Opt on[Long]]
  object Top cContextFunct onal yTypeFeature
      extends Feature[T etCand date, Opt on[Top cContextFunct onal yType]]
  object From nNetworkS ceFeature extends Feature[T etCand date, Boolean]

  object FullScor ngSucceededFeature extends Feature[T etCand date, Boolean]
  object HasD splayedTextFeature extends Feature[T etCand date, Boolean]
  object  nReplyToT et dFeature extends Feature[T etCand date, Opt on[Long]]
  object  nReplyToUser dFeature extends Feature[T etCand date, Opt on[Long]]
  object  sAncestorCand dateFeature extends Feature[T etCand date, Boolean]
  object  sExtendedReplyFeature
      extends DataRecordFeature[T etCand date, Boolean]
      w h BoolDataRecordCompat ble {
    overr de val featureNa : Str ng = RecapFeatures. S_EXTENDED_REPLY.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object  sRandomT etFeature
      extends DataRecordFeature[T etCand date, Boolean]
      w h BoolDataRecordCompat ble {
    overr de val featureNa : Str ng = T  l nesSharedFeatures. S_RANDOM_TWEET.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object  sReadFromCac Feature extends Feature[T etCand date, Boolean]
  object  sRet etFeature extends Feature[T etCand date, Boolean]
  object  sRet etedReplyFeature extends Feature[T etCand date, Boolean]
  object  sSupportAccountReplyFeature extends Feature[T etCand date, Boolean]
  object LastScoredT  stampMsFeature extends Feature[T etCand date, Opt on[Long]]
  object NonSelfFavor edByUser dsFeature extends Feature[T etCand date, Seq[Long]]
  object Num magesFeature extends Feature[T etCand date, Opt on[ nt]]
  object Or g nalT etCreat onT  FromSnowflakeFeature extends Feature[T etCand date, Opt on[T  ]]
  object Pos  onFeature extends Feature[T etCand date, Opt on[ nt]]
  //  nternal  d generated per pred ct on serv ce request
  object Pred ct onRequest dFeature extends Feature[T etCand date, Opt on[Long]]
  object QuotedT et dFeature extends Feature[T etCand date, Opt on[Long]]
  object QuotedUser dFeature extends Feature[T etCand date, Opt on[Long]]
  object ScoreFeature extends Feature[T etCand date, Opt on[Double]]
  object Semant cCore dFeature extends Feature[T etCand date, Opt on[Long]]
  // Key for kafka logg ng
  object Served dFeature extends Feature[T etCand date, Opt on[Long]]
  object S mclustersT etTopKClustersW hScoresFeature
      extends Feature[T etCand date, Map[Str ng, Double]]
  object Soc alContextFeature extends Feature[T etCand date, Opt on[tst.Soc alContext]]
  object S ceT et dFeature
      extends DataRecordOpt onalFeature[T etCand date, Long]
      w h LongD screteDataRecordCompat ble {
    overr de val featureNa : Str ng = T  l nesSharedFeatures.SOURCE_TWEET_ D.getFeatureNa 
    overr de val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.T et d)
  }
  object S ceUser dFeature extends Feature[T etCand date, Opt on[Long]]
  object StreamToKafkaFeature extends Feature[T etCand date, Boolean]
  object SuggestTypeFeature extends Feature[T etCand date, Opt on[st.SuggestType]]
  object TSP tr cTagFeature extends Feature[T etCand date, Set[tsp. tr cTag]]
  object T etLanguageFeature extends Feature[T etCand date, Opt on[Str ng]]
  object T etUrlsFeature extends Feature[T etCand date, Seq[Str ng]]
  object V deoDurat onMsFeature extends Feature[T etCand date, Opt on[ nt]]
  object V e r dFeature
      extends DataRecordFeature[T etCand date, Long]
      w h LongD screteDataRecordCompat ble {
    overr de def featureNa : Str ng = SharedFeatures.USER_ D.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.User d)
  }
  object   ghtedModelScoreFeature extends Feature[T etCand date, Opt on[Double]]
  object  nt onUser dFeature extends Feature[T etCand date, Seq[Long]]
  object  nt onScreenNa Feature extends Feature[T etCand date, Seq[Str ng]]
  object Has mageFeature extends Feature[T etCand date, Boolean]
  object HasV deoFeature extends Feature[T etCand date, Boolean]

  // T etyp e VF Features
  object  sHydratedFeature extends Feature[T etCand date, Boolean]
  object  sNsfwFeature extends Feature[T etCand date, Boolean]
  object QuotedT etDroppedFeature extends Feature[T etCand date, Boolean]
  // Raw T et Text from T etyp e
  object T etTextFeature extends Feature[T etCand date, Opt on[Str ng]]

  object AuthorEnabledPrev ewsFeature extends Feature[T etCand date, Boolean]
  object  sT etPrev ewFeature extends Feature[T etCand date, Boolean]

  // SGS Features
  /**
   * By convent on, t   s set to true for ret ets of non-follo d authors
   * E.g. w re so body t  v e r follows ret ets a T et from so body t  v e r doesn't follow
   */
  object  nNetworkFeature extends FeatureW hDefaultOnFa lure[T etCand date, Boolean] {
    overr de val defaultValue: Boolean = true
  }

  // Query Features
  object AccountAgeFeature extends Feature[P pel neQuery, Opt on[T  ]]
  object Cl ent dFeature
      extends DataRecordOpt onalFeature[P pel neQuery, Long]
      w h LongD screteDataRecordCompat ble {
    overr de def featureNa : Str ng = SharedFeatures.CL ENT_ D.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.Cl entType)
  }
  object Cac dScoredT etsFeature extends Feature[P pel neQuery, Seq[hmt.ScoredT et]]
  object Dev ceLanguageFeature extends Feature[P pel neQuery, Opt on[Str ng]]
  object D sm ss nfoFeature
      extends FeatureW hDefaultOnFa lure[P pel neQuery, Map[st.SuggestType, Opt on[D sm ss nfo]]] {
    overr de def defaultValue: Map[st.SuggestType, Opt on[D sm ss nfo]] = Map.empty
  }
  object Follow ngLastNonPoll ngT  Feature extends Feature[P pel neQuery, Opt on[T  ]]
  object Get n  alFeature
      extends DataRecordFeature[P pel neQuery, Boolean]
      w h BoolDataRecordCompat ble {
    overr de def featureNa : Str ng = RequestContextFeatures. S_GET_ N T AL.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetM ddleFeature
      extends DataRecordFeature[P pel neQuery, Boolean]
      w h BoolDataRecordCompat ble {
    overr de def featureNa : Str ng = RequestContextFeatures. S_GET_M DDLE.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetNe rFeature
      extends DataRecordFeature[P pel neQuery, Boolean]
      w h BoolDataRecordCompat ble {
    overr de def featureNa : Str ng = RequestContextFeatures. S_GET_NEWER.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetOlderFeature
      extends DataRecordFeature[P pel neQuery, Boolean]
      w h BoolDataRecordCompat ble {
    overr de def featureNa : Str ng = RequestContextFeatures. S_GET_OLDER.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object Guest dFeature
      extends DataRecordOpt onalFeature[P pel neQuery, Long]
      w h LongD screteDataRecordCompat ble {
    overr de def featureNa : Str ng = SharedFeatures.GUEST_ D.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.Guest d)
  }
  object HasDarkRequestFeature extends Feature[P pel neQuery, Opt on[Boolean]]
  object  mpress onBloomF lterFeature
      extends FeatureW hDefaultOnFa lure[P pel neQuery, blm. mpress onBloomF lterSeq] {
    overr de def defaultValue: blm. mpress onBloomF lterSeq =
      blm. mpress onBloomF lterSeq(Seq.empty)
  }
  object  sForegroundRequestFeature extends Feature[P pel neQuery, Boolean]
  object  sLaunchRequestFeature extends Feature[P pel neQuery, Boolean]
  object LastNonPoll ngT  Feature extends Feature[P pel neQuery, Opt on[T  ]]
  object NonPoll ngT  sFeature extends Feature[P pel neQuery, Seq[Long]]
  object Pers stenceEntr esFeature extends Feature[P pel neQuery, Seq[T  l neResponseV3]]
  object Poll ngFeature extends Feature[P pel neQuery, Boolean]
  object PullToRefreshFeature extends Feature[P pel neQuery, Boolean]
  // Scores from Real Graph represent ng t  relat onsh p bet en t  v e r and anot r user
  object RealGraph nNetworkScoresFeature extends Feature[P pel neQuery, Map[User d, Double]]
  object RequestJo n dFeature extends Feature[T etCand date, Opt on[Long]]
  //  nternal  d generated per request, ma nly to dedupl cate re-served cac d t ets  n logg ng
  object ServedRequest dFeature extends Feature[P pel neQuery, Opt on[Long]]
  object ServedT et dsFeature extends Feature[P pel neQuery, Seq[Long]]
  object ServedT etPrev ew dsFeature extends Feature[P pel neQuery, Seq[Long]]
  object T  l neServ ceT etsFeature extends Feature[P pel neQuery, Seq[Long]]
  object T  stampFeature
      extends DataRecordFeature[P pel neQuery, Long]
      w h LongD screteDataRecordCompat ble {
    overr de def featureNa : Str ng = SharedFeatures.T MESTAMP.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object T  stampGMTDowFeature
      extends DataRecordFeature[P pel neQuery, Long]
      w h LongD screteDataRecordCompat ble {
    overr de def featureNa : Str ng = RequestContextFeatures.T MESTAMP_GMT_DOW.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object T  stampGMTH Feature
      extends DataRecordFeature[P pel neQuery, Long]
      w h LongD screteDataRecordCompat ble {
    overr de def featureNa : Str ng = RequestContextFeatures.T MESTAMP_GMT_HOUR.getFeatureNa 
    overr de def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object T et mpress onsFeature extends Feature[P pel neQuery, Seq[ mp.T et mpress onsEntry]]
  object UserFollo dTop csCountFeature extends Feature[P pel neQuery, Opt on[ nt]]
  object UserFollow ngCountFeature extends Feature[P pel neQuery, Opt on[ nt]]
  object UserScreenNa Feature extends Feature[P pel neQuery, Opt on[Str ng]]
  object UserStateFeature extends Feature[P pel neQuery, Opt on[um.UserState]]
  object UserTypeFeature extends Feature[P pel neQuery, Opt on[gt.UserType]]
  object WhoToFollowExcludedUser dsFeature
      extends FeatureW hDefaultOnFa lure[P pel neQuery, Seq[Long]] {
    overr de def defaultValue = Seq.empty
  }

  // Result Features
  object ServedS zeFeature extends Feature[P pel neQuery, Opt on[ nt]]
  object HasRandomT etFeature extends Feature[P pel neQuery, Boolean]
  object  sRandomT etAboveFeature extends Feature[T etCand date, Boolean]
  object Served nConversat onModuleFeature extends Feature[T etCand date, Boolean]
  object Conversat onModule2D splayedT etsFeature extends Feature[T etCand date, Boolean]
  object Conversat onModuleHasGapFeature extends Feature[T etCand date, Boolean]
  object SGSVal dL kedByUser dsFeature extends Feature[T etCand date, Seq[Long]]
  object SGSVal dFollo dByUser dsFeature extends Feature[T etCand date, Seq[Long]]
  object Perspect veF lteredL kedByUser dsFeature extends Feature[T etCand date, Seq[Long]]
  object ScreenNa sFeature extends Feature[T etCand date, Map[Long, Str ng]]
  object RealNa sFeature extends Feature[T etCand date, Map[Long, Str ng]]

  /**
   * Features around t  focal T et for T ets wh ch should be rendered  n convo modules.
   * T se are needed  n order to render soc al context above t  root t et  n a convo modules.
   * For example  f   have a convo module A-B-C (A T ets, B repl es to A, C repl es to B), t  descendant features are
   * for t  T et C. T se features are None except for t  root T et for T ets wh ch should render  nto
   * convo modules.
   */
  object FocalT etAuthor dFeature extends Feature[T etCand date, Opt on[Long]]
  object FocalT et nNetworkFeature extends Feature[T etCand date, Opt on[Boolean]]
  object FocalT etRealNa sFeature extends Feature[T etCand date, Opt on[Map[Long, Str ng]]]
  object FocalT etScreenNa sFeature extends Feature[T etCand date, Opt on[Map[Long, Str ng]]]
  object  d aUnderstand ngAnnotat on dsFeature extends Feature[T etCand date, Seq[Long]]
}
