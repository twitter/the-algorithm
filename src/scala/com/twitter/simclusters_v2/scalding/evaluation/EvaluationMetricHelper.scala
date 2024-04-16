package com.tw ter.s mclusters_v2.scald ng.evaluat on

 mport com.tw ter.scald ng.{Execut on, TypedP pe, Un que D}
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Cand dateT et,
  Cand dateT ets,
  ReferenceT et,
  ReferenceT ets,
  T etLabels
}
 mport com.tw ter.algeb rd.Aggregator.s ze
 mport com.tw ter.scald ng.typed.{CoGrouped, ValueP pe}
 mport com.tw ter.ut l.Tw terDateFormat
 mport java.ut l.Calendar

/**
 * Stat st cs about t  number of users who have engaged w h t ets
 */
case class UserEngagerCounts(
  numD st nctTargetUsers: Long,
  numD st nctL keEngagers: Long,
  numD st nctRet etEngagers: Long)

/**
 * T et s de stat st cs, e.x. number of t ets, authors, etc.
 */
case class T etStats(
  numT ets: Long,
  numD st nctT ets: Long,
  numD st nctAuthors: Opt on[Long],
  avgScore: Opt on[Double])

/**
 *  lper data conta ner class for stor ng engage nt counts
 */
case class T etEngage ntCounts(l ke: Long, ret et: Long, cl ck: Long, hasEngage nt: Long)

/**
 *  lper data conta ner class for stor ng engage nt rates
 */
case class T etEngage ntRates(l ke: Double, ret et: Double, cl ck: Double, hasEngage nt: Double)

case class LabelCorrelat ons(
  pearsonCoeff c entForL kes: Double,
  cos neS m lar yGlobal: Double,
  cos neS m lar yPerUserAvg: Double) {
  pr vate val f = java.text.NumberFormat.get nstance
  def format(): Str ng = {
    Seq(
      s"\tPearson Coeff c ent: ${f.format(pearsonCoeff c entForL kes)}",
      s"\tCos ne s m lar y: ${f.format(cos neS m lar yGlobal)}",
      s"\tAverage cos ne s m lar y for all users: ${f.format(cos neS m lar yPerUserAvg)}"
    ).mkStr ng("\n")
  }
}

/**
 *  lper t et data conta ner that can hold both t  reference label engage nts as  ll as t 
 * recom ndat on algor hm's scores.  lpful for evaluat ng jo nt data
 */
case class LabeledT et(
  targetUser d: Long,
  t et d: Long,
  author d: Long,
  labels: T etLabels,
  algor hmScore: Opt on[Double])

case class LabeledT etsResults(
  t etStats: T etStats,
  userEngagerCounts: UserEngagerCounts,
  t etEngage ntCounts: T etEngage ntCounts,
  t etEngage ntRates: T etEngage ntRates,
  labelCorrelat ons: Opt on[LabelCorrelat ons] = None) {
  pr vate val f = java.text.NumberFormat.get nstance

  def format(t le: Str ng = ""): Str ng = {
    val str = Seq(
      s"Number of t ets: ${f.format(t etStats.numT ets)}",
      s"Number of d st nct t ets: ${f.format(t etStats.numD st nctT ets)}",
      s"Number of d st nct users targeted: ${f.format(userEngagerCounts.numD st nctTargetUsers)}",
      s"Number of d st nct authors: ${t etStats.numD st nctAuthors.map(f.format).getOrElse("N/A")}",
      s"Average algor hm score of t ets: ${t etStats.avgScore.map(f.format).getOrElse("N/A")}",
      s"Engager counts:",
      s"\tNumber of users who l ked t ets: ${f.format(userEngagerCounts.numD st nctL keEngagers)}",
      s"\tNumber of users who ret eted t ets: ${f.format(userEngagerCounts.numD st nctRet etEngagers)}",
      s"T et engage nt counts:",
      s"\tNumber of L kes: ${f.format(t etEngage ntCounts.l ke)}",
      s"\tNumber of Ret ets: ${f.format(t etEngage ntCounts.ret et)}",
      s"\tNumber of Cl cks: ${f.format(t etEngage ntCounts.cl ck)}",
      s"\tNumber of t ets w h any engage nts: ${f.format(t etEngage ntCounts.hasEngage nt)}",
      s"T et engage nt rates:",
      s"\tRate of L kes: ${f.format(t etEngage ntRates.l ke * 100)}%",
      s"\tRate of Ret ets: ${f.format(t etEngage ntRates.ret et * 100)}%",
      s"\tRate of Cl cks: ${f.format(t etEngage ntRates.cl ck * 100)}%",
      s"\tRate of any engage nt: ${f.format(t etEngage ntRates.hasEngage nt * 100)}%"
    ).mkStr ng("\n")

    val correlat ons = labelCorrelat ons.map("\n" + _.format()).getOrElse("")

    s"$t le\n$str$correlat ons"
  }
}

case class Cand dateResults(t etStats: T etStats, numD st nctTargetUsers: Long) {
  pr vate val f = java.text.NumberFormat.get nstance

  def format(t le: Str ng = ""): Str ng = {
    val str = Seq(
      s"Number of t ets: ${f.format(t etStats.numT ets)}",
      s"Number of d st nct t ets: ${f.format(t etStats.numD st nctT ets)}",
      s"Number of d st nct users targeted: ${f.format(numD st nctTargetUsers)}",
      s"Number of d st nct authors: ${t etStats.numD st nctAuthors.map(f.format).getOrElse("N/A")}",
      s"Average algor hm score of t ets: ${t etStats.avgScore.map(f.format).getOrElse("N/A")}"
    ).mkStr ng("\n")
    s"$t le\n$str"
  }
}

/**
 *  lper class for evaluat ng a g ven cand date t et set aga nst a reference t et set.
 *   prov des aggregat on evaluat on  tr cs such as sum of engage nts, rate of engage nts, etc.
 */
object Evaluat on tr c lper {
  pr vate def toLong(bool: Boolean): Long = {
     f (bool) 1L else 0L
  }

  /**
   * Core engage nts are user act ons that count towards core  tr cs, e.x. l ke, RT, etc
   */
  pr vate def hasCoreEngage nts(labels: T etLabels): Boolean = {
    labels. sRet eted ||
    labels. sL ked ||
    labels. sQuoted ||
    labels. sRepl ed
  }

  /**
   * W t r t re are core engage nts or cl ck on t  t et
   */
  pr vate def hasCoreEngage ntsOrCl ck(labels: T etLabels): Boolean = {
    hasCoreEngage nts(labels) || labels. sCl cked
  }

  /**
   * Return outer jo n of reference t ets and cand date t ets, keyed by (targetUser d, t et d).
   * T  output of t  can t n be reused to fetch t   nner jo n / left / r ght jo n,
   * w hout hav ng to redo t  expens ve jo n
   *
   * NOTE: Assu s t  un queness of keys ( .e. (target d, t et d)). Make sure to dedup t et ds
   * for each target d, ot rw se .jo n() w ll y eld dupl cate results.
   */
  def outerJo nReferenceAndCand date(
    referenceP pe: TypedP pe[ReferenceT ets],
    cand dateP pe: TypedP pe[Cand dateT ets]
  ): CoGrouped[(Long, Long), (Opt on[ReferenceT et], Opt on[Cand dateT et])] = {

    val references = referenceP pe
      .flatMap { refT ets =>
        refT ets. mpressedT ets.map { refT et =>
          ((refT ets.targetUser d, refT et.t et d), refT et)
        }
      }

    val cand dates = cand dateP pe
      .flatMap { candT ets =>
        candT ets.recom ndedT ets.map { candT et =>
          ((candT ets.targetUser d, candT et.t et d), candT et)
        }
      }

    references.outerJo n(cand dates).w hReducers(50)
  }

  /**
   * Convert reference t ets to labeled t ets.   do t  so that   can re-use t  common
   *  tr c calculat ons for labeled t ets on reference t ets
   */
  def getLabeledReference(referenceP pe: TypedP pe[ReferenceT ets]): TypedP pe[LabeledT et] = {
    referenceP pe
      .flatMap { refT ets =>
        refT ets. mpressedT ets.map { t et =>
          // Reference t ets do not have scores
          LabeledT et(refT ets.targetUser d, t et.t et d, t et.author d, t et.labels, None)
        }
      }
  }

  def getUn queCount[T](p pe: TypedP pe[T])( mpl c  ord: scala.Order ng[T]): Execut on[Long] = {
    p pe.d st nct
      .aggregate(s ze)
      .toOpt onExecut on
      .map(_.getOrElse(0L))
  }

  def countUn queEngagedUsersBy(
    labeledT etsP pe: TypedP pe[LabeledT et],
    f: T etLabels => Boolean
  ): Execut on[Long] = {
    getUn queCount[Long](labeledT etsP pe.collect { case t  f f(t.labels) => t.targetUser d })
  }

  def countUn queLabeledTargetUsers(labeledT etsP pe: TypedP pe[LabeledT et]): Execut on[Long] = {
    getUn queCount[Long](labeledT etsP pe.map(_.targetUser d))
  }

  def countUn queCandTargetUsers(cand dateP pe: TypedP pe[Cand dateT ets]): Execut on[Long] = {
    getUn queCount[Long](cand dateP pe.map(_.targetUser d))
  }

  def countUn queLabeledAuthors(labeledT etP pe: TypedP pe[LabeledT et]): Execut on[Long] = {
    getUn queCount[Long](labeledT etP pe.map(_.author d))
  }

  /**
   *  lper funct on to calculate t  bas c engage nt rates
   */
  def getEngage ntRate(
    bas cStats: T etStats,
    engage ntCount: T etEngage ntCounts
  ): T etEngage ntRates = {
    val numT ets = bas cStats.numT ets.toDouble
     f (numT ets <= 0) throw new  llegalArgu ntExcept on(" nval d t et counts")
    val l keRate = engage ntCount.l ke / numT ets
    val rtRate = engage ntCount.ret et / numT ets
    val cl ckRate = engage ntCount.cl ck / numT ets
    val engage ntRate = engage ntCount.hasEngage nt / numT ets
    T etEngage ntRates(l keRate, rtRate, cl ckRate, engage ntRate)
  }

  /**
   *  lper funct on to calculate t  bas c stats for a p pe of cand date t ets
   */
  def getT etStatsForCand dateExec(
    cand dateP pe: TypedP pe[Cand dateT ets]
  ): Execut on[T etStats] = {
    val p pe = cand dateP pe.map { candT ets =>
      (candT ets.targetUser d, candT ets.recom ndedT ets)
    }.sumByKey // Dedup by target d,  n case t re ex sts mult ple entr es.

    val d st nctT etP pe = p pe.flatMap(_._2.map(_.t et d)).d st nct.aggregate(s ze)

    val ot rStats = p pe
      .map {
        case (u d, recom ndedT ets) =>
          val scoreSum = recom ndedT ets.flatMap(_.score).sum
          (recom ndedT ets.s ze.toLong, scoreSum)
      }
      .sum
      .map {
        case (numT ets, scoreSum) =>
           f (numT ets <= 0) throw new  llegalArgu ntExcept on(" nval d t et counts")
          val avgScore = scoreSum / numT ets.toDouble
          (numT ets, avgScore)
      }
    ValueP pe
      .fold(d st nctT etP pe, ot rStats) {
        case (numD st nctT et, (numT ets, avgScore)) =>
          // no author s de  nformat on for cand date t ets yet
          T etStats(numT ets, numD st nctT et, None, So (avgScore))
      }.getOrElseExecut on(T etStats(0L, 0L, None, None))
  }

  /**
   *  lper funct on to count t  total number of engage nts
   */
  def getLabeledEngage ntCountExec(
    labeledT ets: TypedP pe[LabeledT et]
  ): Execut on[T etEngage ntCounts] = {
    labeledT ets
      .map { labeledT et =>
        val l ke = toLong(labeledT et.labels. sL ked)
        val ret et = toLong(labeledT et.labels. sRet eted)
        val cl ck = toLong(labeledT et.labels. sCl cked)
        val hasEngage nt = toLong(hasCoreEngage ntsOrCl ck(labeledT et.labels))

        (l ke, ret et, cl ck, hasEngage nt)
      }
      .sum
      .map {
        case (l ke, ret et, cl ck, hasEngage nt) =>
          T etEngage ntCounts(l ke, ret et, cl ck, hasEngage nt)
      }
      .getOrElseExecut on(T etEngage ntCounts(0L, 0L, 0L, 0L))
  }

  /**
   * Count t  total number of un que users who have engaged w h t ets
   */
  def getTargetUserStatsForLabeledT etsExec(
    labeledT etsP pe: TypedP pe[LabeledT et]
  ): Execut on[UserEngagerCounts] = {
    val numUn queTargetUsersExec = countUn queLabeledTargetUsers(labeledT etsP pe)
    val numUn queL keUsersExec =
      countUn queEngagedUsersBy(labeledT etsP pe, labels => labels. sL ked)
    val numUn queRet etUsersExec =
      countUn queEngagedUsersBy(labeledT etsP pe, labels => labels. sRet eted)

    Execut on
      .z p(
        numUn queTargetUsersExec,
        numUn queL keUsersExec,
        numUn queRet etUsersExec
      )
      .map {
        case (numTarget, l ke, ret et) =>
          UserEngagerCounts(
            numD st nctTargetUsers = numTarget,
            numD st nctL keEngagers = l ke,
            numD st nctRet etEngagers = ret et
          )
      }
  }

  /**
   *  lper funct on to calculate t  bas c stats for a p pe of labeled t ets.
   */
  def getT etStatsForLabeledT etsExec(
    labeledT etP pe: TypedP pe[LabeledT et]
  ): Execut on[T etStats] = {
    val un queAuthorsExec = countUn queLabeledAuthors(labeledT etP pe)

    val un queT etExec =
      labeledT etP pe.map(_.t et d).d st nct.aggregate(s ze).getOrElseExecut on(0L)
    val scoresExec = labeledT etP pe
      .map { t => (t.targetUser d, (1, t.algor hmScore.getOrElse(0.0))) }
      .sumByKey // Dedup by target d,  n case t re ex sts mult ple entr es.
      .map {
        case (u d, (c1, c2)) =>
          (c1.toLong, c2)
      }
      .sum
      .map {
        case (numT ets, scoreSum) =>
           f (numT ets <= 0) throw new  llegalArgu ntExcept on(" nval d t et counts")
          val avgScore = scoreSum / numT ets.toDouble
          (numT ets, Opt on(avgScore))
      }
      .getOrElseExecut on((0L, None))

    Execut on
      .z p(un queAuthorsExec, un queT etExec, scoresExec)
      .map {
        case (numD st nctAuthors, numUn queT ets, (numT ets, avgScores)) =>
          T etStats(numT ets, numUn queT ets, So (numD st nctAuthors), avgScores)
      }
  }

  /**
   * Pr nt a update  ssage to t  stdout w n a step  s done.
   */
  pr vate def pr ntOnCompleteMsg(stepDescr pt on: Str ng, startT  M ll s: Long): Un  = {
    val formatDate = Tw terDateFormat("yyyy-MM-dd hh:mm:ss")
    val now = Calendar.get nstance().getT  

    val secondsSpent = (now.getT   - startT  M ll s) / 1000
    pr ntln(
      s"- ${formatDate.format(now)}\tStep complete: $stepDescr pt on\t " +
        s"T   spent: ${secondsSpent / 60}m${secondsSpent % 60}s"
    )
  }

  /**
   * Calculate t   tr cs of a p pe of [[Cand dateT ets]]
   */
  pr vate def getEvaluat onResultsForCand dates(
    cand dateP pe: TypedP pe[Cand dateT ets]
  ): Execut on[Cand dateResults] = {
    val t etStatsExec = getT etStatsForCand dateExec(cand dateP pe)
    val numD st nctTargetUsersExec = countUn queCandTargetUsers(cand dateP pe)

    Execut on
      .z p(t etStatsExec, numD st nctTargetUsersExec)
      .map {
        case (t etStats, numD st nctTargetUsers) =>
          Cand dateResults(t etStats, numD st nctTargetUsers)
      }
  }

  /**
   * Calculate t   tr cs of a p pe of [[LabeledT et]]
   */
  pr vate def getEvaluat onResultsForLabeledT ets(
    labeledT etP pe: TypedP pe[LabeledT et],
    getLabelCorrelat ons: Boolean = false
  ): Execut on[LabeledT etsResults] = {
    val t etStatsExec = getT etStatsForLabeledT etsExec(labeledT etP pe)
    val userStatsExec = getTargetUserStatsForLabeledT etsExec(labeledT etP pe)
    val engage ntCountExec = getLabeledEngage ntCountExec(labeledT etP pe)

    val correlat onsExec =  f (getLabelCorrelat ons) {
      Execut on
        .z p(
          LabelCorrelat ons lper.pearsonCoeff c entForL ke(labeledT etP pe),
          LabelCorrelat ons lper.cos neS m lar yForL ke(labeledT etP pe),
          LabelCorrelat ons lper.cos neS m lar yForL kePerUser(labeledT etP pe)
        ).map {
          case (pearsonCoeff, globalCos, avgCos) =>
            So (LabelCorrelat ons(pearsonCoeff, globalCos, avgCos))
        }
    } else {
      ValueP pe(None).getOrElseExecut on(None) // Empty p pe w h a None value
    }

    Execut on
      .z p(t etStatsExec, engage ntCountExec, userStatsExec, correlat onsExec)
      .map {
        case (t etStats, engage ntCount, engagerCount, correlat onsOpt) =>
          val engage ntRate = getEngage ntRate(t etStats, engage ntCount)
          LabeledT etsResults(
            t etStats,
            engagerCount,
            engage ntCount,
            engage ntRate,
            correlat onsOpt)
      }
  }

  pr vate def runAllEvalForCand dates(
    cand dateP pe: TypedP pe[Cand dateT ets],
    outerJo nP pe: TypedP pe[((Long, Long), (Opt on[ReferenceT et], Opt on[Cand dateT et]))]
  ): Execut on[(Cand dateResults, Cand dateResults)] = {
    val t0 = System.currentT  M ll s()

    val cand dateNot n ntersect onP pe =
      outerJo nP pe
        .collect {
          case ((targetUser d, _), (None, So (candT et))) => (targetUser d, Seq(candT et))
        }
        .sumByKey
        .map { case (targetUser d, candT ets) => Cand dateT ets(targetUser d, candT ets) }
        .forceToD sk

    Execut on
      .z p(
        getEvaluat onResultsForCand dates(cand dateP pe),
        getEvaluat onResultsForCand dates(cand dateNot n ntersect onP pe)
      ).onComplete(_ => pr ntOnCompleteMsg("runAllEvalForCand dates()", t0))
  }

  pr vate def runAllEvalFor ntersect on(
    outerJo nP pe: TypedP pe[((Long, Long), (Opt on[ReferenceT et], Opt on[Cand dateT et]))]
  )(
     mpl c  un que D: Un que D
  ): Execut on[(LabeledT etsResults, LabeledT etsResults, LabeledT etsResults)] = {
    val t0 = System.currentT  M ll s()
    val  ntersect onT etsP pe = outerJo nP pe.collect {
      case ((targetUser d, t et d), (So (refT et), So (candT et))) =>
        LabeledT et(targetUser d, t et d, refT et.author d, refT et.labels, candT et.score)
    }.forceToD sk

    val l kedT etsP pe =  ntersect onT etsP pe.f lter(_.labels. sL ked)
    val notL kedT etsP pe =  ntersect onT etsP pe.f lter(!_.labels. sL ked)

    Execut on
      .z p(
        getEvaluat onResultsForLabeledT ets( ntersect onT etsP pe, getLabelCorrelat ons = true),
        getEvaluat onResultsForLabeledT ets(l kedT etsP pe),
        getEvaluat onResultsForLabeledT ets(notL kedT etsP pe)
      ).onComplete(_ => pr ntOnCompleteMsg("runAllEvalFor ntersect on()", t0))
  }

  pr vate def runAllEvalForReferences(
    referenceP pe: TypedP pe[ReferenceT ets],
    outerJo nP pe: TypedP pe[((Long, Long), (Opt on[ReferenceT et], Opt on[Cand dateT et]))]
  ): Execut on[(LabeledT etsResults, LabeledT etsResults)] = {
    val t0 = System.currentT  M ll s()
    val labeledReferenceNot n ntersect onP pe =
      outerJo nP pe.collect {
        case ((targetUser d, _), (So (refT et), None)) =>
          LabeledT et(targetUser d, refT et.t et d, refT et.author d, refT et.labels, None)
      }.forceToD sk

    Execut on
      .z p(
        getEvaluat onResultsForLabeledT ets(getLabeledReference(referenceP pe)),
        getEvaluat onResultsForLabeledT ets(labeledReferenceNot n ntersect onP pe)
      ).onComplete(_ => pr ntOnCompleteMsg("runAllEvalForReferences()", t0))
  }

  def runAllEvaluat ons(
    referenceP pe: TypedP pe[ReferenceT ets],
    cand dateP pe: TypedP pe[Cand dateT ets]
  )(
     mpl c  un que D: Un que D
  ): Execut on[Str ng] = {
    val t0 = System.currentT  M ll s()

    // Force everyth ng to d sk to max m ze data re-use
    Execut on
      .z p(
        referenceP pe.forceToD skExecut on,
        cand dateP pe.forceToD skExecut on
      ).flatMap {
        case (referenceD skP pe, cand dateD skP pe) =>
          outerJo nReferenceAndCand date(referenceD skP pe, cand dateD skP pe).forceToD skExecut on
            .flatMap { outerJo nP pe =>
              val referenceResultsExec = runAllEvalForReferences(referenceD skP pe, outerJo nP pe)
              val  ntersect onResultsExec = runAllEvalFor ntersect on(outerJo nP pe)
              val cand dateResultsExec = runAllEvalForCand dates(cand dateD skP pe, outerJo nP pe)

              Execut on
                .z p(
                  referenceResultsExec,
                   ntersect onResultsExec,
                  cand dateResultsExec
                ).map {
                  case (
                        (allReference, referenceNot n ntersect on),
                        (all ntersect on,  ntersect onL ked,  ntersect onNotL ked),
                        (allCand date, cand dateNot n ntersect on)) =>
                    val t  Spent = (System.currentT  M ll s() - t0) / 1000
                    val resultStr = Seq(
                      "===================================================",
                      s"Evaluat on complete. Took ${t  Spent / 60}m${t  Spent % 60}s ",
                      allReference.format("----- tr cs for all Reference T ets-----"),
                      referenceNot n ntersect on.format(
                        "----- tr cs for Reference T ets that are not  n t   ntersect on-----"
                      ),
                      all ntersect on.format("----- tr cs for all  ntersect on T ets-----"),
                       ntersect onL ked.format("----- tr cs for L ked  ntersect on T ets-----"),
                       ntersect onNotL ked.format(
                        "----- tr cs for not L ked  ntersect on T ets-----"),
                      allCand date.format("----- tr cs for all Cand date T ets-----"),
                      cand dateNot n ntersect on.format(
                        "----- tr cs for Cand date T ets that are not  n t   ntersect on-----"
                      ),
                      "===================================================\n"
                    ).mkStr ng("\n")
                    pr ntln(resultStr)
                    resultStr
                }
                .onComplete(_ =>
                  pr ntOnCompleteMsg(
                    "Evaluat on complete. C ck stdout or output logs for results.",
                    t0))
            }
      }
  }
}
