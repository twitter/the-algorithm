package com.tw ter.recos.graph_common

 mport com.tw ter.graphjet.algor hms.T et DMask
 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport scala.collect on.mutable.L stBuffer

/*
 * T   lper class encodes and decodes t et  ds w h t etyp e's card  nformat on
 * w n query ng recos salsa l brary.  ns de salsa l brary, all t et  ds are
 * encoded w h card  nformat on for t  purpose of  nl ne f lter ng.
 */
class B part eGraph lper(graph: B part eGraph) {
  pr vate val t et DMask = new T et DMask

  def getLeftNodeEdges(leftNode: Long): Seq[(Long, Byte)] = {
    val  erator = graph.getLeftNodeEdges(leftNode)

    val edges: L stBuffer[(Long, Byte)] = L stBuffer()
     f ( erator != null) {
      wh le ( erator.hasNext) {
        val node =  erator.nextLong()
        val engage ntType =  erator.currentEdgeType()
        edges += ((t et DMask.restore(node), engage ntType))
      }
    }
    edges.reverse.d st nct // Most recent edges f rst, no dupl cat ons
  }

  def getR ghtNodeEdges(r ghtNode: Long): Seq[Long] = {
    val  erator = graph.getR ghtNodeEdges(r ghtNode)
    val leftNodes: L stBuffer[Long] = L stBuffer()
     f ( erator != null) {
      wh le ( erator.hasNext) {
        leftNodes +=  erator.nextLong()
      }
    }

    leftNodes.reverse.d st nct // Most recent edges f rst, no dupl cat ons
  }
}
