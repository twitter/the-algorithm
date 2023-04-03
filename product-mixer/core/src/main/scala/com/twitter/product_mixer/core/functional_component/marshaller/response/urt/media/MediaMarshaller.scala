packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melondia

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.Melondia
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelondiaMarshallelonr @Injelonct() (
  melondiaelonntityMarshallelonr: MelondiaelonntityMarshallelonr,
  melondiaKelonyMarshallelonr: MelondiaKelonyMarshallelonr,
  relonctMarshallelonr: RelonctMarshallelonr,
  aspelonctRatioMarshallelonr: AspelonctRatioMarshallelonr) {

  delonf apply(melondia: Melondia): urt.Melondia = urt.Melondia(
    melondiaelonntity = melondia.melondiaelonntity.map(melondiaelonntityMarshallelonr(_)),
    melondiaKelony = melondia.melondiaKelony.map(melondiaKelonyMarshallelonr(_)),
    imagelonPossiblelonCropping = melondia.imagelonPossiblelonCropping.map { reloncts =>
      reloncts.map(relonctMarshallelonr(_))
    },
    aspelonctRatio = melondia.aspelonctRatio.map(aspelonctRatioMarshallelonr(_))
  )
}
