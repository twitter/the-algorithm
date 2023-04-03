packagelon com.twittelonr.follow_reloncommelonndations.flows.ads
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.promotelond_accounts.PromotelondCandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AccountProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AdMelontadata
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Relonason
import com.twittelonr.follow_reloncommelonndations.common.modelonls.UselonrCandidatelonSourcelonDelontails

objelonct PromotelondAccountsUtil {
  delonf toCandidatelonUselonr(promotelondCandidatelonUselonr: PromotelondCandidatelonUselonr): CandidatelonUselonr = {
    CandidatelonUselonr(
      id = promotelondCandidatelonUselonr.id,
      scorelon = Nonelon,
      adMelontadata =
        Somelon(AdMelontadata(promotelondCandidatelonUselonr.position, promotelondCandidatelonUselonr.adImprelonssion)),
      relonason = Somelon(
        Relonason(
          accountProof = Somelon(AccountProof(followProof = Somelon(promotelondCandidatelonUselonr.followProof))))
      ),
      uselonrCandidatelonSourcelonDelontails = Somelon(
        UselonrCandidatelonSourcelonDelontails(
          promotelondCandidatelonUselonr.primaryCandidatelonSourcelon,
          Map.elonmpty,
          Map.elonmpty,
          Nonelon))
    )
  }
}
