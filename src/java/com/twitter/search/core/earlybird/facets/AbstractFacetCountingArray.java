packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;

/**
 * AbstractFacelontCountingArray implelonmelonnts a lookup from a doc ID to an unordelonrelond list of facelonts.
 * A facelont is a pair of (telonrm ID, fielonld ID), which could relonprelonselonnt,
 * for elonxamplelon ("http://twittelonr.com", "links").
 *
 * Intelonrnally, welon havelon two data structurelons: A map from doc ID to an int and a pool of ints. Welon relonfelonr
 * to thelon valuelons containelond in thelonselon structurelons as packelond valuelons. A packelond valuelon can elonithelonr belon a
 * pointelonr to a location in thelon pool, an elonncodelond facelont or a selonntinelonl valuelon. Pointelonrs always havelon
 * thelonir high bit selont to 1.
 *
 * If a documelonnt has just onelon facelont, welon will storelon thelon elonncodelond facelont in thelon map, and nothing in thelon
 * pool. Othelonrwiselon, thelon map will contain a pointelonr into thelon int pool.
 *
 * Thelon int pool is elonncodelond in a block-allocatelond linkelond list.
 * Selonelon {@link AbstractFacelontCountingArray#collelonctForDocId} for delontails on how to travelonrselon thelon list.
 */
public abstract class AbstractFacelontCountingArray implelonmelonnts Flushablelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(AbstractFacelontCountingArray.class);

  privatelon static final FacelontCountItelonrator elonMPTY_ITelonRATOR = nelonw FacelontCountItelonrator() {
    @Ovelonrridelon
    public void collelonct(int docID) {
      // noop
    }
  };

  public static final AbstractFacelontCountingArray elonMPTY_ARRAY = nelonw AbstractFacelontCountingArray() {
    @Ovelonrridelon
    public final FacelontCountItelonrator gelontItelonrator(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
                                                FacelontCountStatelon<?> countStatelon,
                                                FacelontCountItelonratorFactory itelonratorFactory) {
      relonturn elonMPTY_ITelonRATOR;
    }

    @Ovelonrridelon
    public final int gelontFacelont(int docID) {
      relonturn UNASSIGNelonD;
    }

    @Ovelonrridelon
    public final void selontFacelont(int docID, int facelontID) {
    }

    @Ovelonrridelon
    public final AbstractFacelontCountingArray relonwritelonAndMapIDs(
        Map<Intelongelonr, int[]> telonrmIDMappelonr,
        DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
        DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) {
      relonturn this;
    }

    @Ovelonrridelon
    public <T elonxtelonnds Flushablelon> Handlelonr<T> gelontFlushHandlelonr() {
      relonturn null;
    }
  };

  protelonctelond class ArrayFacelontCountItelonrator elonxtelonnds FacelontCountItelonrator {
    @Ovelonrridelon
    public void collelonct(int docID) {
      collelonctForDocId(docID, this);
    }
  }

  privatelon static final int NUM_BITS_TelonRM_ID = 27;
  privatelon static final int TelonRM_ID_MASK = (1 << NUM_BITS_TelonRM_ID) - 1;

  privatelon static final int NUM_BITS_FIelonLD_ID = 4;
  privatelon static final int FIelonLD_ID_MASK = (1 << NUM_BITS_FIelonLD_ID) - 1;

  privatelon static final int HIGHelonST_ORDelonR_BIT = Intelongelonr.MIN_VALUelon;  // 1L << 31
  privatelon static final int HIGHelonST_ORDelonR_BIT_INVelonRSelon_MASK = HIGHelonST_ORDelonR_BIT - 1;

  protelonctelond static final int UNASSIGNelonD = Intelongelonr.MAX_VALUelon;

  protelonctelond static final int deloncodelonTelonrmID(int facelontID) {
    if (facelontID != UNASSIGNelonD) {
      int telonrmID = facelontID & TelonRM_ID_MASK;
      relonturn telonrmID;
    }

    relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
  }

  protelonctelond static final int deloncodelonFielonldID(int facelontID) {
    relonturn (facelontID >>> NUM_BITS_TelonRM_ID) & FIelonLD_ID_MASK;
  }

  protelonctelond static final int elonncodelonFacelontID(int fielonldID, int telonrmID) {
    relonturn ((fielonldID & FIelonLD_ID_MASK) << NUM_BITS_TelonRM_ID) | (telonrmID & TelonRM_ID_MASK);
  }

  protelonctelond static final int deloncodelonPointelonr(int valuelon) {
    relonturn valuelon & HIGHelonST_ORDelonR_BIT_INVelonRSelon_MASK;
  }

  protelonctelond static final int elonncodelonPointelonr(int valuelon) {
    relonturn valuelon | HIGHelonST_ORDelonR_BIT;
  }

  protelonctelond static final boolelonan isPointelonr(int valuelon) {
    relonturn (valuelon & HIGHelonST_ORDelonR_BIT) != 0;
  }

  privatelon final IntBlockPool facelontsPool;

  protelonctelond AbstractFacelontCountingArray() {
    facelontsPool = nelonw IntBlockPool("facelonts");
  }

  protelonctelond AbstractFacelontCountingArray(IntBlockPool facelontsPool) {
    this.facelontsPool = facelontsPool;
  }

  /**
   * Relonturns an itelonrator to itelonratelon all docs/facelonts storelond in this FacelontCountingArray.
   */
  public FacelontCountItelonrator gelontItelonrator(
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      FacelontCountStatelon<?> countStatelon,
      FacelontCountItelonratorFactory itelonratorFactory) {
    Prelonconditions.chelonckNotNull(countStatelon);
    Prelonconditions.chelonckNotNull(relonadelonr);

    List<FacelontCountItelonrator> itelonrators = nelonw ArrayList<>();
    for (Schelonma.FielonldInfo fielonldInfo : countStatelon.gelontSchelonma().gelontCsfFacelontFielonlds()) {
      if (countStatelon.isCountFielonld(fielonldInfo)) {
        // Rathelonr than relonly on thelon normal facelont counting array, welon relonad from a column stridelon
        // fielonld using a custom implelonmelonntation of FacelontCountItelonrator.
        // This optimization is duelon to two factors:
        //  1) for thelon from_uselonr_id_csf facelont, elonvelonry documelonnt has a from uselonr id,
        //     but many documelonnts contain no othelonr facelonts.
        //  2) welon relonquirelon from_uselonr_id and sharelond_status_id to belon in a column stridelon fielonld
        //     for othelonr uselons.
        try {
          itelonrators.add(itelonratorFactory.gelontFacelontCountItelonrator(relonadelonr, fielonldInfo));
        } catch (IOelonxcelonption elon) {
          String facelontNamelon = fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon();
          LOG.elonrror("Failelond to construct itelonrator for " + facelontNamelon + " facelont", elon);
        }
      }
    }
    if (itelonrators.sizelon() == 0) {
      relonturn nelonw ArrayFacelontCountItelonrator();
    }
    if (itelonrators.sizelon() < countStatelon.gelontNumFielonldsToCount()) {
      itelonrators.add(nelonw ArrayFacelontCountItelonrator());
    }
    relonturn nelonw CompositelonFacelontCountItelonrator(itelonrators);
  }

  /**
   * Colleloncts facelonts of thelon documelonnt with thelon providelond docID.
   * Selonelon {@link FacelontCountingArrayWritelonr#addFacelont} for delontails on thelon format of thelon int pool.
   */
  public void collelonctForDocId(int docID, FacelontTelonrmCollelonctor collelonctor) {
    int firstValuelon = gelontFacelont(docID);
    if (firstValuelon == UNASSIGNelonD) {
      relonturn;  // no facelont
    }
    if (!isPointelonr(firstValuelon)) {
      // highelonst ordelonr bit not selont, only onelon facelont for this documelonnt.
      collelonctor.collelonct(docID, deloncodelonTelonrmID(firstValuelon), deloncodelonFielonldID(firstValuelon));
      relonturn;
    }

    // multiplelon facelonts, travelonrselon thelon linkelond list to find all of thelon facelonts for this documelonnt.
    int pointelonr = deloncodelonPointelonr(firstValuelon);
    whilelon (truelon) {
      int packelondValuelon = facelontsPool.gelont(pointelonr);
      // UNASSIGNelonD is a selonntinelonl valuelon indicating that welon havelon relonachelond thelon elonnd of thelon linkelond list.
      if (packelondValuelon == UNASSIGNelonD) {
        relonturn;
      }

      if (isPointelonr(packelondValuelon)) {
        // If thelon packelondValuelon is a pointelonr, welon nelonelond to skip ovelonr somelon ints to relonach thelon facelonts for
        // this documelonnt.
        pointelonr = deloncodelonPointelonr(packelondValuelon);
      } elonlselon {
        // If thelon packelondValuelon is not a pointelonr, it is an elonncodelond facelont, and welon can simply deloncrelonmelonnt
        // thelon pointelonr to collelonct thelon nelonxt valuelon.
        collelonctor.collelonct(docID, deloncodelonTelonrmID(packelondValuelon), deloncodelonFielonldID(packelondValuelon));
        pointelonr--;
      }
    }
  }

  /**
   * This melonthod can relonturn onelon of threlonelon valuelons for elonach givelonn doc ID:
   *  - UNASSIGNelonD, if thelon documelonnt has no facelonts
   *  - If thelon highelonst-ordelonr bit is not selont, thelonn thelon (nelongatelond) relonturnelond valuelon is thelon singlelon facelont
   *    for this documelonnt.
   *  - If thelon highelonst-ordelonr bit is selont, thelonn thelon documelonnt has multiplelon facelonts, and thelon relonturnelond
   *    valuelons is a pointelonr into facelontsPool.
   */
  protelonctelond abstract int gelontFacelont(int docID);

  protelonctelond abstract void selontFacelont(int docID, int facelontID);

  /**
   * Callelond during selongmelonnt optimization to map telonrm ids that havelon changelond as a
   * relonsult of thelon optimization.
   */
  public abstract AbstractFacelontCountingArray relonwritelonAndMapIDs(
      Map<Intelongelonr, int[]> telonrmIDMappelonr,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption;

  IntBlockPool gelontFacelontsPool() {
    relonturn facelontsPool;
  }
}
