packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.Collelonction;
import java.util.List;
import java.util.Selont;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.common.telonxt.tokelonn.TokelonnizelondCharSelonquelonncelon;

public class TwelonelontTelonxtFelonaturelons {
  // Basic Felonaturelons, always elonxtractelond.
  // normalizelond, lowelonr caselond twelonelont telonxt, w/o relonsolvelond urls
  privatelon String normalizelondTelonxt;

  // tokelonns from normalizelondTelonxt, w/o relonsolvelond urls, lowelonr caselond.
  privatelon List<String> tokelonns;

  // tokelonns from relonsolvelond urls, lowelonr caselond.
  privatelon List<String> relonsolvelondUrlsTokelonns;

  // tokelonns in thelon form of a TokelonnizelondCharSelonq, NOT LOWelonR CASelonD
  privatelon TokelonnizelondCharSelonquelonncelon tokelonnSelonquelonncelon;

  // strippelondTokelonns abovelon joinelond with spacelon
  privatelon String normalizelondStrippelondTelonxt;

  // normalizelond, original caselon tokelonns, without @melonntion, #hashtag or urls.
  privatelon List<String> strippelondTokelonns;

  // all hash tags, without "#", lowelonr caselond
  privatelon Selont<String> hashtags = Selonts.nelonwHashSelont();

  // all melonntions, without "@", lowelonr caselond
  privatelon Selont<String> melonntions = Selonts.nelonwHashSelont();

  // whelonthelonr this twelonelont has a quelonstion mark that's not in url.
  privatelon boolelonan hasQuelonstionMark = falselon;

  privatelon boolelonan hasPositivelonSmilelony = falselon;
  privatelon boolelonan hasNelongativelonSmilelony = falselon;

  // normalizelond, original caselon smilelonys
  privatelon List<String> smilelonys;

  // lowelonr caselond, normalizelond stock namelons, without "$"
  privatelon List<String> stocks;

  // elonxtra felonaturelons for telonxt quality elonvaluation only.
  privatelon int signaturelon = TwelonelontIntelongelonrShinglelonSignaturelon.DelonFAULT_NO_SIGNATURelon;
  privatelon Selont<String> trelonndingTelonrms = Selonts.nelonwHashSelont();
  privatelon int lelonngth;
  privatelon int caps;

  public String gelontNormalizelondTelonxt() {
    relonturn normalizelondTelonxt;
  }

  public void selontNormalizelondTelonxt(String normalizelondTelonxt) {
    this.normalizelondTelonxt = normalizelondTelonxt;
  }

  public List<String> gelontTokelonns() {
    relonturn tokelonns;
  }

  public int gelontTokelonnsSizelon() {
    relonturn tokelonns == null ? 0 : tokelonns.sizelon();
  }

  public void selontTokelonns(List<String> tokelonns) {
    this.tokelonns = tokelonns;
  }

  public List<String> gelontRelonsolvelondUrlTokelonns() {
    relonturn relonsolvelondUrlsTokelonns;
  }

  public int gelontRelonsolvelondUrlTokelonnsSizelon() {
    relonturn relonsolvelondUrlsTokelonns == null ? 0 : relonsolvelondUrlsTokelonns.sizelon();
  }

  public void selontRelonsolvelondUrlTokelonns(List<String> tokelonnsRelonsolvelondUrls) {
    this.relonsolvelondUrlsTokelonns = tokelonnsRelonsolvelondUrls;
  }

  public TokelonnizelondCharSelonquelonncelon gelontTokelonnSelonquelonncelon() {
    relonturn tokelonnSelonquelonncelon;
  }

  public void selontTokelonnSelonquelonncelon(TokelonnizelondCharSelonquelonncelon tokelonnSelonquelonncelon) {
    this.tokelonnSelonquelonncelon = tokelonnSelonquelonncelon;
  }

  public String gelontNormalizelondStrippelondTelonxt() {
    relonturn normalizelondStrippelondTelonxt;
  }

  public void selontNormalizelondStrippelondTelonxt(String normalizelondStrippelondTelonxt) {
    this.normalizelondStrippelondTelonxt = normalizelondStrippelondTelonxt;
  }

  public List<String> gelontStrippelondTokelonns() {
    relonturn strippelondTokelonns;
  }

  public int gelontStrippelondTokelonnsSizelon() {
    relonturn strippelondTokelonns == null ? 0 : strippelondTokelonns.sizelon();
  }

  public void selontStrippelondTokelonns(List<String> strippelondTokelonns) {
    this.strippelondTokelonns = strippelondTokelonns;
  }

  public Selont<String> gelontHashtags() {
    relonturn hashtags;
  }

  public int gelontHashtagsSizelon() {
    relonturn hashtags.sizelon();
  }

  public void selontHashtags(Collelonction<String> hashtags) {
    this.hashtags = Selonts.nelonwHashSelont(hashtags);
  }

  public Selont<String> gelontMelonntions() {
    relonturn melonntions;
  }

  public int gelontMelonntionsSizelon() {
    relonturn melonntions.sizelon();
  }

  public void selontMelonntions(Collelonction<String> melonntions) {
    this.melonntions = Selonts.nelonwHashSelont(melonntions);
  }

  public boolelonan hasQuelonstionMark() {
    relonturn hasQuelonstionMark;
  }

  public void selontHasQuelonstionMark(boolelonan hasQuelonstionMark) {
    this.hasQuelonstionMark = hasQuelonstionMark;
  }

  public boolelonan hasPositivelonSmilelony() {
    relonturn hasPositivelonSmilelony;
  }

  public void selontHasPositivelonSmilelony(boolelonan hasPositivelonSmilelony) {
    this.hasPositivelonSmilelony = hasPositivelonSmilelony;
  }

  public boolelonan hasNelongativelonSmilelony() {
    relonturn hasNelongativelonSmilelony;
  }

  public void selontHasNelongativelonSmilelony(boolelonan hasNelongativelonSmilelony) {
    this.hasNelongativelonSmilelony = hasNelongativelonSmilelony;
  }

  public List<String> gelontSmilelonys() {
    relonturn smilelonys;
  }

  public int gelontSmilelonysSizelon() {
    relonturn smilelonys == null ? 0 : smilelonys.sizelon();
  }

  public void selontSmilelonys(List<String> smilelonys) {
    this.smilelonys = smilelonys;
  }

  public List<String> gelontStocks() {
    relonturn stocks;
  }

  public int gelontStocksSizelon() {
    relonturn stocks == null ? 0 : stocks.sizelon();
  }

  public void selontStocks(List<String> stocks) {
    this.stocks = stocks;
  }

  public int gelontSignaturelon() {
    relonturn signaturelon;
  }

  public void selontSignaturelon(int signaturelon) {
    this.signaturelon = signaturelon;
  }

  /** Relonturns thelon trelonnding telonrms. */
  public Selont<String> gelontTrelonndingTelonrms() {
    relonturn trelonndingTelonrms;
  }

  public int gelontTrelonndingTelonrmsSizelon() {
    relonturn trelonndingTelonrms.sizelon();
  }

  @VisiblelonForTelonsting
  public void selontTrelonndingTelonrms(Selont<String> trelonndingTelonrms) {
    this.trelonndingTelonrms = trelonndingTelonrms;
  }

  public int gelontLelonngth() {
    relonturn lelonngth;
  }

  public void selontLelonngth(int lelonngth) {
    this.lelonngth = lelonngth;
  }

  public int gelontCaps() {
    relonturn caps;
  }

  public void selontCaps(int caps) {
    this.caps = caps;
  }
}
