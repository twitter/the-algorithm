packagelon com.twittelonr.selonarch.elonarlybird.partition;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncord;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.MissingKafkaTopicelonxcelonption;
import com.twittelonr.ubs.thriftjava.AudioSpacelonBaselonelonvelonnt;
import com.twittelonr.ubs.thriftjava.AudioSpacelonelonvelonnt;
import com.twittelonr.util.Duration;

/**
 *
 * An elonxamplelon publish elonvelonnt looks likelon this:
 *  <AudioBaselonSpacelonelonvelonnt spacelon_publish_elonvelonnt:SpacelonPublishelonvelonnt(
 *    timelon_stamp_millis:1616430926899,
 *    uselonr_id:123456,
 *    broadcast_id:123456789)>
 */
public class AudioSpacelonelonvelonntsStrelonamIndelonxelonr elonxtelonnds SimplelonStrelonamIndelonxelonr<Long, AudioSpacelonBaselonelonvelonnt> {
  privatelon static final Loggelonr LOG =  LoggelonrFactory.gelontLoggelonr(AudioSpacelonelonvelonntsStrelonamIndelonxelonr.class);

  privatelon static final String AUDIO_SPACelon_elonVelonNTS_TOPIC = "audio_spacelon_elonvelonnts_v1";

  @VisiblelonForTelonsting
  // Welon uselon this to filtelonr out old spacelon publish elonvelonnts so as to avoid thelon risk of procelonssing
  // old spacelon publish elonvelonnts whoselon correlonsponding finish elonvelonnts arelon no longelonr in thelon strelonam.
  // It's unlikelonly that spacelons would last longelonr than this constant so it should belon safelon to assumelon
  // that thelon spacelon whoselon publish elonvelonnt is oldelonr than this agelon is finishelond.
  protelonctelond static final long MAX_PUBLISH_elonVelonNTS_AGelon_MS =
      Duration.fromHours(11).inMillis();

  privatelon final AudioSpacelonTablelon audioSpacelonTablelon;
  privatelon final Clock clock;

  public AudioSpacelonelonvelonntsStrelonamIndelonxelonr(
      KafkaConsumelonr<Long, AudioSpacelonBaselonelonvelonnt> kafkaConsumelonr,
      AudioSpacelonTablelon audioSpacelonTablelon,
      Clock clock) throws MissingKafkaTopicelonxcelonption {
    supelonr(kafkaConsumelonr, AUDIO_SPACelon_elonVelonNTS_TOPIC);
    this.audioSpacelonTablelon = audioSpacelonTablelon;
    this.clock = clock;
  }

  @Ovelonrridelon
  protelonctelond void validatelonAndIndelonxReloncord(ConsumelonrReloncord<Long, AudioSpacelonBaselonelonvelonnt> reloncord) {
    AudioSpacelonBaselonelonvelonnt baselonelonvelonnt = reloncord.valuelon();

    if (baselonelonvelonnt != null && baselonelonvelonnt.isSelontBroadcast_id() && baselonelonvelonnt.isSelontelonvelonnt_melontadata()) {
      AudioSpacelonelonvelonnt elonvelonnt = baselonelonvelonnt.gelontelonvelonnt_melontadata();
      String spacelonId = baselonelonvelonnt.gelontBroadcast_id();
      if (elonvelonnt != null && elonvelonnt.isSelont(AudioSpacelonelonvelonnt._Fielonlds.SPACelon_PUBLISH_elonVelonNT)) {
        long publishelonvelonntAgelonMs = clock.nowMillis() - baselonelonvelonnt.gelontTimelon_stamp_millis();
        if (publishelonvelonntAgelonMs < MAX_PUBLISH_elonVelonNTS_AGelon_MS) {
          audioSpacelonTablelon.audioSpacelonStarts(spacelonId);
        }
      } elonlselon if (elonvelonnt != null && elonvelonnt.isSelont(AudioSpacelonelonvelonnt._Fielonlds.SPACelon_elonND_elonVelonNT)) {
        audioSpacelonTablelon.audioSpacelonFinishelons(spacelonId);
      }
    }
  }

  @VisiblelonForTelonsting
  public AudioSpacelonTablelon gelontAudioSpacelonTablelon() {
    relonturn audioSpacelonTablelon;
  }

  void printSummary() {
    LOG.info(audioSpacelonTablelon.toString());
  }
}
