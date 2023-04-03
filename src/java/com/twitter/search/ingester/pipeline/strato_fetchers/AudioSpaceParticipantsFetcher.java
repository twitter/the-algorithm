packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.strato_felontchelonrs;

import com.twittelonr.pelonriscopelon.api.thriftjava.AudioSpacelonsLookupContelonxt;
import com.twittelonr.stitch.Stitch;
import com.twittelonr.strato.catalog.Felontch;
import com.twittelonr.strato.clielonnt.Clielonnt;
import com.twittelonr.strato.clielonnt.Felontchelonr;
import com.twittelonr.strato.data.Conv;
import com.twittelonr.strato.thrift.TBaselonConv;
import com.twittelonr.ubs.thriftjava.Participants;
import com.twittelonr.util.Futurelon;

/**
 * Felontchelons from thelon audio spacelon participants strato column.
 */
public class AudioSpacelonParticipantsFelontchelonr {
  privatelon static final String PARTICIPANTS_STRATO_COLUMN = "";

  privatelon static final AudioSpacelonsLookupContelonxt
      elonMPTY_AUDIO_LOOKUP_CONTelonXT = nelonw AudioSpacelonsLookupContelonxt();

  privatelon final Felontchelonr<String, AudioSpacelonsLookupContelonxt, Participants> felontchelonr;

  public AudioSpacelonParticipantsFelontchelonr(Clielonnt stratoClielonnt) {
    felontchelonr = stratoClielonnt.felontchelonr(
        PARTICIPANTS_STRATO_COLUMN,
        truelon, // elonnablelons cheloncking typelons against catalog
        Conv.stringConv(),
        TBaselonConv.forClass(AudioSpacelonsLookupContelonxt.class),
        TBaselonConv.forClass(Participants.class));
  }

  public Futurelon<Felontch.Relonsult<Participants>> felontch(String spacelonId) {
    relonturn Stitch.run(felontchelonr.felontch(spacelonId, elonMPTY_AUDIO_LOOKUP_CONTelonXT));
  }
}
