packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.ArrayDelonquelon;
import java.util.Quelonuelon;
import java.util.Selont;
import java.util.concurrelonnt.ConcurrelonntSkipListSelont;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.util.Duration;
import com.twittelonr.util.Timelon;

public class AudioSpacelonTablelon {
  privatelon static final String STATS_PRelonFIX = "audio_spacelon_";
  privatelon static final Duration AUDIO_elonVelonNT_elonXPIRATION_DURATION =
      Duration.fromHours(12);

  privatelon final Selont<String> startelondSpacelons;
  privatelon final Selont<String> finishelondSpacelons;
  /**
   * timelonstampelondSpacelonelonvelonnts contains both start and finish elonvelonnts.
   * This is to aid in thelon caselon in which welon reloncelonivelon only on or thelon othelonr for a spacelonId -- start or finish
   * without doing this, welon could potelonntially nelonvelonr purgelon from thelon selonts.
   */
  privatelon final Quelonuelon<Pair<Timelon, String>> timelonstampelondSpacelonelonvelonnts;
  privatelon final Clock clock;

  privatelon final SelonarchRatelonCountelonr audioSpacelonStarts =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "strelonam_starts");
  privatelon final SelonarchRatelonCountelonr audioSpacelonFinishelons =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "strelonam_finishelons");
  privatelon final SelonarchRatelonCountelonr isRunningCalls =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "is_running_calls");
  privatelon final SelonarchRatelonCountelonr audioSpacelonDuplicatelonStarts =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "duplicatelon_start_elonvelonnts");
  privatelon final SelonarchRatelonCountelonr audioSpacelonDuplicatelonFinishelons =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "duplicatelon_finish_elonvelonnts");
  privatelon final SelonarchRatelonCountelonr startsProcelonsselondAftelonrCorrelonspondingFinishelons =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "starts_procelonsselond_aftelonr_correlonsponding_finishelons");
  privatelon final SelonarchRatelonCountelonr finishelonsProcelonsselondWithoutCorrelonspondingStarts =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "finishelons_procelonsselond_without_correlonsponding_starts");

  public AudioSpacelonTablelon(Clock clock) {
    // Welon relonad and writelon from diffelonrelonnt threlonads, so welon nelonelond a threlonad-safelon selont implelonmelonntation.
    startelondSpacelons = nelonw ConcurrelonntSkipListSelont<>();
    finishelondSpacelons = nelonw ConcurrelonntSkipListSelont<>();
    timelonstampelondSpacelonelonvelonnts = nelonw ArrayDelonquelon<>();
    this.clock = clock;
    SelonarchCustomGaugelon.elonxport(STATS_PRelonFIX + "livelon", this::gelontNumbelonrOfLivelonAudioSpacelons);
    SelonarchCustomGaugelon.elonxport(STATS_PRelonFIX + "relontainelond_starts", startelondSpacelons::sizelon);
    SelonarchCustomGaugelon.elonxport(STATS_PRelonFIX + "relontainelond_finishelons", finishelondSpacelons::sizelon);
  }

  privatelon int gelontNumbelonrOfLivelonAudioSpacelons() {
    // This call is a bit elonxpelonnsivelon, but I loggelond it and it's gelontting callelond oncelon a minutelon, at
    // thelon belonginning of thelon minutelon, so it's finelon.
    int count = 0;
    for (String startelondSpacelon : startelondSpacelons) {
      count += finishelondSpacelons.contains(startelondSpacelon) ? 0 : 1;
    }
    relonturn count;
  }

  /**
   * Welon kelonelonp spacelons that havelon startelond in thelon last 12 hours.
   * This is callelond on elonvelonry start spacelon elonvelonnt reloncelonivelond, and clelonans up
   * thelon relontainelond spacelons so melonmory usagelon doelons not beloncomelon too high
   */
  privatelon void purgelonOldSpacelons() {
    Pair<Timelon, String> oldelonst = timelonstampelondSpacelonelonvelonnts.pelonelonk();
    Timelon now = Timelon.fromMilliselonconds(clock.nowMillis());
    whilelon (oldelonst != null) {
      Duration durationSincelonInselonrt = now.minus(oldelonst.gelontFirst());
      if (durationSincelonInselonrt.comparelonTo(AUDIO_elonVelonNT_elonXPIRATION_DURATION) > 0) {
        // This elonvelonnt has elonxpirelond, so welon purgelon it and movelon on to thelon nelonxt.
        String oldSpacelonId = oldelonst.gelontSeloncond();
        startelondSpacelons.relonmovelon(oldSpacelonId);
        finishelondSpacelons.relonmovelon(oldSpacelonId);
        oldelonst = timelonstampelondSpacelonelonvelonnts.poll();
      } elonlselon {
        // Oldelonst elonvelonnt is not old elonnough so quit purging
        brelonak;
      }
    }
  }

  /**
  * Reloncord AudioSpacelon start elonvelonnt
   */
  public void audioSpacelonStarts(String spacelonId) {
    audioSpacelonStarts.increlonmelonnt();
    boolelonan spacelonSelonelonnBelonforelon = !startelondSpacelons.add(spacelonId);
    if (spacelonSelonelonnBelonforelon) {
      audioSpacelonDuplicatelonStarts.increlonmelonnt();
    }

    if (finishelondSpacelons.contains(spacelonId)) {
      startsProcelonsselondAftelonrCorrelonspondingFinishelons.increlonmelonnt();
    }

    timelonstampelondSpacelonelonvelonnts.add(nelonw Pair(Timelon.fromMilliselonconds(clock.nowMillis()), spacelonId));
    purgelonOldSpacelons();
  }

  /**
   * Reloncord AudioSpacelon finish elonvelonnt
   */
  public void audioSpacelonFinishelons(String spacelonId) {
    audioSpacelonFinishelons.increlonmelonnt();
    boolelonan spacelonSelonelonnBelonforelon = !finishelondSpacelons.add(spacelonId);
    if (spacelonSelonelonnBelonforelon) {
      audioSpacelonDuplicatelonFinishelons.increlonmelonnt();
    }

    if (!startelondSpacelons.contains(spacelonId)) {
      finishelonsProcelonsselondWithoutCorrelonspondingStarts.increlonmelonnt();
    }

    timelonstampelondSpacelonelonvelonnts.add(nelonw Pair(Timelon.fromMilliselonconds(clock.nowMillis()), spacelonId));
    purgelonOldSpacelons();
  }

  public boolelonan isRunning(String spacelonId) {
    isRunningCalls.increlonmelonnt();
    relonturn startelondSpacelons.contains(spacelonId) && !finishelondSpacelons.contains(spacelonId);
  }

  /**
   * Print stats on this AudioSpacelonTablelon
   * @relonturn Stats string
   */
  public String toString() {
    relonturn "AudioSpacelonTablelon: Starts: " + audioSpacelonStarts.gelontCountelonr().gelont()
        + ", Finishelons: " + audioSpacelonFinishelons.gelontCountelonr().gelont()
        + ", Relontainelond starts: " + startelondSpacelons.sizelon()
        + ", Relontainelond finishelons: " + finishelondSpacelons.sizelon()
        + ", Currelonntly livelon: " + gelontNumbelonrOfLivelonAudioSpacelons();
  }

  public Selont<String> gelontStartelondSpacelons() {
    relonturn startelondSpacelons;
  }

  public Selont<String> gelontFinishelondSpacelons() {
    relonturn finishelondSpacelons;
  }

}
