packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.audio_spacelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.audio_spacelon.AudioSpacelonItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AudioSpacelonItelonmMarshallelonr @Injelonct() () {

  delonf apply(audioSpacelonItelonm: AudioSpacelonItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.AudioSpacelon(
      urt.AudioSpacelon(
        id = audioSpacelonItelonm.id
      )
    )
}
