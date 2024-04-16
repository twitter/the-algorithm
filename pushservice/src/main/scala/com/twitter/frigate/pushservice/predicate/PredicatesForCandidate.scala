package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.cand date.MaxT etAge
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.common.pred cate.t et.T etAuthorPred cates
 mport com.tw ter.fr gate.common.pred cate._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.common.ut l.SnowflakeUt ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter. rm .pred cate.g zmoduck._
 mport com.tw ter. rm .pred cate.soc algraph.Edge
 mport com.tw ter. rm .pred cate.soc algraph.Mult Edge
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter. rm .pred cate.soc algraph.Soc alGraphPred cate
 mport com.tw ter.serv ce. tastore.gen.thr ftscala.Locat on
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future

object Pred catesForCand date {

  def oldT etRecsPred cate( mpl c  stats: StatsRece ver): Pred cate[
    T etCand date w h Recom ndat onType w h Target nfo[
      TargetUser w h TargetABDec der w h MaxT etAge
    ]
  ] = {
    val na  = "old_t et"
    Pred cate
      .from[T etCand date w h Recom ndat onType w h Target nfo[
        TargetUser w h TargetABDec der w h MaxT etAge
      ]] { cand date =>
        {
          val crt = cand date.commonRecType
          val defaultAge =  f (RecTypes.mrModel ngBasedTypes.conta ns(crt)) {
            cand date.target.params(PushFeatureSw chParams.Model ngBasedCand dateMaxT etAgeParam)
          } else  f (RecTypes.GeoPopT etTypes.conta ns(crt)) {
            cand date.target.params(PushFeatureSw chParams.GeoPopT etMaxAge nH s)
          } else  f (RecTypes.s mclusterBasedT ets.conta ns(crt)) {
            cand date.target.params(
              PushFeatureSw chParams.S mclusterBasedCand dateMaxT etAgeParam)
          } else  f (RecTypes.detop cTypes.conta ns(crt)) {
            cand date.target.params(PushFeatureSw chParams.Detop cBasedCand dateMaxT etAgeParam)
          } else  f (RecTypes.f1F rstDegreeTypes.conta ns(crt)) {
            cand date.target.params(PushFeatureSw chParams.F1Cand dateMaxT etAgeParam)
          } else  f (crt == CommonRecom ndat onType.ExploreV deoT et) {
            cand date.target.params(PushFeatureSw chParams.ExploreV deoT etAgeParam)
          } else
            cand date.target.params(PushFeatureSw chParams.MaxT etAgeParam)
          SnowflakeUt ls. sRecent(cand date.t et d, defaultAge)
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def t et sNotAreply(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[T etCand date w h T etDeta ls] = {
    val na  = "t et_cand date_not_a_reply"
    Pred cate
      .from[T etCand date w h T etDeta ls] { c =>
        c. sReply match {
          case So (true) => false
          case _ => true
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  /**
   * C ck  f t et conta ns any optouted free form  nterests.
   * Currently,   use   for  d a categor es and semant c core
   * @param stats
   * @return
   */
  def noOptoutFreeForm nterestPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "free_form_ nterest_opt_out"
    val t et d aAnnotat onFeature =
      "t et. d aunderstand ng.t et_annotat ons.safe_category_probab l  es"
    val t etSemant cCoreFeature =
      "t et.core.t et.semant c_core_annotat ons"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    val w hOptOutFreeForm nterestsCounter = stats.counter("w h_optout_ nterests")
    val w houtOptOut nterestsCounter = stats.counter("w hout_optout_ nterests")
    val w hOptOutFreeForm nterestsFrom d aAnnotat onCounter =
      stats.counter("w h_optout_ nterests_from_ d a_annotat on")
    val w hOptOutFreeForm nterestsFromSemant cCoreCounter =
      stats.counter("w h_optout_ nterests_from_semant c_core")
    Pred cate
      .fromAsync { cand date: PushCand date =>
        val t etSemant cCoreEnt y ds = cand date.sparseB naryFeatures
          .getOrElse(t etSemant cCoreFeature, Set.empty[Str ng]).map {  d =>
             d.spl ('.')(2)
          }.toSet
        val t et d aAnnotat on ds = cand date.sparseCont nuousFeatures
          .getOrElse(t et d aAnnotat onFeature, Map.empty[Str ng, Double]).keys.toSet

        cand date.target.optOutFreeFormUser nterests.map {
          case optOutUser nterests: Seq[Str ng] =>
            w hOptOutFreeForm nterestsCounter. ncr()
            val optOutUser nterestsSet = optOutUser nterests.toSet
            val  d aAnno ntersect = optOutUser nterestsSet. ntersect(t et d aAnnotat on ds)
            val semant cCore ntersect = optOutUser nterestsSet. ntersect(t etSemant cCoreEnt y ds)
             f (! d aAnno ntersect. sEmpty) {
              w hOptOutFreeForm nterestsFrom d aAnnotat onCounter. ncr()
            }
             f (!semant cCore ntersect. sEmpty) {
              w hOptOutFreeForm nterestsFromSemant cCoreCounter. ncr()
            }
            semant cCore ntersect. sEmpty &&  d aAnno ntersect. sEmpty
          case _ =>
            w houtOptOut nterestsCounter. ncr()
            true
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def t etCand dateW hLessThan2Soc alContexts sAReply(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[T etCand date w h T etDeta ls w h Soc alContextAct ons] = {
    val na  = "t et_cand date_w h_less_than_2_soc al_contexts_ s_not_a_reply"
    Pred cate
      .from[T etCand date w h T etDeta ls w h Soc alContextAct ons] { cand =>
        cand. sReply match {
          case So (true)  f cand.soc alContextT et ds.s ze < 2 => false
          case _ => true
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def f1Cand date sNotAReply( mpl c  stats: StatsRece ver): Na dPred cate[F1Cand date] = {
    val na  = "f1_cand date_ s_not_a_reply"
    Pred cate
      .from[F1Cand date] { cand date =>
        cand date. sReply match {
          case So (true) => false
          case _ => true
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def outOfNetworkT etCand dateEnabledCrTag(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[OutOfNetworkT etCand date w h Target nfo[TargetUser w h TargetABDec der]] = {
    val na  = "out_of_network_t et_cand date_enabled_crtag"
    val scopedStats = stats.scope(na )
    Pred cate
      .from[OutOfNetworkT etCand date w h Target nfo[TargetUser w h TargetABDec der]] { cand =>
        val d sabledCrTag = cand.target
          .params(PushFeatureSw chParams.OONCand datesD sabledCrTagParam)
        val candGeneratedByD sabledS gnal = cand.tagsCR.ex sts { tagsCR =>
          val tagsCRSet = tagsCR.map(_.toStr ng).toSet
          tagsCRSet.nonEmpty && tagsCRSet.subsetOf(d sabledCrTag.toSet)
        }
         f (candGeneratedByD sabledS gnal) {
          cand.tagsCR.getOrElse(N l).foreach(tag => scopedStats.counter(tag.toStr ng). ncr())
          false
        } else true
      }
      .w hStats(scopedStats)
      .w hNa (na )
  }

  def outOfNetworkT etCand dateEnabledCrtGroup(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[OutOfNetworkT etCand date w h Target nfo[TargetUser w h TargetABDec der]] = {
    val na  = "out_of_network_t et_cand date_enabled_crt_group"
    val scopedStats = stats.scope(na )
    Pred cate
      .from[OutOfNetworkT etCand date w h Target nfo[TargetUser w h TargetABDec der]] { cand =>
        val d sabledCrtGroup = cand.target
          .params(PushFeatureSw chParams.OONCand datesD sabledCrtGroupParam)
        val crtGroup = Cand dateUt l.getCrtGroup(cand.commonRecType)
        val candGeneratedByD sabledCrt = d sabledCrtGroup.conta ns(crtGroup)
         f (candGeneratedByD sabledCrt) {
          scopedStats.counter("f lter_" + crtGroup.toStr ng). ncr()
          false
        } else true
      }
      .w hStats(scopedStats)
      .w hNa (na )
  }

  def outOfNetworkT etCand date sNotAReply(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[OutOfNetworkT etCand date] = {
    val na  = "out_of_network_t et_cand date_ s_not_a_reply"
    Pred cate
      .from[OutOfNetworkT etCand date] { cand =>
        cand. sReply match {
          case So (true) => false
          case _ => true
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def recom ndedT et sAuthoredBySelf(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] =
    Pred cate
      .from[PushCand date] {
        case t etCand date: PushCand date w h T etDeta ls =>
          t etCand date.author d match {
            case So (author d) => author d != t etCand date.target.target d
            case None => true
          }
        case _ =>
          true
      }
      .w hStats(statsRece ver.scope("pred cate_self_author"))
      .w hNa ("self_author")

  def author nSoc alContext( mpl c  statsRece ver: StatsRece ver): Na dPred cate[PushCand date] =
    Pred cate
      .from[PushCand date] {
        case t etCand date: PushCand date w h T etDeta ls w h Soc alContextAct ons =>
          t etCand date.author d match {
            case So (author d) =>
              !t etCand date.soc alContextUser ds.conta ns(author d)
            case None => true
          }
        case _ => true
      }
      .w hStats(statsRece ver.scope("pred cate_author_soc al_context"))
      .w hNa ("author_soc al_context")

  def self nSoc alContext( mpl c  statsRece ver: StatsRece ver): Na dPred cate[PushCand date] = {
    val na  = "self_soc al_context"
    Pred cate
      .from[PushCand date] {
        case cand date: PushCand date w h Soc alContextAct ons =>
          !cand date.soc alContextUser ds.conta ns(cand date.target.target d)
        case _ =>
          true
      }
      .w hStats(statsRece ver.scope(s"${na }_pred cate"))
      .w hNa (na )
  }

  def m nSoc alContext(
    threshold:  nt
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h Soc alContextAct ons] = {
    Pred cate
      .from { cand date: PushCand date w h Soc alContextAct ons =>
        cand date.soc alContextUser ds.s ze >= threshold
      }
      .w hStats(statsRece ver.scope("pred cate_m n_soc al_context"))
      .w hNa ("m n_soc al_context")
  }

  pr vate def anyW h ldContent(
    userStore: ReadableStore[Long, User],
    userCountryStore: ReadableStore[Long, Locat on]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Pred cate[TargetRecUser] =
    G zmoduckUserPred cate.w h ldContentPred cate(
      userStore = userStore,
      userCountryStore = userCountryStore,
      statsRece ver = statsRece ver,
      c ckAllCountr es = true
    )

  def targetUserEx sts( mpl c  statsRece ver: StatsRece ver): Na dPred cate[PushCand date] = {
    TargetUserPred cates
      .targetUserEx sts()(statsRece ver)
      .flatContraMap { cand date: PushCand date => Future.value(cand date.target) }
      .w hNa ("target_user_ex sts")
  }

  def secondaryDormantAccountPred cate(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "secondary_dormant_account"
    TargetUserPred cates
      .secondaryDormantAccountPred cate()(statsRece ver)
      .on { cand date: PushCand date => cand date.target }
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def soc alContextBe ngFollo d(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h Soc alContextAct ons] =
    Soc alGraphPred cate
      .allRelat onEdgesEx st(edgeStore, Relat onsh pType.Follow ng)
      .on { cand date: PushCand date w h Soc alContextAct ons =>
        cand date.soc alContextUser ds.map { u => Edge(cand date.target.target d, u) }
      }
      .w hStats(statsRece ver.scope("pred cate_soc al_context_be ng_follo d"))
      .w hNa ("soc al_context_be ng_follo d")

  pr vate def edgeFromCand date(cand date: PushCand date w h T etAuthor): Opt on[Edge] = {
    cand date.author d map { author d => Edge(cand date.target.target d, author d) }
  }

  def authorNotBe ngDev ceFollo d(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    Soc alGraphPred cate
      .relat onEx sts(edgeStore, Relat onsh pType.Dev ceFollow ng)
      .opt onalOn(
        edgeFromCand date,
        m ss ngResult = false
      )
      .fl p
      .w hStats(statsRece ver.scope("pred cate_author_not_dev ce_follo d"))
      .w hNa ("author_not_dev ce_follo d")
  }

  def authorBe ngFollo d(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    Soc alGraphPred cate
      .relat onEx sts(edgeStore, Relat onsh pType.Follow ng)
      .opt onalOn(
        edgeFromCand date,
        m ss ngResult = false
      )
      .w hStats(statsRece ver.scope("pred cate_author_be ng_follo d"))
      .w hNa ("author_be ng_follo d")
  }

  def authorNotBe ngFollo d(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    Soc alGraphPred cate
      .relat onEx sts(edgeStore, Relat onsh pType.Follow ng)
      .opt onalOn(
        edgeFromCand date,
        m ss ngResult = false
      )
      .fl p
      .w hStats(statsRece ver.scope("pred cate_author_not_be ng_follo d"))
      .w hNa ("author_not_be ng_follo d")
  }

  def recom ndedT etAuthorAcceptableToTargetUser(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    val na  = "recom nded_t et_author_acceptable_to_target_user"
    Soc alGraphPred cate
      .anyRelat onEx sts(
        edgeStore,
        Set(
          Relat onsh pType.Block ng,
          Relat onsh pType.BlockedBy,
          Relat onsh pType.H deRecom ndat ons,
          Relat onsh pType.Mut ng
        )
      )
      .fl p
      .opt onalOn(
        edgeFromCand date,
        m ss ngResult = false
      )
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def relat onNotEx stsPred cate(
    edgeStore: ReadableStore[Relat onEdge, Boolean],
    relat ons: Set[Relat onsh pType]
  ): Pred cate[(Long,  erable[Long])] =
    Soc alGraphPred cate
      .anyRelat onEx stsForMult Edge(
        edgeStore,
        relat ons
      )
      .fl p
      .on {
        case (targetUser d, user ds) =>
          Mult Edge(targetUser d, user ds.toSet)
      }

  def block ng(edgeStore: ReadableStore[Relat onEdge, Boolean]): Pred cate[(Long,  erable[Long])] =
    relat onNotEx stsPred cate(
      edgeStore,
      Set(Relat onsh pType.BlockedBy, Relat onsh pType.Block ng)
    )

  def block ngOrMut ng(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  ): Pred cate[(Long,  erable[Long])] =
    relat onNotEx stsPred cate(
      edgeStore,
      Set(Relat onsh pType.BlockedBy, Relat onsh pType.Block ng, Relat onsh pType.Mut ng)
    )

  def soc alContextNotRet etFollow ng(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h Soc alContextAct ons] = {
    val na  = "soc al_context_not_ret et_follow ng"
    relat onNotEx stsPred cate(edgeStore, Set(Relat onsh pType.NotRet etFollow ng))
      .opt onalOn[PushCand date w h Soc alContextAct ons](
        {
          case cand date: PushCand date w h Soc alContextAct ons
               f RecTypes. sT etRet etType(cand date.commonRecType) =>
            So ((cand date.target.target d, cand date.soc alContextUser ds))
          case _ =>
            None
        },
        m ss ngResult = true
      )
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def soc alContextBlock ngOrMut ng(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h Soc alContextAct ons] =
    block ngOrMut ng(edgeStore)
      .on { cand date: PushCand date w h Soc alContextAct ons =>
        (cand date.target.target d, cand date.soc alContextUser ds)
      }
      .w hStats(statsRece ver.scope("pred cate_soc al_context_block ng_or_mut ng"))
      .w hNa ("soc al_context_block ng_or_mut ng")

  /**
   * Use hyrated T et object for F1 Protected exper  nt for c ck ng null cast as T etyp e hydrat on
   * fa ls for protected Authors w hout pass ng  n Target  d.   do t  spec f cally for
   * F1 Protected T et Exper  nt  n Earlyb rd Adaptor.
   * For rest of t  traff c refer to ex st ng Nullcast Pred cate
   */
  def nullCastF1ProtectedExper entPred cate(
    t etyp eStore: ReadableStore[Long, T etyP eResult]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date w h T etDeta ls] = {
    val na  = "f1_exempted_null_cast_t et"
    val f1NullCastC ckCounter = statsRece ver.scope(na ).counter("f1_null_cast_c ck")
    Pred cate
      .fromAsync { t etCand date: PushCand date w h T etCand date w h T etDeta ls =>
         f (RecTypes.f1F rstDegreeTypes(t etCand date.commonRecType) && t etCand date.target
            .params(PushFeatureSw chParams.EnableF1FromProtectedT etAuthors)) {
          f1NullCastC ckCounter. ncr()
          t etCand date.t et match {
            case So (t etObj) =>
              baseNullCastT et().apply(Seq(T etyP eResult(t etObj, None, None))).map(_. ad)
            case _ => Future.False
          }
        } else {
          nullCastT et(t etyp eStore).apply(Seq(t etCand date)).map(_. ad)
        }
      }
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  pr vate def baseNullCastT et(): Pred cate[T etyP eResult] =
    Pred cate.from { t: T etyP eResult => !t.t et.coreData.ex sts { cd => cd.nullcast } }

  def nullCastT et(
    t etyP eStore: ReadableStore[Long, T etyP eResult]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = "null_cast_t et"
    baseNullCastT et()
      .flatOpt onContraMap[PushCand date w h T etCand date](
        f = (t etCand date: PushCand date
          w h T etCand date) => t etyP eStore.get(t etCand date.t et d),
        m ss ngResult = false
      )
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  /**
   * Use t  pred cate except fn  s true.
   */
  def exceptedPred cate[T <: PushCand date](
    na : Str ng,
    fn: T => Future[Boolean],
    pred cate: Pred cate[T]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    Pred cate
      .fromAsync { e: T => fn(e) }
      .or(pred cate)
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  /**
   *
   * @param edgeStore [[ReadableStore[Relat onEdge, Boolean]]]
   * @return - allow only out-network t ets  f  n-network t ets are d sabled
   */
  def d sable nNetworkT etPred cate(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    val na  = "d sable_ n_network_t et"
    Pred cate
      .fromAsync { cand date: PushCand date w h T etAuthor =>
         f (cand date.target.params(PushParams.D sable nNetworkT etCand datesParam)) {
          authorNotBe ngFollo d(edgeStore)
            .apply(Seq(cand date))
            .map(_. ad)
        } else Future.True
      }.w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  /**
   *
   * @param edgeStore [[ReadableStore[Relat onEdge, Boolean]]]
   * @return - allow only  n-network t ets  f out-network t ets are d sabled
   */
  def d sableOutNetworkT etPred cate(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor] = {
    val na  = "d sable_out_network_t et"
    Pred cate
      .fromAsync { cand date: PushCand date w h T etAuthor =>
         f (cand date.target.params(PushFeatureSw chParams.D sableOutNetworkT etCand datesFS)) {
          authorBe ngFollo d(edgeStore)
            .apply(Seq(cand date))
            .map(_. ad)
        } else Future.True
      }.w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def alwaysTruePred cate: Na dPred cate[PushCand date] = {
    Pred cate
      .all[PushCand date]
      .w hNa ("pred cate_AlwaysTrue")
  }

  def alwaysTruePushCand datePred cate: Na dPred cate[PushCand date] = {
    Pred cate
      .all[PushCand date]
      .w hNa ("pred cate_AlwaysTrue")
  }

  def alwaysFalsePred cate( mpl c  statsRece ver: StatsRece ver): Na dPred cate[PushCand date] = {
    val na  = "pred cate_AlwaysFalse"
    val scopedStatsRece ver = statsRece ver.scope(na )
    Pred cate
      .from { cand date: PushCand date => false }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def accountCountryPred cate(
    allo dCountr es: Set[Str ng]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "AccountCountryPred cate"
    val stats = statsRece ver.scope(na )
    AccountCountryPred cate(allo dCountr es)
      .on { cand date: PushCand date => cand date.target }
      .w hStats(stats)
      .w hNa (na )
  }

  def paramPred cate[T <: PushCand date](
    param: Param[Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = param.getClass.getS mpleNa .str pSuff x("$")
    TargetPred cates
      .paramPred cate(param)
      .on { cand date: PushCand date => cand date.target }
      .w hStats(statsRece ver.scope(s"param_${na }_controlled_pred cate"))
      .w hNa (s"param_${na }_controlled_pred cate")
  }

  def  sDev ceEl g bleForNewsOrSports(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = " s_dev ce_el g ble_for_news_or_sports"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date =>
        cand date.target.dev ce nfo.map(_.ex sts(_. sNewsEl g ble))
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def  sDev ceEl g bleForCreatorPush(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = " s_dev ce_el g ble_for_creator_push"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date =>
        cand date.target.dev ce nfo.map(_.ex sts(sett ngs =>
          sett ngs. sNewsEl g ble || sett ngs. sRecom ndat onsEl g ble))
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  /**
   * L ke [[TargetUserPred cates.ho T  l neFat gue()]] but for cand date.
   */
  def htlFat guePred cate(
    fat gueDurat on: Param[Durat on]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "htl_fat gue"
    Pred cate
      .fromAsync { cand date: PushCand date =>
        val _fat gueDurat on = cand date.target.params(fat gueDurat on)
        TargetUserPred cates
          .ho T  l neFat gue(
            fat gueDurat on = _fat gueDurat on
          ).apply(Seq(cand date.target)).map(_. ad)
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def mr bHoldbackPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "mr_ b_holdback_for_cand date"
    val scopedStats = stats.scope(na )
    Pred catesForCand date.exludeCrtFromPushHoldback
      .or(
        TargetPred cates
          . bNot fsHoldback()
          .on { cand date: PushCand date => cand date.target }
      )
      .w hStats(scopedStats)
      .w hNa (na )
  }

  def cand dateEnabledForEma lPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "cand dates_enabled_for_ema l"
    Pred cate
      .from { cand date: PushCand date =>
         f (cand date.target. sEma lUser)
          cand date. s nstanceOf[T etCand date w h T etAuthor w h Recom ndat onType]
        else true
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def protectedT etF1ExemptPred cate[
    T <: TargetUser w h TargetABDec der,
    Cand <: T etCand date w h T etAuthorDeta ls w h Target nfo[T]
  ](
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    T etCand date w h T etAuthorDeta ls w h Target nfo[
      TargetUser w h TargetABDec der
    ]
  ] = {
    val na  = "f1_exempt_t et_author_protected"
    val sk pForProtectedAuthorScope = stats.scope(na ).scope("sk p_protected_author_for_f1")
    val author sProtectedCounter = sk pForProtectedAuthorScope.counter("author_protected_true")
    val author sNotProtectedCounter = sk pForProtectedAuthorScope.counter("author_protected_false")
    val authorNotFoundCounter = stats.scope(na ).counter("author_not_found")
    Pred cate
      .fromAsync[T etCand date w h T etAuthorDeta ls w h Target nfo[
        TargetUser w h TargetABDec der
      ]] {
        case cand date: F1Cand date
             f cand date.target.params(PushFeatureSw chParams.EnableF1FromProtectedT etAuthors) =>
          cand date.t etAuthor.foreach {
            case So (author) =>
               f (G zmoduckUserPred cate. sProtected(author)) {
                author sProtectedCounter. ncr()
              } else author sNotProtectedCounter. ncr()
            case _ => authorNotFoundCounter. ncr()
          }
          Future.True
        case cand =>
          T etAuthorPred cates.recT etAuthorProtected.apply(Seq(cand)).map(_. ad)
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  /**
   * f lter a not f cat on  f user has already rece ved ANY pr or not f cat on about t  space  d
   * @param stats
   * @return
   */
  def dupl cateSpacesPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Space w h PushCand date] = {
    val na  = "dupl cate_spaces_pred cate"
    Pred cate
      .fromAsync { c: Space w h PushCand date =>
        c.target.pushRec ems.map { pushRec ems =>
          !pushRec ems.space ds.conta ns(c.space d)
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def f lterOONCand datePred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "f lter_oon_cand date"

    Pred cate
      .fromAsync[PushCand date] { cand =>
        val crt = cand.commonRecType
        val  sOONCand date =
          RecTypes. sOutOfNetworkT etRecType(crt) || RecTypes.outOfNetworkTop cT etTypes
            .conta ns(crt) || RecTypes. sOutOfNetworkSpaceType(crt) || RecTypes.userTypes.conta ns(
            crt)
         f ( sOONCand date) {
          cand.target.not f cat onsFromOnlyPeople Follow.map {  nNetworkOnly =>
             f ( nNetworkOnly) {
              stats.scope(na , crt.toStr ng).counter(" nNetworkOnlyOn"). ncr()
            } else {
              stats.scope(na , crt.toStr ng).counter(" nNetworkOnlyOff"). ncr()
            }
            !( nNetworkOnly && cand.target.params(
              PushFeatureSw chParams.EnableOONF lter ngBasedOnUserSett ngs))
          }
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def exludeCrtFromPushHoldback(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = Pred cate
    .from { cand date: PushCand date =>
      val crtNa  = cand date.commonRecType.na 
      val target = cand date.target
      target
        .params(PushFeatureSw chParams.CommonRecom ndat onTypeDenyL stPushHoldbacks)
        .ex sts(crtNa .equals gnoreCase)
    }
    .w hStats(stats.scope("exclude_crt_from_push_holdbacks"))

  def enableSendHandlerCand dates( mpl c  stats: StatsRece ver): Na dPred cate[PushCand date] = {
    val na  = "sendhandler_enable_push_recom ndat ons"
    Pred catesForCand date.exludeCrtFromPushHoldback
      .or(Pred catesForCand date.paramPred cate(
        PushFeatureSw chParams.EnablePushRecom ndat onsParam))
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def openAppExper  ntUserCand dateAllowL st(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "open_app_exper  nt_user_cand date_allow_l st"
    Pred cate
      .fromAsync { cand date: PushCand date =>
        val target = cand date.target
        Future.jo n(target. sOpenAppExper  ntUser, target.targetUser).map {
          case ( sOpenAppUser, targetUser) =>
            val shouldL m OpenAppCrts =
               sOpenAppUser || targetUser.ex sts(_.userType == UserType.Soft)

             f (shouldL m OpenAppCrts) {
              val l stOfAllo dCrt = target
                .params(PushFeatureSw chParams.L stOfCrtsForOpenApp)
                .flatMap(CommonRecom ndat onType.valueOf)
              l stOfAllo dCrt.conta ns(cand date.commonRecType)
            } else true
        }
      }.w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def  sTargetBlueVer f ed(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = " s_target_already_blue_ver f ed"
    Pred cate
      .fromAsync { cand date: PushCand date =>
        val target = cand date.target
        target. sBlueVer f ed.map(_.getOrElse(false))
      }.w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def  sTargetLegacyVer f ed(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = " s_target_already_legacy_ver f ed"
    Pred cate
      .fromAsync { cand date: PushCand date =>
        val target = cand date.target
        target. sVer f ed.map(_.getOrElse(false))
      }.w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def  sTargetSuperFollowCreator( mpl c  stats: StatsRece ver): Na dPred cate[PushCand date] = {
    val na  = " s_target_already_super_follow_creator"
    Pred cate
      .fromAsync { cand date: PushCand date =>
        val target = cand date.target
        target. sSuperFollowCreator.map(
          _.getOrElse(false)
        )
      }.w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def  sChannelVal dPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = " s_channel_val d"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date =>
        cand date
          .getChannels().map(channels =>
            !(channels.toSet.s ze == 1 && channels. ad == ChannelNa .None))
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }
}
