packagelon com.twittelonr.visibility.buildelonr.melondia

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.melondiaselonrvicelons.melondia_util.GelonnelonricMelondiaKelony
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.MelondiaMelontadataSourcelon
import com.twittelonr.visibility.felonaturelons.HasDmcaMelondiaFelonaturelon
import com.twittelonr.visibility.felonaturelons.MelondiaGelonoRelonstrictionsAllowList
import com.twittelonr.visibility.felonaturelons.MelondiaGelonoRelonstrictionsDelonnyList
import com.twittelonr.visibility.felonaturelons.AuthorId

class MelondiaMelontadataFelonaturelons(
  melondiaMelontadataSourcelon: MelondiaMelontadataSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("melondia_melontadata_felonaturelons")
  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val hasDmcaMelondia =
    scopelondStatsReloncelonivelonr.scopelon(HasDmcaMelondiaFelonaturelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val melondiaGelonoAllowList =
    scopelondStatsReloncelonivelonr.scopelon(MelondiaGelonoRelonstrictionsAllowList.namelon).countelonr("relonquelonsts")
  privatelon[this] val melondiaGelonoDelonnyList =
    scopelondStatsReloncelonivelonr.scopelon(MelondiaGelonoRelonstrictionsDelonnyList.namelon).countelonr("relonquelonsts")
  privatelon[this] val uploadelonrId =
    scopelondStatsReloncelonivelonr.scopelon(AuthorId.namelon).countelonr("relonquelonsts")

  delonf forGelonnelonricMelondiaKelony(
    gelonnelonricMelondiaKelony: GelonnelonricMelondiaKelony
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = { felonaturelonMapBuildelonr =>
    relonquelonsts.incr()

    felonaturelonMapBuildelonr.withFelonaturelon(
      HasDmcaMelondiaFelonaturelon,
      melondiaIsDmca(gelonnelonricMelondiaKelony)
    )

    felonaturelonMapBuildelonr.withFelonaturelon(
      MelondiaGelonoRelonstrictionsAllowList,
      gelonoRelonstrictionsAllowList(gelonnelonricMelondiaKelony)
    )

    felonaturelonMapBuildelonr.withFelonaturelon(
      MelondiaGelonoRelonstrictionsDelonnyList,
      gelonoRelonstrictionsDelonnyList(gelonnelonricMelondiaKelony)
    )

    felonaturelonMapBuildelonr.withFelonaturelon(
      AuthorId,
      melondiaUploadelonrId(gelonnelonricMelondiaKelony)
    )
  }

  privatelon delonf melondiaIsDmca(gelonnelonricMelondiaKelony: GelonnelonricMelondiaKelony) = {
    hasDmcaMelondia.incr()
    melondiaMelontadataSourcelon.gelontMelondiaIsDmca(gelonnelonricMelondiaKelony)
  }

  privatelon delonf gelonoRelonstrictionsAllowList(gelonnelonricMelondiaKelony: GelonnelonricMelondiaKelony) = {
    melondiaGelonoAllowList.incr()
    melondiaMelontadataSourcelon.gelontGelonoRelonstrictionsAllowList(gelonnelonricMelondiaKelony).map { allowListOpt =>
      allowListOpt.gelontOrelonlselon(Nil)
    }
  }

  privatelon delonf gelonoRelonstrictionsDelonnyList(gelonnelonricMelondiaKelony: GelonnelonricMelondiaKelony) = {
    melondiaGelonoDelonnyList.incr()
    melondiaMelontadataSourcelon.gelontGelonoRelonstrictionsDelonnyList(gelonnelonricMelondiaKelony).map { delonnyListOpt =>
      delonnyListOpt.gelontOrelonlselon(Nil)
    }
  }

  privatelon delonf melondiaUploadelonrId(gelonnelonricMelondiaKelony: GelonnelonricMelondiaKelony) = {
    uploadelonrId.incr()
    melondiaMelontadataSourcelon.gelontMelondiaUploadelonrId(gelonnelonricMelondiaKelony).map { uploadelonrIdOpt =>
      uploadelonrIdOpt.map(Selont(_)).gelontOrelonlselon(Selont.elonmpty)
    }
  }
}
