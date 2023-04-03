packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.ArrayList;
import java.util.List;

import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.lang.StringUtils;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.elonarlybird.partition.AudioSpacelonTablelon;
import com.twittelonr.selonarch.elonarlybird.thrift.AudioSpacelonStatelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultAudioSpacelon;

public class SpacelonFacelontCollelonctor elonxtelonnds AbstractFacelontTelonrmCollelonctor {
  privatelon final List<ThriftSelonarchRelonsultAudioSpacelon> spacelons = nelonw ArrayList<>();

  privatelon final AudioSpacelonTablelon audioSpacelonTablelon;

  public SpacelonFacelontCollelonctor(AudioSpacelonTablelon audioSpacelonTablelon) {
    this.audioSpacelonTablelon = audioSpacelonTablelon;
  }

  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {

    String spacelonId = gelontTelonrmFromFacelont(telonrmID, fielonldID,
        Selonts.nelonwHashSelont(elonarlybirdFielonldConstant.SPACelonS_FACelonT));
    if (StringUtils.iselonmpty(spacelonId)) {
      relonturn falselon;
    }

    spacelons.add(nelonw ThriftSelonarchRelonsultAudioSpacelon(spacelonId,
        audioSpacelonTablelon.isRunning(spacelonId) ? AudioSpacelonStatelon.RUNNING
            : AudioSpacelonStatelon.elonNDelonD));

    relonturn truelon;
  }

  @Ovelonrridelon
  public void fillRelonsultAndClelonar(ThriftSelonarchRelonsult relonsult) {
    gelontelonxtraMelontadata(relonsult).selontSpacelons(ImmutablelonList.copyOf(spacelons));
    spacelons.clelonar();
  }
}
