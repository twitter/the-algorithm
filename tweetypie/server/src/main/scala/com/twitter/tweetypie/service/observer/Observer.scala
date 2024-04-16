package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e. d a. d aKeyClass f er
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.t ettext.T etText.codePo ntLength
 mport com.tw ter.convers ons.Durat onOps._

/**
 * Observer can be used for stor ng
 * - one-off handler spec f c  tr cs w h m nor log c
 * - reusable T etyp e serv ce  tr cs for mult ple handlers
 */
pr vate[serv ce] object Observer {

  val successStatusStates: Set[StatusState] = Set(
    StatusState.Found,
    StatusState.NotFound,
    StatusState.Deact vatedUser,
    StatusState.SuspendedUser,
    StatusState.ProtectedUser,
    StatusState.ReportedT et,
    StatusState.UnsupportedCl ent,
    StatusState.Drop,
    StatusState.Suppress,
    StatusState.Deleted,
    StatusState.BounceDeleted
  )

  def observeStatusStates(statsRece ver: StatsRece ver): Effect[StatusState] = {
    val stats = statsRece ver.scope("status_state")
    val total = statsRece ver.counter("status_results")

    val foundCounter = stats.counter("found")
    val notFoundCounter = stats.counter("not_found")
    val part alCounter = stats.counter("part al")
    val t  dOutCounter = stats.counter("t  d_out")
    val fa ledCounter = stats.counter("fa led")
    val deact vatedCounter = stats.counter("deact vated")
    val suspendedCounter = stats.counter("suspended")
    val protectedCounter = stats.counter("protected")
    val reportedCounter = stats.counter("reported")
    val overCapac yCounter = stats.counter("over_capac y")
    val unsupportedCl entCounter = stats.counter("unsupported_cl ent")
    val dropCounter = stats.counter("drop")
    val suppressCounter = stats.counter("suppress")
    val deletedCounter = stats.counter("deleted")
    val bounceDeletedCounter = stats.counter("bounce_deleted")

    Effect { st =>
      total. ncr()
      st match {
        case StatusState.Found => foundCounter. ncr()
        case StatusState.NotFound => notFoundCounter. ncr()
        case StatusState.Part al => part alCounter. ncr()
        case StatusState.T  dOut => t  dOutCounter. ncr()
        case StatusState.Fa led => fa ledCounter. ncr()
        case StatusState.Deact vatedUser => deact vatedCounter. ncr()
        case StatusState.SuspendedUser => suspendedCounter. ncr()
        case StatusState.ProtectedUser => protectedCounter. ncr()
        case StatusState.ReportedT et => reportedCounter. ncr()
        case StatusState.OverCapac y => overCapac yCounter. ncr()
        case StatusState.UnsupportedCl ent => unsupportedCl entCounter. ncr()
        case StatusState.Drop => dropCounter. ncr()
        case StatusState.Suppress => suppressCounter. ncr()
        case StatusState.Deleted => deletedCounter. ncr()
        case StatusState.BounceDeleted => bounceDeletedCounter. ncr()
        case _ =>
      }
    }
  }

  def observeSetF eldsRequest(stats: StatsRece ver): Effect[SetAdd  onalF eldsRequest] =
    Effect { request =>
      val t et = request.add  onalF elds
      Add  onalF elds.nonEmptyAdd  onalF eld ds(t et).foreach {  d =>
        val f eldScope = "f eld_%d".format( d)
        val f eldCounter = stats.counter(f eldScope)
        val s zeStats = stats.stat(f eldScope)

        t et.getF eldBlob( d).foreach { blob =>
          f eldCounter. ncr()
          s zeStats.add(blob.content.length)
        }
      }
    }

  def observeSetRet etV s b l yRequest(
    stats: StatsRece ver
  ): Effect[SetRet etV s b l yRequest] = {
    val set nv s bleCounter = stats.counter("set_ nv s ble")
    val setV s bleCounter = stats.counter("set_v s ble")

    Effect { request =>
       f (request.v s ble) setV s bleCounter. ncr() else set nv s bleCounter. ncr()
    }
  }

  def observeDeleteF eldsRequest(stats: StatsRece ver): Effect[DeleteAdd  onalF eldsRequest] = {
    val requestS zeStat = stats.stat("request_s ze")

    Effect { request =>
      requestS zeStat.add(request.t et ds.s ze)

      request.f eld ds.foreach {  d =>
        val f eldScope = "f eld_%d".format( d)
        val f eldCounter = stats.counter(f eldScope)
        f eldCounter. ncr()
      }
    }
  }

  def observeDeleteT etsRequest(stats: StatsRece ver): Effect[DeleteT etsRequest] = {
    val requestS zeStat = stats.stat("request_s ze")
    val userErasureT etsStat = stats.counter("user_erasure_t ets")
    val  sBounceDeleteStat = stats.counter(" s_bounce_delete_t ets")

    Effect {
      case DeleteT etsRequest(t et ds, _, _, _,  sUserErasure, _,  sBounceDelete, _, _) =>
        requestS zeStat.add(t et ds.s ze)
         f ( sUserErasure) {
          userErasureT etsStat. ncr(t et ds.s ze)
        }
         f ( sBounceDelete) {
           sBounceDeleteStat. ncr(t et ds.s ze)
        }
    }
  }

  def observeRet etRequest(stats: StatsRece ver): Effect[Ret etRequest] = {
    val opt onsScope = stats.scope("opt ons")
    val narrowcastCounter = opt onsScope.counter("narrowcast")
    val nullcastCounter = opt onsScope.counter("nullcast")
    val darkCounter = opt onsScope.counter("dark")
    val successOnDupCounter = opt onsScope.counter("success_on_dup")

    Effect { request =>
       f (request.narrowcast.nonEmpty) narrowcastCounter. ncr()
       f (request.nullcast) nullcastCounter. ncr()
       f (request.dark) darkCounter. ncr()
       f (request.returnSuccessOnDupl cate) successOnDupCounter. ncr()
    }
  }

  def observeScrubGeo(stats: StatsRece ver): Effect[GeoScrub] = {
    val opt onsScope = stats.scope("opt ons")
    val hoseb rdEnqueueCounter = opt onsScope.counter("hoseb rd_enqueue")
    val requestS zeStat = stats.stat("request_s ze")

    Effect { request =>
      requestS zeStat.add(request.status ds.s ze)
       f (request.hoseb rdEnqueue) hoseb rdEnqueueCounter. ncr()
    }
  }

  def observeEventOrRetry(stats: StatsRece ver,  sRetry: Boolean): Un  = {
    val statNa  =  f ( sRetry) "retry" else "event"
    stats.counter(statNa ). ncr()
  }

  def observeAsync nsertRequest(stats: StatsRece ver): Effect[Async nsertRequest] = {
    val  nsertScope = stats.scope(" nsert")
    val ageStat =  nsertScope.stat("age")
    Effect { request =>
      observeEventOrRetry( nsertScope, request.retryAct on. sDef ned)
      ageStat.add(Snowflake d.t  From d(request.t et. d).unt lNow. nM ll s)
    }
  }

  def observeAsyncSetAdd  onalF eldsRequest(
    stats: StatsRece ver
  ): Effect[AsyncSetAdd  onalF eldsRequest] = {
    val setAdd  onalF eldsScope = stats.scope("set_add  onal_f elds")
    Effect { request =>
      observeEventOrRetry(setAdd  onalF eldsScope, request.retryAct on. sDef ned)
    }
  }

  def observeAsyncSetRet etV s b l yRequest(
    stats: StatsRece ver
  ): Effect[AsyncSetRet etV s b l yRequest] = {
    val setRet etV s b l yScope = stats.scope("set_ret et_v s b l y")

    Effect { request =>
      observeEventOrRetry(setRet etV s b l yScope, request.retryAct on. sDef ned)
    }
  }

  def observeAsyncUndeleteT etRequest(stats: StatsRece ver): Effect[AsyncUndeleteT etRequest] = {
    val undeleteT etScope = stats.scope("undelete_t et")
    Effect { request => observeEventOrRetry(undeleteT etScope, request.retryAct on. sDef ned) }
  }

  def observeAsyncDeleteT etRequest(stats: StatsRece ver): Effect[AsyncDeleteRequest] = {
    val deleteT etScope = stats.scope("delete_t et")
    Effect { request => observeEventOrRetry(deleteT etScope, request.retryAct on. sDef ned) }
  }

  def observeAsyncDeleteAdd  onalF eldsRequest(
    stats: StatsRece ver
  ): Effect[AsyncDeleteAdd  onalF eldsRequest] = {
    val deleteAdd  onalF eldsScope = stats.scope("delete_add  onal_f elds")
    Effect { request =>
      observeEventOrRetry(
        deleteAdd  onalF eldsScope,
        request.retryAct on. sDef ned
      )
    }
  }

  def observeAsyncTakedownRequest(stats: StatsRece ver): Effect[AsyncTakedownRequest] = {
    val takedownScope = stats.scope("takedown")
    Effect { request => observeEventOrRetry(takedownScope, request.retryAct on. sDef ned) }
  }

  def observeAsyncUpdatePoss blySens  veT etRequest(
    stats: StatsRece ver
  ): Effect[AsyncUpdatePoss blySens  veT etRequest] = {
    val updatePoss blySens  veT etScope = stats.scope("update_poss bly_sens  ve_t et")
    Effect { request =>
      observeEventOrRetry(updatePoss blySens  veT etScope, request.act on. sDef ned)
    }
  }

  def observeRepl cated nsertT etRequest(stats: StatsRece ver): Effect[T et] = {
    val ageStat = stats.stat("age") //  n m ll seconds
    Effect { request => ageStat.add(Snowflake d.t  From d(request. d).unt lNow. nM ll s) }
  }

  def ca lToUnderscore(str: Str ng): Str ng = {
    val bldr = new Str ngBu lder
    str.foldLeft(false) { (prevWasLo rcase, c) =>
       f (prevWasLo rcase && c. sUpper) {
        bldr += '_'
      }
      bldr += c.toLo r
      c. sLo r
    }
    bldr.result
  }

  def observeAdd  onalF elds(stats: StatsRece ver): Effect[T et] = {
    val add  onalScope = stats.scope("add  onal_f elds")

    Effect { t et =>
      for (f eld d <- Add  onalF elds.nonEmptyAdd  onalF eld ds(t et))
        add  onalScope.counter(f eld d.toStr ng). ncr()
    }
  }

  /**
   *   count how many t ets have each of t se attr butes so that  
   * can observe general trends, as  ll as for track ng down t 
   * cause of behav or changes, l ke  ncreased calls to certa n
   * serv ces.
   */
  def countT etAttr butes(stats: StatsRece ver, byCl ent: Boolean): Effect[T et] = {
    val ageStat = stats.stat("age")
    val t etCounter = stats.counter("t ets")
    val ret etCounter = stats.counter("ret ets")
    val repl esCounter = stats.counter("repl es")
    val  nReplyToT etCounter = stats.counter(" n_reply_to_t et")
    val selfRepl esCounter = stats.counter("self_repl es")
    val d rectedAtCounter = stats.counter("d rected_at")
    val  nt onsCounter = stats.counter(" nt ons")
    val  nt onsStat = stats.stat(" nt ons")
    val urlsCounter = stats.counter("urls")
    val urlsStat = stats.stat("urls")
    val hashtagsCounter = stats.counter("hashtags")
    val hashtagsStat = stats.stat("hashtags")
    val  d aCounter = stats.counter(" d a")
    val  d aStat = stats.stat(" d a")
    val photosCounter = stats.counter(" d a", "photos")
    val g fsCounter = stats.counter(" d a", "an mated_g fs")
    val v deosCounter = stats.counter(" d a", "v deos")
    val cardsCounter = stats.counter("cards")
    val card2Counter = stats.counter("card2")
    val geoCoordsCounter = stats.counter("geo_coord nates")
    val placeCounter = stats.counter("place")
    val quotedT etCounter = stats.counter("quoted_t et")
    val selfRet etCounter = stats.counter("self_ret et")
    val languageScope = stats.scope("language")
    val textLengthStat = stats.stat("text_length")
    val selfThreadCounter = stats.counter("self_thread")
    val commun  esT etCounter = stats.counter("commun  es")

    observeAdd  onalF elds(stats).also {
      Effect[T et] { t et =>
        def coreDataF eld[T](f: T etCoreData => T): Opt on[T] =
          t et.coreData.map(f)

        def coreDataOpt onF eld[T](f: T etCoreData => Opt on[T]) =
          coreDataF eld(f).flatten

        (Snowflake d. sSnowflake d(t et. d) match {
          case true => So (Snowflake d.t  From d(t et. d))
          case false => coreDataF eld(_.createdAtSecs.seconds.afterEpoch)
        }).foreach { createdAt => ageStat.add(createdAt.unt lNow. nSeconds) }

         f (!byCl ent) {
          val  nt ons = get nt ons(t et)
          val urls = getUrls(t et)
          val hashtags = getHashtags(t et)
          val  d a = get d a(t et)
          val  d aKeys =  d a.flatMap(_. d aKey)
          val share = coreDataOpt onF eld(_.share)
          val selfThread tadata = getSelfThread tadata(t et)
          val commun  es = getCommun  es(t et)

          t etCounter. ncr()
           f (share. sDef ned) ret etCounter. ncr()
           f (coreDataOpt onF eld(_.d rectedAtUser). sDef ned) d rectedAtCounter. ncr()

          coreDataOpt onF eld(_.reply).foreach { reply =>
            repl esCounter. ncr()
             f (reply. nReplyToStatus d.nonEmpty) {
              // repl esCounter counts all T ets w h a Reply struct,
              // but that  ncludes both d rected-at T ets and
              // conversat onal repl es. Only conversat onal repl es
              // have  nReplyToStatus d present, so t  counter lets
              // us spl  apart those two cases.
               nReplyToT etCounter. ncr()
            }

            // Not all T et objects have CoreData yet  sSelfReply() requ res  .  Thus, t 
            //  nvocat on  s guarded by t  `coreDataOpt onF eld(_.reply)` above.
             f ( sSelfReply(t et)) selfRepl esCounter. ncr()
          }

           f ( nt ons.nonEmpty)  nt onsCounter. ncr()
           f (urls.nonEmpty) urlsCounter. ncr()
           f (hashtags.nonEmpty) hashtagsCounter. ncr()
           f ( d a.nonEmpty)  d aCounter. ncr()
           f (selfThread tadata.nonEmpty) selfThreadCounter. ncr()
           f (commun  es.nonEmpty) commun  esT etCounter. ncr()

           nt onsStat.add( nt ons.s ze)
          urlsStat.add(urls.s ze)
          hashtagsStat.add(hashtags.s ze)
           d aStat.add( d a.s ze)

           f ( d aKeys.ex sts( d aKeyClass f er. s mage(_))) photosCounter. ncr()
           f ( d aKeys.ex sts( d aKeyClass f er. sG f(_))) g fsCounter. ncr()
           f ( d aKeys.ex sts( d aKeyClass f er. sV deo(_))) v deosCounter. ncr()

           f (t et.cards.ex sts(_.nonEmpty)) cardsCounter. ncr()
           f (t et.card2.nonEmpty) card2Counter. ncr()
           f (coreDataOpt onF eld(_.coord nates).nonEmpty) geoCoordsCounter. ncr()
           f (T etLenses.place.get(t et).nonEmpty) placeCounter. ncr()
           f (T etLenses.quotedT et.get(t et).nonEmpty) quotedT etCounter. ncr()
           f (share.ex sts(_.s ceUser d == getUser d(t et))) selfRet etCounter. ncr()

          t et.language
            .map(_.language)
            .foreach(lang => languageScope.counter(lang). ncr())
          coreDataF eld(_.text).foreach(text => textLengthStat.add(codePo ntLength(text)))
        }
      }
    }
  }

}
