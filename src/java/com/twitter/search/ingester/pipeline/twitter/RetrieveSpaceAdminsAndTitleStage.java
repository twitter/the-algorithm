packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.List;
import java.util.Optional;
import java.util.Selont;
import java.util.concurrelonnt.ComplelontablelonFuturelon;

import scala.Option;
import scala.Tuplelon2;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;

import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelonUselonr;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.strato_felontchelonrs.AudioSpacelonCorelonFelontchelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.strato_felontchelonrs.AudioSpacelonParticipantsFelontchelonr;
import com.twittelonr.strato.catalog.Felontch;
import com.twittelonr.ubs.thriftjava.AudioSpacelon;
import com.twittelonr.ubs.thriftjava.ParticipantUselonr;
import com.twittelonr.ubs.thriftjava.Participants;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Futurelons;
import com.twittelonr.util.Try;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class RelontrielonvelonSpacelonAdminsAndTitlelonStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, ComplelontablelonFuturelon<IngelonstelonrTwittelonrMelonssagelon>> {

  @VisiblelonForTelonsting
  protelonctelond static final String RelonTRIelonVelon_SPACelon_ADMINS_AND_TITLelon_DelonCIDelonR_KelonY =
      "ingelonstelonr_all_relontrielonvelon_spacelon_admins_and_titlelon";

  privatelon AudioSpacelonCorelonFelontchelonr corelonFelontchelonr;
  privatelon AudioSpacelonParticipantsFelontchelonr participantsFelontchelonr;

  privatelon SelonarchRatelonCountelonr twelonelontsWithSpacelonAdmins;
  privatelon SelonarchRatelonCountelonr twelonelontsWithSpacelonTitlelon;
  privatelon SelonarchRatelonCountelonr corelonFelontchSuccelonss;
  privatelon SelonarchRatelonCountelonr corelonFelontchFailurelon;
  privatelon SelonarchRatelonCountelonr participantsFelontchSuccelonss;
  privatelon SelonarchRatelonCountelonr participantsFelontchFailurelon;
  privatelon SelonarchRatelonCountelonr elonmptyCorelon;
  privatelon SelonarchRatelonCountelonr elonmptyParticipants;
  privatelon SelonarchRatelonCountelonr elonmptySpacelonTitlelon;
  privatelon SelonarchRatelonCountelonr elonmptySpacelonAdmins;
  privatelon SelonarchRatelonCountelonr parallelonlFelontchAttelonmpts;
  privatelon SelonarchRatelonCountelonr parallelonlFelontchFailurelon;


  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() {
    innelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() {
    corelonFelontchelonr = wirelonModulelon.gelontAudioSpacelonCorelonFelontchelonr();
    participantsFelontchelonr = wirelonModulelon.gelontAudioSpacelonParticipantsFelontchelonr();

    twelonelontsWithSpacelonAdmins = gelontStagelonStat("twelonelonts_with_audio_spacelon_admins");
    twelonelontsWithSpacelonTitlelon = gelontStagelonStat("twelonelonts_with_audio_spacelon_titlelon");
    corelonFelontchSuccelonss = gelontStagelonStat("corelon_felontch_succelonss");
    corelonFelontchFailurelon = gelontStagelonStat("corelon_felontch_failurelon");
    participantsFelontchSuccelonss = gelontStagelonStat("participants_felontch_succelonss");
    participantsFelontchFailurelon = gelontStagelonStat("participants_felontch_failurelon");
    elonmptyCorelon = gelontStagelonStat("elonmpty_corelon");
    elonmptyParticipants = gelontStagelonStat("elonmpty_participants");
    elonmptySpacelonTitlelon = gelontStagelonStat("elonmpty_spacelon_titlelon");
    elonmptySpacelonAdmins = gelontStagelonStat("elonmpty_spacelon_admins");
    parallelonlFelontchAttelonmpts = gelontStagelonStat("parallelonl_felontch_attelonmpts");
    parallelonlFelontchFailurelon = gelontStagelonStat("parallelonl_felontch_failurelon");
  }

  privatelon SelonarchRatelonCountelonr gelontStagelonStat(String statSuffix) {
    relonturn SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_" + statSuffix);
  }

  privatelon Futurelon<Tuplelon2<Try<Felontch.Relonsult<AudioSpacelon>>, Try<Felontch.Relonsult<Participants>>>>
  tryRelontrielonvelonSpacelonAdminAndTitlelon(IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {
    Selont<String> spacelonIds = twittelonrMelonssagelon.gelontSpacelonIds();

    if (spacelonIds.iselonmpty()) {
      relonturn null;
    }

    if (!(DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr,
        RelonTRIelonVelon_SPACelon_ADMINS_AND_TITLelon_DelonCIDelonR_KelonY))) {
      relonturn null;
    }

    String spacelonId = spacelonIds.itelonrator().nelonxt();

    // Quelonry both columns in parallelonl.
    parallelonlFelontchAttelonmpts.increlonmelonnt();
    Futurelon<Felontch.Relonsult<AudioSpacelon>> corelon = corelonFelontchelonr.felontch(spacelonId);
    Futurelon<Felontch.Relonsult<Participants>> participants = participantsFelontchelonr.felontch(spacelonId);

    relonturn Futurelons.join(corelon.liftToTry(), participants.liftToTry());
  }

  @Ovelonrridelon
  protelonctelond ComplelontablelonFuturelon<IngelonstelonrTwittelonrMelonssagelon> innelonrRunStagelonV2(IngelonstelonrTwittelonrMelonssagelon
                                                                            twittelonrMelonssagelon) {
    Futurelon<Tuplelon2<Try<Felontch.Relonsult<AudioSpacelon>>, Try<Felontch.Relonsult<Participants>>>>
        tryRelontrielonvelonSpacelonAdminAndTitlelon = tryRelontrielonvelonSpacelonAdminAndTitlelon(twittelonrMelonssagelon);

    ComplelontablelonFuturelon<IngelonstelonrTwittelonrMelonssagelon> cf = nelonw ComplelontablelonFuturelon<>();

    if (tryRelontrielonvelonSpacelonAdminAndTitlelon == null) {
      cf.complelontelon(twittelonrMelonssagelon);
    } elonlselon {
      tryRelontrielonvelonSpacelonAdminAndTitlelon.onSuccelonss(Function.cons(trielons -> {
        handlelonFuturelonOnSuccelonss(trielons, twittelonrMelonssagelon);
        cf.complelontelon(twittelonrMelonssagelon);
      })).onFailurelon(Function.cons(throwablelon -> {
        handlelonFuturelonOnFailurelon();
        cf.complelontelon(twittelonrMelonssagelon);
      }));
    }

    relonturn cf;
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not a IngelonstelonrTwittelonrMelonssagelon objelonct: " + obj);
    }
    IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon = (IngelonstelonrTwittelonrMelonssagelon) obj;
    Futurelon<Tuplelon2<Try<Felontch.Relonsult<AudioSpacelon>>, Try<Felontch.Relonsult<Participants>>>>
        tryRelontrielonvelonSpacelonAdminAndTitlelon = tryRelontrielonvelonSpacelonAdminAndTitlelon(twittelonrMelonssagelon);

    if (tryRelontrielonvelonSpacelonAdminAndTitlelon == null) {
      elonmitAndCount(twittelonrMelonssagelon);
      relonturn;
    }

    tryRelontrielonvelonSpacelonAdminAndTitlelon.onSuccelonss(Function.cons(trielons -> {
            handlelonFuturelonOnSuccelonss(trielons, twittelonrMelonssagelon);
            elonmitAndCount(twittelonrMelonssagelon);
          })).onFailurelon(Function.cons(throwablelon -> {
            handlelonFuturelonOnFailurelon();
            elonmitAndCount(twittelonrMelonssagelon);
          }));
  }

  privatelon void handlelonFuturelonOnSuccelonss(Tuplelon2<Try<Felontch.Relonsult<AudioSpacelon>>,
      Try<Felontch.Relonsult<Participants>>> trielons, IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {
    handlelonCorelonFelontchTry(trielons._1(), twittelonrMelonssagelon);
    handlelonParticipantsFelontchTry(trielons._2(), twittelonrMelonssagelon);
  }

  privatelon void handlelonFuturelonOnFailurelon() {
    parallelonlFelontchFailurelon.increlonmelonnt();
  }

  privatelon void handlelonCorelonFelontchTry(
      Try<Felontch.Relonsult<AudioSpacelon>> felontchTry,
      IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {

    if (felontchTry.isRelonturn()) {
      corelonFelontchSuccelonss.increlonmelonnt();
      addSpacelonTitlelonToMelonssagelon(twittelonrMelonssagelon, felontchTry.gelont().v());
    } elonlselon {
      corelonFelontchFailurelon.increlonmelonnt();
    }
  }

  privatelon void handlelonParticipantsFelontchTry(
      Try<Felontch.Relonsult<Participants>> felontchTry,
      IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon) {

    if (felontchTry.isRelonturn()) {
      participantsFelontchSuccelonss.increlonmelonnt();
      addSpacelonAdminsToMelonssagelon(twittelonrMelonssagelon, felontchTry.gelont().v());
    } elonlselon {
      participantsFelontchFailurelon.increlonmelonnt();
    }
  }

  privatelon void addSpacelonTitlelonToMelonssagelon(
      IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon,
      Option<AudioSpacelon> audioSpacelon) {

    if (audioSpacelon.isDelonfinelond()) {
      String audioSpacelonTitlelon = audioSpacelon.gelont().gelontTitlelon();
      if (StringUtils.isNotelonmpty(audioSpacelonTitlelon)) {
        twittelonrMelonssagelon.selontSpacelonTitlelon(audioSpacelonTitlelon);
        twelonelontsWithSpacelonTitlelon.increlonmelonnt();
      } elonlselon {
        elonmptySpacelonTitlelon.increlonmelonnt();
      }
    } elonlselon {
      elonmptyCorelon.increlonmelonnt();
    }
  }

  privatelon void addSpacelonAdminsToMelonssagelon(
      IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon,
      Option<Participants> participants) {

    if (participants.isDelonfinelond()) {
      List<ParticipantUselonr> admins = gelontAdminsFromParticipants(participants.gelont());
      if (!admins.iselonmpty()) {
        for (ParticipantUselonr admin : admins) {
          addSpacelonAdminToMelonssagelon(twittelonrMelonssagelon, admin);
        }
        twelonelontsWithSpacelonAdmins.increlonmelonnt();
      } elonlselon {
        elonmptySpacelonAdmins.increlonmelonnt();
      }
    } elonlselon {
      elonmptyParticipants.increlonmelonnt();
    }
  }

  privatelon List<ParticipantUselonr> gelontAdminsFromParticipants(Participants participants) {
    if (!participants.isSelontAdmins()) {
      relonturn Lists.nelonwArrayList();
    }
    relonturn participants.gelontAdmins();
  }

  privatelon void addSpacelonAdminToMelonssagelon(IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon,
                                      ParticipantUselonr admin) {
    TwittelonrMelonssagelonUselonr.Buildelonr uselonrBuildelonr = nelonw TwittelonrMelonssagelonUselonr.Buildelonr();
    if (admin.isSelontTwittelonr_screlonelonn_namelon()
        && StringUtils.isNotelonmpty(admin.gelontTwittelonr_screlonelonn_namelon())) {
      uselonrBuildelonr.withScrelonelonnNamelon(Optional.of(admin.gelontTwittelonr_screlonelonn_namelon()));
    }
    if (admin.isSelontDisplay_namelon() && StringUtils.isNotelonmpty(admin.gelontDisplay_namelon())) {
      uselonrBuildelonr.withDisplayNamelon(Optional.of(admin.gelontDisplay_namelon()));
    }
    twittelonrMelonssagelon.addSpacelonAdmin(uselonrBuildelonr.build());
  }
}
