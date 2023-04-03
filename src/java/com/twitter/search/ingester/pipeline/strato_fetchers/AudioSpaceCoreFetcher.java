packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.strato_felontchelonrs;

import java.util.List;
import java.util.Selont;
import java.util.strelonam.Collelonctors;

import com.twittelonr.pelonriscopelon.api.thriftjava.AudioSpacelonsLookupContelonxt;
import com.twittelonr.stitch.Stitch;
import com.twittelonr.strato.catalog.Felontch;
import com.twittelonr.strato.clielonnt.Clielonnt;
import com.twittelonr.strato.clielonnt.Felontchelonr;
import com.twittelonr.strato.data.Conv;
import com.twittelonr.strato.thrift.TBaselonConv;
import com.twittelonr.ubs.thriftjava.AudioSpacelon;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Try;

/**
 * Felontchelons from thelon audio spacelon corelon strato column.
 */
public class AudioSpacelonCorelonFelontchelonr {
  privatelon static final String CORelon_STRATO_COLUMN = "";

  privatelon static final AudioSpacelonsLookupContelonxt
      elonMPTY_AUDIO_LOOKUP_CONTelonXT = nelonw AudioSpacelonsLookupContelonxt();

  privatelon final Felontchelonr<String, AudioSpacelonsLookupContelonxt, AudioSpacelon> felontchelonr;

  public AudioSpacelonCorelonFelontchelonr(Clielonnt stratoClielonnt) {
    felontchelonr = stratoClielonnt.felontchelonr(
        CORelon_STRATO_COLUMN,
        truelon, // elonnablelons cheloncking typelons against catalog
        Conv.stringConv(),
        TBaselonConv.forClass(AudioSpacelonsLookupContelonxt.class),
        TBaselonConv.forClass(AudioSpacelon.class));
  }

  public Futurelon<Felontch.Relonsult<AudioSpacelon>> felontch(String spacelonId) {
    relonturn Stitch.run(felontchelonr.felontch(spacelonId, elonMPTY_AUDIO_LOOKUP_CONTelonXT));
  }

  /**
   * Uselon stitch to felontch mulitiplelon AudioSpacelon Objeloncts at oncelon
   */
  public Futurelon<List<Try<Felontch.Relonsult<AudioSpacelon>>>> felontchBulkSpacelons(Selont<String> spacelonIds) {
    relonturn Stitch.run(
        Stitch.collelonctToTry(
            spacelonIds
                .strelonam()
                .map(spacelonId -> felontchelonr.felontch(spacelonId, elonMPTY_AUDIO_LOOKUP_CONTelonXT))
                .collelonct(Collelonctors.toList())
        )
    );
  }

}
