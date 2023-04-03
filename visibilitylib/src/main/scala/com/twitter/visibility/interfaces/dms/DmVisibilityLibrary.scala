packagelon com.twittelonr.visibility.intelonrfacelons.dms

import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.visibility.VisibilityLibrary
import com.twittelonr.visibility.buildelonr.VisibilityRelonsult
import com.twittelonr.visibility.buildelonr.uselonrs.AuthorFelonaturelons
import com.twittelonr.visibility.common.DmId
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.common.UselonrSourcelon
import com.twittelonr.visibility.felonaturelons.FelonaturelonMap
import com.twittelonr.visibility.modelonls.ContelonntId.{DmId => DmContelonntId}
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl.DirelonctMelonssagelons
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt
import com.twittelonr.visibility.rulelons.Drop
import com.twittelonr.visibility.rulelons.Relonason.DelonactivatelondAuthor
import com.twittelonr.visibility.rulelons.Relonason.elonraselondAuthor
import com.twittelonr.visibility.rulelons.Relonason.Nsfw

objelonct DmVisibilityLibrary {
  typelon Typelon = DmVisibilityRelonquelonst => Stitch[DmVisibilityRelonsponselon]

  caselon class DmVisibilityRelonquelonst(
    dmId: DmId,
    dmAuthorUselonrId: UselonrId,
    vielonwelonrContelonxt: VielonwelonrContelonxt)

  caselon class DmVisibilityRelonsponselon(isMelonssagelonNsfw: Boolelonan)

  val DelonfaultSafelontyLelonvelonl: SafelontyLelonvelonl = DirelonctMelonssagelons

  delonf apply(
    visibilityLibrary: VisibilityLibrary,
    stratoClielonnt: StratoClielonnt,
    uselonrSourcelon: UselonrSourcelon,
    elonnablelonVfFelonaturelonHydrationInShim: Gatelon[Unit] = Gatelon.Falselon
  ): Typelon = {
    val libraryStatsReloncelonivelonr = visibilityLibrary.statsReloncelonivelonr
    val vfelonnginelonCountelonr = libraryStatsReloncelonivelonr.countelonr("vf_elonnginelon_relonquelonsts")

    val authorFelonaturelons = nelonw AuthorFelonaturelons(uselonrSourcelon, libraryStatsReloncelonivelonr)

    { r: DmVisibilityRelonquelonst =>
      vfelonnginelonCountelonr.incr()

      val contelonntId = DmContelonntId(r.dmId)
      val dmAuthorUselonrId = r.dmAuthorUselonrId
      val isVfFelonaturelonHydrationelonnablelond = elonnablelonVfFelonaturelonHydrationInShim()

      val felonaturelonMap =
        visibilityLibrary.felonaturelonMapBuildelonr(
          Selonq(authorFelonaturelons.forAuthorId(dmAuthorUselonrId))
        )

      val relonsp = if (isVfFelonaturelonHydrationelonnablelond) {
        FelonaturelonMap.relonsolvelon(felonaturelonMap, libraryStatsReloncelonivelonr).flatMap { relonsolvelondFelonaturelonMap =>
          visibilityLibrary.runRulelonelonnginelon(
            contelonntId,
            relonsolvelondFelonaturelonMap,
            r.vielonwelonrContelonxt,
            DelonfaultSafelontyLelonvelonl
          )
        }
      } elonlselon {
        visibilityLibrary
          .runRulelonelonnginelon(
            contelonntId,
            felonaturelonMap,
            r.vielonwelonrContelonxt,
            DelonfaultSafelontyLelonvelonl
          )
      }

      relonsp.map(buildRelonsponselon)
    }
  }

  privatelon[this] delonf buildRelonsponselon(visibilityRelonsult: VisibilityRelonsult) =
    visibilityRelonsult.velonrdict match {
      caselon Drop(Nsfw | elonraselondAuthor | DelonactivatelondAuthor, _) =>
        DmVisibilityRelonsponselon(isMelonssagelonNsfw = truelon)
      caselon _ =>
        DmVisibilityRelonsponselon(isMelonssagelonNsfw = falselon)
    }

}
