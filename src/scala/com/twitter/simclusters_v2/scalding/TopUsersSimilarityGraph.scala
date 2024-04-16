package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Max
 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter. rm .cand date.thr ftscala.Cand date
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.pluck.s ce.cassowary.Follow ngsCos neS m lar  esManhattanS ce
 mport com.tw ter.sbf.core.Algor hmConf g
 mport com.tw ter.sbf.core.MHAlgor hm
 mport com.tw ter.sbf.core.Pred ct onStat
 mport com.tw ter.sbf.core.SparseB naryMatr x
 mport com.tw ter.sbf.core.SparseRealMatr x
 mport com.tw ter.sbf.graph.Graph
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.s ce.lzo_scrooge.F xedPathLzoScrooge
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser
 mport com.tw ter.wtf.scald ng.s ms.thr ftscala.S m larUserPa r
 mport java. o.Pr ntWr er
 mport java.text.Dec malFormat
 mport java.ut l
 mport org.apac .hadoop.conf.Conf gurat on
 mport org.apac .hadoop.fs.F leSystem
 mport org.apac .hadoop.fs.Path
 mport scala.collect on.JavaConverters._

case class TopUser( d: Long, act veFollo rCount:  nt, screenNa : Str ng)

case class TopUserW hMapped d(topUser: TopUser, mapped d:  nt)

case class AdjL st(s ce d: Long, ne ghbors: L st[(Long, Float)])

object TopUsersS m lar yGraph {
  val log = Logger()

  def topUsers(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt,
    topK:  nt
  ): TypedP pe[TopUser] = {
    userS ceP pe
      .collect {
        case f: FlatUser
             f f.act veFollo rs.ex sts(_ >= m nAct veFollo rs)
              && f.follo rs. sDef ned && f. d. sDef ned && f.screenNa . sDef ned
              && !f.deact vated.conta ns(true) && !f.suspended.conta ns(true) =>
          TopUser(f. d.get, f.act veFollo rs.get.to nt, f.screenNa .get)
      }
      .groupAll
      .sortedReverseTake(topK)(Order ng.by(_.act veFollo rCount))
      .values
      .flatten
  }

  /**
   * T  funct on returns t  top most follo d user ds truncated to topK
   * Offers t  sa  funct onal y as TopUsersS m lar yGraph.topUsers but more eff c ent
   * as   donot store screenna s wh le group ng and sort ng t  users
   */
  def topUser ds(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt,
    topK:  nt
  ): TypedP pe[Long] = {
    userS ceP pe
      .collect {
        case f: FlatUser
             f f.act veFollo rs.ex sts(_ >= m nAct veFollo rs)
              && f.follo rs. sDef ned && f. d. sDef ned && f.screenNa . sDef ned
              && !f.deact vated.conta ns(true) && !f.suspended.conta ns(true) =>
          (f. d.get, f.act veFollo rs.get)
      }
      .groupAll
      .sortedReverseTake(topK)(Order ng.by(_._2))
      .values
      .flatten
      .keys
  }

  def topUsersW hMapped ds(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt
  ): TypedP pe[TopUserW hMapped d] = {
    userS ceP pe
      .collect {
        case f: FlatUser
             f f.act veFollo rs.ex sts(_ >= m nAct veFollo rs)
              && f.follo rs. sDef ned && f. d. sDef ned && f.screenNa . sDef ned
              && !f.deact vated.conta ns(true) && !f.suspended.conta ns(true) =>
          TopUser(f. d.get, f.act veFollo rs.get.to nt, f.screenNa .get)
      }
      .groupAll
      .mapGroup {
        case (_, topUser er) =>
          topUser er.z pW h ndex.map {
            case (topUser,  d) =>
              TopUserW hMapped d(topUser,  d)
          }
      }
      .values
  }

  def topUsersW hMapped dsTopK(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt,
    topK:  nt
  ): TypedP pe[TopUserW hMapped d] = {
    userS ceP pe
      .collect {
        case f: FlatUser
             f f.act veFollo rs.ex sts(_ >= m nAct veFollo rs)
              && f.follo rs. sDef ned && f. d. sDef ned && f.screenNa . sDef ned
              && !f.deact vated.conta ns(true) && !f.suspended.conta ns(true) =>
          TopUser(f. d.get, f.act veFollo rs.get.to nt, f.screenNa .get)
      }
      .groupAll
      .sortedReverseTake(topK)(Order ng.by(_.act veFollo rCount))
      .map {
        case (_, topUser er) =>
          topUser er.z pW h ndex.map {
            case (topUser,  d) =>
              TopUserW hMapped d(topUser,  d)
          }
      }
      .flatten
  }

  /**
   * T  funct on returns t  top most follo d and ver f ed user ds truncated to topK
   */
  def v s(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt,
    topK:  nt
  ): TypedP pe[Long] = {
    userS ceP pe
      .collect {
        case f: FlatUser
             f f.ver f ed.conta ns(true) && f. d. sDef ned &&
              f.screenNa . sDef ned && !f.deact vated.conta ns(true) && !f.suspended.conta ns(
              true) &&
              f.act veFollo rs.ex sts(_ >= m nAct veFollo rs) =>
          (f. d.get, f.act veFollo rs.get)
      }
      .groupAll
      .sortedReverseTake(topK)(Order ng.by(_._2))
      .values
      .flatten
      .keys
  }

  def topUsers n mory(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt,
    topK:  nt
  ): Execut on[L st[TopUserW hMapped d]] = {
    log. nfo(s"W ll fetch top $topK users w h at least $m nAct veFollo rs many act ve follo rs")
    topUsers(userS ceP pe, m nAct veFollo rs, topK).to erableExecut on
      .map {  dFollo rsL st =>
         dFollo rsL st.toL st.sortBy(_. d).z pW h ndex.map {
          case (topuser,  ndex) =>
            TopUserW hMapped d(topuser,  ndex)
        }
      }
  }

  def addSelfLoop(
     nput: TypedP pe[(Long, Map[Long, Float])],
    maxToSelfLoop  ght: Float => Float
  ): TypedP pe[(Long, Map[Long, Float])] = {
     nput
      .map {
        case (node d, ne ghborMap)  f ne ghborMap.nonEmpty =>
          val maxEntry = ne ghborMap.values.max
          val selfLoop  ght = maxToSelfLoop  ght(maxEntry)
          (node d, ne ghborMap ++ Map(node d -> selfLoop  ght))
        case (node d, emptyMap) =>
          (node d, emptyMap)
      }
  }

  def makeGraph(
    backf llP pe: TypedP pe[(Long, Map[Long, Float])],
    d rToReadFromOrSaveTo: Str ng
  ): Execut on[TypedP pe[(Long, Map[Long, Float])]] = {
    backf llP pe
      .map {
        case (node d, nbrMap) =>
          val cands = nbrMap.toL st.map { case (n d, wt) => Cand date(n d, wt) }
          Cand dates(node d, cand dates = cands)
      }
      .make(new F xedPathLzoScrooge(d rToReadFromOrSaveTo, Cand dates))
      .map { tp =>
        tp.map {
          case Cand dates(node d, cands) =>
            (node d, cands.map { case Cand date(n d, wt, _) => (n d, wt.toFloat) }.toMap)
        }
      }
  }

  def getSubgraphFromUserGrouped nput(
    fullGraph: TypedP pe[Cand dates],
    usersTo nclude: TypedP pe[Long],
    maxNe ghborsPerNode:  nt,
    degreeThresholdForStat:  nt
  )(
     mpl c  un q d: Un que D
  ): TypedP pe[(Long, Map[Long, Float])] = {
    val numUsersW hZeroEdges = Stat("num_users_w h_zero_edges")
    val numUsersW hSmallDegree = Stat("num_users_w h_degree_lt_" + degreeThresholdForStat)
    val numUsersW hEnoughDegree = Stat("num_users_w h_degree_gte_" + degreeThresholdForStat)

    fullGraph
      .map { cands =>
        (
          cands.user d,
          // T se cand dates are already sorted, but leav ng    n just  n case t  behav or changes upstream
          cands.cand dates
            .map { c => (c.user d, c.score) }.sortBy(-_._2).take(maxNe ghborsPerNode).toMap
        )
      }
      .r ghtJo n(usersTo nclude.asKeys)
      // uncom nt for adhoc job
      //.w hReducers(110)
      .mapValues(_._1) // d scard t  Un 
      .toTypedP pe
      .count("num_s ms_records_from_top_users")
      .flatMap {
        case (node d, So (ne ghborMap)) =>
          ne ghborMap.flatMap {
            case (ne ghbor d, edgeWt) =>
              L st(
                (node d, Map(ne ghbor d -> Max(edgeWt.toFloat))),
                (ne ghbor d, Map(node d -> Max(edgeWt.toFloat)))
              )
          }
        case (node d, None) => L st((node d, Map.empty[Long, Max[Float]]))
      }
      .sumByKey
      // uncom nt for adhoc job
      //.w hReducers(150)
      .toTypedP pe
      .mapValues(_.mapValues(_.get)) // get t  max for each value  n each map
      .count("num_s ms_records_after_sym tr zat on_before_keep ng_only_top_users")
      .jo n(usersTo nclude.asKeys) // only keep records for top users
      // uncom nt for adhoc job
      //.w hReducers(100)
      .mapValues(_._1)
      .toTypedP pe
      .map {
        case (node d, ne ghborsMap) =>
           f (ne ghborsMap.nonEmpty) {
             f (ne ghborsMap.s ze < degreeThresholdForStat) {
              numUsersW hSmallDegree. nc()
            } else {
              numUsersW hEnoughDegree. nc()
            }
          } else {
            numUsersW hZeroEdges. nc()
          }
          (node d, ne ghborsMap)
      }
      .count("num_s ms_records_after_sym tr zat on_only_top_users")
  }

  def getSubgraphFromUserGrouped nput(
    fullGraph: TypedP pe[Cand dates],
    usersTo nclude: Set[Long],
    maxNe ghborsPerNode:  nt
  )(
     mpl c  un q d: Un que D
  ): TypedP pe[(Long, Map[Long, Float])] = {
    val numUsersW hZeroEdges = Stat("num_users_w h_zero_edges")
    val numUsersW hDegreeLessThan10 = Stat("num_users_w h_degree_less_than_10")

    val ( nt dsTo ncludeSorted: Array[ nt], long dsTo ncludeSorted: Array[Long]) =
      setToSortedArrays(usersTo nclude)
    log. nfo("S ze of  ntArray " +  nt dsTo ncludeSorted.length)
    log. nfo("S ze of longArray " + long dsTo ncludeSorted.length)

    fullGraph
      .collect {
        case cand dates
             f  s d n ntOrLongArray(
              cand dates.user d,
               nt dsTo ncludeSorted,
              long dsTo ncludeSorted) =>
          val s ce d = cand dates.user d
          val toKeep = cand dates.cand dates.collect {
            case ne ghbor
                 f  s d n ntOrLongArray(
                  ne ghbor.user d,
                   nt dsTo ncludeSorted,
                  long dsTo ncludeSorted) =>
              (ne ghbor.user d, ne ghbor.score.toFloat)
          }.toL st

          val toKeepLength = toKeep.s ze
           f (toKeep. sEmpty) {
            numUsersW hZeroEdges. nc()
          } else  f (toKeepLength < 10) {
            numUsersW hDegreeLessThan10. nc()
          }

          val knn =  f (toKeepLength > maxNe ghborsPerNode) {
            toKeep.sortBy(_._2).takeR ght(maxNe ghborsPerNode)
          } else toKeep

          knn.flatMap {
            case (nbr d, wt) =>
              L st(
                (s ce d, Map(nbr d -> Max(wt))),
                (nbr d, Map(s ce d -> Max(wt)))
              )
          }
      }
      .flatten
      .sumByKey
      .toTypedP pe
      .mapValues(_.mapValues(_.get)) // get t  max for each value  n each map
  }

  def get n morySubgraphFromUserGrouped nput(
    fullGraph: TypedP pe[Cand dates],
    usersTo nclude: Set[Long],
    maxNe ghborsPerNode:  nt
  )(
     mpl c  un q d: Un que D
  ): Execut on[ erable[AdjL st]] = {
    getSubgraphFromUserGrouped nput(fullGraph, usersTo nclude, maxNe ghborsPerNode).map {
      case (s ce d,   ghtedNe ghbors) =>
        AdjL st(
          s ce d,
            ghtedNe ghbors.toL st.sortBy(_._1)
        )
    }.to erableExecut on
  }

  def  s d n ntOrLongArray(
     d: Long,
     ntArraySorted: Array[ nt],
    longArraySorted: Array[Long]
  ): Boolean = {
     f ( d <  nteger.MAX_VALUE) {
      ut l.Arrays.b narySearch( ntArraySorted,  d.to nt) >= 0
    } else {
      ut l.Arrays.b narySearch(longArraySorted,  d.toLong) >= 0
    }
  }

  /**
   * Creates two sorted arrays out of a set, one w h  nts and one w h longs.
   * Sorted arrays are only sl ghtly more expens ve to search  n, but emp r cally  've found
   * that t  MapReduce job runs more rel ably us ng t m than us ng Set d rectly.
   *
   * @param  nSet
   *
   * @return
   */
  def setToSortedArrays( nSet: Set[Long]): (Array[ nt], Array[Long]) = {
    val ( ntArrayUnconvertedSorted, longArraySorted) =
       nSet.toArray.sorted.part  on { l => l <  nteger.MAX_VALUE }
    ( ntArrayUnconvertedSorted.map(_.to nt), longArraySorted)
  }

  def get n morySubgraph(
    fullGraph: TypedP pe[S m larUserPa r],
    usersTo nclude: Set[Long],
    maxNe ghborsPerNode:  nt
  )(
     mpl c  un q d: Un que D
  ): Execut on[ erable[AdjL st]] = {
    val numVal dEdges = Stat("num_val d_edges")
    val num nval dEdges = Stat("num_ nval d_edges")

    val ( nt dsTo ncludeSorted: Array[ nt], long dsTo ncludeSorted: Array[Long]) =
      setToSortedArrays(usersTo nclude)
    log. nfo("S ze of  ntArray " +  nt dsTo ncludeSorted.length)
    log. nfo("S ze of longArray " + long dsTo ncludeSorted.length)

    fullGraph
      .f lter { edge =>
        val res =
           s d n ntOrLongArray(edge.s ce d,  nt dsTo ncludeSorted, long dsTo ncludeSorted) &&
             s d n ntOrLongArray(edge.dest nat on d,  nt dsTo ncludeSorted, long dsTo ncludeSorted)
         f (res) {
          numVal dEdges. nc()
        } else {
          num nval dEdges. nc()
        }
        res
      }
      .map { edge => (edge.s ce d, (edge.dest nat on d, edge.cos neScore.toFloat)) }
      .group
      .sortedReverseTake(maxNe ghborsPerNode)(Order ng.by(_._2))
      .toTypedP pe
      .flatMap {
        case (s ce d,   ghtedNe ghbors) =>
            ghtedNe ghbors.flatMap {
            case (dest d, wt) =>
              /*
          By default, a k-nearest ne ghbor graph need not be sym tr c, s nce  f u  s  n v's
          k nearest ne ghbors, that doesn't guarantee that v  s  n u's.
          T  step adds edges  n both d rect ons, but hav ng a Map ensures that each ne ghbor
          only appears once and not tw ce. Us ng Max() operator from Algeb rd,   take t  max
            ght of (u, v) and (v, u) -    s expected that t  two w ll be pretty much t  sa .

          Example  llustrat ng how Map and Max work toget r:
          Map(1 -> Max(2)) + Map(1 -> Max(3)) = Map(1 -> Max(3))
               */
              L st(
                (s ce d, Map(dest d -> Max(wt))),
                (dest d, Map(s ce d -> Max(wt)))
              )
          }
      }
      .sumByKey
      .map {
        case (s ce d,   ghtedNe ghbors) =>
          AdjL st(
            s ce d,
              ghtedNe ghbors.toL st.map { case ( d, maxWt) => ( d, maxWt.get) }.sortBy(_._1)
          )
      }
      .to erableExecut on
  }

  def convert erableToGraph(
    adjL st:  erable[AdjL st],
    vert cesMapp ng: Map[Long,  nt],
    wtExponent: Float
  ): Graph = {
    val n = vert cesMapp ng.s ze
    val ne ghbors: Array[Array[ nt]] = new Array[Array[ nt]](n)
    val wts: Array[Array[Float]] = new Array[Array[Float]](n)

    var numEdges = 0L
    var numVert ces = 0

    val  er = adjL st. erator
    val vert cesW hAtleastOneEdgeBu lder = Set.newBu lder[Long]

    wh le ( er.hasNext) {
      val AdjL st(or g nal d, wtedNe ghbors) =  er.next()
      val wtedNe ghborsS ze = wtedNe ghbors.s ze
      val new d = vert cesMapp ng(or g nal d) // throw except on  f or g nal d not  n map
       f (new d < 0 || new d >= n) {
        throw new  llegalStateExcept on(
          s"$or g nal d has been mapped to $new d, wh ch  s outs de" +
            s"t  expected range [0, " + (n - 1) + "]")
      }
      vert cesW hAtleastOneEdgeBu lder += or g nal d
      ne ghbors(new d) = new Array[ nt](wtedNe ghborsS ze)
      wts(new d) = new Array[Float](wtedNe ghborsS ze)
      wtedNe ghbors.z pW h ndex.foreach {
        case ((nbr d, wt),  ndex) =>
          ne ghbors(new d)( ndex) = vert cesMapp ng(nbr d)
          wts(new d)( ndex) = wt
          numEdges += 1
      }

       f (math.abs(wtExponent - 1.0) > 1e-5) {
        var maxWt = Float.M nValue
        for ( ndex <- wts(new d). nd ces) {
          wts(new d)( ndex) = math.pow(wts(new d)( ndex), wtExponent).toFloat
           f (wts(new d)( ndex) > maxWt) {
            maxWt = wts(new d)( ndex)
          }
        }
      }
      numVert ces += 1
       f (numVert ces % 100000 == 0) {
        log. nfo(s"Done w h $numVert ces many vert ces.")
      }
    }

    val vert cesW hAtleastOneEdge = vert cesW hAtleastOneEdgeBu lder.result()
    val vert cesW hZeroEdges = vert cesMapp ng.keySet.d ff(vert cesW hAtleastOneEdge)

    vert cesW hZeroEdges.foreach { or g nal d =>
      ne ghbors(vert cesMapp ng(or g nal d)) = new Array[ nt](0)
      wts(vert cesMapp ng(or g nal d)) = new Array[Float](0)
    }

    log. nfo("Number of vert ces w h zero edges " + vert cesW hZeroEdges.s ze)
    log. nfo("Number of edges " + numEdges)
     f (vert cesW hZeroEdges.nonEmpty) {
      log. nfo("T  vert ces w h zero edges: " + vert cesW hZeroEdges.mkStr ng(","))
    }

    new Graph(n, numEdges / 2, ne ghbors, wts)
  }

  def run(
    userS ceP pe: TypedP pe[FlatUser],
    m nAct veFollo rs:  nt,
    topK:  nt,
    getSubgraphFn: Set[Long] => Execut on[ erable[AdjL st]],
    wtExponent: Float
  )(
     mpl c   d: Un que D
  ): Execut on[(L st[TopUserW hMapped d], Graph)] = {
    topUsers n mory(
      userS ceP pe,
      m nAct veFollo rs,
      topK
    ).flatMap { topUsers =>
      val  dMap = topUsers.map { topUser => (topUser.topUser. d, topUser.mapped d) }.toMap

      log. nfo("Got  dMap w h " +  dMap.s ze + " entr es.")
      getSubgraphFn( dMap.keySet)
        .map {  erableAdjL sts =>
          log. nfo("Go ng to convert  erable to graph")
          val t c = System.currentT  M ll s()
          val graph = convert erableToGraph(
             erableAdjL sts,
             dMap,
            wtExponent
          )
          val toc = System.currentT  M ll s()
          val seconds = (toc - t c) * 1.0 / 1e6
          log. nfo("Took %.2f seconds to convert  erable to graph".format(seconds))
          (topUsers, graph)
        }
    }
  }

  def runUs ngJo n(
    mappedUsers: TypedP pe[(Long,  nt)],
    allEdges: TypedP pe[Cand dates],
    maxNe ghborsPerNode:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[( nt, Str ng)] = {
    val numEdgesAfterF rstJo n = Stat("num_edges_after_f rst_jo n")
    val numEdgesAfterSecondJo n = Stat("num_edges_after_second_jo n")
    val numEdgesLostTopKTruncated = Stat("num_edges_lost_topk_truncated")
    val f nalNumEdges = Stat("f nal_num_edges")

    allEdges
      .map { cs => (cs.user d, cs.cand dates) }
      .jo n(mappedUsers)
      .w hReducers(6000)
      .flatMap {
        case ( d, (ne ghbors, mapped d)) =>
          val before = ne ghbors.s ze
          val topKNe ghbors = ne ghbors.sortBy(-_.score).take(maxNe ghborsPerNode)
          val after = topKNe ghbors.s ze
          numEdgesLostTopKTruncated. ncBy(before - after)
          topKNe ghbors.map { cand date =>
            numEdgesAfterF rstJo n. nc()
            (cand date.user d, (mapped d, cand date.score.toFloat))
          }
      }
      .jo n(mappedUsers)
      .w hReducers(9000)
      .flatMap {
        case ( d, ((mappedNe ghbor d, score), mapped d)) =>
          numEdgesAfterSecondJo n. nc()
          L st(
            (mapped d, Map(mappedNe ghbor d -> Max(score))),
            (mappedNe ghbor d, Map(mapped d -> Max(score)))
          )
      }
      .sumByKey
      .w hReducers(9100)
      .map {
        case ( d, nbrMap) =>
          val sorted = nbrMap.mapValues(_.get).toL st.sortBy(-_._2)
          f nalNumEdges. ncBy(sorted.s ze)
          val str = sorted.map { case (nbr d, wt) => "%d %.2f".format(nbr d, wt) }.mkStr ng(" ")
          ( d, str)
      }

  }

  def wr eToHDFSF le(l nes:  erator[Str ng], conf: Conf gurat on, outputF le: Str ng): Un  = {
    val fs = F leSystem.new nstance(conf)
    val outputStream = fs.create(new Path(outputF le))
    log. nfo("W ll wr e to " + outputF le)
    var numL nes = 0
    val t c = System.currentT  M ll s()
    try {
      val wr er = new Pr ntWr er(outputStream)
      wh le (l nes.hasNext) {
        wr er.pr ntln(l nes.next())
        numL nes += 1
         f (numL nes % 1000000 == 0) {
          log. nfo(s"Done wr  ng $numL nes l nes")
        }
      }
      wr er.flush()
      wr er.close()
    } f nally {
      outputStream.close()
    }
    val toc = System.currentT  M ll s()
    val seconds = (toc - t c) * 1.0 / 1e6
    log. nfo(
      "F n s d wr  ng %d l nes to %s. Took %.2f seconds".format(numL nes, outputF le, seconds))
  }

  def wr eToHDFS fHDFS(l nes:  erator[Str ng], mode: Mode, outputF le: Str ng): Un  = {
    mode match {
      case Hdfs(_, conf) =>
        wr eToHDFSF le(l nes, conf, outputF le)
      case _ => ()
    }
  }

  def wr eTopUsers(topUsers: L st[TopUserW hMapped d], mode: Mode, outputF le: Str ng): Un  = {
    val topUsersL nes =
      topUsers.map { topUser =>
        // Add 1 to mapped d so as to get 1- ndexed  ds, wh ch are fr endl er to humans.
        L st(
          topUser.topUser. d,
          topUser.mapped d + 1,
          topUser.topUser.screenNa ,
          topUser.topUser.act veFollo rCount
        ).mkStr ng("\t")
      }. erator
    wr eToHDFS fHDFS(topUsersL nes, mode, outputF le)
  }

  def readS ms nput( sKeyValS ce: Boolean,  nputD r: Str ng): TypedP pe[Cand dates] = {
     f ( sKeyValS ce) {
      log. nfo("W ll treat " +  nputD r + " as SequenceF les  nput")
      val raw nput = Follow ngsCos neS m lar  esManhattanS ce(path =  nputD r)
      TypedP pe.from(raw nput).map(_._2)
    } else {
      log. nfo("W ll treat " +  nputD r + " as LzoScrooge  nput")
      TypedP pe.from(new F xedPathLzoScrooge( nputD r, Cand dates))
    }
  }
}

/**
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:top_users_only && \
 * oscar hdfs --hadoop-cl ent- mory 120000 --user cassowary --host atla-aor-08-sr1 \
 * --bundle top_users_only --tool com.tw ter.s mclusters_v2.scald ng.ClusterHdfsGraphApp \
 * --screen --screen-detac d --tee ldap_logs/SBFOnSubGraphOf100MTopusersW hMapped ds_120GB_RAM \
 * -- -- nputD r adhoc/ldap_subgraphOf100MTopUsersW hMapped ds --numNodesPerCommun y 200 \
 * --outputD r adhoc/ldap_SBFOnSubGraphOf100MTopusersW hMapped ds_k500K_120GB_RAM --assu dNumberOfNodes 100200000
 */
object ClusterHdfsGraphApp extends Tw terExecut onApp {
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val  nputD r = args(" nputD r")
          val numNodesPerCommun y = args. nt("numNodesPerCommun y", 200)
          val outputD r = args("outputD r")
          val assu dNumberOfNodes = args. nt("assu dNumberOfNodes")
          //val useEdge  ghts = args.boolean("useEdge  ghts")

          val  nput = TypedP pe.from(TypedTsv[( nt, Str ng)]( nputD r)).map {
            case ( d, nbrStr) =>
              val nbrsW h  ghts = nbrStr.spl (" ")
              val nbrsArray = nbrsW h  ghts.z pW h ndex
                .collect {
                  case (str,  ndex)  f  ndex % 2 == 0 =>
                    str.to nt
                }
              ( d, nbrsArray.sorted)
          }

          pr ntln("Gonna assu  total number of nodes  s " + assu dNumberOfNodes)

           nput.to erableExecut on.flatMap { adjL sts er =>
            val nbrs: Array[Array[ nt]] = new Array[Array[ nt]](assu dNumberOfNodes)
            var numEdges = 0L
            var numVert ces = 0
            var maxVertex d = 0

            val t c = System.currentT  M ll s
            adjL sts er.foreach {
              case ( d, nbrArray) =>
                 f ( d >= assu dNumberOfNodes) {
                  throw new  llegalStateExcept on(
                    s"Y kes! Entry w h  d $ d, >= assu dNumberOfNodes")
                }
                nbrs( d) = nbrArray
                 f ( d > maxVertex d) {
                  maxVertex d =  d
                }
                numEdges += nbrArray.length
                numVert ces += 1
                 f (numVert ces % 100000 == 0) {
                  pr ntln(s"Done load ng $numVert ces many vert ces. Edges so far: $numEdges")
                }
            }
            (0 unt l assu dNumberOfNodes).foreach {   =>
               f (nbrs( ) == null) {
                nbrs( ) = Array[ nt]()
              }
            }
            val toc = System.currentT  M ll s()
            pr ntln(
              "maxVertex d  s " + maxVertex d + ", assu dNumberOfNodes  s " + assu dNumberOfNodes)
            pr ntln(
              s"Done load ng graph w h $assu dNumberOfNodes nodes and $numEdges edges (count ng each edge tw ce)")
            pr ntln("Number of nodes w h at least ne ghbor  s " + numVert ces)
            pr ntln("T   to load t  graph " + (toc - t c) / 1000.0 / 60.0 + " m nutes")

            val graph = new Graph(assu dNumberOfNodes, numEdges / 2, nbrs, null)
            val k = assu dNumberOfNodes / numNodesPerCommun y
            pr ntln("W ll set number of commun  es to " + k)
            val algoConf g = new Algor hmConf g()
              .w hCpu(16).w hK(k)
              .w hWtCoeff(10.0).w hMaxEpoch(5)
            var z = new SparseB naryMatr x(assu dNumberOfNodes, k)
            val err = new Pr ntWr er(System.err)

            pr ntln("Go ng to  n al ze from random ne ghborhoods")
            z. n FromBestNe ghborhoods(
              graph,
              (gr: Graph,  :  nteger) => algoConf g.rng.nextDouble,
              false,
              err)
            pr ntln("Done  n  al z ng from random ne ghborhoods")

            val prec0 = MHAlgor hm.clusterPrec s on(graph, z, 0, 1000, algoConf g.rng)
            pr ntln("Prec s on of cluster 0:" + prec0.prec s on)
            val prec1 = MHAlgor hm.clusterPrec s on(graph, z, 1, 1000, algoConf g.rng)
            pr ntln("Prec s on of cluster 1:" + prec1.prec s on)
            pr ntln(
              "Fract on of empty rows after  n  al z ng from random ne ghborhoods: " + z.emptyRowProport on)

            val t c2 = System.currentT  M ll s
            val algo = new MHAlgor hm(algoConf g, graph, z, err)
            val opt m zedZ = algo.opt m ze
            val toc2 = System.currentT  M ll s
            pr ntln("T   to opt m ze: %.2f seconds\n".format((toc2 - t c2) / 1000.0))
            pr ntln("T   to  n  al ze & opt m ze: %.2f seconds\n".format((toc2 - toc) / 1000.0))

            val srm = MHAlgor hm. ur st callyScoreClusterAss gn nts(graph, opt m zedZ)
            val output er = (0 to srm.getNumRows).map { row d =>
              val rowW h nd ces = srm.getCol dsForRow(row d)
              val rowW hScores = srm.getValuesForRow(row d)
              val str = rowW h nd ces
                .z p(rowW hScores).map {
                  case (col d, score) =>
                    "%d:%.2g".format(col d + 1, score)
                }.mkStr ng(" ")
              "%d %s".format(row d, str)
            }

            TypedP pe.from(output er).wr eExecut on(TypedTsv(outputD r))
          }
        }
    }
}

/**
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:top_users_only && \
 * oscar hdfs --hadoop-cl ent- mory 60000 --user cassowary --host atla-aor-08-sr1 \
 * --bundle top_users_only --tool com.tw ter.s mclusters_v2.scald ng.ScalableTopUsersS m lar yGraphApp \
 * --screen --screen-detac d --tee ldap_logs/SubGraphOf100MTopusersW hMapped ds \
 * -- --mappedUsersD r adhoc/ldap_top100M_mappedUsers \
 * -- nputD r adhoc/ldap_approx mate_cos ne_s m lar y_follow \
 * --outputD r adhoc/ldap_subgraphOf100MTopUsersW hMapped ds_correct_topK
 */
object ScalableTopUsersS m lar yGraphApp extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default
  val log = Logger()

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val  nputD r = args(" nputD r")
          val mappedUsersD r = args("mappedUsersD r")
          val maxNe ghbors = args. nt("maxNe ghbors", 100)
          val outputD r = args("outputD r")

          val mappedUsers = TypedP pe
            .from(TypedTsv[(Long,  nt, Str ng,  nt)](mappedUsersD r))
            .map {
              case ( d, _, _, mapped d) =>
                ( d, mapped d)
            }
            .shard(200)

          val s ms = TypedP pe
            .from(Follow ngsCos neS m lar  esManhattanS ce(path =  nputD r))
            .map(_._2)

          TopUsersS m lar yGraph
            .runUs ngJo n(
              mappedUsers,
              s ms,
              maxNe ghbors
            ).wr eExecut on(TypedTsv(args("outputD r")))
        }
    }
}

/**
 * Scald ng app us ng Execut ons that does t  follow ng:
 *
 * 1. Get t  top N most follo d users on Tw ter
 * (also maps t m to  ds 1 -> N  n  nt space for eas er process ng)
 * 2. For each user from t  step above, get t  top K most s m lar users for t  user from t 
 * l st of N users from t  step above.
 * 3. Construct an und rected graph by sett ng an edge bet en (u, v)  f
 * e  r v  s  n u's top-K s m lar users l st, or u  s  n v's top-K s m lar user's l st.
 * 4. T    ght for t  (u, v) edge  s set to be t  cos ne s m lar y bet en u and v's
 * follo r l sts, ra sed to so  exponent > 1.
 * T  last step  s a  ur st c re  ght ng procedure to g ve more  mportance to edges  nvolv ng
 * more s m lar users.
 * 5. Wr e t  above graph to HDFS  n  t s format,
 *  .e. one l ne per node, w h t  l ne for each node spec fy ng t  l st of ne ghbors along
 * w h t  r   ghts. T  f rst l ne spec f es t  number of nodes and t  number of edges.
 *
 *  've tested t  Scald ng job for values of topK upto 20M.
 *
 * Example  nvocat on:
 * $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:top_users_s m lar y_graph && \
 * oscar hdfs --hadoop-cl ent- mory 60000 --host atla-amw-03-sr1 --bundle top_users_s m lar y_graph \
 * --tool com.tw ter.s mclusters_v2.scald ng.TopUsersS m lar yGraphApp \
 * --hadoop-propert es "elephantb rd.use.comb ne. nput.format=true;elephantb rd.comb ne.spl .s ze=468435456;mapred.m n.spl .s ze=468435456;mapreduce.reduce. mory.mb=5096;mapreduce.reduce.java.opts=-Xmx4400m" \
 * --screen --screen-detac d --tee logs/20MSubGraphExecut on -- --date 2017-10-24 \
 * --m nAct veFollo rs 300 --topK 20000000 \
 * -- nputUserGroupedD r /user/cassowary/manhattan_sequence_f les/approx mate_cos ne_s m lar y_follow/ \
 * --grouped nput nSequenceF les \
 * --maxNe ghborsPerNode 100 --wtExponent 2 \
 * --outputTopUsersD r /user/y _ldap/s mclusters_graph_prep_q42017/top20MUsers \
 * --outputGraphD r /user/y _ldap/s mclusters_graph_prep_q42017/top20Musers_exp2_100ne ghbors_ t s_graph
 *
 */
object TopUsersS m lar yGraphApp extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default
  val log = Logger()

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val m nAct veFollo rs = args. nt("m nAct veFollo rs", 100000)
          val topK = args. nt("topK")
          val date = DateRange.parse(args("date"))
          val  nputS m larPa rsD r = args.opt onal(" nputS m larPa rsD r")
          val  nputUserGroupedD r = args.opt onal(" nputUserGroupedD r")
          val  sGrouped nputSequenceF les = args.boolean("grouped nput nSequenceF les")
          val outputTopUsersD r = args("outputTopUsersD r")
          val maxNe ghborsPerNode = args. nt("maxNe ghborsPerNode", 300)
          val wtExponent = args.float("wtExponent", 3.5f)
          val outputGraphD r = args("outputGraphD r")

          val userS ce = DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, date).toTypedP pe
          val except on = new  llegalStateExcept on(
            "Please spec fy only one of  nputS m larPa rsD r or  nputUserGroupedD r"
          )

          ( nputS m larPa rsD r,  nputUserGroupedD r) match {
            case (So (_), So (_)) => throw except on
            case (None, None) => throw except on
            case _ => // no-op
          }

          def getSubgraphFn(usersTo nclude: Set[Long]) = {
            ( nputS m larPa rsD r,  nputUserGroupedD r) match {
              case (So (s m larPa rs), None) =>
                val s m larUserPa rs: TypedP pe[S m larUserPa r] =
                  TypedP pe.from(
                    new F xedPathLzoScrooge(
                       nputS m larPa rsD r.get,
                      S m larUserPa r
                    ))
                TopUsersS m lar yGraph.get n morySubgraph(
                  s m larUserPa rs,
                  usersTo nclude,
                  maxNe ghborsPerNode)
              case (None, So (grouped nput)) =>
                val cand datesP pe =
                  TopUsersS m lar yGraph.readS ms nput( sGrouped nputSequenceF les, grouped nput)
                TopUsersS m lar yGraph.get n morySubgraphFromUserGrouped nput(
                  cand datesP pe,
                  usersTo nclude,
                  maxNe ghborsPerNode
                )
              case _ => Execut on.from(N l) //   should never get  re
            }
          }

          TopUsersS m lar yGraph
            .run(
              userS ce,
              m nAct veFollo rs,
              topK,
              getSubgraphFn,
              wtExponent
            ).flatMap {
              case (topUsersL st, graph) =>
                //  're wr  ng to HDFS  selves, from t  subm ter node.
                // W n   use TypedP pe.wr e,  's fa l ng for large topK, e.g.10M.
                //   can make t  subm ter node have a lot of  mory, but  's
                // d ff cult and subopt mal to g ve t  much  mory to all mappers.
                val topUsersExec = Execut on.from(
                  TopUsersS m lar yGraph
                    .wr eTopUsers(topUsersL st, mode, outputTopUsersD r + "/all")
                )

                //   want to make sure t  wr e of t  topUsers succeeds, and
                // only t n wr e out t  graph. A graph w hout t  topUsers  s useless.
                topUsersExec.map { _ =>
                  //  're wr  ng to HDFS  selves, from t  subm ter node.
                  // W n   use TypedP pe.wr e,   fa ls due to OOM on t  mappers.
                  //   can make t  subm ter node have a lot of  mory, but  's d ff cult
                  // and subopt mal to g ve t  much  mory to all mappers.
                  TopUsersS m lar yGraph.wr eToHDFS fHDFS(
                    graph
                      . erableStr ngRepresentat on(new Dec malFormat("#.###")). erator().asScala,
                    mode,
                    outputGraphD r + "/all"
                  )
                }
            }
        }
    }

}

/**
 * App that only outputs t  topK users on Tw ter by act ve follo r count. Example  nvocat on:
 * $ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:top_users_only && \
 * oscar hdfs --hadoop-cl ent- mory 60000 --host atla-aor-08-sr1 --bundle top_users_only \
 * --tool com.tw ter.s mclusters_v2.scald ng.TopUsersOnlyApp \
 * #are t se hadoop-propert es needed for t  job?
 * #--hadoop-propert es "scald ng.w h.reducers.set.expl c ly=true;elephantb rd.use.comb ne. nput.format=true;elephantb rd.comb ne.spl .s ze=468435456;mapred.m n.spl .s ze=468435456" \
 * --screen --screen-detac d --tee logs/10MTopusersOnlyExecut on -- --date 2017-10-20 \
 * --m nAct veFollo rs 500 --topK 10000000 \
 * --outputTopUsersD r /user/y _ldap/s mclusters_graph_prep_q42017/top10MUsers
 *
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:top_users_only && \
 * oscar hdfs --hadoop-cl ent- mory 60000 --user cassowary --host atla-aor-08-sr1 \
 * --bundle top_users_only --tool com.tw ter.s mclusters_v2.scald ng.TopUsersOnlyApp \
 * --screen --screen-detac d --tee ldap_logs/100MTopusersW hMapped ds \
 * -- --date 2019-10-11 --m nAct veFollo rs 67 --outputTopUsersD r adhoc/ldap_top100M_mappedUsers \
 * -- ncludeMapped ds
 */
object TopUsersOnlyApp extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default
  val log = Logger()

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val m nAct veFollo rs = args. nt("m nAct veFollo rs", 100000)
          val topK = args. nt("topK", 20000000)
          val date = DateRange.parse(args("date"))
          val outputTopUsersD r = args("outputTopUsersD r")
          val  ncludeMapped ds = args.boolean(" ncludeMapped ds")

           f ( ncludeMapped ds) {
            pr ntln("Go ng to  nclude mapped ds  n output")
            TopUsersS m lar yGraph
              .topUsersW hMapped ds(
                DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, date).toTypedP pe,
                m nAct veFollo rs
              )
              .map {
                case TopUserW hMapped d(TopUser( d, act veFollo rCount, screenNa ), mapped d) =>
                  ( d, act veFollo rCount, screenNa , mapped d)
              }
              .wr eExecut on(TypedTsv(outputTopUsersD r))
          } else {
            TopUsersS m lar yGraph
              .topUsers n mory(
                DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, date).toTypedP pe,
                m nAct veFollo rs,
                topK
              ).map { topUsersL st =>
                TopUsersS m lar yGraph.wr eTopUsers(
                  topUsersL st,
                  mode,
                  outputTopUsersD r + "/all")
              }
          }
        }
    }
}
