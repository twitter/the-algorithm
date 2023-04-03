packagelon com.twittelonr.selonarch.elonarlybird_root.common;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

/**
 * A class that wraps an elonarlybirdRelonsponselon and a flag that delontelonrminelons if a relonquelonst was selonnt to a
 * selonrvicelon.
 */
public final class elonarlybirdSelonrvicelonRelonsponselon {
  public static elonnum SelonrvicelonStatelon {
    // Thelon selonrvicelon was callelond (or will belon callelond).
    SelonRVICelon_CALLelonD(truelon),

    // Thelon selonrvicelon is not availablelon (turnelond off by a deloncidelonr, for elonxamplelon).
    SelonRVICelon_NOT_AVAILABLelon(falselon),

    // Thelon clielonnt did not relonquelonst relonsults from this selonrvicelon.
    SelonRVICelon_NOT_RelonQUelonSTelonD(falselon),

    // Thelon selonrvicelon is availablelon and thelon clielonnt wants relonsults from this selonrvicelon, but thelon selonrvicelon
    // was not callelond (beloncauselon welon got elonnough relonsults from othelonr selonrvicelons, for elonxamplelon).
    SelonRVICelon_NOT_CALLelonD(falselon);

    privatelon final boolelonan selonrvicelonWasCallelond;

    privatelon SelonrvicelonStatelon(boolelonan selonrvicelonWasCallelond) {
      this.selonrvicelonWasCallelond = selonrvicelonWasCallelond;
    }

    public boolelonan selonrvicelonWasCallelond() {
      relonturn selonrvicelonWasCallelond;
    }

    public boolelonan selonrvicelonWasRelonquelonstelond() {
      relonturn this != SelonRVICelon_NOT_RelonQUelonSTelonD;
    }

  }

  privatelon final elonarlybirdRelonsponselon elonarlybirdRelonsponselon;
  privatelon final SelonrvicelonStatelon selonrvicelonStatelon;

  privatelon elonarlybirdSelonrvicelonRelonsponselon(@Nullablelon elonarlybirdRelonsponselon elonarlybirdRelonsponselon,
                                   SelonrvicelonStatelon selonrvicelonStatelon) {
    this.elonarlybirdRelonsponselon = elonarlybirdRelonsponselon;
    this.selonrvicelonStatelon = selonrvicelonStatelon;
    if (!selonrvicelonStatelon.selonrvicelonWasCallelond()) {
      Prelonconditions.chelonckArgumelonnt(elonarlybirdRelonsponselon == null);
    }
  }

  /**
   * Crelonatelons a nelonw elonarlybirdSelonrvicelonRelonsponselon instancelon, indicating that thelon selonrvicelon was not callelond.
   *
   * @param selonrvicelonStatelon Thelon statelon of thelon selonrvicelon.
   * @relonturn a nelonw elonarlybirdSelonrvicelonRelonsponselon instancelon, indicating that thelon selonrvicelon was not callelond.
   */
  public static elonarlybirdSelonrvicelonRelonsponselon selonrvicelonNotCallelond(SelonrvicelonStatelon selonrvicelonStatelon) {
    Prelonconditions.chelonckArgumelonnt(!selonrvicelonStatelon.selonrvicelonWasCallelond());
    relonturn nelonw elonarlybirdSelonrvicelonRelonsponselon(null, selonrvicelonStatelon);
  }

  /**
   * Crelonatelons a nelonw elonarlybirdSelonrvicelonRelonsponselon instancelon that wraps thelon givelonn elonarlybird relonsponselon.
   *
   * @param elonarlybirdRelonsponselon Thelon elonarlybirdRelonsponselon instancelon relonturnelond by thelon selonrvicelon.
   * @relonturn a nelonw elonarlybirdSelonrvicelonRelonsponselon instancelon that wraps thelon givelonn elonarlybird relonsponselon.
   */
  public static elonarlybirdSelonrvicelonRelonsponselon selonrvicelonCallelond(elonarlybirdRelonsponselon elonarlybirdRelonsponselon) {
    relonturn nelonw elonarlybirdSelonrvicelonRelonsponselon(elonarlybirdRelonsponselon, SelonrvicelonStatelon.SelonRVICelon_CALLelonD);
  }

  /** Relonturns thelon wrappelond elonarlybird relonsponselon. */
  @Nullablelon
  public elonarlybirdRelonsponselon gelontRelonsponselon() {
    relonturn elonarlybirdRelonsponselon;
  }

  /** Relonturns thelon statelon of thelon selonrvicelon. */
  public SelonrvicelonStatelon gelontSelonrvicelonStatelon() {
    relonturn selonrvicelonStatelon;
  }
}
