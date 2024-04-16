package com.tw ter.s mclusters_v2.scald ng
package mult _type_graph.assemble_mult _type_graph

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.scald ng_ nternal.job.Requ redB naryComparators.ordSer
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.{DateRange, Days, Stat, Un que D}
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  LeftNode,
  Noun,
  R ghtNode,
  R ghtNodeType,
  R ghtNodeW hEdge  ght
}
 mport java.ut l.T  Zone
 mport com.tw ter. es ce.thr ftscala.{ nteract onEvent,  nteract onType, ReferenceT et}
 mport com.tw ter.s mclusters_v2.common.{Country, Language, Top c d, T et d, User d}
 mport com.tw ter.users ce.snapshot.comb ned.Users ceScalaDataset
 mport com.tw ter.fr gate.data_p pel ne.mag crecs.mag crecs_not f cat ons_l e.thr ftscala.Mag cRecsNot f cat onL e
 mport com.tw ter.twadoop.user.gen.thr ftscala.Comb nedUser

object AssembleMult TypeGraph {
   mport Conf g._

   mpl c  val nounOrder ng: Order ng[Noun] = new Order ng[Noun] {
    //   def ne an order ng for each noun type as spec f ed  n s mclusters_v2/mult _type_graph.thr ft
    // Please make sure   don't remove anyth ng  re that's st ll a part of t  un on Noun thr ft and
    // v ce versa,  f   add a new noun type to thr ft, an order ng for   needs to added  re as  ll.
    def nounTypeOrder(noun: Noun):  nt = noun match {
      case _: Noun.User d => 0
      case _: Noun.Country => 1
      case _: Noun.Language => 2
      case _: Noun.Query => 3
      case _: Noun.Top c d => 4
      case _: Noun.T et d => 5
    }

    overr de def compare(x: Noun, y: Noun):  nt = (x, y) match {
      case (Noun.User d(a), Noun.User d(b)) => a compare b
      case (Noun.Country(a), Noun.Country(b)) => a compare b
      case (Noun.Language(a), Noun.Language(b)) => a compare b
      case (Noun.Query(a), Noun.Query(b)) => a compare b
      case (Noun.Top c d(a), Noun.Top c d(b)) => a compare b
      case (Noun.T et d(a), Noun.T et d(b)) => a compare b
      case (nounA, nounB) => nounTypeOrder(nounA) compare nounTypeOrder(nounB)
    }
  }
   mpl c  val r ghtNodeTypeOrder ng: Order ng[R ghtNodeType] = ordSer[R ghtNodeType]

   mpl c  val r ghtNodeTypeW hNounOrder ng: Order ng[R ghtNode] =
    new Order ng[R ghtNode] {
      overr de def compare(x: R ghtNode, y: R ghtNode):  nt = {
        Order ng
          .Tuple2(r ghtNodeTypeOrder ng, nounOrder ng)
          .compare((x.r ghtNodeType, x.noun), (y.r ghtNodeType, y.noun))
      }
    }

  def getUserT et nteract onGraph(
    t et nteract onEvents: TypedP pe[ nteract onEvent],
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numUserT et nteract onEntr es = Stat("num_user_t et_ nteract on_entr es")
    val numD st nctUserT et nteract onEntr es = Stat("num_d st nct_user_t et_ nteract on_entr es")
    val numFavedT ets = Stat("num_faved_t ets")
    val numRepl edT ets = Stat("num_repl ed_t ets")
    val numRet etedT ets = Stat("num_ret eted_t ets")
    val userT et nteract onsByType: TypedP pe[((User d, R ghtNodeType), T et d)] =
      t et nteract onEvents
        .flatMap { event =>
          val referenceT et: Opt on[ReferenceT et] = event.referenceT et
          val target d: Long = event.target d
          val user d: Long = event.engag ngUser d

          //  To f nd t   d of t  t et that was  nteracted w h
          //  For l kes, t   s t  target d; for ret et or reply,    s t  referenceT et's  d
          //  One th ng to note  s that for l kes, referenceT et  s empty
          val (t et dOpt, r ghtNodeTypeOpt) = {
            event. nteract onType match {
              case So ( nteract onType.Favor e) =>
                // Only allow favor es on or g nal t ets, not ret ets, to avo d double-count ng
                // because   have ret et-type t ets  n t  data s ce as  ll
                (
                   f (referenceT et. sEmpty) {
                    numFavedT ets. nc()
                    So (target d)
                  } else None,
                  So (R ghtNodeType.FavT et))
              case So ( nteract onType.Reply) =>
                numRepl edT ets. nc()
                (referenceT et.map(_.t et d), So (R ghtNodeType.ReplyT et))
              case So ( nteract onType.Ret et) =>
                numRet etedT ets. nc()
                (referenceT et.map(_.t et d), So (R ghtNodeType.Ret etT et))
              case _ => (None, None)
            }
          }
          for {
            t et d <- t et dOpt
            r ghtNodeType <- r ghtNodeTypeOpt
          } y eld {
            numUserT et nteract onEntr es. nc()
            ((user d, r ghtNodeType), t et d)
          }
        }

    userT et nteract onsByType
      .mapValues(Set(_))
      .sumByKey
      .flatMap {
        case ((user d, r ghtNodeType), t et dSet) =>
          t et dSet.map { t et d =>
            numD st nctUserT et nteract onEntr es. nc()
            (
              LeftNode.User d(user d),
              R ghtNodeW hEdge  ght(
                r ghtNode = R ghtNode(r ghtNodeType = r ghtNodeType, noun = Noun.T et d(t et d)),
                  ght = 1.0))
          }
      }
  }

  def getUserFavGraph(
    userUserFavEdges: TypedP pe[(User d, User d, Double)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val num nputFavEdges = Stat("num_ nput_fav_edges")
    userUserFavEdges.map {
      case (src d, dest d, edgeWt) =>
        num nputFavEdges. nc()
        (
          LeftNode.User d(src d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.FavUser, noun = Noun.User d(dest d)),
              ght = edgeWt))
    }
  }

  def getUserFollowGraph(
    userUserFollowEdges: TypedP pe[(User d, User d)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numFlockFollowEdges = Stat("num_flock_follow_edges")
    userUserFollowEdges.map {
      case (src d, dest d) =>
        numFlockFollowEdges. nc()
        (
          LeftNode.User d(src d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.FollowUser, noun = Noun.User d(dest d)),
              ght = 1.0))
    }
  }

  def getUserBlockGraph(
    userUserBlockEdges: TypedP pe[(User d, User d)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numFlockBlockEdges = Stat("num_flock_block_edges")
    userUserBlockEdges.map {
      case (src d, dest d) =>
        numFlockBlockEdges. nc()
        (
          LeftNode.User d(src d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.BlockUser, noun = Noun.User d(dest d)),
              ght = 1.0))
    }
  }

  def getUserAbuseReportGraph(
    userUserAbuseReportEdges: TypedP pe[(User d, User d)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numFlockAbuseEdges = Stat("num_flock_abuse_edges")
    userUserAbuseReportEdges.map {
      case (src d, dest d) =>
        numFlockAbuseEdges. nc()
        (
          LeftNode.User d(src d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.AbuseReportUser, noun = Noun.User d(dest d)),
              ght = 1.0))
    }
  }

  def f lter nval dUsers(
    flockEdges: TypedP pe[(User d, User d)],
    val dUsers: TypedP pe[User d]
  ): TypedP pe[(User d, User d)] = {
    flockEdges
      .jo n(val dUsers.asKeys)
      //      .w hReducers(10000)
      .map {
        case (src d, (dest d, _)) =>
          (dest d, src d)
      }
      .jo n(val dUsers.asKeys)
      //      .w hReducers(10000)
      .map {
        case (dest d, (src d, _)) =>
          (src d, dest d)
      }
  }

  def getUserSpamReportGraph(
    userUserSpamReportEdges: TypedP pe[(User d, User d)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numFlockSpamEdges = Stat("num_flock_spam_edges")
    userUserSpamReportEdges.map {
      case (src d, dest d) =>
        numFlockSpamEdges. nc()
        (
          LeftNode.User d(src d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.SpamReportUser, noun = Noun.User d(dest d)),
              ght = 1.0))
    }
  }

  def getUserTop cFollowGraph(
    top cUserFollo dByEdges: TypedP pe[(Top c d, User d)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numTFGEdges = Stat("num_tfg_edges")
    top cUserFollo dByEdges.map {
      case (top c d, user d) =>
        numTFGEdges. nc()
        (
          LeftNode.User d(user d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.FollowTop c, noun = Noun.Top c d(top c d)),
              ght = 1.0)
        )
    }
  }

  def getUserS gnUpCountryGraph(
    userS gnUpCountryEdges: TypedP pe[(User d, (Country, Language))]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numUserS ceEntr esRead = Stat("num_user_s ce_entr es")
    userS gnUpCountryEdges.map {
      case (user d, (country, lang)) =>
        numUserS ceEntr esRead. nc()
        (
          LeftNode.User d(user d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.S gnUpCountry, noun = Noun.Country(country)),
              ght = 1.0))
    }
  }

  def getMag cRecsNot fOpenOrCl ckT etsGraph(
    userMRNot fOpenOrCl ckEvents: TypedP pe[Mag cRecsNot f cat onL e]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numNot fOpenOrCl ckEntr es = Stat("num_not f_open_or_cl ck")
    userMRNot fOpenOrCl ckEvents.flatMap { entry =>
      numNot fOpenOrCl ckEntr es. nc()
      for {
        user d <- entry.targetUser d
        t et d <- entry.t et d
      } y eld {
        (
          LeftNode.User d(user d),
          R ghtNodeW hEdge  ght(
            r ghtNode = R ghtNode(
              r ghtNodeType = R ghtNodeType.Not fOpenOrCl ckT et,
              noun = Noun.T et d(t et d)),
              ght = 1.0))
      }
    }
  }

  def getUserConsu dLanguagesGraph(
    userConsu dLanguageEdges: TypedP pe[(User d, Seq[(Language, Double)])]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numPengu nS ceEntr esRead = Stat("num_pengu n_s ce_entr es")
    userConsu dLanguageEdges.flatMap {
      case (user d, langW h  ghts) =>
        numPengu nS ceEntr esRead. nc()
        langW h  ghts.map {
          case (lang,   ght) =>
            (
              LeftNode.User d(user d),
              R ghtNodeW hEdge  ght(
                r ghtNode = R ghtNode(
                  r ghtNodeType = R ghtNodeType.Consu dLanguage,
                  noun = Noun.Language(lang)),
                  ght =   ght))
        }
    }
  }

  def getSearchGraph(
    userSearchQueryEdges: TypedP pe[(User d, Str ng)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numSearchQuer es = Stat("num_search_quer es")
    userSearchQueryEdges.map {
      case (user d, query) =>
        numSearchQuer es. nc()
        (
          LeftNode.User d(user d),
          R ghtNodeW hEdge  ght(
            r ghtNode =
              R ghtNode(r ghtNodeType = R ghtNodeType.SearchQuery, noun = Noun.Query(query)),
              ght = 1.0))
    }
  }

  def bu ldEmployeeGraph(
    fullGraph: TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numEmployeeEdges = Stat("num_employee_edges")
    val employee ds = Conf g.SampledEmployee ds
    fullGraph
      .collect {
        case (LeftNode.User d(user d), r ghtNodeW h  ght)  f employee ds.conta ns(user d) =>
          numEmployeeEdges. nc()
          (LeftNode.User d(user d), r ghtNodeW h  ght)
      }
  }

  def getTruncatedGraph(
    fullGraph: TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)],
    topKW hFrequency: TypedP pe[(R ghtNodeType, Seq[(Noun, Double)])]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val numEntr esTruncatedGraph = Stat("num_entr es_truncated_graph")
    val numTopKTruncatedNouns = Stat("num_topk_truncated_nouns")

     mpl c  val r ghtNodeSer: R ghtNode => Array[Byte] = B naryScalaCodec(R ghtNode)
    val topNouns: TypedP pe[R ghtNode] = topKW hFrequency
      .flatMap {
        case (r ghtNodeType, nounsL st) =>
          nounsL st
            .map {
              case (nounVal, aggregatedFrequency) =>
                numTopKTruncatedNouns. nc()
                R ghtNode(r ghtNodeType, nounVal)
            }
      }

    fullGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          (r ghtNodeW h  ght.r ghtNode, (leftNode, r ghtNodeW h  ght))
      }
      .sketch(reducers = 5000)
      .jo n(topNouns.asKeys.toTypedP pe)
      .map {
        case (r ghtNode, ((left, r ghtNodeW h  ght), _)) =>
          numEntr esTruncatedGraph. nc()
          (left, r ghtNodeW h  ght)
      }
  }

  def getTopKR ghtNounsW hFrequenc es(
    fullGraph: TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)],
    topKConf g: Map[R ghtNodeType,  nt],
    m nFrequency:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(R ghtNodeType, Seq[(Noun, Double)])] = {
    val maxAcrossR ghtNounType:  nt = topKConf g.values erator.max
    fullGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          (r ghtNodeW h  ght.r ghtNode, 1.0)
      }
      .sumByKey
      //      .w hReducers(20000)
      .toTypedP pe
      .f lter(_._2 >= m nFrequency)
      .map {
        case (r ghtNode, freq) =>
          (r ghtNode.r ghtNodeType, (r ghtNode.noun, freq))
      }
      .group(r ghtNodeTypeOrder ng)
      // Note:  f maxAcrossR ghtNounType  s >15M,   m ght result  n OOM on reducer
      .sortedReverseTake(maxAcrossR ghtNounType)(Order ng.by(_._2))
      // An alternat ve to us ng group follo d by sortedReverseTake  s to def ne TopKMono ds,
      // one for each R ghtNodeType to get t  most frequent r ghtNouns
      .map {
        case (r ghtNodeType, nounsL stW hFreq) =>
          val truncatedL st = nounsL stW hFreq
            .sortBy(-_._2)
            .take(topKConf g.getOrElse(r ghtNodeType, NumTopNounsForUnknownR ghtNodeType))
          (r ghtNodeType, truncatedL st)
      }
  }

  def getVal dUsers(
    userS ce: TypedP pe[Comb nedUser]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[User d] = {
    val numVal dUsers = Stat("num_val d_users")
    userS ce
      .flatMap { u =>
        for {
          user <- u.user
           f user. d != 0
          safety <- user.safety
           f !(safety.suspended || safety.deact vated)
        } y eld {
          numVal dUsers. nc()
          user. d
        }
      }
  }

  def getFullGraph(
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] = {

    // l st of val d User ds - to f lter out deact vated or suspended user accounts
    val userS ce: TypedP pe[Comb nedUser] =
      DAL
        .readMostRecentSnapshotNoOlderThan(Users ceScalaDataset, Days(7)).toTypedP pe
    val val dUsers: TypedP pe[User d] = getVal dUsers(userS ce).forceToD sk

    //Dataset read operat ons

    //  eS ce t et engage nts data for t et favs, repl es, ret ets - from last 14 days
    val t etS ce: TypedP pe[ nteract onEvent] =
      ExternalDataS ces. eS ceT etEngage ntsS ce(dateRange =
        DateRange(dateRange.end - Days(14), dateRange.end))

    // user-user fav edges
    val userUserFavEdges: TypedP pe[(User d, User d, Double)] =
      ExternalDataS ces.getFavEdges(HalfL fe nDaysForFavScore)

    // user-user follow edges
    val userUserFollowEdges: TypedP pe[(User d, User d)] =
      f lter nval dUsers(ExternalDataS ces.flockFollowsS ce, val dUsers)

    // user-user block edges
    val userUserBlockEdges: TypedP pe[(User d, User d)] =
      f lter nval dUsers(ExternalDataS ces.flockBlocksS ce, val dUsers)

    // user-user abuse report edges
    val userUserAbuseReportEdges: TypedP pe[(User d, User d)] =
      f lter nval dUsers(ExternalDataS ces.flockReportAsAbuseS ce, val dUsers)

    // user-user spam report edges
    val userUserSpamReportEdges: TypedP pe[(User d, User d)] =
      f lter nval dUsers(ExternalDataS ces.flockReportAsSpamS ce, val dUsers)

    // user-s gnup country edges
    val userS gnUpCountryEdges: TypedP pe[(User d, (Country, Language))] =
      ExternalDataS ces.userS ce

    // user-consu d language edges
    val userConsu dLanguageEdges: TypedP pe[(User d, Seq[(Language, Double)])] =
      ExternalDataS ces. nferredUserConsu dLanguageS ce

    // user-top c follow edges
    val top cUserFollo dByEdges: TypedP pe[(Top c d, User d)] =
      ExternalDataS ces.top cFollowGraphS ce

    // user-MRNot fOpenOrCl ck events from last 7 days
    val userMRNot fOpenOrCl ckEvents: TypedP pe[Mag cRecsNot f cat onL e] =
      ExternalDataS ces.mag cRecsNotf cat onOpenOrCl ckEventsS ce(dateRange =
        DateRange(dateRange.end - Days(7), dateRange.end))

    // user-searchQuery str ngs from last 7 days
    val userSearchQueryEdges: TypedP pe[(User d, Str ng)] =
      ExternalDataS ces.adapt veSearchScr beLogsS ce(dateRange =
        DateRange(dateRange.end - Days(7), dateRange.end))

    getUserT et nteract onGraph(t etS ce) ++
      getUserFavGraph(userUserFavEdges) ++
      getUserFollowGraph(userUserFollowEdges) ++
      getUserBlockGraph(userUserBlockEdges) ++
      getUserAbuseReportGraph(userUserAbuseReportEdges) ++
      getUserSpamReportGraph(userUserSpamReportEdges) ++
      getUserS gnUpCountryGraph(userS gnUpCountryEdges) ++
      getUserConsu dLanguagesGraph(userConsu dLanguageEdges) ++
      getUserTop cFollowGraph(top cUserFollo dByEdges) ++
      getMag cRecsNot fOpenOrCl ckT etsGraph(userMRNot fOpenOrCl ckEvents) ++
      getSearchGraph(userSearchQueryEdges)
  }
}
