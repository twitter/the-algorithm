package com.tw ter.graph.batch.job.t epcred

 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.job._
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch._

/**
 * Reg ster t  beg nn ng of t  t epcred job  n analyt c batch table
 *
 * Opt ons:
 * --  ghted: do   ghted pagerank
 * --hadoop_conf g: /etc/hadoop/hadoop-conf-proc-atla
 *
 */
class T epcredBatchJob(args: Args) extends Analyt cs erat veBatchJob(args) {

  def WE GHTED = args("  ghted").toBoolean

  overr de def t  out = H s(36)
  overr de def hasFlow = false
  def descr pt onSuff x = "   ghted=" + args("  ghted")
  overr de def batch ncre nt = H s(24)
  overr de def f rstT   = R chDate("2015-10-02")
  overr de def batchDescr pt on = classOf[T epcredBatchJob].getCanon calNa  + descr pt onSuff x

  overr de def run = {
    val success = super.run
    pr ntln("Batch Stat: " +  ssage ader + " " + jobStat.get.toStr ng)
    success
  }

  def startT   = dateRange.start
  def dateStr ng = startT  .toStr ng("yyyy/MM/dd")

  overr de def ch ldren = {
    val BASED R = "/user/cassowary/t epcred/"
    val baseD r = BASED R + ( f (WE GHTED) "  ghted" else "un  ghted") + "/da ly/"
    val tmpD r = baseD r + "tmp"
    val outputD r = baseD r + dateStr ng
    val pageRankD r = outputD r + "/f nalmass"
    val t epcredD r = outputD r + "/f nalt epcred"
    val yesterdayStr = (startT   - Days(1)).toStr ng("yyyy/MM/dd")
    val yestPageRankD r = baseD r + yesterdayStr + "/f nalmass"
    val TWEEPCRED = "/t epcred"
    val curRep = ( f (WE GHTED) baseD r else BASED R) + "current"
    val todayRep = ( f (WE GHTED) baseD r else BASED R) + dateStr ng
    val newArgs = args + ("pwd", So (tmpD r)) +
      ("output_pagerank", So (pageRankD r)) +
      ("output_t epcred", So (t epcredD r)) +
      (" nput_pagerank", So (yestPageRankD r)) +
      ("current_t epcred", So (curRep + TWEEPCRED)) +
      ("today_t epcred", So (todayRep + TWEEPCRED))

    val prJob = new PreparePageRankData(newArgs)

    L st(prJob)
  }

  pr vate def  ssage ader = {
    val dateStr ng = dateRange.start.toStr ng("yyyy/MM/dd")
    classOf[T epcredBatchJob].getS mpleNa  +
      ( f (WE GHTED) "   ghted " else " un  ghted ") + dateStr ng
  }
}
