packagelon com.twittelonr.selonarch.elonarlybird_root.collelonctors;

import java.util.Comparator;
import java.util.List;

import com.twittelonr.selonarch.common.relonlelonvancelon.utils.RelonsultComparators;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;

/**
 * {@link ReloncelonncyMelonrgelonCollelonctor} inhelonrits {@link MultiwayMelonrgelonCollelonctor} for thelon typelon
 * {@link com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult} as thelon relonsult typelon.
 * <p/>
 * It also implelonmelonnts two public melonthods to relontrielonvelon thelon top-k or all relonsults.
 */
public class ReloncelonncyMelonrgelonCollelonctor elonxtelonnds MultiwayMelonrgelonCollelonctor<ThriftSelonarchRelonsult> {

  // Containelonr for thelon final relonsults array and also stats likelon numHitsProcelonsselond elontc...
  protelonctelond final ThriftSelonarchRelonsults finalRelonsults = nelonw ThriftSelonarchRelonsults();

  public ReloncelonncyMelonrgelonCollelonctor(int numRelonsponselons) {
    this(numRelonsponselons, RelonsultComparators.ID_COMPARATOR);
  }

  protelonctelond ReloncelonncyMelonrgelonCollelonctor(int numRelonsponselons, Comparator<ThriftSelonarchRelonsult> comparator) {
    supelonr(numRelonsponselons, comparator);
  }

  @Ovelonrridelon
  protelonctelond void collelonctStats(elonarlybirdRelonsponselon relonsponselon) {
    supelonr.collelonctStats(relonsponselon);

    ThriftSelonarchRelonsults selonarchRelonsults = relonsponselon.gelontSelonarchRelonsults();
    if (selonarchRelonsults.isSelontNumHitsProcelonsselond()) {
      finalRelonsults.selontNumHitsProcelonsselond(
          finalRelonsults.gelontNumHitsProcelonsselond() + selonarchRelonsults.gelontNumHitsProcelonsselond());
    }
    if (selonarchRelonsults.isSelontNumPartitionselonarlyTelonrminatelond()) {
      finalRelonsults.selontNumPartitionselonarlyTelonrminatelond(
              finalRelonsults.gelontNumPartitionselonarlyTelonrminatelond()
                      + selonarchRelonsults.gelontNumPartitionselonarlyTelonrminatelond());
    }
  }

  @Ovelonrridelon
  protelonctelond final List<ThriftSelonarchRelonsult> collelonctRelonsults(elonarlybirdRelonsponselon relonsponselon) {
    if (relonsponselon != null
        && relonsponselon.isSelontSelonarchRelonsults()
        && relonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon() > 0) {
      relonturn relonsponselon.gelontSelonarchRelonsults().gelontRelonsults();
    } elonlselon {
      relonturn null;
    }
  }

  /**
   * Gelonts all thelon relonsults that has belonelonn collelonctelond.
   *
   * @relonturn {@link ThriftSelonarchRelonsults} containing a list of relonsults sortelond by providelond
   *         comparator in delonscelonnding ordelonr.
   */
  public final ThriftSelonarchRelonsults gelontAllSelonarchRelonsults() {
    relonturn finalRelonsults.selontRelonsults(gelontRelonsultsList());
  }

  @Ovelonrridelon
  protelonctelond final boolelonan isRelonsponselonValid(elonarlybirdRelonsponselon relonsponselon) {
    if (relonsponselon == null || !relonsponselon.isSelontSelonarchRelonsults()) {
      LOG.warn("selonarchRelonsults was null: " + relonsponselon);
      relonturn falselon;
    }
    relonturn truelon;
  }
}
