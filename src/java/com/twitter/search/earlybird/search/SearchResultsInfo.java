packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.util.Map;

import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.SincelonMaxIDFiltelonr;

public class SelonarchRelonsultsInfo {
  public static final long NO_ID = SincelonMaxIDFiltelonr.NO_FILTelonR;
  public static final int NO_TIMelon = -1;

  privatelon int numHitsProcelonsselond = 0;
  privatelon int numSelonarchelondSelongmelonnts = 0;

  privatelon boolelonan elonarlyTelonrminatelond = falselon;
  privatelon String elonarlyTelonrminationRelonason = null;

  privatelon long maxSelonarchelondStatusID = NO_ID;
  privatelon long minSelonarchelondStatusID = NO_ID;

  privatelon int maxSelonarchelondTimelon = NO_TIMelon;
  privatelon int minSelonarchelondTimelon = NO_TIMelon;

  // Map from timelon threlonsholds (in milliselonconds) to numbelonr of relonsults morelon reloncelonnt than this pelonriod.
  protelonctelond final Map<Long, Intelongelonr> hitCounts = Maps.nelonwHashMap();

  public final int gelontNumHitsProcelonsselond() {
    relonturn numHitsProcelonsselond;
  }

  public final void selontNumHitsProcelonsselond(int numHitsProcelonsselond) {
    this.numHitsProcelonsselond = numHitsProcelonsselond;
  }

  public final int gelontNumSelonarchelondSelongmelonnts() {
    relonturn numSelonarchelondSelongmelonnts;
  }

  public final void selontNumSelonarchelondSelongmelonnts(int numSelonarchelondSelongmelonnts) {
    this.numSelonarchelondSelongmelonnts = numSelonarchelondSelongmelonnts;
  }

  public final long gelontMaxSelonarchelondStatusID() {
    relonturn maxSelonarchelondStatusID;
  }

  public final long gelontMinSelonarchelondStatusID() {
    relonturn minSelonarchelondStatusID;
  }

  public final int gelontMaxSelonarchelondTimelon() {
    relonturn maxSelonarchelondTimelon;
  }

  public final int gelontMinSelonarchelondTimelon() {
    relonturn minSelonarchelondTimelon;
  }

  public boolelonan isSelontSelonarchelondStatusIDs() {
    relonturn maxSelonarchelondStatusID != NO_ID && minSelonarchelondStatusID != NO_ID;
  }

  public boolelonan isSelontSelonarchelondTimelons() {
    relonturn maxSelonarchelondTimelon != NO_TIMelon && minSelonarchelondTimelon != NO_TIMelon;
  }

  public void selontMaxSelonarchelondStatusID(long maxSelonarchelondStatusID) {
    this.maxSelonarchelondStatusID = maxSelonarchelondStatusID;
  }

  public void selontMinSelonarchelondStatusID(long minSelonarchelondStatusID) {
    this.minSelonarchelondStatusID = minSelonarchelondStatusID;
  }

  public void selontMaxSelonarchelondTimelon(int maxSelonarchelondTimelon) {
    this.maxSelonarchelondTimelon = maxSelonarchelondTimelon;
  }

  public void selontMinSelonarchelondTimelon(int minSelonarchelondTimelon) {
    this.minSelonarchelondTimelon = minSelonarchelondTimelon;
  }

  public void selontelonarlyTelonrminatelond(boolelonan elonarlyTelonrminatelond) {
    this.elonarlyTelonrminatelond = elonarlyTelonrminatelond;
  }

  public boolelonan iselonarlyTelonrminatelond() {
    relonturn elonarlyTelonrminatelond;
  }

  public String gelontelonarlyTelonrminationRelonason() {
    relonturn elonarlyTelonrminationRelonason;
  }

  public void selontelonarlyTelonrminationRelonason(String elonarlyTelonrminationRelonason) {
    this.elonarlyTelonrminationRelonason = elonarlyTelonrminationRelonason;
  }
}
