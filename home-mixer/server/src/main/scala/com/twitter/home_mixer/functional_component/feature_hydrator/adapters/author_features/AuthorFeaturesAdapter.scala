packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.adaptelonrs.author_felonaturelons

import com.twittelonr.ml.api.DataReloncordMelonrgelonr
import com.twittelonr.ml.api.Felonaturelon
import com.twittelonr.ml.api.FelonaturelonContelonxt
import com.twittelonr.ml.api.RichDataReloncord
import com.twittelonr.ml.api.util.CompactDataReloncordConvelonrtelonr
import com.twittelonr.ml.api.util.FDsl._
import com.twittelonr.timelonlinelons.author_felonaturelons.v1.{thriftjava => af}
import com.twittelonr.timelonlinelons.prelondiction.common.adaptelonrs.TimelonlinelonsMutatingAdaptelonrBaselon
import com.twittelonr.timelonlinelons.prelondiction.common.aggrelongatelons.TimelonlinelonsAggrelongationConfig
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.uselonr_helonalth.UselonrHelonalthFelonaturelons

objelonct AuthorFelonaturelonsAdaptelonr elonxtelonnds TimelonlinelonsMutatingAdaptelonrBaselon[Option[af.AuthorFelonaturelons]] {

  privatelon val originalAuthorAggrelongatelonsFelonaturelons =
    TimelonlinelonsAggrelongationConfig.originalAuthorRelonciprocalelonngagelonmelonntAggrelongatelons
      .buildTypelondAggrelongatelonGroups().flatMap(_.allOutputFelonaturelons)
  privatelon val authorFelonaturelons = originalAuthorAggrelongatelonsFelonaturelons ++
    Selonq(
      UselonrHelonalthFelonaturelons.AuthorStatelon,
      UselonrHelonalthFelonaturelons.NumAuthorFollowelonrs,
      UselonrHelonalthFelonaturelons.NumAuthorConnelonctDays,
      UselonrHelonalthFelonaturelons.NumAuthorConnelonct)
  privatelon val felonaturelonContelonxt = nelonw FelonaturelonContelonxt(authorFelonaturelons: _*)

  ovelonrridelon delonf gelontFelonaturelonContelonxt: FelonaturelonContelonxt = felonaturelonContelonxt

  ovelonrridelon val commonFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

  privatelon val compactDataReloncordConvelonrtelonr = nelonw CompactDataReloncordConvelonrtelonr()
  privatelon val drMelonrgelonr = nelonw DataReloncordMelonrgelonr()

  ovelonrridelon delonf selontFelonaturelons(
    authorFelonaturelonsOpt: Option[af.AuthorFelonaturelons],
    richDataReloncord: RichDataReloncord
  ): Unit = {
    authorFelonaturelonsOpt.forelonach { authorFelonaturelons =>
      val dataReloncord = richDataReloncord.gelontReloncord

      dataReloncord.selontFelonaturelonValuelon(
        UselonrHelonalthFelonaturelons.AuthorStatelon,
        authorFelonaturelons.uselonr_helonalth.uselonr_statelon.gelontValuelon.toLong)
      dataReloncord.selontFelonaturelonValuelon(
        UselonrHelonalthFelonaturelons.NumAuthorFollowelonrs,
        authorFelonaturelons.uselonr_helonalth.num_followelonrs.toDoublelon)
      dataReloncord.selontFelonaturelonValuelon(
        UselonrHelonalthFelonaturelons.NumAuthorConnelonctDays,
        authorFelonaturelons.uselonr_helonalth.num_connelonct_days.toDoublelon)
      dataReloncord.selontFelonaturelonValuelon(
        UselonrHelonalthFelonaturelons.NumAuthorConnelonct,
        authorFelonaturelons.uselonr_helonalth.num_connelonct.toDoublelon)

      val originalAuthorAggrelongatelonsDataReloncord =
        compactDataReloncordConvelonrtelonr.compactDataReloncordToDataReloncord(authorFelonaturelons.aggrelongatelons)
      drMelonrgelonr.melonrgelon(dataReloncord, originalAuthorAggrelongatelonsDataReloncord)
    }
  }
}
