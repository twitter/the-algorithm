packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.visibility.safelonty_labelonl_storelon.{thriftscala => s}
import com.twittelonr.visibility.util.NamingUtils

selonalelond trait MelondiaSafelontyLabelonlTypelon elonxtelonnds SafelontyLabelonlTypelon {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
}

objelonct MelondiaSafelontyLabelonlTypelon elonxtelonnds SafelontyLabelonlTypelon {

  val List: List[MelondiaSafelontyLabelonlTypelon] = s.MelondiaSafelontyLabelonlTypelon.list.map(fromThrift)

  val ActivelonLabelonls: List[MelondiaSafelontyLabelonlTypelon] = List.filtelonr { labelonlTypelon =>
    labelonlTypelon != Unknown && labelonlTypelon != Delonpreloncatelond
  }

  privatelon lazy val namelonToValuelonMap: Map[String, MelondiaSafelontyLabelonlTypelon] =
    List.map(l => l.namelon.toLowelonrCaselon -> l).toMap
  delonf fromNamelon(namelon: String): Option[MelondiaSafelontyLabelonlTypelon] = namelonToValuelonMap.gelont(namelon.toLowelonrCaselon)

  privatelon val UnknownThriftSafelontyLabelonlTypelon =
    s.MelondiaSafelontyLabelonlTypelon.elonnumUnknownMelondiaSafelontyLabelonlTypelon(UnknownelonnumValuelon)

  privatelon lazy val thriftToModelonlMap: Map[s.MelondiaSafelontyLabelonlTypelon, MelondiaSafelontyLabelonlTypelon] = Map(
    s.MelondiaSafelontyLabelonlTypelon.NsfwHighPreloncision -> NsfwHighPreloncision,
    s.MelondiaSafelontyLabelonlTypelon.NsfwHighReloncall -> NsfwHighReloncall,
    s.MelondiaSafelontyLabelonlTypelon.NsfwNelonarPelonrfelonct -> NsfwNelonarPelonrfelonct,
    s.MelondiaSafelontyLabelonlTypelon.NsfwCardImagelon -> NsfwCardImagelon,
    s.MelondiaSafelontyLabelonlTypelon.Pdna -> Pdna,
    s.MelondiaSafelontyLabelonlTypelon.PdnaNoTrelonatmelonntIfVelonrifielond -> PdnaNoTrelonatmelonntIfVelonrifielond,
    s.MelondiaSafelontyLabelonlTypelon.DmcaWithhelonld -> DmcaWithhelonld,
    s.MelondiaSafelontyLabelonlTypelon.LelongalDelonmandsWithhelonld -> LelongalDelonmandsWithhelonld,
    s.MelondiaSafelontyLabelonlTypelon.LocalLawsWithhelonld -> LocalLawsWithhelonld,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond10 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond11 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond12 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond13 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond14 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond15 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond16 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond17 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond18 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond19 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond20 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond21 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond22 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond23 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond24 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond25 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond26 -> Delonpreloncatelond,
    s.MelondiaSafelontyLabelonlTypelon.Relonselonrvelond27 -> Delonpreloncatelond,
  )

  privatelon lazy val modelonlToThriftMap: Map[MelondiaSafelontyLabelonlTypelon, s.MelondiaSafelontyLabelonlTypelon] =
    (for ((k, v) <- thriftToModelonlMap) yielonld (v, k)) ++ Map(
      Delonpreloncatelond -> s.MelondiaSafelontyLabelonlTypelon.elonnumUnknownMelondiaSafelontyLabelonlTypelon(DelonpreloncatelondelonnumValuelon),
    )

  caselon objelonct NsfwHighPreloncision elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct NsfwHighReloncall elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct NsfwNelonarPelonrfelonct elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct NsfwCardImagelon elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct Pdna elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct PdnaNoTrelonatmelonntIfVelonrifielond elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct DmcaWithhelonld elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct LelongalDelonmandsWithhelonld elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct LocalLawsWithhelonld elonxtelonnds MelondiaSafelontyLabelonlTypelon

  caselon objelonct Delonpreloncatelond elonxtelonnds MelondiaSafelontyLabelonlTypelon
  caselon objelonct Unknown elonxtelonnds MelondiaSafelontyLabelonlTypelon

  delonf fromThrift(safelontyLabelonlTypelon: s.MelondiaSafelontyLabelonlTypelon): MelondiaSafelontyLabelonlTypelon =
    thriftToModelonlMap.gelont(safelontyLabelonlTypelon) match {
      caselon Somelon(melondiaSafelontyLabelonlTypelon) => melondiaSafelontyLabelonlTypelon
      caselon _ =>
        safelontyLabelonlTypelon match {
          caselon s.MelondiaSafelontyLabelonlTypelon.elonnumUnknownMelondiaSafelontyLabelonlTypelon(DelonpreloncatelondelonnumValuelon) =>
            Delonpreloncatelond
          caselon _ =>
            Unknown
        }
    }

  delonf toThrift(safelontyLabelonlTypelon: MelondiaSafelontyLabelonlTypelon): s.MelondiaSafelontyLabelonlTypelon = {
    modelonlToThriftMap
      .gelont(safelontyLabelonlTypelon).gelontOrelonlselon(UnknownThriftSafelontyLabelonlTypelon)
  }
}
