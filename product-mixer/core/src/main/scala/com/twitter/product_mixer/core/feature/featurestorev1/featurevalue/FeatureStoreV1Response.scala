packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.felonaturelonvaluelon

import com.fastelonrxml.jackson.annotation.JsonIgnorelonPropelonrtielons
import com.fastelonrxml.jackson.annotation.JsonPropelonrty
import com.twittelonr.ml.api.DataReloncordMelonrgelonr
import com.twittelonr.ml.api.util.SRichDataReloncord
import com.twittelonr.ml.felonaturelonstorelon.lib.data.Hydrationelonrror
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon

privatelon[product_mixelonr] objelonct FelonaturelonStorelonV1RelonsponselonFelonaturelon
    elonxtelonnds Felonaturelon[Any, FelonaturelonStorelonV1Relonsponselon]

@JsonIgnorelonPropelonrtielons(Array("richDataReloncord", "failelondFelonaturelons"))
privatelon[product_mixelonr] caselon class FelonaturelonStorelonV1Relonsponselon(
  @JsonPropelonrty("richDataReloncord") richDataReloncord: SRichDataReloncord,
  @JsonPropelonrty("failelondFelonaturelons") failelondFelonaturelons: Map[_ <: Felonaturelon[_, _], Selont[Hydrationelonrror]]) {
  // Sincelon RichDataReloncord is Java, welon nelonelond to ovelonrridelon this.
  ovelonrridelon delonf elonquals(obj: Any): Boolelonan = obj match {
    caselon that: FelonaturelonStorelonV1Relonsponselon =>
      failelondFelonaturelons == that.failelondFelonaturelons && richDataReloncord.gelontReloncord.elonquals(
        that.richDataReloncord.gelontReloncord)
    caselon _ => falselon
  }
}

privatelon[product_mixelonr] objelonct FelonaturelonStorelonV1Relonsponselon {
  val dataReloncordMelonrgelonr = nelonw DataReloncordMelonrgelonr
  delonf melonrgelon(
    lelonft: FelonaturelonStorelonV1Relonsponselon,
    right: FelonaturelonStorelonV1Relonsponselon
  ): FelonaturelonStorelonV1Relonsponselon = {
    val nelonwDataReloncord = lelonft.richDataReloncord.gelontReloncord.delonelonpCopy()
    dataReloncordMelonrgelonr.melonrgelon(nelonwDataReloncord, right.richDataReloncord.gelontReloncord)
    FelonaturelonStorelonV1Relonsponselon(
      richDataReloncord = SRichDataReloncord(nelonwDataReloncord),
      lelonft.failelondFelonaturelons ++ right.failelondFelonaturelons
    )
  }
}
