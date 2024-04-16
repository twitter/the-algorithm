package com.tw ter.s mclusters_v2.scald ng
package mult _type_graph.assemble_mult _type_graph

 mport com.tw ter.dal.cl ent.dataset.{KeyValDALDataset, SnapshotDALDataset}
 mport com.tw ter.scald ng.{Execut on, _}
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.{D, _}
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe.typedP peToR chP pe
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  LeftNode,
  Noun,
  NounW hFrequency,
  NounW hFrequencyL st,
  R ghtNodeType,
  R ghtNodeTypeStruct,
  R ghtNodeW hEdge  ght,
  R ghtNodeW hEdge  ghtL st,
  Mult TypeGraphEdge
}
 mport com.tw ter.wtf.scald ng.jobs.common.DateRangeExecut onApp
 mport java.ut l.T  Zone

/**
 *  n t  f le,   assemble t  mult _type_graph user-ent y engage nt s gnals
 *
 *   works as follows and t  follow ng datasets are produced as a result:
 *
 * 1. FullGraph (fullMult TypeGraphSnapshotDataset) : reads datasets from mult ple s ces and generates
 * a b part e graph w h LeftNode -> R ghtNode edges, captur ng a user's engage nt w h var ed ent y types
 *
 * 2. TruncatedGraph (truncatedMult TypeGraphKeyValDataset): a truncated vers on of t  FullGraph
 * w re   only store t  topK most frequently occurr ng R ghtNodes  n t  b part e graph LeftNode -> R ghtNode
 *
 * 3. TopKNouns (topKR ghtNounsKeyValDataset): t  stores t  topK most frequent Nouns for each engage nt type
 * Please note that t  dataset  s currently only be ng used for t  debugger to f nd wh ch nodes   cons der as t 
 * most frequently occurr ng,  n FullGraph
 */

tra  AssembleMult TypeGraphBaseApp extends DateRangeExecut onApp {
  val truncatedMult TypeGraphKeyValDataset: KeyValDALDataset[
    KeyVal[LeftNode, R ghtNodeW hEdge  ghtL st]
  ]
  val topKR ghtNounsKeyValDataset: KeyValDALDataset[
    KeyVal[R ghtNodeTypeStruct, NounW hFrequencyL st]
  ]
  val fullMult TypeGraphSnapshotDataset: SnapshotDALDataset[Mult TypeGraphEdge]
  val  sAdhoc: Boolean
  val truncatedMult TypeGraphMHOutputPath: Str ng
  val topKR ghtNounsMHOutputPath: Str ng
  val fullMult TypeGraphThr ftOutputPath: Str ng

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
     mport Conf g._
     mport AssembleMult TypeGraph._

    val numKeys nTruncatedGraph = Stat("num_keys_truncated_mts")
    val numKeys nTopKNounsGraph = Stat("num_keys_topk_nouns_mts")

    val fullGraph: TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] =
      getFullGraph().count("num_entr es_full_graph")

    val topKR ghtNodes: TypedP pe[(R ghtNodeType, Seq[(Noun, Double)])] =
      getTopKR ghtNounsW hFrequenc es(
        fullGraph,
        TopKConf g,
        GlobalDefaultM nFrequencyOfR ghtNodeType)

    val truncatedGraph: TypedP pe[(LeftNode, R ghtNodeW hEdge  ght)] =
      getTruncatedGraph(fullGraph, topKR ghtNodes).count("num_entr es_truncated_graph")

    // key transformat ons - truncated graph, keyed by LeftNode
    val truncatedGraphKeyedBySrc: TypedP pe[(LeftNode, R ghtNodeW hEdge  ghtL st)] =
      truncatedGraph
        .map {
          case (LeftNode.User d(user d), r ghtNodeW h  ght) =>
            user d -> L st(r ghtNodeW h  ght)
        }
        .sumByKey
        .map {
          case (user d, r ghtNodeW h  ghtL st) =>
            (LeftNode.User d(user d), R ghtNodeW hEdge  ghtL st(r ghtNodeW h  ghtL st))
        }

    // key transformat on - topK nouns, keyed by t  R ghtNodeNounType
    val topKNounsKeyedByType: TypedP pe[(R ghtNodeTypeStruct, NounW hFrequencyL st)] =
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

    //Wr eExecs - truncated graph
    val truncatedGraphTsvExec: Execut on[Un ] =
      truncatedGraphKeyedBySrc.wr eExecut on(
        TypedTsv[(LeftNode, R ghtNodeW hEdge  ghtL st)](AdhocRootPref x + "truncated_graph_tsv"))

    val truncatedGraphDALExec: Execut on[Un ] = truncatedGraphKeyedBySrc
      .map {
        case (leftNode, r ghtNodeW h  ghtL st) =>
          numKeys nTruncatedGraph. nc()
          KeyVal(leftNode, r ghtNodeW h  ghtL st)
      }
      .wr eDALVers onedKeyValExecut on(
        truncatedMult TypeGraphKeyValDataset,
        D.Suff x(
          ( f (! sAdhoc)
             RootPath
           else
             AdhocRootPref x)
            + truncatedMult TypeGraphMHOutputPath),
        Expl c EndT  (dateRange.`end`)
      )

    //Wr eExec - topK r ghtnouns
    val topKNounsTsvExec: Execut on[Un ] =
      topKNounsKeyedByType.wr eExecut on(
        TypedTsv[(R ghtNodeTypeStruct, NounW hFrequencyL st)](
          AdhocRootPref x + "top_k_r ght_nouns_tsv"))

    // wr  ng topKNouns MH dataset for debugger
    val topKNounsDALExec: Execut on[Un ] = topKNounsKeyedByType
      .map {
        case (engage ntType, r ghtL st) =>
          val r ghtL stMH =
            NounW hFrequencyL st(r ghtL st.nounW hFrequencyL st.take(TopKR ghtNounsForMHDump))
          numKeys nTopKNounsGraph. nc()
          KeyVal(engage ntType, r ghtL stMH)
      }
      .wr eDALVers onedKeyValExecut on(
        topKR ghtNounsKeyValDataset,
        D.Suff x(
          ( f (! sAdhoc)
             RootPath
           else
             AdhocRootPref x)
            + topKR ghtNounsMHOutputPath),
        Expl c EndT  (dateRange.`end`)
      )

    //Wr eExec - fullGraph
    val fullGraphDALExec: Execut on[Un ] = fullGraph
      .map {
        case (leftNode, r ghtNodeW h  ght) =>
          Mult TypeGraphEdge(leftNode, r ghtNodeW h  ght)
      }.wr eDALSnapshotExecut on(
        fullMult TypeGraphSnapshotDataset,
        D.Da ly,
        D.Suff x(
          ( f (! sAdhoc)
             RootThr ftPath
           else
             AdhocRootPref x)
            + fullMult TypeGraphThr ftOutputPath),
        D.Parquet,
        dateRange.`end`
      )

     f ( sAdhoc) {
      Ut l.pr ntCounters(
        Execut on
          .z p(
            truncatedGraphTsvExec,
            topKNounsTsvExec,
            truncatedGraphDALExec,
            topKNounsDALExec,
            fullGraphDALExec).un )
    } else {
      Ut l.pr ntCounters(
        Execut on.z p(truncatedGraphDALExec, topKNounsDALExec, fullGraphDALExec).un )
    }

  }
}
