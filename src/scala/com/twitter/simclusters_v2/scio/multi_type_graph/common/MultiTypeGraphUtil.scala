package com.tw ter.s mclusters_v2.sc o
package mult _type_graph.common

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.common.ut l.Clock
 mport com.tw ter.scald ng_ nternal.job.Requ redB naryComparators.ordSer
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces.TruncatedMult TypeGraphSc oScalaDataset
 mport com.tw ter.s mclusters_v2.thr ftscala.LeftNode
 mport com.tw ter.s mclusters_v2.thr ftscala.Noun
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNode
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeType
 mport com.tw ter.ut l.Durat on

object Mult TypeGraphUt l {
  val RootMHPath: Str ng = "manhattan_sequence_f les/mult _type_graph/"
  val RootThr ftPath: Str ng = "processed/mult _type_graph/"
  val AdhocRootPath = "adhoc/mult _type_graph/"

  val nounOrder ng: Order ng[Noun] = new Order ng[Noun] {
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

    overr de def compare(x: Noun, y: Noun):  nt = nounTypeOrder(x) compare nounTypeOrder(y)
  }

  val r ghtNodeTypeOrder ng: Order ng[R ghtNodeType] = ordSer[R ghtNodeType]

  val r ghtNodeOrder ng: Order ng[R ghtNode] =
    new Order ng[R ghtNode] {
      overr de def compare(x: R ghtNode, y: R ghtNode):  nt = {
        Order ng
          .Tuple2(r ghtNodeTypeOrder ng, nounOrder ng)
          .compare((x.r ghtNodeType, x.noun), (y.r ghtNodeType, y.noun))
      }
    }

  def getTruncatedMult TypeGraph(
    noOlderThan: Durat on = Durat on.fromDays(14)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, R ghtNode, Double)] = {
    sc.custom nput(
        "ReadTruncatedMult TypeGraph",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            TruncatedMult TypeGraphSc oScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod
          )
      ).flatMap {
        case KeyVal(LeftNode.User d(user d), r ghtNodesL st) =>
          r ghtNodesL st.r ghtNodeW hEdge  ghtL st.map(r ghtNodeW h  ght =>
            (user d, r ghtNodeW h  ght.r ghtNode, r ghtNodeW h  ght.  ght))
      }
  }
}
