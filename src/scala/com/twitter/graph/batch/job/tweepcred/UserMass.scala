package com.tw ter.graph.batch.job.t epcred

 mport com.tw ter.twadoop.user.gen.Comb nedUser
 mport com.tw ter.ut l.T  
 mport com.tw ter.wtf.scald ng.jobs.common.DateUt l

case class UserMass nfo(user d: Long, mass: Double)

/**
 *  lper class to calculate user mass, borro d from repo reputat ons
 */
object UserMass {

  pr vate val currentT  stamp = T  .now. nM ll seconds
  pr vate val constantD v s onFactorGt_threshFr endsToFollo rsRat oUMass = 5.0
  pr vate val threshAbsNumFr endsUMass = 500
  pr vate val threshFr endsToFollo rsRat oUMass = 0.6
  pr vate val dev ce  ghtAdd  ve = 0.5
  pr vate val age  ghtAdd  ve = 0.2
  pr vate val restr cted  ghtMult pl cat ve = 0.1

  def getUserMass(comb nedUser: Comb nedUser): Opt on[UserMass nfo] = {
    val user = Opt on(comb nedUser.user)
    val user d = user.map(_. d).getOrElse(0L)
    val userExtended = Opt on(comb nedUser.user_extended)
    val age = user.map(_.created_at_msec).map(DateUt l.d ffDays(_, currentT  stamp)).getOrElse(0)
    val  sRestr cted = user.map(_.safety).ex sts(_.restr cted)
    val  sSuspended = user.map(_.safety).ex sts(_.suspended)
    val  sVer f ed = user.map(_.safety).ex sts(_.ver f ed)
    val hasVal dDev ce = user.flatMap(u => Opt on(u.dev ces)).ex sts(_. sSet ssag ng_dev ces)
    val numFollo rs = userExtended.flatMap(u => Opt on(u.follo rs)).map(_.to nt).getOrElse(0)
    val numFollow ngs = userExtended.flatMap(u => Opt on(u.follow ngs)).map(_.to nt).getOrElse(0)

     f (user d == 0L || user.map(_.safety).ex sts(_.deact vated)) {
      None
    } else {
      val mass =
         f ( sSuspended)
          0
        else  f ( sVer f ed)
          100
        else {
          var score = dev ce  ghtAdd  ve * 0.1 +
            ( f (hasVal dDev ce) dev ce  ghtAdd  ve else 0)
          val normal zedAge =  f (age > 30) 1.0 else (1.0 m n scala.math.log(1.0 + age / 15.0))
          score *= normal zedAge
           f (score < 0.01) score = 0.01
           f ( sRestr cted) score *= restr cted  ghtMult pl cat ve
          score = (score m n 1.0) max 0
          score *= 100
          score
        }

      val fr endsToFollo rsRat o = (1.0 + numFollow ngs) / (1.0 + numFollo rs)
      val adjustedMass =
         f (numFollow ngs > threshAbsNumFr endsUMass &&
          fr endsToFollo rsRat o > threshFr endsToFollo rsRat oUMass) {
          mass / scala.math.exp(
            constantD v s onFactorGt_threshFr endsToFollo rsRat oUMass *
              (fr endsToFollo rsRat o - threshFr endsToFollo rsRat oUMass)
          )
        } else {
          mass
        }

      So (UserMass nfo(user d, adjustedMass))
    }
  }
}
