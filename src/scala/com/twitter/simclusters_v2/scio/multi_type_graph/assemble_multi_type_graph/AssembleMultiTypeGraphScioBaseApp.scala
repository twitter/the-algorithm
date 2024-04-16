package com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.coders.Coder
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.D skFormat
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.fr gate.data_p pel ne.mag crecs.mag crecs_not f cat ons_l e.thr ftscala.Mag cRecsNot f cat onL e
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter. es ce.thr ftscala. nteract onType
 mport com.tw ter. es ce.thr ftscala.ReferenceT et
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.sc o_ nternal.coders.Thr ftStructLazyB naryScroogeCoder
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.s mclusters_v2.common.Country
 mport com.tw ter.s mclusters_v2.common.Language
 mport com.tw ter.s mclusters_v2.common.Top c d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Mult TypeGraphForTopKR ghtNodesThr ftSc oScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.TopKR ghtNounsSc oScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.TruncatedMult TypeGraphSc oScalaDataset
 mport com.tw ter.s mclusters_v2.sc o.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.Conf g.GlobalDefaultM nFrequencyOfR ghtNodeType
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.Conf g.HalfL fe nDaysForFavScore
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.Conf g.NumTopNounsForUnknownR ghtNodeType
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.Conf g.SampledEmployee ds
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.Conf g.TopKConf g
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.assemble_mult _type_graph.Conf g.TopKR ghtNounsForMHDump
 mport com.tw ter.s mclusters_v2.sc o.mult _type_graph.common.Mult TypeGraphUt l
 mport com.tw ter.s mclusters_v2.thr ftscala.EdgeW hDecayed  ghts
 mport com.tw ter.s mclusters_v2.thr ftscala.LeftNode
 mport com.tw ter.s mclusters_v2.thr ftscala.Mult TypeGraphEdge
 mport com.tw ter.s mclusters_v2.thr ftscala.Noun
 mport com.tw ter.s mclusters_v2.thr ftscala.NounW hFrequency
 mport com.tw ter.s mclusters_v2.thr ftscala.NounW hFrequencyL st
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNode
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeType
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeTypeStruct
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeW hEdge  ght
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeW hEdge  ghtL st
 mport com.tw ter.twadoop.user.gen.thr ftscala.Comb nedUser
 mport com.tw ter.ut l.Durat on
 mport java.t  . nstant
 mport org.joda.t  . nterval

/**
 * Sc o vers on of
 * src/scala/com/tw ter/s mclusters_v2/scald ng/mult _type_graph/assemble_mult _type_graph/AssembleMult TypeGraph.scala
 */
tra  AssembleMult TypeGraphSc oBaseApp extends Sc oBeamJob[DateRangeOpt ons] {
  // Prov des an  mpl c  b nary thr ft scrooge coder by default.
  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder

  val  sAdhoc: Boolean
  val rootMHPath: Str ng
  val rootThr ftPath: Str ng

  val truncatedMult TypeGraphMHOutputD r: Str ng =
    Conf g.truncatedMult TypeGraphMHOutputD r
  val truncatedMult TypeGraphThr ftOutputD r: Str ng =
    Conf g.truncatedMult TypeGraphThr ftOutputD r
  val topKR ghtNounsMHOutputD r: Str ng = Conf g.topKR ghtNounsMHOutputD r
  val topKR ghtNounsOutputD r: Str ng = Conf g.topKR ghtNounsOutputD r

  val fullMult TypeGraphThr ftOutputD r: Str ng =
    Conf g.fullMult TypeGraphThr ftOutputD r
  val truncatedMult TypeGraphKeyValDataset: KeyValDALDataset[
    KeyVal[LeftNode, R ghtNodeW hEdge  ghtL st]
  ] = TruncatedMult TypeGraphSc oScalaDataset
  val topKR ghtNounsKeyValDataset: KeyValDALDataset[
    KeyVal[R ghtNodeTypeStruct, NounW hFrequencyL st]
  ] = TopKR ghtNounsSc oScalaDataset
  val topKR ghtNounsMHKeyValDataset: KeyValDALDataset[
    KeyVal[R ghtNodeTypeStruct, NounW hFrequencyL st]
  ] = TopKR ghtNounsMhSc oScalaDataset
  val fullMult TypeGraphSnapshotDataset: SnapshotDALDataset[Mult TypeGraphEdge] =
    FullMult TypeGraphSc oScalaDataset
  val mult TypeGraphTopKForR ghtNodesSnapshotDataset: SnapshotDALDataset[
    Mult TypeGraphEdge
  ] =
    Mult TypeGraphForTopKR ghtNodesThr ftSc oScalaDataset

  def getVal dUsers(
     nput: SCollect on[Comb nedUser]
  ): SCollect on[User d] = {
     nput
      .flatMap { u =>
        for {
          user <- u.user
           f user. d != 0
          safety <- user.safety
           f !(safety.suspended || safety.deact vated)
        } y eld {
          user. d
        }
      }
  }

  def f lter nval dUsers(
    flockEdges: SCollect on[(User d, User d)],
    val dUsers: SCollect on[User d]
  ): SCollect on[(User d, User d)] = {
    val val dUsersW hValues = val dUsers.map(user d => (user d, ()))
    flockEdges
      .jo n(val dUsersW hValues)
      .map {
        case (src d, (dest d, _)) =>
          (dest d, src d)
      }
      .jo n(val dUsersW hValues)
      .map {
        case (dest d, (src d, _)) =>
          (src d, dest d)
      }
  }

  def getFavEdges(
     nput: SCollect on[EdgeW hDecayed  ghts],
    halfL fe nDaysForFavScore:  nt,
  ): SCollect on[(Long, Long, Double)] = {
     nput
      .flatMap { edge =>
         f (edge.  ghts.halfL fe nDaysToDecayedSums.conta ns(halfL fe nDaysForFavScore)) {
          So (
            (
              edge.s ce d,
              edge.dest nat on d,
              edge.  ghts.halfL fe nDaysToDecayedSums(halfL fe nDaysForFavScore)))
        } else {
          None
        }
      }
  }

  def leftR ghtTuple(
    leftNodeUser d: User d,
    r ghtNodeType: R ghtNodeType,
    r ghtNoun: Noun,
      ght: Double = 1.0
  ): (LeftNode, R ghtNodeW hEdge  ght) = {
    (
      LeftNode.User d(leftNodeUser d),
      R ghtNodeW hEdge  ght(
        r ghtNode = R ghtNode(r ghtNodeType = r ghtNodeType, noun = r ghtNoun),
          ght =   ght))
  }

  def getUserFavGraph(
    userUserFavEdges: SCollect on[(User d, User d, Double)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userUserFavEdges.map {
      case (src d, dest d, edgeWt) =>
        leftR ghtTuple(src d, R ghtNodeType.FavUser, Noun.User d(dest d), edgeWt)
    }
  }

  def getUserFollowGraph(
    userUserFollowEdges: SCollect on[(User d, User d)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userUserFollowEdges.map {
      case (src d, dest d) =>
        leftR ghtTuple(src d, R ghtNodeType.FollowUser, Noun.User d(dest d), 1.0)
    }
  }

  def getUserBlockGraph(
    userUserBlockEdges: SCollect on[(User d, User d)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userUserBlockEdges.map {
      case (src d, dest d) =>
        leftR ghtTuple(src d, R ghtNodeType.BlockUser, Noun.User d(dest d), 1.0)
    }
  }

  def getUserAbuseReportGraph(
    userUserAbuseReportEdges: SCollect on[(User d, User d)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userUserAbuseReportEdges.map {
      case (src d, dest d) =>
        leftR ghtTuple(src d, R ghtNodeType.AbuseReportUser, Noun.User d(dest d), 1.0)
    }
  }

  def getUserSpamReportGraph(
    userUserSpamReportEdges: SCollect on[(User d, User d)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userUserSpamReportEdges.map {
      case (src d, dest d) =>
        leftR ghtTuple(src d, R ghtNodeType.SpamReportUser, Noun.User d(dest d), 1.0)
    }
  }

  def getUserTop cFollowGraph(
    top cUserFollo dByEdges: SCollect on[(Top c d, User d)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    top cUserFollo dByEdges.map {
      case (top c d, user d) =>
        leftR ghtTuple(user d, R ghtNodeType.FollowTop c, Noun.Top c d(top c d), 1.0)
    }
  }

  def getUserS gnUpCountryGraph(
    userS gnUpCountryEdges: SCollect on[(User d, Country)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userS gnUpCountryEdges.map {
      case (user d, country) =>
        leftR ghtTuple(user d, R ghtNodeType.S gnUpCountry, Noun.Country(country), 1.0)
    }
  }

  def getMag cRecsNot fOpenOrCl ckT etsGraph(
    userMRNot fOpenOrCl ckEvents: SCollect on[Mag cRecsNot f cat onL e]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userMRNot fOpenOrCl ckEvents.flatMap { entry =>
      for {
        user d <- entry.targetUser d
        t et d <- entry.t et d
      } y eld {
        leftR ghtTuple(user d, R ghtNodeType.Not fOpenOrCl ckT et, Noun.T et d(t et d), 1.0)
      }
    }
  }

  def getUserConsu dLanguagesGraph(
    userConsu dLanguageEdges: SCollect on[(User d, Seq[(Language, Double)])]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userConsu dLanguageEdges.flatMap {
      case (user d, langW h  ghts) =>
        langW h  ghts.map {
          case (lang,   ght) =>
            leftR ghtTuple(user d, R ghtNodeType.Consu dLanguage, Noun.Language(lang), 1.0)
        }
    }
  }

  def getSearchGraph(
    userSearchQueryEdges: SCollect on[(User d, Str ng)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    userSearchQueryEdges.map {
      case (user d, query) =>
        leftR ghtTuple(user d, R ghtNodeType.SearchQuery, Noun.Query(query), 1.0)
    }
  }

  def getUserT et nteract onGraph(
    t et nteract onEvents: SCollect on[ nteract onEvent],
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val userT et nteract onsByType: SCollect on[((User d, T et d), R ghtNodeType)] =
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
                    So (target d)
                  } else None,
                  So (R ghtNodeType.FavT et))
              case So ( nteract onType.Reply) =>
                (referenceT et.map(_.t et d), So (R ghtNodeType.ReplyT et))
              case So ( nteract onType.Ret et) =>
                (referenceT et.map(_.t et d), So (R ghtNodeType.Ret etT et))
              case _ => (None, None)
            }
          }
          for {
            t et d <- t et dOpt
            r ghtNodeType <- r ghtNodeTypeOpt
          } y eld {
            ((user d, t et d), r ghtNodeType)
          }
        }

    userT et nteract onsByType
      .mapValues(Set(_))
      .sumByKey
      .flatMap {
        case ((user d, t et d), r ghtNodeTypeSet) =>
          r ghtNodeTypeSet.map { r ghtNodeType =>
            leftR ghtTuple(user d, r ghtNodeType, Noun.T et d(t et d), 1.0)
          }
      }
  }

  def getTopKR ghtNounsW hFrequenc es(
    fullGraph: SCollect on[(LeftNode, R ghtNodeW hEdge  ght)],
    topKConf g: Map[R ghtNodeType,  nt],
    m nFrequency:  nt,
  ): SCollect on[(R ghtNodeType, Seq[(Noun, Double)])] = {
    val maxAcrossR ghtNounType:  nt = topKConf g.values erator.max

    fullGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          (r ghtNodeW h  ght.r ghtNode, 1.0)
      }
      .sumByKey
      .f lter(_._2 >= m nFrequency)
      .map {
        case (r ghtNode, freq) =>
          (r ghtNode.r ghtNodeType, (r ghtNode.noun, freq))
      }
      .topByKey(maxAcrossR ghtNounType)(Order ng.by(_._2))
      .map {
        case (r ghtNodeType, nounsL stW hFreq) =>
          val truncatedL st = nounsL stW hFreq.toSeq
            .sortBy(-_._2)
            .take(topKConf g.getOrElse(r ghtNodeType, NumTopNounsForUnknownR ghtNodeType))
          (r ghtNodeType, truncatedL st)
      }
  }

  def getTruncatedGraph(
    fullGraph: SCollect on[(LeftNode, R ghtNodeW hEdge  ght)],
    topKW hFrequency: SCollect on[(R ghtNodeType, Seq[(Noun, Double)])]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val topNouns = topKW hFrequency
      .flatMap {
        case (r ghtNodeType, nounsL st) =>
          nounsL st
            .map {
              case (nounVal, aggregatedFrequency) =>
                R ghtNode(r ghtNodeType, nounVal)
            }
      }.map(nouns => (nouns, ()))

    fullGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          (r ghtNodeW h  ght.r ghtNode, (leftNode, r ghtNodeW h  ght))
      }
      .hashJo n(topNouns)
      .map {
        case (r ghtNode, ((left, r ghtNodeW h  ght), _)) =>
          (left, r ghtNodeW h  ght)
      }
  }

  def bu ldEmployeeGraph(
    graph: SCollect on[(LeftNode, R ghtNodeW hEdge  ght)]
  ): SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] = {
    val employee ds = SampledEmployee ds
    graph
      .collect {
        case (LeftNode.User d(user d), r ghtNodeW h  ght)  f employee ds.conta ns(user d) =>
          (LeftNode.User d(user d), r ghtNodeW h  ght)
      }
  }

  overr de def conf gureP pel ne(sc: Sc oContext, opts: DateRangeOpt ons): Un  = {
    // Def ne t   mpl c  Sc oContext to read datasets from ExternalDataS ces
     mpl c  def sc oContext: Sc oContext = sc

    // DAL.Env ron nt var able for Wr eExecs
    val dalEnv =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

    // Def ne date  ntervals
    val  nterval_7days =
      new  nterval(opts. nterval.getEnd.m nus eks(1), opts. nterval.getEnd.m nusM ll s(1))
    val  nterval_14days =
      new  nterval(opts. nterval.getEnd.m nus eks(2), opts. nterval.getEnd.m nusM ll s(1))

    /*
     * Dataset read operat ons
     */
    // Get l st of val d User ds - to f lter out deact vated or suspended user accounts
    val val dUsers = getVal dUsers(ExternalDataS ces.userS ce(Durat on.fromDays(7)))

    //  eS ce t et engage nts data for t et favs, repl es, ret ets - from last 14 days
    val t etS ce = ExternalDataS ces. eS ceT etEngage ntsS ce( nterval_14days)

    // Read TFlock datasets
    val flockFollowS ce = ExternalDataS ces.flockFollowS ce(Durat on.fromDays(7))
    val flockBlockS ce = ExternalDataS ces.flockBlockS ce(Durat on.fromDays(7))
    val flockReportAsAbuseS ce =
      ExternalDataS ces.flockReportAsAbuseS ce(Durat on.fromDays(7))
    val flockReportAsSpamS ce =
      ExternalDataS ces.flockReportAsSpamS ce(Durat on.fromDays(7))

    // user-user fav edges
    val userUserFavS ce = ExternalDataS ces.userUserFavS ce(Durat on.fromDays(14))
    val userUserFavEdges = getFavEdges(userUserFavS ce, HalfL fe nDaysForFavScore)

    // user-user follow edges
    val userUserFollowEdges = f lter nval dUsers(flockFollowS ce, val dUsers)

    // user-user block edges
    val userUserBlockEdges = f lter nval dUsers(flockBlockS ce, val dUsers)

    // user-user abuse report edges
    val userUserAbuseReportEdges = f lter nval dUsers(flockReportAsAbuseS ce, val dUsers)

    // user-user spam report edges
    val userUserSpamReportEdges = f lter nval dUsers(flockReportAsSpamS ce, val dUsers)

    // user-s gnup country edges
    val userS gnUpCountryEdges = ExternalDataS ces
      .userCountryS ce(Durat on.fromDays(7))

    // user-consu d language edges
    val userConsu dLanguageEdges =
      ExternalDataS ces. nferredUserConsu dLanguageS ce(Durat on.fromDays(7))

    // user-top c follow edges
    val top cUserFollo dByEdges =
      ExternalDataS ces.top cFollowGraphS ce(Durat on.fromDays(7))

    // user-MRNot fOpenOrCl ck events from last 7 days
    val userMRNot fOpenOrCl ckEvents =
      ExternalDataS ces.mag cRecsNotf cat onOpenOrCl ckEventsS ce( nterval_7days)

    // user-searchQuery str ngs from last 7 days
    val userSearchQueryEdges =
      ExternalDataS ces.adapt veSearchScr beLogsS ce( nterval_7days)

    /*
     * Generate t  full graph
     */
    val fullGraph =
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

    // Get Top K R ghtNodes
    val topKR ghtNodes: SCollect on[(R ghtNodeType, Seq[(Noun, Double)])] =
      getTopKR ghtNounsW hFrequenc es(
        fullGraph,
        TopKConf g,
        GlobalDefaultM nFrequencyOfR ghtNodeType)

    // key transformat on - topK nouns, keyed by t  R ghtNodeNounType
    val topKNounsKeyedByType: SCollect on[(R ghtNodeTypeStruct, NounW hFrequencyL st)] =
      topKR ghtNodes
        .map {
          case (r ghtNodeType, r ghtNounsW hScoresL st) =>
            val nounsL stW hFrequency: Seq[NounW hFrequency] = r ghtNounsW hScoresL st
              .map {
                case (noun, aggregatedFrequency) =>
                  NounW hFrequency(noun, aggregatedFrequency)
              }
            (R ghtNodeTypeStruct(r ghtNodeType), NounW hFrequencyL st(nounsL stW hFrequency))
        }

    // Get Truncated graph based on t  top K R ghtNodes
    val truncatedGraph: SCollect on[(LeftNode, R ghtNodeW hEdge  ght)] =
      getTruncatedGraph(fullGraph, topKR ghtNodes)

    // key transformat ons - truncated graph, keyed by LeftNode
    // Note: By wrapp ng and unwrapp ng w h t  LeftNode.User d,   don't have to deal
    // w h def n ng   own custo r order ng for LeftNode type
    val truncatedGraphKeyedBySrc: SCollect on[(LeftNode, R ghtNodeW hEdge  ghtL st)] =
      truncatedGraph
        .collect {
          case (LeftNode.User d(user d), r ghtNodeW h  ght) =>
            user d -> L st(r ghtNodeW h  ght)
        }
        .sumByKey
        .map {
          case (user d, r ghtNodeW h  ghtL st) =>
            (LeftNode.User d(user d), R ghtNodeW hEdge  ghtL st(r ghtNodeW h  ghtL st))
        }

    // Wr eExecs
    // Wr e TopK R ghtNodes to DAL - save all t  top K nodes for t  cluster ng step
    topKNounsKeyedByType
      .map {
        case (engage ntType, r ghtL st) =>
          KeyVal(engage ntType, r ghtL st)
      }
      .saveAsCustomOutput(
        na  = "Wr eTopKNouns",
        DAL.wr eVers onedKeyVal(
          topKR ghtNounsKeyValDataset,
          PathLa t.Vers onedPath(pref x =
            rootMHPath + topKR ghtNounsOutputD r),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = dalEnv,
        )
      )

    // Wr e TopK R ghtNodes to DAL - only take TopKR ghtNounsForMHDump R ghtNodes for MH dump
    topKNounsKeyedByType
      .map {
        case (engage ntType, r ghtL st) =>
          val r ghtL stMH =
            NounW hFrequencyL st(r ghtL st.nounW hFrequencyL st.take(TopKR ghtNounsForMHDump))
          KeyVal(engage ntType, r ghtL stMH)
      }
      .saveAsCustomOutput(
        na  = "Wr eTopKNounsToMHForDebugger",
        DAL.wr eVers onedKeyVal(
          topKR ghtNounsMHKeyValDataset,
          PathLa t.Vers onedPath(pref x =
            rootMHPath + topKR ghtNounsMHOutputD r),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = dalEnv,
        )
      )

    // Wr e truncated graph (Mult TypeGraphTopKForR ghtNodes) to DAL  n KeyVal format
    truncatedGraphKeyedBySrc
      .map {
        case (leftNode, r ghtNodeW h  ghtL st) =>
          KeyVal(leftNode, r ghtNodeW h  ghtL st)
      }.saveAsCustomOutput(
        na  = "Wr eTruncatedMult TypeGraph",
        DAL.wr eVers onedKeyVal(
          truncatedMult TypeGraphKeyValDataset,
          PathLa t.Vers onedPath(pref x =
            rootMHPath + truncatedMult TypeGraphMHOutputD r),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = dalEnv,
        )
      )

    // Wr e truncated graph (Mult TypeGraphTopKForR ghtNodes) to DAL  n thr ft format
    truncatedGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          Mult TypeGraphEdge(leftNode, r ghtNodeW h  ght)
      }.saveAsCustomOutput(
        na  = "Wr eTruncatedMult TypeGraphThr ft",
        DAL.wr eSnapshot(
          mult TypeGraphTopKForR ghtNodesSnapshotDataset,
          PathLa t.F xedPath(rootThr ftPath + truncatedMult TypeGraphThr ftOutputD r),
           nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          D skFormat.Thr ft(),
          env ron ntOverr de = dalEnv
        )
      )

    // Wr e full graph to DAL
    fullGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          Mult TypeGraphEdge(leftNode, r ghtNodeW h  ght)
      }
      .saveAsCustomOutput(
        na  = "Wr eFullMult TypeGraph",
        DAL.wr eSnapshot(
          fullMult TypeGraphSnapshotDataset,
          PathLa t.F xedPath(rootThr ftPath + fullMult TypeGraphThr ftOutputD r),
           nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          D skFormat.Thr ft(),
          env ron ntOverr de = dalEnv
        )
      )

  }
}
