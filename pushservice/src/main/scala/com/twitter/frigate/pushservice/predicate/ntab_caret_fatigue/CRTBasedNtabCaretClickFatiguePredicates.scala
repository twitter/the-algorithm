package com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.not f cat onserv ce.thr ftscala.Gener cType
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter.not f cat onserv ce.gener cfeedbackstore.FeedbackPromptValue
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.fr gate.common.base.Cand date
 mport com.tw ter.fr gate.common.base.Recom ndat onType
 mport com.tw ter.fr gate.common.base.Target nfo
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.SeeLessOftenType
 mport com.tw ter.fr gate.common. tory. tory
 mport com.tw ter.fr gate.common.pred cate.Fr gate toryFat guePred cate.T  Ser es
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.common.pred cate.ntab_caret_fat gue.NtabCaretCl ckFat guePred cate lper
 mport com.tw ter.fr gate.pushserv ce.pred cate.CaretFeedback toryF lter
 mport com.tw ter.not f cat onserv ce.thr ftscala.CaretFeedbackDeta ls
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.fr gate.common.pred cate.Fat guePred cate
 mport com.tw ter.fr gate.pushserv ce.ut l.PushCapUt l
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l

object CRTBasedNtabCaretCl ckFat guePred cates {

  pr vate val Mag cRecsCategory = "Mag cRecs"

  pr vate val H ghQual yRefreshableTypes: Set[Opt on[Str ng]] = Set(
    So ("Mag cRecH ghQual yT et"),
  )

  pr vate def getUserState  ght(target: Target): Future[Double] = {
    PushDev ceUt l. sNtabOnlyEl g ble.map {
      case true =>
        target.params(PushFeatureSw chParams.SeeLessOftenNtabOnlyNot fUserPushCap  ght)
      case _ => 1.0
    }
  }

  def crtToSeeLessOftenType(
    crt: CommonRecom ndat onType,
    cand date: Cand date
      w h Recom ndat onType
      w h Target nfo[
        Target
      ],
  ): SeeLessOftenType = {
    val crtToSeeLessOftenTypeMap: Map[CommonRecom ndat onType, SeeLessOftenType] = {
      RecTypes.f1F rstDegreeTypes.map((_, SeeLessOftenType.F1Type)).toMap
    }

    crtToSeeLessOftenTypeMap.getOrElse(crt, SeeLessOftenType.Ot rTypes)
  }

  def gener cTypeToSeeLessOftenType(
    gener cType: Gener cType,
    cand date: Cand date
      w h Recom ndat onType
      w h Target nfo[
        Target
      ]
  ): SeeLessOftenType = {
    val gener cTypeToSeeLessOftenTypeMap: Map[Gener cType, SeeLessOftenType] = {
      Map(Gener cType.Mag cRecF rstDegreeT etRecent -> SeeLessOftenType.F1Type)
    }

    gener cTypeToSeeLessOftenTypeMap.getOrElse(gener cType, SeeLessOftenType.Ot rTypes)
  }

  def get  ghtForCaretFeedback(
    d sl kedType: SeeLessOftenType,
    cand date: Cand date
      w h Recom ndat onType
      w h Target nfo[
        Target
      ]
  ): Double = {
    def get  ghtFromD sl kedAndCurrentType(
      d sl kedType: SeeLessOftenType,
      currentType: SeeLessOftenType
    ): Double = {
      val   ghtMap: Map[(SeeLessOftenType, SeeLessOftenType), Double] = {

        Map(
          (SeeLessOftenType.F1Type, SeeLessOftenType.F1Type) -> cand date.target.params(
            PushFeatureSw chParams.SeeLessOftenF1Tr ggerF1PushCap  ght),
          (SeeLessOftenType.Ot rTypes, SeeLessOftenType.Ot rTypes) -> cand date.target.params(
            PushFeatureSw chParams.SeeLessOftenNonF1Tr ggerNonF1PushCap  ght),
          (SeeLessOftenType.F1Type, SeeLessOftenType.Ot rTypes) -> cand date.target.params(
            PushFeatureSw chParams.SeeLessOftenF1Tr ggerNonF1PushCap  ght),
          (SeeLessOftenType.Ot rTypes, SeeLessOftenType.F1Type) -> cand date.target.params(
            PushFeatureSw chParams.SeeLessOftenNonF1Tr ggerF1PushCap  ght)
        )
      }

        ghtMap
        .getOrElse(
          (d sl kedType, currentType),
          cand date.target.params(PushFeatureSw chParams.SeeLessOftenDefaultPushCap  ght))
    }

    get  ghtFromD sl kedAndCurrentType(
      d sl kedType,
      crtToSeeLessOftenType(cand date.commonRecType, cand date))
  }

  pr vate def  sOuts deCrtBasedNtabCaretCl ckFat guePer odContFn(
    cand date: Cand date
      w h Recom ndat onType
      w h Target nfo[
        Target
      ],
     tory:  tory,
    feedbackDeta ls: Seq[CaretFeedbackDeta ls],
    f lter tory: T  Ser es => T  Ser es =
      Fat guePred cate.recTypesOnlyF lter(RecTypes.sharedNTabCaretFat gueTypes),
    f lterCaretFeedback tory: Target => Seq[
      CaretFeedbackDeta ls
    ] => Seq[CaretFeedbackDeta ls] =
      CaretFeedback toryF lter.caretFeedback toryF lter(Seq(Mag cRecsCategory)),
    knobs: Seq[Double],
    pushCapKnobs: Seq[Double],
    po rKnobs: Seq[Double],
    f1  ght: Double,
    nonF1  ght: Double,
    defaultPushCap:  nt,
    stats: StatsRece ver,
    tr pHqT et  ght: Double = 0.0,
  ): Boolean = {
    val f lteredFeedbackDeta ls = f lterCaretFeedback tory(cand date.target)(feedbackDeta ls)
    val   ght = {
       f (RecTypes.H ghQual yT etTypes.conta ns(
          cand date.commonRecType) && (tr pHqT et  ght != 0)) {
        tr pHqT et  ght
      } else  f (RecTypes. sF1Type(cand date.commonRecType)) {
        f1  ght
      } else {
        nonF1  ght
      }
    }
    val f ltered tory =  tory(f lter tory( tory. tory.toSeq).toMap)
     sOuts deFat guePer od(
      f ltered tory,
      f lteredFeedbackDeta ls,
      Seq(),
      Cont nuousFunct onParam(
        knobs,
        pushCapKnobs,
        po rKnobs,
          ght,
        defaultPushCap
      ),
      stats.scope(
         f (RecTypes. sF1Type(cand date.commonRecType)) "mr_ntab_d sl ke_f1_cand date_fn"
        else  f (RecTypes.H ghQual yT etTypes.conta ns(cand date.commonRecType))
          "mr_ntab_d sl ke_h gh_qual y_cand date_fn"
        else "mr_ntab_d sl ke_nonf1_cand date_fn")
    )
  }

  pr vate def  sOuts deFat guePer od(
     tory:  tory,
    feedbackDeta ls: Seq[CaretFeedbackDeta ls],
    feedbacks: Seq[FeedbackModel],
    param: Cont nuousFunct onParam,
    stats: StatsRece ver
  ): Boolean = {
    val fat guePer od: Durat on =
      NtabCaretCl ckFat gueUt ls.durat onToF lterForFeedback(
        feedbackDeta ls,
        feedbacks,
        param,
        param.defaultValue,
        stats
      )

    val hasRecentSent =
      NtabCaretCl ckFat guePred cate lper.hasRecentSend( tory, fat guePer od)
    !hasRecentSent

  }

  def gener cCRTBasedNtabCaretCl ckFnFat guePred cate[
    Cand <: Cand date w h Recom ndat onType w h Target nfo[
      Target
    ]
  ](
    f lter tory: T  Ser es => T  Ser es =
      Fat guePred cate.recTypesOnlyF lter(RecTypes.sharedNTabCaretFat gueTypes),
    f lterCaretFeedback tory: Target => Seq[
      CaretFeedbackDeta ls
    ] => Seq[CaretFeedbackDeta ls] = CaretFeedback toryF lter
      .caretFeedback toryF lter(Seq(Mag cRecsCategory)),
    f lter nl neFeedback tory: Seq[FeedbackModel] => Seq[FeedbackModel] =
      NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(RecTypes.sharedNTabCaretFat gueTypes)
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Cand] = {
    val pred cateNa  = "gener c_crt_based_ntab_d sl ke_fat gue_fn"
    Pred cate
      .fromAsync[Cand] { cand: Cand =>
        {
           f (!cand.target.params(PushFeatureSw chParams.EnableGener cCRTBasedFat guePred cate)) {
            Future.True
          } else {
            val scopedStats = stats.scope(pred cateNa )
            val totalRequests = scopedStats.counter("mr_ntab_d sl ke_total")
            val total90Day =
              scopedStats.counter("mr_ntab_d sl ke_90day_d sl ke")
            val totalD sabled =
              scopedStats.counter("mr_ntab_d sl ke_not_90day_d sl ke")
            val totalSuccess = scopedStats.counter("mr_ntab_d sl ke_success")
            val totalF ltered = scopedStats.counter("mr_ntab_d sl ke_f ltered")
            val totalW h tory =
              scopedStats.counter("mr_ntab_d sl ke_w h_ tory")
            val totalW hout tory =
              scopedStats.counter("mr_ntab_d sl ke_w hout_ tory")
            totalRequests. ncr()

            Future
              .jo n(
                cand.target. tory,
                cand.target.caretFeedbacks,
                cand.target.dynam cPushcap,
                cand.target.optoutAdjustedPushcap,
                PushCapUt l.getDefaultPushCap(cand.target),
                getUserState  ght(cand.target)
              ).map {
                case (
                       tory,
                      So (feedbackDeta ls),
                      dynam cPushcapOpt,
                      optoutAdjustedPushcapOpt,
                      defaultPushCap,
                      userState  ght) => {
                  totalW h tory. ncr()

                  val feedbackDeta lsDeduped =
                    NtabCaretCl ckFat guePred cate lper.dedupFeedbackDeta ls(
                      f lterCaretFeedback tory(cand.target)(feedbackDeta ls),
                      stats
                    )

                  val pushCap:  nt = (dynam cPushcapOpt, optoutAdjustedPushcapOpt) match {
                    case (_, So (optoutAdjustedPushcap)) => optoutAdjustedPushcap
                    case (So (pushcap nfo), _) => pushcap nfo.pushcap
                    case _ => defaultPushCap
                  }
                  val f ltered tory =  tory(f lter tory( tory. tory.toSeq).toMap)

                  val hasUserD sl ke nLast90Days =
                    NtabCaretCl ckFat gueUt ls.hasUserD sl ke nLast90Days(feedbackDeta lsDeduped)
                  val  sF1Tr ggerFat gueEnabled = cand.target
                    .params(PushFeatureSw chParams.EnableContFnF1Tr ggerSeeLessOftenFat gue)
                  val  sNonF1Tr ggerFat gueEnabled = cand.target.params(
                    PushFeatureSw chParams.EnableContFnNonF1Tr ggerSeeLessOftenFat gue)

                  val  sOut sdeSeeLessOftenFat gue =
                     f (hasUserD sl ke nLast90Days && ( sF1Tr ggerFat gueEnabled ||  sNonF1Tr ggerFat gueEnabled)) {
                      total90Day. ncr()

                      val feedbackDeta lsGroupedBySeeLessOftenType: Map[Opt on[
                        SeeLessOftenType
                      ], Seq[
                        CaretFeedbackDeta ls
                      ]] = feedbackDeta ls.groupBy(feedbackDeta l =>
                        feedbackDeta l.gener cNot f cat on tadata.map(x =>
                          gener cTypeToSeeLessOftenType(x.gener cType, cand)))

                      val  sOuts deFat guePer odSeq =
                        for (elem <- feedbackDeta lsGroupedBySeeLessOftenType  f elem._1. sDef ned)
                          y eld {
                            val d sl kedSeeLessOftenType: SeeLessOftenType = elem._1.get
                            val seqCaretFeedbackDeta ls: Seq[CaretFeedbackDeta ls] = elem._2

                            val   ght = get  ghtForCaretFeedback(
                              d sl kedSeeLessOftenType,
                              cand) * userState  ght

                             f ( sOuts deFat guePer od(
                                 tory = f ltered tory,
                                feedbackDeta ls = seqCaretFeedbackDeta ls,
                                feedbacks = Seq(),
                                param = Cont nuousFunct onParam(
                                  knobs = cand.target
                                    .params(PushFeatureSw chParams.SeeLessOftenL stOfDayKnobs),
                                  knobValues = cand.target
                                    .params(
                                      PushFeatureSw chParams.SeeLessOftenL stOfPushCap  ghtKnobs).map(
                                      _ * pushCap),
                                  po rs = cand.target
                                    .params(PushFeatureSw chParams.SeeLessOftenL stOfPo rKnobs),
                                    ght =   ght,
                                  defaultValue = pushCap
                                ),
                                scopedStats
                              )) {
                              true
                            } else {
                              false
                            }
                          }

                       sOuts deFat guePer odSeq.forall( dent y)
                    } else {
                      totalD sabled. ncr()
                      true
                    }

                   f ( sOut sdeSeeLessOftenFat gue) {
                    totalSuccess. ncr()
                  } else totalF ltered. ncr()

                   sOut sdeSeeLessOftenFat gue
                }

                case _ =>
                  totalSuccess. ncr()
                  totalW hout tory. ncr()
                  true
              }
          }
        }
      }.w hStats(stats.scope(pred cateNa ))
      .w hNa (pred cateNa )
  }

  def f1Tr ggeredCRTBasedNtabCaretCl ckFnFat guePred cate[
    Cand <: Cand date w h Recom ndat onType w h Target nfo[
      Target
    ]
  ](
    f lter tory: T  Ser es => T  Ser es =
      Fat guePred cate.recTypesOnlyF lter(RecTypes.sharedNTabCaretFat gueTypes),
    f lterCaretFeedback tory: Target => Seq[
      CaretFeedbackDeta ls
    ] => Seq[CaretFeedbackDeta ls] = CaretFeedback toryF lter
      .caretFeedback toryF lter(Seq(Mag cRecsCategory)),
    f lter nl neFeedback tory: Seq[FeedbackModel] => Seq[FeedbackModel] =
      NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(RecTypes.sharedNTabCaretFat gueTypes)
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Cand] = {
    val pred cateNa  = "f1_tr ggered_crt_based_ntab_d sl ke_fat gue_fn"
    Pred cate
      .fromAsync[Cand] { cand: Cand =>
        {
          val scopedStats = stats.scope(pred cateNa )
          val totalRequests = scopedStats.counter("mr_ntab_d sl ke_total")
          val total90Day =
            scopedStats.counter("mr_ntab_d sl ke_90day_d sl ke")
          val totalD sabled =
            scopedStats.counter("mr_ntab_d sl ke_not_90day_d sl ke")
          val totalSuccess = scopedStats.counter("mr_ntab_d sl ke_success")
          val totalF ltered = scopedStats.counter("mr_ntab_d sl ke_f ltered")
          val totalW h tory =
            scopedStats.counter("mr_ntab_d sl ke_w h_ tory")
          val totalW hout tory =
            scopedStats.counter("mr_ntab_d sl ke_w hout_ tory")
          totalRequests. ncr()

          Future
            .jo n(
              cand.target. tory,
              cand.target.caretFeedbacks,
              cand.target.dynam cPushcap,
              cand.target.optoutAdjustedPushcap,
              cand.target.not f cat onFeedbacks,
              PushCapUt l.getDefaultPushCap(cand.target),
              getUserState  ght(cand.target)
            ).map {
              case (
                     tory,
                    So (feedbackDeta ls),
                    dynam cPushcapOpt,
                    optoutAdjustedPushcapOpt,
                    So (feedbacks),
                    defaultPushCap,
                    userState  ght) =>
                totalW h tory. ncr()

                val feedbackDeta lsDeduped =
                  NtabCaretCl ckFat guePred cate lper.dedupFeedbackDeta ls(
                    f lterCaretFeedback tory(cand.target)(feedbackDeta ls),
                    stats
                  )

                val pushCap:  nt = (dynam cPushcapOpt, optoutAdjustedPushcapOpt) match {
                  case (_, So (optoutAdjustedPushcap)) => optoutAdjustedPushcap
                  case (So (pushcap nfo), _) => pushcap nfo.pushcap
                  case _ => defaultPushCap
                }
                val f ltered tory =  tory(f lter tory( tory. tory.toSeq).toMap)

                val  sOuts de nl neD sl keFat gue =
                   f (cand.target
                      .params(PushFeatureSw chParams.EnableContFnF1Tr gger nl neFeedbackFat gue)) {
                    val   ght =
                       f (RecTypes. sF1Type(cand.commonRecType)) {
                        cand.target
                          .params(PushFeatureSw chParams. nl neFeedbackF1Tr ggerF1PushCap  ght)
                      } else {
                        cand.target
                          .params(PushFeatureSw chParams. nl neFeedbackF1Tr ggerNonF1PushCap  ght)
                      }

                    val  nl neFeedbackFat gueParam = Cont nuousFunct onParam(
                      cand.target
                        .params(PushFeatureSw chParams. nl neFeedbackL stOfDayKnobs),
                      cand.target
                        .params(PushFeatureSw chParams. nl neFeedbackL stOfPushCap  ghtKnobs)
                        .map(_ * pushCap),
                      cand.target
                        .params(PushFeatureSw chParams. nl neFeedbackL stOfPo rKnobs),
                        ght,
                      pushCap
                    )

                     s nl neD sl keOuts deFat guePer od(
                      cand,
                      feedbacks
                        .collect {
                          case feedbackPromptValue: FeedbackPromptValue =>
                             nl neFeedbackModel(feedbackPromptValue, None)
                        },
                      f ltered tory,
                      Seq(
                        f lter nl neFeedback tory,
                        NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(
                          RecTypes.f1F rstDegreeTypes)),
                       nl neFeedbackFat gueParam,
                      scopedStats
                    )
                  } else true

                lazy val  sOuts dePromptD sl keFat gue =
                   f (cand.target
                      .params(PushFeatureSw chParams.EnableContFnF1Tr ggerPromptFeedbackFat gue)) {
                    val   ght =
                       f (RecTypes. sF1Type(cand.commonRecType)) {
                        cand.target
                          .params(PushFeatureSw chParams.PromptFeedbackF1Tr ggerF1PushCap  ght)
                      } else {
                        cand.target
                          .params(PushFeatureSw chParams.PromptFeedbackF1Tr ggerNonF1PushCap  ght)
                      }

                    val promptFeedbackFat gueParam = Cont nuousFunct onParam(
                      cand.target
                        .params(PushFeatureSw chParams.PromptFeedbackL stOfDayKnobs),
                      cand.target
                        .params(PushFeatureSw chParams.PromptFeedbackL stOfPushCap  ghtKnobs)
                        .map(_ * pushCap),
                      cand.target
                        .params(PushFeatureSw chParams.PromptFeedbackL stOfPo rKnobs),
                        ght,
                      pushCap
                    )

                     sPromptD sl keOuts deFat guePer od(
                      feedbacks
                        .collect {
                          case feedbackPromptValue: FeedbackPromptValue =>
                            PromptFeedbackModel(feedbackPromptValue, None)
                        },
                      f ltered tory,
                      Seq(
                        f lter nl neFeedback tory,
                        NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(
                          RecTypes.f1F rstDegreeTypes)),
                      promptFeedbackFat gueParam,
                      scopedStats
                    )
                  } else true

                 sOuts de nl neD sl keFat gue &&  sOuts dePromptD sl keFat gue

              case _ =>
                totalSuccess. ncr()
                totalW hout tory. ncr()
                true
            }
        }
      }.w hStats(stats.scope(pred cateNa ))
      .w hNa (pred cateNa )
  }

  def nonF1Tr ggeredCRTBasedNtabCaretCl ckFnFat guePred cate[
    Cand <: Cand date w h Recom ndat onType w h Target nfo[
      Target
    ]
  ](
    f lter tory: T  Ser es => T  Ser es =
      Fat guePred cate.recTypesOnlyF lter(RecTypes.sharedNTabCaretFat gueTypes),
    f lterCaretFeedback tory: Target => Seq[
      CaretFeedbackDeta ls
    ] => Seq[CaretFeedbackDeta ls] = CaretFeedback toryF lter
      .caretFeedback toryF lter(Seq(Mag cRecsCategory)),
    f lter nl neFeedback tory: Seq[FeedbackModel] => Seq[FeedbackModel] =
      NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(RecTypes.sharedNTabCaretFat gueTypes)
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Cand] = {
    val pred cateNa  = "non_f1_tr ggered_crt_based_ntab_d sl ke_fat gue_fn"
    Pred cate
      .fromAsync[Cand] { cand: Cand =>
        {
          val scopedStats = stats.scope(pred cateNa )
          val totalRequests = scopedStats.counter("mr_ntab_d sl ke_total")
          val total90Day =
            scopedStats.counter("mr_ntab_d sl ke_90day_d sl ke")
          val totalD sabled =
            scopedStats.counter("mr_ntab_d sl ke_not_90day_d sl ke")
          val totalSuccess = scopedStats.counter("mr_ntab_d sl ke_success")
          val totalF ltered = scopedStats.counter("mr_ntab_d sl ke_f ltered")
          val totalW h tory =
            scopedStats.counter("mr_ntab_d sl ke_w h_ tory")
          val totalW hout tory =
            scopedStats.counter("mr_ntab_d sl ke_w hout_ tory")
          val totalFeedbackSuccess = scopedStats.counter("mr_total_feedback_success")
          totalRequests. ncr()

          Future
            .jo n(
              cand.target. tory,
              cand.target.caretFeedbacks,
              cand.target.dynam cPushcap,
              cand.target.optoutAdjustedPushcap,
              cand.target.not f cat onFeedbacks,
              PushCapUt l.getDefaultPushCap(cand.target),
              getUserState  ght(cand.target),
            ).map {
              case (
                     tory,
                    So (feedbackDeta ls),
                    dynam cPushcapOpt,
                    optoutAdjustedPushcapOpt,
                    So (feedbacks),
                    defaultPushCap,
                    userState  ght) =>
                totalW h tory. ncr()

                val f lteredfeedbackDeta ls =
                   f (cand.target.params(
                      PushFeatureSw chParams.AdjustTr pHqT etTr ggeredNtabCaretCl ckFat gue)) {
                    val refreshableTypeF lter = CaretFeedback toryF lter
                      .caretFeedback toryF lterByRefreshableTypeDenyL st(
                        H ghQual yRefreshableTypes)
                    refreshableTypeF lter(cand.target)(feedbackDeta ls)
                  } else {
                    feedbackDeta ls
                  }

                val feedbackDeta lsDeduped =
                  NtabCaretCl ckFat guePred cate lper.dedupFeedbackDeta ls(
                    f lterCaretFeedback tory(cand.target)(f lteredfeedbackDeta ls),
                    stats
                  )

                val pushCap:  nt = (dynam cPushcapOpt, optoutAdjustedPushcapOpt) match {
                  case (_, So (optoutAdjustedPushcap)) => optoutAdjustedPushcap
                  case (So (pushcap nfo), _) => pushcap nfo.pushcap
                  case _ => defaultPushCap
                }
                val f ltered tory =  tory(f lter tory( tory. tory.toSeq).toMap)

                val  sOuts de nl neD sl keFat gue =
                   f (cand.target
                      .params(
                        PushFeatureSw chParams.EnableContFnNonF1Tr gger nl neFeedbackFat gue)) {
                    val   ght =
                       f (RecTypes. sF1Type(cand.commonRecType))
                        cand.target
                          .params(PushFeatureSw chParams. nl neFeedbackNonF1Tr ggerF1PushCap  ght)
                      else
                        cand.target
                          .params(
                            PushFeatureSw chParams. nl neFeedbackNonF1Tr ggerNonF1PushCap  ght)

                    val  nl neFeedbackFat gueParam = Cont nuousFunct onParam(
                      cand.target
                        .params(PushFeatureSw chParams. nl neFeedbackL stOfDayKnobs),
                      cand.target
                        .params(PushFeatureSw chParams. nl neFeedbackL stOfPushCap  ghtKnobs)
                        .map(_ * pushCap),
                      cand.target
                        .params(PushFeatureSw chParams. nl neFeedbackL stOfPo rKnobs),
                        ght,
                      pushCap
                    )

                    val excludedCRTs: Set[CommonRecom ndat onType] =
                       f (cand.target.params(
                          PushFeatureSw chParams.AdjustTr pHqT etTr ggeredNtabCaretCl ckFat gue)) {
                        RecTypes.f1F rstDegreeTypes ++ RecTypes.H ghQual yT etTypes
                      } else {
                        RecTypes.f1F rstDegreeTypes
                      }

                     s nl neD sl keOuts deFat guePer od(
                      cand,
                      feedbacks
                        .collect {
                          case feedbackPromptValue: FeedbackPromptValue =>
                             nl neFeedbackModel(feedbackPromptValue, None)
                        },
                      f ltered tory,
                      Seq(
                        f lter nl neFeedback tory,
                        NtabCaretCl ckFat gueUt ls.feedbackModelExcludeCRT(excludedCRTs)),
                       nl neFeedbackFat gueParam,
                      scopedStats
                    )
                  } else true

                lazy val  sOuts dePromptD sl keFat gue =
                   f (cand.target
                      .params(
                        PushFeatureSw chParams.EnableContFnNonF1Tr ggerPromptFeedbackFat gue)) {
                    val   ght =
                       f (RecTypes. sF1Type(cand.commonRecType))
                        cand.target
                          .params(PushFeatureSw chParams.PromptFeedbackNonF1Tr ggerF1PushCap  ght)
                      else
                        cand.target
                          .params(
                            PushFeatureSw chParams.PromptFeedbackNonF1Tr ggerNonF1PushCap  ght)

                    val promptFeedbackFat gueParam = Cont nuousFunct onParam(
                      cand.target
                        .params(PushFeatureSw chParams.PromptFeedbackL stOfDayKnobs),
                      cand.target
                        .params(PushFeatureSw chParams.PromptFeedbackL stOfPushCap  ghtKnobs)
                        .map(_ * pushCap),
                      cand.target
                        .params(PushFeatureSw chParams.PromptFeedbackL stOfPo rKnobs),
                        ght,
                      pushCap
                    )

                     sPromptD sl keOuts deFat guePer od(
                      feedbacks
                        .collect {
                          case feedbackPromptValue: FeedbackPromptValue =>
                            PromptFeedbackModel(feedbackPromptValue, None)
                        },
                      f ltered tory,
                      Seq(
                        f lter nl neFeedback tory,
                        NtabCaretCl ckFat gueUt ls.feedbackModelExcludeCRT(
                          RecTypes.f1F rstDegreeTypes)),
                      promptFeedbackFat gueParam,
                      scopedStats
                    )
                  } else true

                 sOuts de nl neD sl keFat gue &&  sOuts dePromptD sl keFat gue
              case _ =>
                totalFeedbackSuccess. ncr()
                totalW hout tory. ncr()
                true
            }
        }
      }.w hStats(stats.scope(pred cateNa ))
      .w hNa (pred cateNa )
  }

  def tr pHqT etTr ggeredCRTBasedNtabCaretCl ckFnFat guePred cate[
    Cand <: Cand date w h Recom ndat onType w h Target nfo[
      Target
    ]
  ](
    f lter tory: T  Ser es => T  Ser es =
      Fat guePred cate.recTypesOnlyF lter(RecTypes.sharedNTabCaretFat gueTypes),
    f lterCaretFeedback tory: Target => Seq[
      CaretFeedbackDeta ls
    ] => Seq[CaretFeedbackDeta ls] = CaretFeedback toryF lter
      .caretFeedback toryF lter(Seq(Mag cRecsCategory)),
    f lter nl neFeedback tory: Seq[FeedbackModel] => Seq[FeedbackModel] =
      NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(RecTypes.sharedNTabCaretFat gueTypes)
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Cand] = {
    val pred cateNa  = "tr p_hq_t et_tr ggered_crt_based_ntab_d sl ke_fat gue_fn"
    Pred cate
      .fromAsync[Cand] { cand: Cand =>
        {
          val scopedStats = stats.scope(pred cateNa )
          val totalRequests = scopedStats.counter("mr_ntab_d sl ke_total")
          val total90Day =
            scopedStats.counter("mr_ntab_d sl ke_90day_d sl ke")
          val totalD sabled =
            scopedStats.counter("mr_ntab_d sl ke_not_90day_d sl ke")
          val totalSuccess = scopedStats.counter("mr_ntab_d sl ke_success")
          val totalF ltered = scopedStats.counter("mr_ntab_d sl ke_f ltered")
          val totalW h tory =
            scopedStats.counter("mr_ntab_d sl ke_w h_ tory")
          val totalW hout tory =
            scopedStats.counter("mr_ntab_d sl ke_w hout_ tory")
          val totalFeedbackSuccess = scopedStats.counter("mr_total_feedback_success")
          totalRequests. ncr()

          Future
            .jo n(
              cand.target. tory,
              cand.target.caretFeedbacks,
              cand.target.dynam cPushcap,
              cand.target.optoutAdjustedPushcap,
              cand.target.not f cat onFeedbacks,
              PushCapUt l.getDefaultPushCap(cand.target),
              getUserState  ght(cand.target),
            ).map {
              case (
                     tory,
                    So (feedbackDeta ls),
                    dynam cPushcapOpt,
                    optoutAdjustedPushcapOpt,
                    So (feedbacks),
                    defaultPushCap,
                    userState  ght) =>
                totalW h tory. ncr()
                 f (cand.target.params(
                    PushFeatureSw chParams.AdjustTr pHqT etTr ggeredNtabCaretCl ckFat gue)) {

                  val refreshableTypeF lter = CaretFeedback toryF lter
                    .caretFeedback toryF lterByRefreshableType(H ghQual yRefreshableTypes)
                  val f lteredfeedbackDeta ls = refreshableTypeF lter(cand.target)(feedbackDeta ls)

                  val feedbackDeta lsDeduped =
                    NtabCaretCl ckFat guePred cate lper.dedupFeedbackDeta ls(
                      f lterCaretFeedback tory(cand.target)(f lteredfeedbackDeta ls),
                      stats
                    )

                  val pushCap:  nt = (dynam cPushcapOpt, optoutAdjustedPushcapOpt) match {
                    case (_, So (optoutAdjustedPushcap)) => optoutAdjustedPushcap
                    case (So (pushcap nfo), _) => pushcap nfo.pushcap
                    case _ => defaultPushCap
                  }
                  val f ltered tory =  tory(f lter tory( tory. tory.toSeq).toMap)

                  val  sOuts de nl neD sl keFat gue =
                     f (cand.target
                        .params(
                          PushFeatureSw chParams.EnableContFnNonF1Tr gger nl neFeedbackFat gue)) {
                      val   ght = {
                         f (RecTypes.H ghQual yT etTypes.conta ns(cand.commonRecType)) {
                          cand.target
                            .params(
                              PushFeatureSw chParams. nl neFeedbackNonF1Tr ggerNonF1PushCap  ght)
                        } else {
                          cand.target
                            .params(
                              PushFeatureSw chParams. nl neFeedbackNonF1Tr ggerF1PushCap  ght)
                        }
                      }

                      val  nl neFeedbackFat gueParam = Cont nuousFunct onParam(
                        cand.target
                          .params(PushFeatureSw chParams. nl neFeedbackL stOfDayKnobs),
                        cand.target
                          .params(PushFeatureSw chParams. nl neFeedbackL stOfPushCap  ghtKnobs)
                          .map(_ * pushCap),
                        cand.target
                          .params(PushFeatureSw chParams. nl neFeedbackL stOfPo rKnobs),
                          ght,
                        pushCap
                      )

                      val  ncludedCRTs: Set[CommonRecom ndat onType] =
                        RecTypes.H ghQual yT etTypes

                       s nl neD sl keOuts deFat guePer od(
                        cand,
                        feedbacks
                          .collect {
                            case feedbackPromptValue: FeedbackPromptValue =>
                               nl neFeedbackModel(feedbackPromptValue, None)
                          },
                        f ltered tory,
                        Seq(
                          f lter nl neFeedback tory,
                          NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT( ncludedCRTs)),
                         nl neFeedbackFat gueParam,
                        scopedStats
                      )
                    } else true

                  lazy val  sOuts dePromptD sl keFat gue =
                     f (cand.target
                        .params(
                          PushFeatureSw chParams.EnableContFnNonF1Tr ggerPromptFeedbackFat gue)) {
                      val   ght =
                         f (RecTypes. sF1Type(cand.commonRecType))
                          cand.target
                            .params(
                              PushFeatureSw chParams.PromptFeedbackNonF1Tr ggerF1PushCap  ght)
                        else
                          cand.target
                            .params(
                              PushFeatureSw chParams.PromptFeedbackNonF1Tr ggerNonF1PushCap  ght)

                      val promptFeedbackFat gueParam = Cont nuousFunct onParam(
                        cand.target
                          .params(PushFeatureSw chParams.PromptFeedbackL stOfDayKnobs),
                        cand.target
                          .params(PushFeatureSw chParams.PromptFeedbackL stOfPushCap  ghtKnobs)
                          .map(_ * pushCap),
                        cand.target
                          .params(PushFeatureSw chParams.PromptFeedbackL stOfPo rKnobs),
                          ght,
                        pushCap
                      )

                       sPromptD sl keOuts deFat guePer od(
                        feedbacks
                          .collect {
                            case feedbackPromptValue: FeedbackPromptValue =>
                              PromptFeedbackModel(feedbackPromptValue, None)
                          },
                        f ltered tory,
                        Seq(
                          f lter nl neFeedback tory,
                          NtabCaretCl ckFat gueUt ls.feedbackModelExcludeCRT(
                            RecTypes.f1F rstDegreeTypes)),
                        promptFeedbackFat gueParam,
                        scopedStats
                      )
                    } else true

                   sOuts de nl neD sl keFat gue &&  sOuts dePromptD sl keFat gue
                } else {
                  true
                }
              case _ =>
                totalFeedbackSuccess. ncr()
                totalW hout tory. ncr()
                true
            }
        }
      }.w hStats(stats.scope(pred cateNa ))
      .w hNa (pred cateNa )
  }

  pr vate def getDeduped nl neFeedbackByType(
     nl neFeedbacks: Seq[FeedbackModel],
    feedbackType: FeedbackTypeEnum.Value,
    revertedFeedbackType: FeedbackTypeEnum.Value
  ): Seq[FeedbackModel] = {
     nl neFeedbacks
      .f lter(feedback =>
        feedback.feedbackTypeEnum == feedbackType ||
          feedback.feedbackTypeEnum == revertedFeedbackType)
      .groupBy(feedback => feedback.not f cat on mpress on d.getOrElse(""))
      .toSeq
      .collect {
        case ( mpress on d, feedbacks: Seq[FeedbackModel])  f (feedbacks.nonEmpty) =>
          val latestFeedback = feedbacks.maxBy(feedback => feedback.t  stampMs)
           f (latestFeedback.feedbackTypeEnum == feedbackType)
            So (latestFeedback)
          else None
        case _ => None
      }
      .flatten
  }

  pr vate def getDeduped nl neFeedback(
     nl neFeedbacks: Seq[FeedbackModel],
    target: Target
  ): Seq[FeedbackModel] = {
    val  nl neD sl keFeedback =
       f (target.params(PushFeatureSw chParams.Use nl neD sl keForFat gue)) {
        getDeduped nl neFeedbackByType(
           nl neFeedbacks,
          FeedbackTypeEnum. nl neD sl ke,
          FeedbackTypeEnum. nl neRevertedD sl ke)
      } else Seq()
    val  nl neD sm ssFeedback =
       f (target.params(PushFeatureSw chParams.Use nl neD sm ssForFat gue)) {
        getDeduped nl neFeedbackByType(
           nl neFeedbacks,
          FeedbackTypeEnum. nl neD sm ss,
          FeedbackTypeEnum. nl neRevertedD sm ss)
      } else Seq()
    val  nl neSeeLessFeedback =
       f (target.params(PushFeatureSw chParams.Use nl neSeeLessForFat gue)) {
        getDeduped nl neFeedbackByType(
           nl neFeedbacks,
          FeedbackTypeEnum. nl neSeeLess,
          FeedbackTypeEnum. nl neRevertedSeeLess)
      } else Seq()
    val  nl neNotRelevantFeedback =
       f (target.params(PushFeatureSw chParams.Use nl neNotRelevantForFat gue)) {
        getDeduped nl neFeedbackByType(
           nl neFeedbacks,
          FeedbackTypeEnum. nl neNotRelevant,
          FeedbackTypeEnum. nl neRevertedNotRelevant)
      } else Seq()

     nl neD sl keFeedback ++  nl neD sm ssFeedback ++  nl neSeeLessFeedback ++  nl neNotRelevantFeedback
  }

  pr vate def  s nl neD sl keOuts deFat guePer od(
    cand date: Cand date
      w h Recom ndat onType
      w h Target nfo[
        Target
      ],
     nl neFeedbacks: Seq[FeedbackModel],
    f ltered tory:  tory,
    feedbackF lters: Seq[Seq[FeedbackModel] => Seq[FeedbackModel]],
     nl neFeedbackFat gueParam: Cont nuousFunct onParam,
    stats: StatsRece ver
  ): Boolean = {
    val scopedStats = stats.scope(" nl ne_d sl ke_fat gue")

    val  nl neNegat veFeedback =
      getDeduped nl neFeedback( nl neFeedbacks, cand date.target)

    val hydrated nl neNegat veFeedback = FeedbackModelHydrator.HydrateNot f cat on(
       nl neNegat veFeedback,
      f ltered tory. tory.toSeq.map(_._2))

     f ( sOuts deFat guePer od(
        f ltered tory,
        Seq(),
        feedbackF lters.foldLeft(hydrated nl neNegat veFeedback)((feedbacks, feedbackF lter) =>
          feedbackF lter(feedbacks)),
         nl neFeedbackFat gueParam,
        scopedStats
      )) {
      scopedStats.counter("feedback_ nl ne_d sl ke_success"). ncr()
      true
    } else {
      scopedStats.counter("feedback_ nl ne_d sl ke_f ltered"). ncr()
      false
    }
  }

  pr vate def  sPromptD sl keOuts deFat guePer od(
    feedbacks: Seq[FeedbackModel],
    f ltered tory:  tory,
    feedbackF lters: Seq[Seq[FeedbackModel] => Seq[FeedbackModel]],
     nl neFeedbackFat gueParam: Cont nuousFunct onParam,
    stats: StatsRece ver
  ): Boolean = {
    val scopedStats = stats.scope("prompt_d sl ke_fat gue")

    val promptD sl keFeedback = feedbacks
      .f lter(feedback => feedback.feedbackTypeEnum == FeedbackTypeEnum.Prompt rrelevant)
    val hydratedPromptD sl keFeedback = FeedbackModelHydrator.HydrateNot f cat on(
      promptD sl keFeedback,
      f ltered tory. tory.toSeq.map(_._2))

     f ( sOuts deFat guePer od(
        f ltered tory,
        Seq(),
        feedbackF lters.foldLeft(hydratedPromptD sl keFeedback)((feedbacks, feedbackF lter) =>
          feedbackF lter(feedbacks)),
         nl neFeedbackFat gueParam,
        scopedStats
      )) {
      scopedStats.counter("feedback_prompt_d sl ke_success"). ncr()
      true
    } else {
      scopedStats.counter("feedback_prompt_d sl ke_f ltered"). ncr()
      false
    }
  }
}
