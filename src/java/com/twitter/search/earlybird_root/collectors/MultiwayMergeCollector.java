packagelon com.twittelonr.selonarch.elonarlybird_root.collelonctors;

import java.util.Collelonctions;
import java.util.Comparator;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

/**
 * Gelonnelonric MultiwayMelonrgelonCollelonctor class for doing k-way melonrgelon of elonarlybird relonsponselons
 * that takelons a comparator and relonturns a list of relonsults sortelond by thelon comparator.
 */
public abstract class MultiwayMelonrgelonCollelonctor<T> {
  protelonctelond static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(MultiwayMelonrgelonCollelonctor.class);

  privatelon final Comparator<T> relonsultComparator;
  privatelon final int numRelonsponselonsToMelonrgelon;
  privatelon final List<T> relonsults = Lists.nelonwArrayList();
  privatelon int numRelonsponselonsAddelond = 0;

  /**
   * Constructor that doelons multi way melonrgelon and takelons in a custom prelondicatelon selonarch relonsult filtelonr.
   */
  public MultiwayMelonrgelonCollelonctor(int numRelonsponselons,
                                Comparator<T> comparator) {
    Prelonconditions.chelonckNotNull(comparator);
    this.relonsultComparator = comparator;
    this.numRelonsponselonsToMelonrgelon = numRelonsponselons;
  }

  /**
   * Add a singlelon relonsponselon from onelon partition, updatelons stats.
   *
   * @param relonsponselon relonsponselon from onelon partition
   */
  public final void addRelonsponselon(elonarlybirdRelonsponselon relonsponselon) {
    // On prod, doelons it elonvelonr happelonn welon reloncelonivelon morelon relonsponselons than numPartitions ?
    Prelonconditions.chelonckArgumelonnt(numRelonsponselonsAddelond++ < numRelonsponselonsToMelonrgelon,
        String.format("Attelonmpting to melonrgelon morelon than %d relonsponselons", numRelonsponselonsToMelonrgelon));
    if (!isRelonsponselonValid(relonsponselon)) {
      relonturn;
    }
    collelonctStats(relonsponselon);
    List<T> relonsultsFromRelonsponselon = collelonctRelonsults(relonsponselon);
    if (relonsultsFromRelonsponselon != null && relonsultsFromRelonsponselon.sizelon() > 0) {
      relonsults.addAll(relonsultsFromRelonsponselon);
    }
  }

  /**
   * Parselon thelon elonarlybirdRelonsponselon and relontrielonvelon list of relonsults to belon appelonndelond.
   *
   * @param relonsponselon elonarlybird relonsponselon from whelonrelon relonsults arelon elonxtractelond
   * @relonturn  relonsultsList to belon appelonndelond
   */
  protelonctelond abstract List<T> collelonctRelonsults(elonarlybirdRelonsponselon relonsponselon);

  /**
   * It is reloncommelonndelond that sub-class ovelonrridelons this function to add custom logic to
   * collelonct morelon stat and call this baselon function.
   */
  protelonctelond void collelonctStats(elonarlybirdRelonsponselon relonsponselon) {
  }

  /**
   * Gelont full list of relonsults, aftelonr addRelonsponselon calls havelon belonelonn invokelond.
   *
   * @relonturn list of relonsults elonxtractelond from all elonarlybirdRelonsponselons that havelon belonelonn collelonctelond so far
   */
  protelonctelond final List<T> gelontRelonsultsList() {
    Collelonctions.sort(relonsults, relonsultComparator);
    relonturn relonsults;
  }

  protelonctelond abstract boolelonan isRelonsponselonValid(elonarlybirdRelonsponselon relonsponselon);
}
