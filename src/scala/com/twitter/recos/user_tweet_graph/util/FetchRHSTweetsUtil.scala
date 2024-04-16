package com.tw ter.recos.user_t et_graph.ut l

 mport com.tw ter.graphjet.b part e.Mult Seg nt erator
 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport com.tw ter.graphjet.b part e.seg nt.B part eGraphSeg nt
 mport scala.collect on.mutable.L stBuffer
 mport com.tw ter.recos.ut l.Act on

object FetchRHST etsUt l {
  // get RHS t ets g ven LHS users
  def fetchRHST ets(
    user ds: Seq[Long],
    b part eGraph: B part eGraph,
    allo dAct ons: Set[Act on.Value]
  ): Seq[Long] = {
    val allo dAct onStr ngs = allo dAct ons.map(_.toStr ng)
    user ds.d st nct
      .flatMap { user d =>
        val t et ds erator = b part eGraph
          .getLeftNodeEdges(user d).as nstanceOf[Mult Seg nt erator[B part eGraphSeg nt]]

        val t et ds = new L stBuffer[Long]()
         f (t et ds erator != null) {
          wh le (t et ds erator.hasNext) {
            val r ghtNode = t et ds erator.nextLong()
            val edgeType = t et ds erator.currentEdgeType()
             f (allo dAct onStr ngs.conta ns(UserT etEdgeTypeMask(edgeType).toStr ng))
              t et ds += r ghtNode
          }
        }
        t et ds.d st nct
      }
  }
}
