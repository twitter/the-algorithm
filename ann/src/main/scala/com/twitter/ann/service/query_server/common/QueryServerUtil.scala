package com.tw ter.ann.serv ce.query_server.common

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.search.common.f le.AbstractF le
 mport scala.collect on.JavaConverters._

object QueryServerUt l {

  pr vate val log = Logger.get("QueryServerUt l")

  /**
   * Val date  f t  abstract f le (d rectory) s ze  s w h n t  def ned l m s.
   * @param d r Hdfs/Local d rectory
   * @param m n ndexS zeBytes m n mum s ze of f le  n bytes (Exclus ve)
   * @param max ndexS zeBytes m n mum s ze of f le  n bytes (Exclus ve)
   * @return true  f f le s ze w h n m n ndexS zeBytes and max ndexS zeBytes else false
   */
  def  sVal d ndexD rS ze(
    d r: AbstractF le,
    m n ndexS zeBytes: Long,
    max ndexS zeBytes: Long
  ): Boolean = {
    val recurs ve = true
    val d rS ze = d r.l stF les(recurs ve).asScala.map(_.getS ze nBytes).sum

    log.debug(s"Ann  ndex d rectory ${d r.getPath} s ze  n bytes $d rS ze")

    val  sVal d = (d rS ze > m n ndexS zeBytes) && (d rS ze < max ndexS zeBytes)
     f (! sVal d) {
      log. nfo(s"Ann  ndex d rectory  s  nval d ${d r.getPath} s ze  n bytes $d rS ze")
    }
     sVal d
  }
}
