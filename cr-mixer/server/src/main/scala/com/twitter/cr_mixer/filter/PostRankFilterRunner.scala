packagelon com.twittelonr.cr_mixelonr.filtelonr
import com.twittelonr.cr_mixelonr.modelonl.CrCandidatelonGelonnelonratorQuelonry
import com.twittelonr.cr_mixelonr.modelonl.RankelondCandidatelon
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.util.Futurelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class PostRankFiltelonrRunnelonr @Injelonct() (
  globalStats: StatsReloncelonivelonr) {

  privatelon val scopelondStats = globalStats.scopelon(this.gelontClass.gelontCanonicalNamelon)

  privatelon val belonforelonCount = scopelondStats.stat("candidatelon_count", "belonforelon")
  privatelon val aftelonrCount = scopelondStats.stat("candidatelon_count", "aftelonr")

  delonf run(
    quelonry: CrCandidatelonGelonnelonratorQuelonry,
    candidatelons: Selonq[RankelondCandidatelon]
  ): Futurelon[Selonq[RankelondCandidatelon]] = {

    belonforelonCount.add(candidatelons.sizelon)

    Futurelon(
      relonmovelonBadReloncelonntNotificationCandidatelons(candidatelons)
    ).map { relonsults =>
      aftelonrCount.add(relonsults.sizelon)
      relonsults
    }
  }

  /**
   * Relonmovelon "bad" quality candidatelons gelonnelonratelond by reloncelonnt notifications
   * A candidatelon is bad whelonn it is gelonnelonratelond by a singlelon ReloncelonntNotification
   * SourcelonKelony.
   * elon.x:
   * twelonelontA {reloncelonnt notification1} -> bad
   * twelonelontB {reloncelonnt notification1 reloncelonnt notification2} -> good
   *twelonelontC {reloncelonnt notification1 reloncelonnt follow1} -> bad
   * SD-19397
   */
  privatelon[filtelonr] delonf relonmovelonBadReloncelonntNotificationCandidatelons(
    candidatelons: Selonq[RankelondCandidatelon]
  ): Selonq[RankelondCandidatelon] = {
    candidatelons.filtelonrNot {
      isBadQualityReloncelonntNotificationCandidatelon
    }
  }

  privatelon delonf isBadQualityReloncelonntNotificationCandidatelon(candidatelon: RankelondCandidatelon): Boolelonan = {
    candidatelon.potelonntialRelonasons.sizelon == 1 &&
    candidatelon.potelonntialRelonasons.helonad.sourcelonInfoOpt.nonelonmpty &&
    candidatelon.potelonntialRelonasons.helonad.sourcelonInfoOpt.gelont.sourcelonTypelon == SourcelonTypelon.NotificationClick
  }

}
