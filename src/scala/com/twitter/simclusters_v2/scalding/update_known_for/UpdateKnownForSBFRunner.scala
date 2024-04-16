package com.tw ter.s mclusters_v2.scald ng.update_known_for

 mport com.tw ter.algeb rd.Max
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.sbf.core.Algor hmConf g
 mport com.tw ter.sbf.core.MHAlgor hm
 mport com.tw ter.sbf.core.SparseB naryMatr x
 mport com.tw ter.sbf.core.SparseRealMatr x
 mport com.tw ter.sbf.graph.Graph
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Hdfs
 mport com.tw ter.scald ng.Mode
 mport com.tw ter.scald ng.Stat
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.scald ng.CompareClusters
 mport com.tw ter.s mclusters_v2.scald ng.KnownForS ces
 mport com.tw ter.s mclusters_v2.scald ng.TopUser
 mport com.tw ter.s mclusters_v2.scald ng.TopUserW hMapped d
 mport com.tw ter.s mclusters_v2.scald ng.TopUsersS m lar yGraph
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport java. o.Pr ntWr er
 mport java.ut l.T  Zone
 mport org.apac .commons.math3.random.JDKRandomGenerator
 mport org.apac .commons.math3.random.RandomAdaptor
 mport org.apac .hadoop.fs.F leSystem
 mport org.apac .hadoop.fs.Path
 mport scala.collect on.mutable

object UpdateKnownForSBFRunner {

  /**
   * T  ma n log c of t  job.   works as follows:
   *
   *  1. read t  top 20M users, and convert t  r User ds to an  nteger  d from 0 to 20M  n order to use t  cluster ng l brary
   *  2. read t  user s m lar y graph from S ms, and convert t  r User ds to t  sa  mapped  nteger  d
   *  3. read t  prev ous known_for data set for  n  al zat on of t  cluster ng algor hm;
   *     for users w hout prev ous ass gn nts,   randomly ass gn t m to so  unused clusters ( f t re are any).
   *  4. run t  cluster ng algor hm for x  erat ons (x = 4  n t  prod sett ng)
   *  5. output of t  cluster ng result as t  new known_for.
   *
   */
  def runUpdateKnownFor(
    s msGraph: TypedP pe[Cand dates],
    m nAct veFollo rs:  nt,
    topK:  nt,
    maxNe ghbors:  nt,
    tempLocat onPath: Str ng,
    prev ousKnownFor: TypedP pe[(User d, Array[(Cluster d, Float)])],
    maxEpochsForCluster ng:  nt,
    square  ghtsEnable: Boolean,
    wtCoeff: Double,
    mode: Mode
  )(
     mpl c 
    un que d: Un que D,
    tz: T  Zone
  ): Execut on[TypedP pe[(User d, Array[(Cluster d, Float)])]] = {

    val tempLocat onPathS msGraph = tempLocat onPath + "/s ms_graph"
    val tempLocat onPathMapped ds = tempLocat onPath + "/mapped_user_ ds"
    val tempLocat onPathCluster ng = tempLocat onPath + "/cluster ng_output"

    val mapped dsToUser ds: TypedP pe[( nt, User d)] =
      getTopFollo dUsersW hMapped ds(m nAct veFollo rs, topK)
        .map {
          case ( d, mapped d) =>
            (mapped d,  d)
        }
        .shard(part  ons = topK / 1e5.to nt)

    val mappedS msGraph nput: TypedP pe[( nt, L st[( nt, Float)])] =
      getMappedS msGraph(
        mapped dsToUser ds,
        s msGraph,
        maxNe ghbors
      ) // T  s msGraph  re cons sts of t  mapped  ds and mapped ngbr  ds and not t  or g nal user ds

    val mappedS msGraphVers onedKeyVal: Vers onedKeyValS ce[ nt, L st[( nt, Float)]] =
      AdhocKeyValS ces. nter d ateSBFResultsDevelS ce(tempLocat onPathS msGraph)
    val mapped dsToUser dsVers onedKeyVal: Vers onedKeyValS ce[ nt, User d] =
      AdhocKeyValS ces.mapped nd cesDevelS ce(tempLocat onPathMapped ds)

    // exec to wr e  nter d ate results for mapped S ms Graph and mapped ds
    val mappedS msGraphAndMapped dsWr eExec: Execut on[Un ] = Execut on
      .z p(
        mappedS msGraph nput.wr eExecut on(mappedS msGraphVers onedKeyVal),
        mapped dsToUser ds.wr eExecut on(mapped dsToUser dsVers onedKeyVal)
      ).un 

    mappedS msGraphAndMapped dsWr eExec.flatMap { _ =>
      // T  s msGraph and t  mapped ds from user d(long) -> mapped ds are
      // hav ng to be wr ten to a temporary locat on and read aga n before runn ng
      // t  cluster ng algor hm.

      Execut on
        .z p(
          read nter d ateExec(
            TypedP pe.from(mappedS msGraphVers onedKeyVal),
            mode,
            tempLocat onPathS msGraph),
          read nter d ateExec(
            TypedP pe.from(mapped dsToUser dsVers onedKeyVal),
            mode,
            tempLocat onPathMapped ds)
        )
        .flatMap {
          case (mappedS msGraph nputReadAga n, mapped dsToUser dsReadAga n) =>
            val prev ousKnownForMapped dsAss gn nts: TypedP pe[( nt, L st[(Cluster d, Float)])] =
              getKnownForW hMapped ds(
                prev ousKnownFor,
                mapped dsToUser dsReadAga n,
              )

            val cluster ngResults = getCluster ngAss gn nts(
              mappedS msGraph nputReadAga n,
              prev ousKnownForMapped dsAss gn nts,
              maxEpochsForCluster ng,
              square  ghtsEnable,
              wtCoeff
            )
            cluster ngResults
              .flatMap { updatedKnownFor =>
                // convert t  l st of updated KnownFor to a TypedP pe
                convertKnownForL stToTypedP pe(
                  updatedKnownFor,
                  mode,
                  tempLocat onPathCluster ng
                )
              }
              .flatMap { updatedKnownForTypedP pe =>
                // convert t  mapped  nteger  d to raw user  ds
                val updatedKnownFor =
                  updatedKnownForTypedP pe
                    .jo n(mapped dsToUser dsReadAga n)
                    .values
                    .swap
                    .mapValues(_.toArray)

                Execut on.from(updatedKnownFor)
              }
        }
    }
  }

  /**
   *  lper funct on to compare newKnownFor w h t  prev ous  ek knownFor ass gn nts
   */
  def evaluateUpdatedKnownFor(
    newKnownFor: TypedP pe[(User d, Array[(Cluster d, Float)])],
     nputKnownFor: TypedP pe[(User d, Array[(Cluster d, Float)])]
  )(
     mpl c  un que d: Un que D
  ): Execut on[Str ng] = {

    val m nS zeOfB ggerClusterForCompar son = 10

    val compareClusterExec = CompareClusters.summar ze(
      CompareClusters.compare(
        KnownForS ces.transpose( nputKnownFor),
        KnownForS ces.transpose(newKnownFor),
        m nS zeOfB ggerCluster = m nS zeOfB ggerClusterForCompar son
      ))

    val compareProducerExec = CompareClusters.compareClusterAss gn nts(
      newKnownFor.mapValues(_.toL st),
       nputKnownFor.mapValues(_.toL st)
    )

    Execut on
      .z p(compareClusterExec, compareProducerExec)
      .map {
        case (compareClusterResults, compareProducerResult) =>
          s"Cos ne s m lar y d str but on bet en cluster  mbersh p vectors for " +
            s"clusters w h at least $m nS zeOfB ggerClusterForCompar son  mbers\n" +
            Ut l.prettyJsonMapper
              .wr eValueAsStr ng(compareClusterResults) +
            "\n\n-------------------\n\n" +
            "Custom counters:\n" + compareProducerResult +
            "\n\n-------------------\n\n"
      }
  }

  /**
   *
   * Convert t  l st of updated KnownFor to a TypedP pe
   *
   * T  step should have been done us ng TypedP pe.from(updatedKnownForL st), ho ver, due to t 
   * large s ze of t  l st, TypedP pe would throw out-of- mory except ons. So   have to f rst
   * dump   to a temp f le on HDFS and us ng a custom zed read funct on to load to TypedP pe
   *
   */
  def convertKnownForL stToTypedP pe(
    updatedKnownForL st: L st[( nt, L st[(Cluster d, Float)])],
    mode: Mode,
    temporaryOutputStr ngPath: Str ng
  ): Execut on[TypedP pe[( nt, L st[(Cluster d, Float)])]] = {

    val str ngOutput = updatedKnownForL st.map {
      case (mappedUser d, clusterArray) =>
        assert(clusterArray. sEmpty || clusterArray.length == 1)
        val str =  f (clusterArray.nonEmpty) {
          clusterArray. ad._1 + " " + clusterArray. ad._2 // each user  s known for at most 1 cluster
        } else {
          ""
        }
         f (mappedUser d % 100000 == 0)
          pr ntln(s"Mapped ds:$mappedUser d  ClusterAss gned$str")
        s"$mappedUser d $str"
    }

    // us ng Execut on to enforce t  order of t  follow ng 3 steps:
    // 1. wr e t  l st of str ngs to a temp f le on HDFS
    // 2. read t  str ngs to TypedP pe
    // 3. delete t  temp f le
    Execut on
      .from(
        // wr e t  output to HDFS; t  data w ll be loaded to Typedp pe later;
        // t  reason of do ng t   s that   can not just do TypeP pe.from(str ngOutput) wh ch
        // results  n OOM.
        TopUsersS m lar yGraph.wr eToHDFS fHDFS(
          str ngOutput.to erator,
          mode,
          temporaryOutputStr ngPath
        )
      )
      .flatMap { _ =>
        pr ntln(s"Start load ng t  data from $temporaryOutputStr ngPath")
        val clustersW hScores = TypedP pe.from(TypedTsv[Str ng](temporaryOutputStr ngPath)).map {
          mapped dsW hArrays =>
            val strArray = mapped dsW hArrays.tr m().spl ("\\s+")
            assert(strArray.length == 3 || strArray.length == 1)
            val row d = strArray(0).to nt
            val clusterAss gn nt: L st[(Cluster d, Float)] =
               f (strArray.length > 1) {
                L st((strArray(1).to nt, strArray(2).toFloat))
              } else {
                // t  knownFors w ll have users w h Array.empty as t  r ass gn nt  f
                // t  cluster ng step have empty results for that user.
                N l
              }

             f (row d % 100000 == 0)
              pr ntln(s"row d:$row d  ClusterAss gned: $clusterAss gn nt")
            (row d, clusterAss gn nt)
        }
        // return t  dataset as an execut on and delete t  temp locat on
        read nter d ateExec(clustersW hScores, mode, temporaryOutputStr ngPath)
      }
  }

  /**
   *  lper funct on to read t  dataset as execut on and delete t  temporary
   * locat on on HDFS for PDP compl ance
   */
  def read nter d ateExec[K, V](
    dataset: TypedP pe[(K, V)],
    mode: Mode,
    tempLocat onPath: Str ng
  ): Execut on[TypedP pe[(K, V)]] = {
    Execut on
      .from(dataset)
      .flatMap { output =>
        // delete t  temporary outputs for PDP compl ance
        mode match {
          case Hdfs(_, conf) =>
            val fs = F leSystem.new nstance(conf)
             f (fs.deleteOnEx (new Path(tempLocat onPath))) {
              pr ntln(s"Successfully deleted t  temporary folder $tempLocat onPath!")
            } else {
              pr ntln(s"Fa led to delete t  temporary folder $tempLocat onPath!")
            }
          case _ => ()
        }
        Execut on.from(output)
      }
  }

  /**
   * Converts t  user Ds  n t  s ms graph to t  r mapped  nteger  nd ces.
   * All t  users who donot have a mapp ng are f ltered out from t  s ms graph  nput
   *
   * @param mappedUsers mapp ng of long user Ds to t  r  nteger  nd ces
   * @param allEdges s ms graph
   * @param maxNe ghborsPerNode number of ne ghbors for each user
   *
   * @return s msGraph of users and ne ghbors w h t  r mapped  nterger  ds
   */
  def getMappedS msGraph(
    mappedUsers: TypedP pe[( nt, User d)],
    allEdges: TypedP pe[Cand dates],
    maxNe ghborsPerNode:  nt
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[( nt, L st[( nt, Float)])] = {

    val numEdgesAfterF rstJo n = Stat("num_edges_after_f rst_jo n")
    val numEdgesAfterSecondJo n = Stat("num_edges_after_second_jo n")
    val numEdgesLostTopKTruncated = Stat("num_edges_lost_topk_truncated")
    val f nalNumEdges = Stat("f nal_num_edges")

    val mappedUser dsTo ds: TypedP pe[(User d,  nt)] = mappedUsers.swap
    allEdges
      .map { cs => (cs.user d, cs.cand dates) }
      // f lter t  users not present  n t  mapped user Ds l st
      .jo n(mappedUser dsTo ds)
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
      .jo n(mappedUser dsTo ds)
      .w hReducers(9000)
      .flatMap {
        case ( d, ((mappedNe ghbor d, score), mapped d)) =>
          numEdgesAfterSecondJo n. nc()
          // to make t  graph sym tr c, add those edges back that m ght have been f ltered
          // due to maxNe ghborsPerNodefor a user but not for  s ne ghbors
          L st(
            (mapped d, Map(mappedNe ghbor d -> Max(score))),
            (mappedNe ghbor d, Map(mapped d -> Max(score)))
          )
      }
      .sumByKey
      .w hReducers(9100)
      .map {
        case ( d, nbrMap) =>
          // Graph  n  al zat on expects ne ghbors to be sorted  n ascend ng order of  ds
          val sorted = nbrMap.mapValues(_.get).toL st.sortBy(_._1)
          f nalNumEdges. ncBy(sorted.s ze)
          ( d, sorted)
      }
  }

  def getTopFollo dUsersW hMapped ds(
    m nAct veFollo rs:  nt,
    topK:  nt
  )(
     mpl c  un que d: Un que D,
    t  Zone: T  Zone
  ): TypedP pe[(Long,  nt)] = {
    val numTopUsersMapp ngs = Stat("num_top_users_w h_mapped_ ds")
    pr ntln("Go ng to  nclude mapped ds  n output")
    TopUsersS m lar yGraph
      .topUsersW hMapped dsTopK(
        DAL
          .readMostRecentSnapshotNoOlderThan(
            Users ceFlatScalaDataset,
            Days(30)).w hRemoteReadPol cy(Expl c Locat on(ProcAtla)).toTypedP pe,
        m nAct veFollo rs,
        topK
      )
      .map {
        case TopUserW hMapped d(TopUser( d, act veFollo rCount, screenNa ), mapped d) =>
          numTopUsersMapp ngs. nc()
          ( d, mapped d)
      }
  }

  /**
   * Map t  user ds  n t  knownFor dataset to t  r  nteger  ds   .
   */
  def getKnownForW hMapped ds(
    knownForDataset: TypedP pe[(User d, Array[(Cluster d, Float)])], //or g nal user d as t  key
    mapped dsW hUser d: TypedP pe[( nt, User d)] //mapped user d as t  key
  ): TypedP pe[( nt, L st[(Cluster d, Float)])] = {
    val user dsAndT  rMapped nd ces = mapped dsW hUser d.map {
      case (mapped d, or g nal d) => (or g nal d, mapped d)
    }
    knownForDataset.jo n(user dsAndT  rMapped nd ces).map {
      case (user d, (userClusterArray, mappedUser d)) =>
        (mappedUser d, userClusterArray.toL st)
    }
  }

  /**
   * Attach t  cluster ass gn nts from knownFor dataset to t  users  n mapped S ms graph  .
   */
  def attachClusterAss gn nts(
    mappedS msGraph: TypedP pe[( nt, L st[( nt, Float)])],
    knownForAss gn nts: TypedP pe[( nt, L st[(Cluster d, Float)])],
    square  ghts: Boolean
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[( nt, Array[ nt], Array[Float], L st[(Cluster d, Float)])] = {
    val numPopularUsersW hNoKnownForBefore = Stat(
      "num_popular_users_w h_no_knownfor_before_but_popular_now")

    val  nput = mappedS msGraph.map {
      case ( d, nbrsL st) =>
        val ngbr ds = nbrsL st.map(_._1).toArray
        val ngbrWts =  f (square  ghts) {
          nbrsL st.map(_._2).map(currWt => currWt * currWt * 10).toArray
        } else {
          nbrsL st.map(_._2).toArray
        }
        ( d, ngbr ds, ngbrWts)
    }

    //  nput s msGraph cons sts of popular ppl w h most follo d users, who m ght not have been
    // a knownFor user  n t  prev ous  ek. So left jo n w h t  knownFor dataset, and t se
    // new popular users w ll not have any pr or cluster ass gn nts wh le cluster ng t  t  
     nput
      .groupBy(_._1)
      .leftJo n(knownForAss gn nts.groupBy(_._1))
      .toTypedP pe
      .map {
        case (mappedUser d, ((mapped d, ngbr ds, ngbrWts), knownForResult)) =>
          val clustersL st: L st[( nt, Float)] = knownForResult match {
            case So (values) => values._2
            case None =>
              numPopularUsersW hNoKnownForBefore. nc()
              L st.empty
          }
          (mappedUser d, ngbr ds, ngbrWts, clustersL st)
      }
  }

  /**
   *  n  al ze graph w h users and ne ghbors w h edge   ghts  .
   */
  def getGraphFromS ms nput(
    mappedS ms er:  erable[
      ( nt, Array[ nt], Array[Float], L st[(Cluster d, Float)])
    ],
    numUsers:  nt
  ): Graph = {
    val nbrs ds: Array[Array[ nt]] = new Array[Array[ nt]](numUsers)
    val nbrsWts: Array[Array[Float]] = new Array[Array[Float]](numUsers)
    var numEdges = 0L
    var numVert ces = 0
    var numVert cesW hNoNgbrs = 0
    mappedS ms er.foreach {
      case ( d, nbrArray ds, nbArrayScores, _) =>
        nbrs ds( d) = nbrArray ds
        nbrsWts( d) = nbArrayScores
        numEdges += nbrArray ds.length
        numVert ces += 1
         f (numVert ces % 100000 == 0) {
          pr ntln(s"Done load ng $numVert ces many vert ces. Edges so far: $numEdges")
        }
    }

    (0 unt l numUsers).foreach {   =>
       f (nbrs ds( ) == null) {
        numVert cesW hNoNgbrs += 1
        nbrs ds( ) = Array[ nt]()
        nbrsWts( ) = Array[Float]()
      }
    }

    pr ntln(
      s"Done load ng graph w h $numUsers nodes and $numEdges edges (count ng each edge tw ce)")
    pr ntln("Number of nodes w h at least one ne ghbor  s " + numVert ces)
    pr ntln("Number of nodes w h at no ne ghbors  s " + numVert cesW hNoNgbrs)
    new Graph(numUsers, numEdges / 2, nbrs ds, nbrsWts)
  }

  /**
   *  lper funct on that  n  al zes users to clusters based on prev ous knownFor ass gn nts
   * and for users w h no prev ous ass gn nts, ass gn t m randomly to any of t  empty clusters
   */
  def  n  al zeSparseB naryMatr x(
    graph: Graph,
    mappedS msGraph er:  erable[
      ( nt, Array[ nt], Array[Float], L st[(Cluster d, Float)])
    ], // user w h ne ghbors, ne ghbor wts and prev ous knownfor ass gn nts
    numUsers:  nt,
    numClusters:  nt,
    algoConf g: Algor hmConf g,
  ): SparseB naryMatr x = {
    var clustersSeenFromPrev ous ek: Set[ nt] = Set.empty
    var emptyClustersFromPrev ous ek: Set[ nt] = Set.empty
    var usersW hNoAss gn ntsFromPrev ous ek: Set[ nt] = Set.empty
    mappedS msGraph er.foreach {
      case ( d, _, _, knownFor) =>
         f (knownFor. sEmpty) {
          usersW hNoAss gn ntsFromPrev ous ek +=  d
        }
        knownFor.foreach {
          case (cluster d, _) =>
            clustersSeenFromPrev ous ek += cluster d
        }
    }
    (1 to numClusters).foreach {   =>
       f (!clustersSeenFromPrev ous ek.conta ns( )) emptyClustersFromPrev ous ek +=  
    }
    var z = new SparseB naryMatr x(numUsers, numClusters)
    pr ntln("Go ng to  n  al ze from prev ous KnownFor")
    var zero ndexedCluster dsFromPrev ous ek: Set[ nt] = Set.empty
    for (cluster dOne ndexed <- emptyClustersFromPrev ous ek) {
      zero ndexedCluster dsFromPrev ous ek += (cluster dOne ndexed - 1)
    }
    //  n  al ze z - users w h no prev ous ass gn nts are ass gned to empty clusters
    z. n FromSubsetOfRowsForSpec f edColumns(
      graph,
      (gr: Graph,  :  nteger) => algoConf g.rng.nextDouble,
      zero ndexedCluster dsFromPrev ous ek.toArray,
      usersW hNoAss gn ntsFromPrev ous ek.toArray,
      new Pr ntWr er(System.err)
    )
    pr ntln(" n  al zed t  empty clusters")
    mappedS msGraph er.foreach {
      case ( d, _, _, knownFor) =>
        val currClustersForUserZero ndexed = knownFor.map(_._1).map(x => x - 1)
        // Users who have a prev ous cluster ass gn nt are  n  al zed w h t  sa  cluster
         f (currClustersForUserZero ndexed.nonEmpty) {
          z.updateRow( d, currClustersForUserZero ndexed.sorted.toArray)
        }
    }
    pr ntln("Done  n  al z ng from prev ous knownFor ass gn nt")
    z
  }

  /**
   * Opt m ze t  sparseB naryMatr x. T  funct on runs t  cluster ng epochs and computes t 
   * cluster ass gn nts for t  next  ek, based on t  underly ng user-user graph
   */
  def opt m zeSparseB naryMatr x(
    algoConf g: Algor hmConf g,
    graph: Graph,
    z: SparseB naryMatr x
  ): SparseB naryMatr x = {
    val prec0 = MHAlgor hm.clusterPrec s on(graph, z, 0, 1000, algoConf g.rng)
    pr ntln("Prec s on of cluster 0:" + prec0.prec s on)
    val prec1 = MHAlgor hm.clusterPrec s on(graph, z, 1, 1000, algoConf g.rng)
    pr ntln("Prec s on of cluster 1:" + prec1.prec s on)
    val algo = new MHAlgor hm(algoConf g, graph, z, new Pr ntWr er(System.err))
    val opt m zedZ = algo.opt m ze
    opt m zedZ
  }

  /**
   *  lper funct on that takes t   ur st cally scored assoc at on of user to a cluster
   * and returns t  knownFor result
   * @param srm SparseRealMatr x w h (row, col) score denot ng t   mbersh p score of user  n t  cluster
   * @return ass gn nts of users (mapped  nteger  nd ces) to clusters w h knownFor scores.
   */
  def getKnownFor ur st cScores(srm: SparseRealMatr x): L st[( nt, L st[(Cluster d, Float)])] = {
    val knownForAss gn ntsFromClusterScores = (0 unt l srm.getNumRows).map { row d =>
      val rowW h nd ces = srm.getCol dsForRow(row d)
      val rowW hScores = srm.getValuesForRow(row d)
      val allClustersW hScores: Array[(Cluster d, Float)] =
        rowW h nd ces.z p(rowW hScores).map {
          case (col d, score) => (col d + 1, score.toFloat)
        }
       f (row d % 100000 == 0) {
        pr ntln(" ns de output er:" + row d + " " + srm.getNumRows)
      }

      val clusterAss gn ntW hMaxScore: L st[(Cluster d, Float)] =
         f (allClustersW hScores.length > 1) {
          //  f sparseB naryMatr x z has rows w h more than one non-zero column ( .e a user
          //  n  al zed w h more than one cluster), and t  cluster ng algor hm doesnot f nd
          // a better proposal for cluster ass gn nt, t  user's mult -cluster  mbersh p
          // from t   n  al zat on step can cont nue.
          //   found that t  happens  n ~0.1% of t  knownFor users.  nce choose t 
          // cluster w h t  h g st score to deal w h such edge cases.
          val result: (Cluster d, Float) = allClustersW hScores.maxBy(_._2)
          pr ntln(
            "Found a user w h mapped d: %s w h more than 1 cluster ass gn nt:%s; Ass gned to t  best cluster: %s"
              .format(
                row d.toStr ng,
                allClustersW hScores.mkStr ng("Array(", ", ", ")"),
                result
                  .toStr ng()))
          L st(result)
        } else {
          allClustersW hScores.toL st
        }
      (row d, clusterAss gn ntW hMaxScore)
    }
    knownForAss gn ntsFromClusterScores.toL st
  }

  /**
   * Funct on that computes t  cluster ng ass gn nts to users
   *
   * @param mappedS msGraph user-user graph as  nput to cluster ng
   * @param prev ousKnownForAss gn nts prev ous  ek cluster ng ass gn nts
   * @param maxEpochsForCluster ng number of ne ghbors for each user
   * @param square  ghts boolean flag for t  edge   ghts  n t  s ms graph
   * @param wtCoeff wtCoeff
   *
   * @return users w h clusters ass gned
   */
  def getCluster ngAss gn nts(
    mappedS msGraph: TypedP pe[( nt, L st[( nt, Float)])],
    prev ousKnownForAss gn nts: TypedP pe[( nt, L st[(Cluster d, Float)])],
    maxEpochsForCluster ng:  nt,
    square  ghts: Boolean,
    wtCoeff: Double
  )(
     mpl c  un que d: Un que D
  ): Execut on[L st[( nt, L st[(Cluster d, Float)])]] = {

    attachClusterAss gn nts(
      mappedS msGraph,
      prev ousKnownForAss gn nts,
      square  ghts).to erableExecut on.flatMap { mappedS msGraphW hClusters er =>
      val t c = System.currentT  M ll s
      var maxVertex d = 0
      var maxCluster d nPrev ousAss gn nt = 0
      mappedS msGraphW hClusters er.foreach {
        case ( d, _, _, knownFor) =>
          maxVertex d = Math.max( d, maxVertex d)
          knownFor.foreach {
            case (cluster d, _) =>
              maxCluster d nPrev ousAss gn nt =
                Math.max(cluster d, maxCluster d nPrev ousAss gn nt)
          }
      }

      val numUsersToCluster =
        maxVertex d + 1 //s nce users  re mapped w h  ndex start ng from 0, us ng z pW h ndex
      pr ntln("Total number of topK users to be clustered t  t  :" + numUsersToCluster)
      pr ntln(
        "Total number of clusters  n t  prev ous knownFor ass gn nt:" + maxCluster d nPrev ousAss gn nt)
      pr ntln("W ll set number of commun  es to " + maxCluster d nPrev ousAss gn nt)

      //  n  al ze t  graph w h users, ne ghbors and t  correspond ng edge   ghts
      val graph = getGraphFromS ms nput(mappedS msGraphW hClusters er, numUsersToCluster)
      val toc = System.currentT  M ll s()
      pr ntln("T   to load t  graph " + (toc - t c) / 1000.0 / 60.0 + " m nutes")

      // def ne t  algoConf g para ters
      val algoConf g = new Algor hmConf g()
        .w hCpu(16).w hK(maxCluster d nPrev ousAss gn nt)
        .w hWtCoeff(wtCoeff.toDouble)
        .w hMaxEpoch(maxEpochsForCluster ng)
      algoConf g.d v deResult ntoConnectedComponents = false
      algoConf g.m nClusterS ze = 1
      algoConf g.update m d ately = true
      algoConf g.rng = new RandomAdaptor(new JDKRandomGenerator(1))

      //  n  al ze a sparseB naryMatr x w h users ass gned to t  r prev ous  ek knownFor
      // ass gn nts. For those users who do not a pr or ass gn nt,   ass gn
      // t  (user + t  ne ghbors from t  graph) to t  empty clusters.
      // Please note that t  ne ghborhood-based  n  al zat on to empty clusters can
      // have a few cases w re t  sa  user was ass gned to more than one cluster
      val z =  n  al zeSparseB naryMatr x(
        graph,
        mappedS msGraphW hClusters er,
        numUsersToCluster,
        maxCluster d nPrev ousAss gn nt,
        algoConf g
      )

      // Run t  epochs of t  cluster ng algor hm to f nd t  new cluster ass gn nts
      val t c2 = System.currentT  M ll s
      val opt m zedZ = opt m zeSparseB naryMatr x(algoConf g, graph, z)
      val toc2 = System.currentT  M ll s
      pr ntln("T   to opt m ze: %.2f seconds\n".format((toc2 - t c2) / 1000.0))
      pr ntln("T   to  n  al ze & opt m ze: %.2f seconds\n".format((toc2 - toc) / 1000.0))

      // Attach scores to t  cluster ass gn nts
      val srm = MHAlgor hm. ur st callyScoreClusterAss gn nts(graph, opt m zedZ)

      // Get t  knownfor ass gn nts of users from t   ur st c scores
      // ass gned based on ne gbhorhood of t  user and t  r cluster ass gn nts
      // T  returned result has user Ds  n t  mapped  nteger  nd ces
      val knownForAss gn ntsFromClusterScores: L st[( nt, L st[(Cluster d, Float)])] =
        getKnownFor ur st cScores(srm)

      Execut on.from(knownForAss gn ntsFromClusterScores)
    }
  }
}
