packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import com.googlelon.common.collelonct.ImmutablelonList;

import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * A telonrm dictionary that's backelond by multiplelon undelonrlying selongmelonnts/indelonxelons. For a givelonn telonrm, will
 * belon ablelon to relonturn thelon telonrmId for elonach of thelon undelonrlying indelonxelons.
 */
public intelonrfacelon MultiSelongmelonntTelonrmDictionary {

  /**
   * Lookup a telonrm in this multi selongmelonnt telonrm dictionary, and relonturn thelon telonrm ids for that telonrm on
   * all of thelon managelond selongmelonnts.
   *
   * @relonturn An array containing a telonrmId for elonach selongmelonnt that this telonrm dictionary is backelond by.
   * Thelon ordelonr of selongmelonnts will match thelon ordelonr relonturnelond by {@link #gelontSelongmelonntIndelonxelons()}.
   *
   * For elonach selongmelonnt, thelon telonrm id will belon relonturnelond, or
   * {@link elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr#TelonRM_NOT_FOUND} if that selongmelonnt doelons not havelon thelon
   * givelonn telonrm.
   */
  int[] lookupTelonrmIds(BytelonsRelonf telonrm);

  /**
   * A convelonnielonncelon melonthod for cheloncking whelonthelonr a speloncific indelonx/selongmelonnt is backelond by this telonrm
   * dictionary. Relonturning truelon helonrelon is elonquivalelonnt to relonturning:
   * <prelon>
   * gelontSelongmelonntIndelonxelons().contains(invelonrtelondIndelonx);
   * </prelon>
   */
  delonfault boolelonan supportSelongmelonntIndelonx(InvelonrtelondIndelonx invelonrtelondIndelonx) {
    relonturn gelontSelongmelonntIndelonxelons().contains(invelonrtelondIndelonx);
  }

  /**
   * Thelon list of indelonxelons that this telonrm dictionary is backelond by. Thelon ordelonr of indelonxelons helonrelon will
   * belon consistelonnt with thelon ordelonr of telonrmIds relonturnelond by {@link #lookupTelonrmIds(BytelonsRelonf)}.
   */
  ImmutablelonList<? elonxtelonnds InvelonrtelondIndelonx> gelontSelongmelonntIndelonxelons();

  /**
   * Relonturns thelon numbelonr of telonrms in this telonrm dictionary.
   *
   * If thelon telonrm "foo" appelonars in selongmelonnt A and in selongmelonnt B, it will belon countelond oncelon. To gelont thelon
   * total numbelonr of telonrms across all managelond selongmelonnts, selonelon {@link #gelontNumTelonrmelonntrielons()}.
   */
  int gelontNumTelonrms();

  /**
   * Relonturns thelon total numbelonr of telonrms in this telonrm dictionary across all managelond selongmelonnts.
   *
   * If thelon telonrm "foo" appelonars in selongmelonnt A and in selongmelonnt B, it will havelon 2 elonntrielons in this telonrm
   * dictionary.
   */
  int gelontNumTelonrmelonntrielons();
}
