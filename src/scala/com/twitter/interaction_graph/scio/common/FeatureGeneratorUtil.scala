package com.tw ter. nteract on_graph.sc o.common

 mport com.spot fy.sc o.Sc o tr cs
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter. nteract on_graph.sc o.common.FeatureGroups.DWELL_T ME_FEATURE_L ST
 mport com.tw ter. nteract on_graph.sc o.common.FeatureGroups.STATUS_FEATURE_L ST
 mport com.tw ter. nteract on_graph.sc o.common.UserUt l.DUMMY_USER_ D
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.EdgeFeature
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.T  Ser esStat st cs
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter. nteract on_graph.thr ftscala.VertexFeature

object FeatureGeneratorUt l {

  //  n  al ze a T  Ser esStat st cs object by (value, age) pa r
  def  n  al zeTSS(featureValue: Double, age:  nt = 1): T  Ser esStat st cs =
    T  Ser esStat st cs(
       an = featureValue,
      m2ForVar ance = 0.0,
      ewma = featureValue,
      numElapsedDays = age,
      numNonZeroDays = age,
      numDaysS nceLast = So (age)
    )

  /**
   * Create vertex feature from  nteract onGraphRaw nput graph (src, dst, feature na , age, featureValue)
   *   w ll represent non-d rect onal features (eg num_create_t ets) as "outgo ng" values.
   * @return
   */
  def getVertexFeature(
     nput: SCollect on[ nteract onGraphRaw nput]
  ): SCollect on[Vertex] = {
    // For vertex features   need to calculate both  n and out featureValue
    val vertexAggregatedFeatureValues =  nput
      .flatMap {  nput =>
         f ( nput.dst != DUMMY_USER_ D) {
          Seq(
            (( nput.src,  nput.na .value), ( nput.featureValue, 0.0)),
            (( nput.dst,  nput.na .value), (0.0,  nput.featureValue))
          )
        } else {
          //   put t  non-d rect onal features as "outgo ng" values
          Seq((( nput.src,  nput.na .value), ( nput.featureValue, 0.0)))
        }
      }
      .sumByKey
      .map {
        case ((user d, na  d), (outEdges,  nEdges)) =>
          (user d, (FeatureNa (na  d), outEdges,  nEdges))
      }.groupByKey

    vertexAggregatedFeatureValues.map {
      case (user d, records) =>
        // sort features by FeatureNa  for determ n st c order (esp dur ng test ng)
        val features = records.toSeq.sortBy(_._1.value).flatMap {
          case (na , outEdges,  nEdges) =>
            // create out vertex features
            val outFeatures =  f (outEdges > 0) {
              val outTss =  n  al zeTSS(outEdges)
              L st(
                VertexFeature(
                  na  = na ,
                  outgo ng = true,
                  tss = outTss
                ))
            } else N l

            // create  n vertex features
            val  nFeatures =  f ( nEdges > 0) {
              val  nTss =  n  al zeTSS( nEdges)
              L st(
                VertexFeature(
                  na  = na ,
                  outgo ng = false,
                  tss =  nTss
                ))
            } else N l

            outFeatures ++  nFeatures
        }
        Vertex(user d = user d, features = features)
    }
  }

  /**
   * Create edge feature from  nteract onGraphRaw nput graph (src, dst, feature na , age, featureValue)
   *   w ll exclude all non-d rect onal features (eg num_create_t ets) from all edge aggregates
   */
  def getEdgeFeature(
     nput: SCollect on[ nteract onGraphRaw nput]
  ): SCollect on[Edge] = {
     nput
      .w hNa ("f lter non-d rect onal features")
      .flatMap {  nput =>
         f ( nput.dst != DUMMY_USER_ D) {
          Sc o tr cs.counter("getEdgeFeature", s"d rect onal feature ${ nput.na .na }"). nc()
          So ((( nput.src,  nput.dst), ( nput.na ,  nput.age,  nput.featureValue)))
        } else {
          Sc o tr cs.counter("getEdgeFeature", s"non-d rect onal feature ${ nput.na .na }"). nc()
          None
        }
      }
      .w hNa ("group features by pa rs")
      .groupByKey
      .map {
        case ((src, dst), records) =>
          // sort features by FeatureNa  for determ n st c order (esp dur ng test ng)
          val features = records.toSeq.sortBy(_._1.value).map {
            case (na , age, featureValue) =>
              val tss =  n  al zeTSS(featureValue, age)
              EdgeFeature(
                na  = na ,
                tss = tss
              )
          }
          Edge(
            s ce d = src,
            dest nat on d = dst,
              ght = So (0.0),
            features = features.toSeq
          )
      }
  }

  // For sa  user  d, comb ne d fferent vertex feature records  nto one record
  // T   nput w ll assu  for each (user d, featureNa , d rect on), t re w ll be only one record
  def comb neVertexFeatures(
    vertex: SCollect on[Vertex],
  ): SCollect on[Vertex] = {
    vertex
      .groupBy { v: Vertex =>
        v.user d
      }
      .map {
        case (user d, vertexes) =>
          val comb ner = vertexes.foldLeft(VertexFeatureComb ner(user d)) {
            case (comb ner, vertex) =>
              comb ner.addFeature(vertex)
          }
          comb ner.getComb nedVertex(0)
      }

  }

  def comb neEdgeFeatures(
    edge: SCollect on[Edge]
  ): SCollect on[Edge] = {
    edge
      .groupBy { e =>
        (e.s ce d, e.dest nat on d)
      }
      .w hNa ("comb n ng edge features for each (src, dst)")
      .map {
        case ((src, dst), edges) =>
          val comb ner = edges.foldLeft(EdgeFeatureComb ner(src, dst)) {
            case (comb ner, edge) =>
              comb ner.addFeature(edge)
          }
          comb ner.getComb nedEdge(0)
      }
  }

  def comb neVertexFeaturesW hDecay(
     tory: SCollect on[Vertex],
    da ly: SCollect on[Vertex],
     tory  ght: Double,
    da ly  ght: Double
  ): SCollect on[Vertex] = {

     tory
      .keyBy(_.user d)
      .cogroup(da ly.keyBy(_.user d)).map {
        case (user d, (h, d)) =>
          // Add ng  tory  erators
          val  toryComb ner = h.toL st.foldLeft(VertexFeatureComb ner(user d)) {
            case (comb ner, vertex) =>
              comb ner.addFeature(vertex,  tory  ght, 0)
          }
          // Add ng da ly  erators
          val f nalComb ner = d.toL st.foldLeft( toryComb ner) {
            case (comb ner, vertex) =>
              comb ner.addFeature(vertex, da ly  ght, 1)
          }

          f nalComb ner.getComb nedVertex(
            2
          ) // 2  ans totally   have 2 days(yesterday and today) data to comb ne toget r
      }
  }

  def comb neEdgeFeaturesW hDecay(
     tory: SCollect on[Edge],
    da ly: SCollect on[Edge],
     tory  ght: Double,
    da ly  ght: Double
  ): SCollect on[Edge] = {

     tory
      .keyBy { e =>
        (e.s ce d, e.dest nat on d)
      }
      .w hNa ("comb ne  tory and da ly edges w h decay")
      .cogroup(da ly.keyBy { e =>
        (e.s ce d, e.dest nat on d)
      }).map {
        case ((src, dst), (h, d)) =>
          //val comb ner = EdgeFeatureComb ner(src, dst)
          // Add ng  tory  erators

          val  toryComb ner = h.toL st.foldLeft(EdgeFeatureComb ner(src, dst)) {
            case (comb ner, edge) =>
              comb ner.addFeature(edge,  tory  ght, 0)
          }

          val f nalComb ner = d.toL st.foldLeft( toryComb ner) {
            case (comb ner, edge) =>
              comb ner.addFeature(edge, da ly  ght, 1)
          }

          f nalComb ner.getComb nedEdge(
            2
          ) // 2  ans totally   have 2 days(yesterday and today) data to comb ne toget r

      }
  }

  /**
   * Create features from follow ng graph (src, dst, age, featureValue)
   * Note that   w ll f lter out vertex features represented as edges from t  edge output.
   */
  def getFeatures(
     nput: SCollect on[ nteract onGraphRaw nput]
  ): (SCollect on[Vertex], SCollect on[Edge]) = {
    (getVertexFeature( nput), getEdgeFeature( nput))
  }

  // remove t  edge features that from flock, address book or sms as   w ll refresh t m on a da ly bas s
  def removeStatusFeatures(e: Edge): Seq[Edge] = {
    val updatedFeatureL st = e.features.f lter { e =>
      !STATUS_FEATURE_L ST.conta ns(e.na )
    }
     f (updatedFeatureL st.s ze > 0) {
      val edge = Edge(
        s ce d = e.s ce d,
        dest nat on d = e.dest nat on d,
          ght = e.  ght,
        features = updatedFeatureL st
      )
      Seq(edge)
    } else
      N l
  }

  // c ck  f t  edge feature has features ot r than d ll t   feature
  def edgeW hFeatureOt rThanD llT  (e: Edge): Boolean = {
    e.features.ex sts { f =>
      !DWELL_T ME_FEATURE_L ST.conta ns(f.na )
    }
  }
}
