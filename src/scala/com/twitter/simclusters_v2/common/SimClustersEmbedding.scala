package com.tw ter.s mclusters_v2.common

 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusterW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport scala.collect on.mutable
 mport scala.language. mpl c Convers ons
 mport scala.ut l.hash ng.MurmurHash3.arrayHash
 mport scala.ut l.hash ng.MurmurHash3.productHash
 mport scala.math._

/**
 * A representat on of a S mClusters Embedd ng, des gned for low  mory footpr nt and performance.
 * For serv ces that cac  m ll ons of embedd ngs,   found t  to s gn f cantly reduce allocat ons,
 *  mory footpr nt and overall performance.
 *
 * Embedd ng data  s stored  n pre-sorted arrays rat r than structures wh ch use a lot of po nters
 * (e.g. Map). A m n mal set of laz ly-constructed  nter d ate data  s kept.
 *
 * Be wary of add ng furt r `val` or `lazy val`s to t  class; mater al z ng and stor ng more data
 * on t se objects could s gn f cantly affect  n- mory cac  performance.
 *
 * Also,  f   are us ng t  code  n a place w re   care about  mory footpr nt, be careful
 * not to mater al ze any of t  lazy vals unless   need t m.
 */
sealed tra  S mClustersEmbedd ng extends Equals {
   mport S mClustersEmbedd ng._

  /**
   * Any compl ant  mple ntat on of t  S mClustersEmbedd ng tra  must ensure that:
   * - t  cluster and score arrays are ordered as descr bed below
   * - t  cluster and score arrays are treated as  mmutable (.hashCode  s  mo zed)
   * - t  s ze of all cluster and score arrays  s t  sa 
   * - all cluster scores are > 0
   * - cluster  ds are un que
   */
  //  n descend ng score order - t   s useful for truncat on, w re   care most about t  h g st scor ng ele nts
  pr vate[s mclusters_v2] val cluster ds: Array[Cluster d]
  pr vate[s mclusters_v2] val scores: Array[Double]
  //  n ascend ng cluster order. T   s useful for operat ons w re   try to f nd t  sa  cluster  n anot r embedd ng, e.g. dot product
  pr vate[s mclusters_v2] val sortedCluster ds: Array[Cluster d]
  pr vate[s mclusters_v2] val sortedScores: Array[Double]

  /**
   * Bu ld and return a Set of all clusters  n t  embedd ng
   */
  lazy val cluster dSet: Set[Cluster d] = sortedCluster ds.toSet

  /**
   * Bu ld and return Seq representat on of t  embedd ng
   */
  lazy val embedd ng: Seq[(Cluster d, Double)] =
    sortedCluster ds.z p(sortedScores).sortBy(-_._2).toSeq

  /**
   * Bu ld and return a Map representat on of t  embedd ng
   */
  lazy val map: Map[Cluster d, Double] = sortedCluster ds.z p(sortedScores).toMap

  lazy val l1norm: Double = Cos neS m lar yUt l.l1NormArray(sortedScores)

  lazy val l2norm: Double = Cos neS m lar yUt l.normArray(sortedScores)

  lazy val logNorm: Double = Cos neS m lar yUt l.logNormArray(sortedScores)

  lazy val expScaledNorm: Double =
    Cos neS m lar yUt l.expScaledNormArray(sortedScores, DefaultExponent)

  /**
   * T  L2 Normal zed Embedd ng. Opt m ze for Cos ne S m lar y Calculat on.
   */
  lazy val normal zedSortedScores: Array[Double] =
    Cos neS m lar yUt l.applyNormArray(sortedScores, l2norm)

  lazy val logNormal zedSortedScores: Array[Double] =
    Cos neS m lar yUt l.applyNormArray(sortedScores, logNorm)

  lazy val expScaledNormal zedSortedScores: Array[Double] =
    Cos neS m lar yUt l.applyNormArray(sortedScores, expScaledNorm)

  /**
   * T  Standard Dev at on of an Embedd ng.
   */
  lazy val std: Double = {
     f (scores. sEmpty) {
      0.0
    } else {
      val sum = scores.sum
      val  an = sum / scores.length
      var var ance: Double = 0.0
      for (  <- scores. nd ces) {
        val v = scores( ) -  an
        var ance += (v * v)
      }
      math.sqrt(var ance / scores.length)
    }
  }

  /**
   * Return t  score of a g ven cluster d.
   */
  def get(cluster d: Cluster d): Opt on[Double] = {
    var   = 0
    wh le (  < sortedCluster ds.length) {
      val t  d = sortedCluster ds( )
       f (cluster d == t  d) return So (sortedScores( ))
       f (t  d > cluster d) return None
        += 1
    }
    None
  }

  /**
   * Return t  score of a g ven cluster d.  f not ex st, return default.
   */
  def getOrElse(cluster d: Cluster d, default: Double = 0.0): Double = {
    requ re(default >= 0.0)
    var   = 0
    wh le (  < sortedCluster ds.length) {
      val t  d = sortedCluster ds( )
       f (cluster d == t  d) return sortedScores( )
       f (t  d > cluster d) return default
        += 1
    }
    default
  }

  /**
   * Return t  cluster  ds
   */
  def getCluster ds(): Array[Cluster d] = cluster ds

  /**
   * Return t  cluster  ds w h t  h g st scores
   */
  def topCluster ds(s ze:  nt): Seq[Cluster d] = cluster ds.take(s ze)

  /**
   * Return true  f t  embedd ng conta ns a g ven cluster d
   */
  def conta ns(cluster d: Cluster d): Boolean = cluster dSet.conta ns(cluster d)

  def sum(anot r: S mClustersEmbedd ng): S mClustersEmbedd ng = {
     f (anot r. sEmpty) t 
    else  f (t . sEmpty) anot r
    else {
      var  1 = 0
      var  2 = 0
      val l = scala.collect on.mutable.ArrayBuffer.empty[( nt, Double)]
      wh le ( 1 < sortedCluster ds.length &&  2 < anot r.sortedCluster ds.length) {
         f (sortedCluster ds( 1) == anot r.sortedCluster ds( 2)) {
          l += Tuple2(sortedCluster ds( 1), sortedScores( 1) + anot r.sortedScores( 2))
           1 += 1
           2 += 1
        } else  f (sortedCluster ds( 1) > anot r.sortedCluster ds( 2)) {
          l += Tuple2(anot r.sortedCluster ds( 2), anot r.sortedScores( 2))
          // anot r cluster  s lo r.  ncre nt   to see  f t  next one matc s t 's
           2 += 1
        } else {
          l += Tuple2(sortedCluster ds( 1), sortedScores( 1))
          // t  cluster  s lo r.  ncre nt   to see  f t  next one matc s anot rs's
           1 += 1
        }
      }
       f ( 1 == sortedCluster ds.length &&  2 != anot r.sortedCluster ds.length)
        // t  was shorter. Prepend rema n ng ele nts from anot r
        l ++= anot r.sortedCluster ds.drop( 2).z p(anot r.sortedScores.drop( 2))
      else  f ( 1 != sortedCluster ds.length &&  2 == anot r.sortedCluster ds.length)
        // anot r was shorter. Prepend rema n ng ele nts from t 
        l ++= sortedCluster ds.drop( 1).z p(sortedScores.drop( 1))
      S mClustersEmbedd ng(l)
    }
  }

  def scalarMult ply(mult pl er: Double): S mClustersEmbedd ng = {
    requ re(mult pl er > 0.0, "S mClustersEmbedd ng.scalarMult ply requ res mult pl er > 0.0")
    DefaultS mClustersEmbedd ng(
      cluster ds,
      scores.map(_ * mult pl er),
      sortedCluster ds,
      sortedScores.map(_ * mult pl er)
    )
  }

  def scalarD v de(d v sor: Double): S mClustersEmbedd ng = {
    requ re(d v sor > 0.0, "S mClustersEmbedd ng.scalarD v de requ res d v sor > 0.0")
    DefaultS mClustersEmbedd ng(
      cluster ds,
      scores.map(_ / d v sor),
      sortedCluster ds,
      sortedScores.map(_ / d v sor)
    )
  }

  def dotProduct(anot r: S mClustersEmbedd ng): Double = {
    Cos neS m lar yUt l.dotProductForSortedClusterAndScores(
      sortedCluster ds,
      sortedScores,
      anot r.sortedCluster ds,
      anot r.sortedScores)
  }

  def cos neS m lar y(anot r: S mClustersEmbedd ng): Double = {
    Cos neS m lar yUt l.dotProductForSortedClusterAndScores(
      sortedCluster ds,
      normal zedSortedScores,
      anot r.sortedCluster ds,
      anot r.normal zedSortedScores)
  }

  def logNormCos neS m lar y(anot r: S mClustersEmbedd ng): Double = {
    Cos neS m lar yUt l.dotProductForSortedClusterAndScores(
      sortedCluster ds,
      logNormal zedSortedScores,
      anot r.sortedCluster ds,
      anot r.logNormal zedSortedScores)
  }

  def expScaledCos neS m lar y(anot r: S mClustersEmbedd ng): Double = {
    Cos neS m lar yUt l.dotProductForSortedClusterAndScores(
      sortedCluster ds,
      expScaledNormal zedSortedScores,
      anot r.sortedCluster ds,
      anot r.expScaledNormal zedSortedScores)
  }

  /**
   * Return true  f t   s an empty embedd ng
   */
  def  sEmpty: Boolean = sortedCluster ds. sEmpty

  /**
   * Return t  Jaccard S m lar y Score bet en two embedd ngs.
   * Note: t   mple ntat on should be opt m zed  f   start to use    n product on
   */
  def jaccardS m lar y(anot r: S mClustersEmbedd ng): Double = {
     f (t . sEmpty || anot r. sEmpty) {
      0.0
    } else {
      val  ntersect = cluster dSet. ntersect(anot r.cluster dSet).s ze
      val un on = cluster dSet.un on(anot r.cluster dSet).s ze
       ntersect.toDouble / un on
    }
  }

  /**
   * Return t  Fuzzy Jaccard S m lar y Score bet en two embedd ngs.
   * Treat each S mclusters embedd ng as fuzzy set, calculate t  fuzzy set s m lar y
   *  tr cs of two embedd ngs
   *
   * Paper 2.2.1: https://openrev ew.net/pdf? d=SkxXg2C5FX
   */
  def fuzzyJaccardS m lar y(anot r: S mClustersEmbedd ng): Double = {
     f (t . sEmpty || anot r. sEmpty) {
      0.0
    } else {
      val v1C = sortedCluster ds
      val v1S = sortedScores
      val v2C = anot r.sortedCluster ds
      val v2S = anot r.sortedScores

      requ re(v1C.length == v1S.length)
      requ re(v2C.length == v2S.length)

      var  1 = 0
      var  2 = 0
      var nu rator = 0.0
      var denom nator = 0.0

      wh le ( 1 < v1C.length &&  2 < v2C.length) {
         f (v1C( 1) == v2C( 2)) {
          nu rator += m n(v1S( 1), v2S( 2))
          denom nator += max(v1S( 1), v2S( 2))
           1 += 1
           2 += 1
        } else  f (v1C( 1) > v2C( 2)) {
          denom nator += v2S( 2)
           2 += 1
        } else {
          denom nator += v1S( 1)
           1 += 1
        }
      }

      wh le ( 1 < v1C.length) {
        denom nator += v1S( 1)
         1 += 1
      }
      wh le ( 2 < v2C.length) {
        denom nator += v2S( 2)
         2 += 1
      }

      nu rator / denom nator
    }
  }

  /**
   * Return t  Eucl dean D stance Score bet en two embedd ngs.
   * Note: t   mple ntat on should be opt m zed  f   start to use    n product on
   */
  def eucl deanD stance(anot r: S mClustersEmbedd ng): Double = {
    val un onClusters = cluster dSet.un on(anot r.cluster dSet)
    val var ance = un onClusters.foldLeft(0.0) {
      case (sum, cluster d) =>
        val d stance = math.abs(t .getOrElse(cluster d) - anot r.getOrElse(cluster d))
        sum + d stance * d stance
    }
    math.sqrt(var ance)
  }

  /**
   * Return t  Manhattan D stance Score bet en two embedd ngs.
   * Note: t   mple ntat on should be opt m zed  f   start to use    n product on
   */
  def manhattanD stance(anot r: S mClustersEmbedd ng): Double = {
    val un onClusters = cluster dSet.un on(anot r.cluster dSet)
    un onClusters.foldLeft(0.0) {
      case (sum, cluster d) =>
        sum + math.abs(t .getOrElse(cluster d) - anot r.getOrElse(cluster d))
    }
  }

  /**
   * Return t  number of overlapp ng clusters bet en two embedd ngs.
   */
  def overlapp ngClusters(anot r: S mClustersEmbedd ng):  nt = {
    var  1 = 0
    var  2 = 0
    var count = 0

    wh le ( 1 < sortedCluster ds.length &&  2 < anot r.sortedCluster ds.length) {
       f (sortedCluster ds( 1) == anot r.sortedCluster ds( 2)) {
        count += 1
         1 += 1
         2 += 1
      } else  f (sortedCluster ds( 1) > anot r.sortedCluster ds( 2)) {
        // v2 cluster  s lo r.  ncre nt   to see  f t  next one matc s v1's
         2 += 1
      } else {
        // v1 cluster  s lo r.  ncre nt   to see  f t  next one matc s v2's
         1 += 1
      }
    }
    count
  }

  /**
   * Return t  largest product cluster scores
   */
  def maxEle ntw seProduct(anot r: S mClustersEmbedd ng): Double = {
    var  1 = 0
    var  2 = 0
    var maxProduct: Double = 0.0

    wh le ( 1 < sortedCluster ds.length &&  2 < anot r.sortedCluster ds.length) {
       f (sortedCluster ds( 1) == anot r.sortedCluster ds( 2)) {
        val product = sortedScores( 1) * anot r.sortedScores( 2)
         f (product > maxProduct) maxProduct = product
         1 += 1
         2 += 1
      } else  f (sortedCluster ds( 1) > anot r.sortedCluster ds( 2)) {
        // v2 cluster  s lo r.  ncre nt   to see  f t  next one matc s v1's
         2 += 1
      } else {
        // v1 cluster  s lo r.  ncre nt   to see  f t  next one matc s v2's
         1 += 1
      }
    }
    maxProduct
  }

  /**
   * Return a new S mClustersEmbedd ng w h Max Embedd ng S ze.
   *
   * Prefer to truncate on embedd ng construct on w re poss ble. Do ng so  s c aper.
   */
  def truncate(s ze:  nt): S mClustersEmbedd ng = {
     f (cluster ds.length <= s ze) {
      t 
    } else {
      val truncatedCluster ds = cluster ds.take(s ze)
      val truncatedScores = scores.take(s ze)
      val (sortedCluster ds, sortedScores) =
        truncatedCluster ds.z p(truncatedScores).sortBy(_._1).unz p

      DefaultS mClustersEmbedd ng(
        truncatedCluster ds,
        truncatedScores,
        sortedCluster ds,
        sortedScores)
    }
  }

  def toNormal zed: S mClustersEmbedd ng = {
    // Add  onal safety c ck. Only EmptyEmbedd ng's l2norm  s 0.0.
     f (l2norm == 0.0) {
      EmptyEmbedd ng
    } else {
      t .scalarD v de(l2norm)
    }
  }

   mpl c  def toThr ft: Thr ftS mClustersEmbedd ng = {
    Thr ftS mClustersEmbedd ng(
      embedd ng.map {
        case (cluster d, score) =>
          S mClusterW hScore(cluster d, score)
      }
    )
  }

  def canEqual(a: Any): Boolean = a. s nstanceOf[S mClustersEmbedd ng]

  /*   def ne equal y as hav ng t  sa  clusters and scores.
   * T   mple ntat on  s arguably  ncorrect  n t  case:
   *   (1 -> 1.0, 2 -> 0.0) == (1 -> 1.0)  // equals returns false
   * Ho ver, compl ant  mple ntat ons of S mClustersEmbedd ng should not  nclude zero-  ght
   * clusters, so t   mple ntat on should work correctly.
   */
  overr de def equals(that: Any): Boolean =
    that match {
      case that: S mClustersEmbedd ng =>
        that.canEqual(t ) &&
          t .sortedCluster ds.sa Ele nts(that.sortedCluster ds) &&
          t .sortedScores.sa Ele nts(that.sortedScores)
      case _ => false
    }

  /**
   * hashcode  mple ntat on based on t  contents of t  embedd ng. As a lazy val, t  rel es on
   * t  embedd ng contents be ng  mmutable.
   */
  overr de lazy val hashCode:  nt = {
    /* Arrays uses object  d as hashCode, so d fferent arrays w h t  sa  contents hash
     * d fferently. To prov de a stable hash code,   take t  sa  approach as how a
     * `case class(clusters: Seq[ nt], scores: Seq[Double])` would be has d. See
     * ScalaRunT  ._hashCode and MurmurHash3.productHash
     * https://g hub.com/scala/scala/blob/2.12.x/src/l brary/scala/runt  /ScalaRunT  .scala#L167
     * https://g hub.com/scala/scala/blob/2.12.x/src/l brary/scala/ut l/hash ng/MurmurHash3.scala#L64
     *
     * Note that t  hashcode  s arguably  ncorrect  n t  case:
     *   (1 -> 1.0, 2 -> 0.0).hashcode == (1 -> 1.0).hashcode  // returns false
     * Ho ver, compl ant  mple ntat ons of S mClustersEmbedd ng should not  nclude zero-  ght
     * clusters, so t   mple ntat on should work correctly.
     */
    productHash((arrayHash(sortedCluster ds), arrayHash(sortedScores)))
  }
}

object S mClustersEmbedd ng {
  val EmptyEmbedd ng: S mClustersEmbedd ng =
    DefaultS mClustersEmbedd ng(Array.empty, Array.empty, Array.empty, Array.empty)

  val DefaultExponent: Double = 0.3

  // Descend ng by score t n ascend ng by Cluster d
   mpl c  val order: Order ng[(Cluster d, Double)] =
    (a: (Cluster d, Double), b: (Cluster d, Double)) => {
      b._2 compare a._2 match {
        case 0 => a._1 compare b._1
        case c => c
      }
    }

  /**
   * Constructors
   *
   * T se constructors:
   * - do not make assumpt ons about t  order ng of t  cluster/scores.
   * - do assu  that cluster  ds are un que
   * -  gnore (drop) any cluster whose score  s <= 0
   */
  def apply(embedd ng: (Cluster d, Double)*): S mClustersEmbedd ng =
    bu ldDefaultS mClustersEmbedd ng(embedd ng)

  def apply(embedd ng:  erable[(Cluster d, Double)]): S mClustersEmbedd ng =
    bu ldDefaultS mClustersEmbedd ng(embedd ng)

  def apply(embedd ng:  erable[(Cluster d, Double)], s ze:  nt): S mClustersEmbedd ng =
    bu ldDefaultS mClustersEmbedd ng(embedd ng, truncate = So (s ze))

   mpl c  def apply(thr ftEmbedd ng: Thr ftS mClustersEmbedd ng): S mClustersEmbedd ng =
    bu ldDefaultS mClustersEmbedd ng(thr ftEmbedd ng.embedd ng.map(_.toTuple))

  def apply(thr ftEmbedd ng: Thr ftS mClustersEmbedd ng, truncate:  nt): S mClustersEmbedd ng =
    bu ldDefaultS mClustersEmbedd ng(
      thr ftEmbedd ng.embedd ng.map(_.toTuple),
      truncate = So (truncate))

  pr vate def bu ldDefaultS mClustersEmbedd ng(
    embedd ng:  erable[(Cluster d, Double)],
    truncate: Opt on[ nt] = None
  ): S mClustersEmbedd ng = {
    val truncated dAndScores = {
      val  dsAndScores = embedd ng.f lter(_._2 > 0.0).toArray.sorted(order)
      truncate match {
        case So (t) =>  dsAndScores.take(t)
        case _ =>  dsAndScores
      }
    }

     f (truncated dAndScores. sEmpty) {
      EmptyEmbedd ng
    } else {
      val (cluster ds, scores) = truncated dAndScores.unz p
      val (sortedCluster ds, sortedScores) = truncated dAndScores.sortBy(_._1).unz p
      DefaultS mClustersEmbedd ng(cluster ds, scores, sortedCluster ds, sortedScores)
    }
  }

  /** ***** Aggregat on  thods ******/
  /**
   * A h gh performance vers on of Sum a l st of S mClustersEmbedd ngs.
   * Suggest us ng  n Onl ne Serv ces to avo d t  unnecessary GC.
   * For offl ne or stream ng. Please c ck [[S mClustersEmbedd ngMono d]]
   */
  def sum(s mClustersEmbedd ngs:  erable[S mClustersEmbedd ng]): S mClustersEmbedd ng = {
     f (s mClustersEmbedd ngs. sEmpty) {
      EmptyEmbedd ng
    } else {
      val sum = s mClustersEmbedd ngs.foldLeft(mutable.Map[Cluster d, Double]()) {
        (sum, embedd ng) =>
          for (  <- embedd ng.sortedCluster ds. nd ces) {
            val cluster d = embedd ng.sortedCluster ds( )
            sum.put(cluster d, embedd ng.sortedScores( ) + sum.getOrElse(cluster d, 0.0))
          }
          sum
      }
      S mClustersEmbedd ng(sum)
    }
  }

  /**
   * Support a f xed s ze S mClustersEmbedd ng Sum
   */
  def sum(
    s mClustersEmbedd ngs:  erable[S mClustersEmbedd ng],
    maxS ze:  nt
  ): S mClustersEmbedd ng = {
    sum(s mClustersEmbedd ngs).truncate(maxS ze)
  }

  /**
   * A h gh performance vers on of  an a l st of S mClustersEmbedd ngs.
   * Suggest us ng  n Onl ne Serv ces to avo d t  unnecessary GC.
   */
  def  an(s mClustersEmbedd ngs:  erable[S mClustersEmbedd ng]): S mClustersEmbedd ng = {
     f (s mClustersEmbedd ngs. sEmpty) {
      EmptyEmbedd ng
    } else {
      sum(s mClustersEmbedd ngs).scalarD v de(s mClustersEmbedd ngs.s ze)
    }
  }

  /**
   * Support a f xed s ze S mClustersEmbedd ng  an
   */
  def  an(
    s mClustersEmbedd ngs:  erable[S mClustersEmbedd ng],
    maxS ze:  nt
  ): S mClustersEmbedd ng = {
     an(s mClustersEmbedd ngs).truncate(maxS ze)
  }
}

case class DefaultS mClustersEmbedd ng(
  overr de val cluster ds: Array[Cluster d],
  overr de val scores: Array[Double],
  overr de val sortedCluster ds: Array[Cluster d],
  overr de val sortedScores: Array[Double])
    extends S mClustersEmbedd ng {

  overr de def toStr ng: Str ng =
    s"DefaultS mClustersEmbedd ng(${cluster ds.z p(scores).mkStr ng(",")})"
}

object DefaultS mClustersEmbedd ng {
  // To support ex st ng code wh ch bu lds embedd ngs from a Seq
  def apply(embedd ng: Seq[(Cluster d, Double)]): S mClustersEmbedd ng = S mClustersEmbedd ng(
    embedd ng)
}
