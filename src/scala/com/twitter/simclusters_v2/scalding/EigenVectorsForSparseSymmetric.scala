package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.scald ng.{Execut on, TypedP pe, TypedTsv}
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport java.ut l
 mport no.u b.c pr.matr x.Matr x
 mport no.u b.c pr.matr x.sparse.{ArpackSym, L nkedSparseMatr x}
 mport scala.collect on.JavaConverters._

object E genVectorsForSparseSym tr c {
  val log: Logger = Logger()

  /**
   * Construct matr x from t  rows of t  matr x, spec f ed as a map. T  outer map  s  ndexed by row d, and t   nner maps are  ndexed by column d.
   * Note that t   nput matr x  s  ntended to be sym tr c.
   *
   * @param map   A map spec fy ng t  rows of t  matr x. T  outer map  s  ndexed by row d, and t   nner maps are  ndexed by column d. Both rows and columns are zero- ndexed.
   * @param nRows number of rows  n matr x
   * @param nCols number of columns  n matr x
   *
   * @return t  constructed matr x
   */
  def getMatr x(map: Map[ nt, Map[ nt, Double]], nRows:  nt, nCols:  nt): Matr x = {
    val nonzeros = map.toSeq.flatMap {
      case ( , subMap) =>
        subMap.toSeq.map {
          case (j, value) =>
            ( , j, value)
        }
    }
    getMatr x(nonzeros, nRows, nCols)
  }

  /**
   * Construct matr x from  erable of t  non-zero entr es. Note that t   nput matr x  s  ntended to be sym tr c.
   *
   * @param nonzeros non-zeros  n ( , j, v) format, w re    s row, j  s column, and v  s value. Both rows and columns are zero- ndexed.
   * @param nRows    number of rows  n matr x
   * @param nCols    number of columns  n matr x
   *
   * @return t  constructed matr x
   */
  def getMatr x(nonzeros:  erable[( nt,  nt, Double)], nRows:  nt, nCols:  nt): Matr x = {
    val matr x = new L nkedSparseMatr x(nRows, nCols)
    var numEntr es = 0
    var maxRow = 0
    var maxCol = 0

    nonzeros.foreach {
      case ( , j, v) =>
         f (  > maxRow) {
          maxRow =  
        }
         f (j > maxCol) {
          maxCol = j
        }
        numEntr es += 1
        matr x.set( , j, v)
    }
    log. nfo(
      "F n s d bu ld ng matr x w h %d entr es and maxRow %d and maxCol %d"
        .format(numEntr es, maxRow, maxCol))

    matr x
  }

  /**
   * Pr nts out var ous d agnost cs about how much t  g ven matr x d ffers from a perfect
   * sym tr c matr x.  f ( ,j) and (j, ) are d fferent,   sets both of t m to be t  max of t  two.
   * Call t  funct on before  nvok ng EVD.
   *
   * @param matr x Matr x wh ch  s mod f ed ( f need be)  n place.
   */
  def ensureMatr x sSym tr c(matr x: Matr x): Un  = {
    var numUnequalEntr es = 0
    var numEntr esD fferentBy1Percent = 0
    var numEqualEntr es = 0
    var numUnequalDueToZero = 0
    var maxUnequal = (0, 0, 0.0, 0.0)
    matr x. erator().asScala.foreach { entry =>
      val curr = entry.get()
      val opp = matr x.get(entry.column(), entry.row())
       f (curr == opp) {
        numEqualEntr es += 1
      } else {
        numUnequalEntr es += 1
         f (opp == 0) {
          numUnequalDueToZero += 1
        }
         f (opp != 0 && (math.abs(curr - opp) / math.m n(curr, opp)) > 0.01) {
          numEntr esD fferentBy1Percent += 1
        }
         f (opp != 0 && math.abs(curr - opp) > maxUnequal._4) {
          maxUnequal = (entry.row(), entry.column(), curr, math.abs(curr - opp))
        }
        val max = math.max(curr, opp)
        matr x.set(entry.column(), entry.row(), max)
        matr x.set(entry.row(), entry.column(), max)
      }
    }

    var numUnEqualPr nted = 0
    matr x. erator().asScala.foreach { entry =>
      val opp = matr x.get(entry.column(), entry.row())
       f (numUnEqualPr nted < 10 && entry.get() != opp) {
        numUnEqualPr nted += 1
        log. nfo(
          "Entr es for (%d, %d) are %s and %s"
            .format(entry.row(), entry.column(), entry.get(), opp))
      }
    }

    log. nfo(
      "Num unequal entr es: %d, num unequal due to zero: %d, num unequal by 1percent or more: %d, num equal entr es: %d, maxUnequal: %s"
        .format(
          numUnequalEntr es,
          numUnequalDueToZero,
          numEntr esD fferentBy1Percent,
          numEqualEntr es,
          maxUnequal))
  }

  /**
   * Get t  top-k e genvalues (largest magn ude) and e genvectors for an  nput matr x.
   * Top e genvalues  ans t y're t  largest  n magn ude.
   *  nput matr x needs to be perfectly sym tr c;  f  's not, t  funct on w ll fa l.
   *
   * Many of t  e genvectors w ll have very small values along most of t  d  ns ons. T   thod also
   * only reta ns t  b gger entr es  n an e genvector.
   *
   * @param matr x               sym tr c  nput matr x.
   * @param k                    how many of t  top e genvectors to get.
   * @param rat oToLargestCutoff An entry needs to be at least 1/rat oToLargestCutoff of t  b ggest entry  n that vector to be reta ned.
   *
   * @return seq of (e genvalue, e genvector) pa rs.
   */
  def getTruncatedEVD(
    matr x: Matr x,
    k:  nt,
    rat oToLargestCutoff: Float
  ): Seq[(Double, Seq[( nt, Double)])] = {
    val solver = new ArpackSym(matr x)
    val resultsMap = solver.solve(k, ArpackSym.R z.LM).asScala.toMap
    val results = resultsMap.to ndexedSeq.sortBy { case (e gValue, _) => -e gValue }
    results.z pW h ndex.map {
      case ((e gValue, denseVectorJava),  ndex) =>
        val denseVector = new Array[Double](denseVectorJava.s ze())
        denseVector. nd ces.foreach {  ndex => denseVector( ndex) = denseVectorJava.get( ndex) }
        val denseVectorMax = denseVector.maxBy { entry => math.abs(entry) }
        val cutOff = math.abs(denseVectorMax) / rat oToLargestCutoff
        val s gn f cantEntr es = denseVector.z pW h ndex
          .f lter { case (vectorEntry, _) => math.abs(vectorEntry) >= cutOff }
          .sortBy { case (vectorEntry, _) => -1 * math.abs(vectorEntry) }
        (e gValue.toDouble, s gn f cantEntr es.toSeq.map(_.swap))
    }
  }

  /**
   * Compute U*D ag*Ut - w re D ag  s a d agonal matr x, and U  s a sparse matr x.
   * T   s pr mar ly for test ng - to make sure that t  computed e genvectors can be used to
   * reconstruct t  or g nal matr x up to so  reasonable approx mat on.
   *
   * @param d agToUColumns seq of (d agonal entr es, assoc ated column  n U)
   * @param cutoff         cutoff for  nclud ng a value  n t  result.
   *
   * @return result of mult pl cat on, returned as a map of t  rows  n t  results.
   */
  def uT  sD agT  sUT(
    d agToUColumns: Seq[(Double, Seq[( nt, Double)])],
    cutoff: Double
  ): Map[ nt, Map[ nt, Double]] = {
    val result = new ut l.HashMap[ nt, ut l.HashMap[ nt, Double]]()
    d agToUColumns.foreach {
      case (d ag, uColumn) =>
        uColumn.foreach {
          case ( ,  Val) =>
            uColumn.foreach {
              case (j, jVal) =>
                val prod = d ag *  Val * jVal
                 f (result.conta nsKey( )) {
                  val newVal =  f (result.get( ).conta nsKey(j)) {
                    result.get( ).get(j) + prod
                  } else prod
                  result.get( ).put(j, newVal)
                } else {
                  result.put( , new ut l.HashMap[ nt, Double])
                  result.get( ).put(j, prod)
                }
            }
        }
    }
    val unf ltered = result.asScala.toMap.mapValues(_.asScala.toMap)
    unf ltered
      .mapValues { m => m.f lter { case (_, value) => math.abs(value) >= cutoff } }
      .f lter { case (_, vector) => vector.nonEmpty }
  }

  /** Note: T  requ res a full EVD to correctly compute t   nverse! :-( */
  def get nverseFromEVD(
    evd: Seq[(Double, Seq[( nt, Double)])],
    cutoff: Double
  ): Map[ nt, Map[ nt, Double]] = {
    val evd nverse = evd.map {
      case (e gValue, e gVector) =>
        (1.0 / e gValue, e gVector)
    }
    uT  sD agT  sUT(evd nverse, cutoff)
  }
}

object PCAProject onMatr xAdhoc extends Tw terExecut onApp {
  val log = Logger()

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, _) =>
        Execut on.w h d { _ =>
          val args = conf g.getArgs
          val k = args. nt("k", 100)
          val rat oToLargestEntry nVectorCutoff = args. nt("rat oToLargestEntry nVectorCutoff", 100)
          val m nClusterFavers = args. nt("m nClusterFavers", 1000)
          val  nput = TypedP pe.from(AdhocKeyValS ces.clusterDeta lsS ce(args(" nputD r")))
          val outputD r = args("outputD r")

          val f lteredClustersExec =
             nput
              .collect {
                case ((_, cluster d), deta ls)
                     f deta ls.numUsersW hNonZeroFavScore > m nClusterFavers =>
                  cluster d
              }
              .to erableExecut on
              .map { fc =>
                val fcSet = fc.toSet
                log. nfo("Number of clusters w h favers more than %d  s %d"
                  .format(m nClusterFavers, fcSet.s ze))
                fcSet
              }

          f lteredClustersExec
            .flatMap { f lteredClusters =>
               nput.flatMap {
                case ((_, cluster d), deta ls) =>
                   f (f lteredClusters(cluster d)) {
                    deta ls.ne ghborClusters.getOrElse(N l).collect {
                      case ne ghbor
                           f f lteredClusters(
                            ne ghbor.cluster d) && ne ghbor.favCos neS m lar y. sDef ned =>
                        (cluster d, ne ghbor.cluster d, ne ghbor.favCos neS m lar y.get)
                    }
                  } else N l
              }.to erableExecut on
            }
            .flatMap { edges er =>
              val edges = edges er.toSeq
              val old dToNew d = edges
                .flatMap { case ( , j, _) => Seq( , j) }
                .d st nct
                .z pW h ndex
                .toMap

              val mapStr ng = old dToNew d.toL st
                .take(5).map {
                  case (old, nw) =>
                    Seq(old, nw).mkStr ng(" ")
                }.mkStr ng("\n")
              log. nfo("A few entr es of Old d to New d map  s")
              log. nfo(mapStr ng)

              val new dToOld d = old dToNew d.map(_.swap)
              log. nfo(
                "Num clusters after f lter ng out those w h no ne ghbors w h favers more than %d  s %d"
                  .format(m nClusterFavers, old dToNew d.s ze))
              val newEdges = edges.map {
                case (old , oldJ, value) =>
                  (old dToNew d(old ), old dToNew d(oldJ), value)
              }
              log. nfo("Go ng to bu ld matr x")
              val matr x = E genVectorsForSparseSym tr c.getMatr x(
                newEdges,
                old dToNew d.s ze,
                old dToNew d.s ze)
              E genVectorsForSparseSym tr c.ensureMatr x sSym tr c(matr x)

              log. nfo("Go ng to solve now for %d e genvalues".format(k))
              val t c = System.currentT  M ll s()
              val results = E genVectorsForSparseSym tr c.getTruncatedEVD(
                matr x,
                k,
                rat oToLargestEntry nVectorCutoff)
              val toc = System.currentT  M ll s()
              log. nfo("F n s d solv ng  n %.2f m nutes".format((toc - t c) / 1000 / 60.0))

              val e gValues = results.map(_._1).map { x => "%.3g".format(x) }.mkStr ng(" ")
              val e gValueNorm = math.sqrt(results.map(_._1).map(x => x * x).sum)
              val matr xNorm = math.sqrt(matr x. erator().asScala.map(_.get()).map(x => x * x).sum)

              pr ntln(
                "matr xNorm %s, e gValueNorm %s, expla ned fract on %s"
                  .format(matr xNorm, e gValueNorm, e gValueNorm / matr xNorm))

              log. nfo("T  e genvalues are:")
              log. nfo(e gValues)

              val nnz nE genVectors = results.map(_._2.s ze).sum
              log. nfo("Average nnz per e genvector us ng rat oToLargestCutoff %d  s %.2g"
                .format(rat oToLargestEntry nVectorCutoff, nnz nE genVectors * 1.0 / results.s ze))
              val transposedRaw = results.z pW h ndex.flatMap {
                case ((_, e gVector), e g ndex) =>
                  e gVector.map {
                    case ( ndex, vectorEntry) =>
                      val cluster d = new dToOld d( ndex)
                      Map(cluster d -> L st((e g ndex, vectorEntry)))
                  }
              }
              val transposed = Mono d.sum(transposedRaw).mapValues { rowForCluster =>
                rowForCluster
                  .map {
                    case (d m d,   ght) =>
                      "%d:%.2g".format(d m d,   ght)
                  }.mkStr ng(" ")
              }
              TypedP pe.from(transposed.toSeq).wr eExecut on(TypedTsv(outputD r))
            }
        }
    }
}
