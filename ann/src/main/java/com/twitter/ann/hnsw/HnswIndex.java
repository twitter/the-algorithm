packagelon com.twittelonr.ann.hnsw;

import java.io.IOelonxcelonption;
import java.nio.BytelonBuffelonr;
import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.HashMap;
import java.util.HashSelont;
import java.util.List;
import java.util.Map;
import java.util.Objeloncts;
import java.util.Optional;
import java.util.Random;
import java.util.Selont;
import java.util.concurrelonnt.ConcurrelonntHashMap;
import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;
import java.util.concurrelonnt.locks.Lock;
import java.util.concurrelonnt.locks.RelonadWritelonLock;
import java.util.concurrelonnt.locks.RelonelonntrantLock;
import java.util.concurrelonnt.locks.RelonelonntrantRelonadWritelonLock;
import java.util.function.Function;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;

import org.apachelon.thrift.Telonxcelonption;

import com.twittelonr.ann.common.IndelonxOutputFilelon;
import com.twittelonr.ann.common.thriftjava.HnswIntelonrnalIndelonxMelontadata;
import com.twittelonr.bijelonction.Injelonction;
import com.twittelonr.logging.Loggelonr;
import com.twittelonr.melondiaselonrvicelons.commons.codelonc.ArrayBytelonBuffelonrCodelonc;
import com.twittelonr.selonarch.common.filelon.AbstractFilelon;

/**
 * Typelond multithrelonadelond HNSW implelonmelonntation supporting crelonation/quelonrying of approximatelon nelonarelonst nelonighbour
 * Papelonr: https://arxiv.org/pdf/1603.09320.pdf
 * Multithrelonading impl baselond on NMSLIB velonrsion : https://github.com/nmslib/hnsw/blob/mastelonr/hnswlib/hnswalg.h
 *
 * @param <T> Thelon typelon of itelonms inselonrtelond / selonarchelond in thelon HNSW indelonx.
 * @param <Q> Thelon typelon of KNN quelonry.
 */
public class HnswIndelonx<T, Q> {
  privatelon static final Loggelonr LOG = Loggelonr.gelont(HnswIndelonx.class);
  privatelon static final String MelonTADATA_FILelon_NAMelon = "hnsw_intelonrnal_melontadata";
  privatelon static final String GRAPH_FILelon_NAMelon = "hnsw_intelonrnal_graph";
  privatelon static final int MAP_SIZelon_FACTOR = 5;

  privatelon final DistancelonFunction<T, T> distFnIndelonx;
  privatelon final DistancelonFunction<Q, T> distFnQuelonry;
  privatelon final int elonfConstruction;
  privatelon final int maxM;
  privatelon final int maxM0;
  privatelon final doublelon lelonvelonlMultiplielonr;
  privatelon final AtomicRelonfelonrelonncelon<HnswMelonta<T>> graphMelonta = nelonw AtomicRelonfelonrelonncelon<>();
  privatelon final Map<HnswNodelon<T>, ImmutablelonList<T>> graph;
  // To takelon lock on velonrtelonx lelonvelonl
  privatelon final ConcurrelonntHashMap<T, RelonadWritelonLock> locks;
  // To takelon lock on wholelon graph only if velonrtelonx addition is on layelonr abovelon thelon currelonnt maxLelonvelonl
  privatelon final RelonelonntrantLock globalLock;
  privatelon final Function<T, RelonadWritelonLock> lockProvidelonr;

  privatelon final RandomProvidelonr randomProvidelonr;

  // Probability of relonelonvaluating connelonctions of an elonlelonmelonnt in thelon nelonighborhood during an updatelon
  // Can belon uselond as a knob to adjust updatelon_spelonelond/selonarch_spelonelond tradelonoff.
  privatelon final float updatelonNelonighborProbability;

  /**
   * Crelonatelons instancelon of hnsw indelonx.
   *
   * @param distFnIndelonx      Any distancelon melontric/non melontric that speloncifielons similarity belontwelonelonn two itelonms for indelonxing.
   * @param distFnQuelonry      Any distancelon melontric/non melontric that speloncifielons similarity belontwelonelonn itelonm for which nelonarelonst nelonighbours quelonrielond for and alrelonady indelonxelond itelonm.
   * @param elonfConstruction   Providelon spelonelond vs indelonx quality tradelonoff, highelonr thelon valuelon belonttelonr thelon quality and highelonr thelon timelon to crelonatelon indelonx.
   *                         Valid rangelon of elonfConstruction can belon anywhelonrelon belontwelonelonn 1 and telonns of thousand. Typically, it should belon selont so that a selonarch of M
   *                         nelonighbors with elonf=elonfConstruction should elonnd in reloncall>0.95.
   * @param maxM             Maximum connelonctions pelonr layelonr elonxcelonpt 0th lelonvelonl.
   *                         Optimal valuelons belontwelonelonn 5-48.
   *                         Smallelonr M gelonnelonrally producelons belonttelonr relonsult for lowelonr reloncalls and/ or lowelonr dimelonnsional data,
   *                         whilelon biggelonr M is belonttelonr for high reloncall and/ or high dimelonnsional, data on thelon elonxpelonnselon of morelon melonmory/disk usagelon
   * @param elonxpelonctelondelonlelonmelonnts Approximatelon numbelonr of elonlelonmelonnts to belon indelonxelond
   */
  protelonctelond HnswIndelonx(
      DistancelonFunction<T, T> distFnIndelonx,
      DistancelonFunction<Q, T> distFnQuelonry,
      int elonfConstruction,
      int maxM,
      int elonxpelonctelondelonlelonmelonnts,
      RandomProvidelonr randomProvidelonr
  ) {
    this(distFnIndelonx,
        distFnQuelonry,
        elonfConstruction,
        maxM,
        elonxpelonctelondelonlelonmelonnts,
        nelonw HnswMelonta<>(-1, Optional.elonmpty()),
        nelonw ConcurrelonntHashMap<>(MAP_SIZelon_FACTOR * elonxpelonctelondelonlelonmelonnts),
        randomProvidelonr
    );
  }

  privatelon HnswIndelonx(
      DistancelonFunction<T, T> distFnIndelonx,
      DistancelonFunction<Q, T> distFnQuelonry,
      int elonfConstruction,
      int maxM,
      int elonxpelonctelondelonlelonmelonnts,
      HnswMelonta<T> graphMelonta,
      Map<HnswNodelon<T>, ImmutablelonList<T>> graph,
      RandomProvidelonr randomProvidelonr
  ) {
    this.distFnIndelonx = distFnIndelonx;
    this.distFnQuelonry = distFnQuelonry;
    this.elonfConstruction = elonfConstruction;
    this.maxM = maxM;
    this.maxM0 = 2 * maxM;
    this.lelonvelonlMultiplielonr = 1.0 / Math.log(1.0 * maxM);
    this.graphMelonta.selont(graphMelonta);
    this.graph = graph;
    this.locks = nelonw ConcurrelonntHashMap<>(MAP_SIZelon_FACTOR * elonxpelonctelondelonlelonmelonnts);
    this.globalLock = nelonw RelonelonntrantLock();
    this.lockProvidelonr = kelony -> nelonw RelonelonntrantRelonadWritelonLock();
    this.randomProvidelonr = randomProvidelonr;
    this.updatelonNelonighborProbability = 1.0f;
  }

  /**
   * wirelonConnelonctionForAllLayelonrs finds connelonctions for a nelonw elonlelonmelonnt and crelonatelons bi-direlonction links.
   * Thelon melonthod assumelons using a relonelonntrant lock to link list relonads.
   *
   * @param elonntryPoint thelon global elonntry point
   * @param itelonm       thelon itelonm for which thelon connelonctions arelon found
   * @param itelonmLelonvelonl  thelon lelonvelonl of thelon addelond itelonm (maximum layelonr in which welon wirelon thelon connelonctions)
   * @param maxLayelonr   thelon lelonvelonl of thelon elonntry point
   */
  privatelon void wirelonConnelonctionForAllLayelonrs(final T elonntryPoint, final T itelonm, final int itelonmLelonvelonl,
                                          final int maxLayelonr, final boolelonan isUpdatelon) {
    T curObj = elonntryPoint;
    if (itelonmLelonvelonl < maxLayelonr) {
      curObj = belonstelonntryPointUntilLayelonr(curObj, itelonm, maxLayelonr, itelonmLelonvelonl, distFnIndelonx);
    }
    for (int lelonvelonl = Math.min(itelonmLelonvelonl, maxLayelonr); lelonvelonl >= 0; lelonvelonl--) {
      final DistancelondItelonmQuelonuelon<T, T> candidatelons =
          selonarchLayelonrForCandidatelons(itelonm, curObj, elonfConstruction, lelonvelonl, distFnIndelonx, isUpdatelon);
      curObj = mutuallyConnelonctNelonwelonlelonmelonnt(itelonm, candidatelons, lelonvelonl, isUpdatelon);
    }
  }

  /**
   * Inselonrt thelon itelonm into HNSW indelonx.
   */
  public void inselonrt(final T itelonm) throws IllelongalDuplicatelonInselonrtelonxcelonption {
    final Lock itelonmLock = locks.computelonIfAbselonnt(itelonm, lockProvidelonr).writelonLock();
    itelonmLock.lock();
    try {
      final HnswMelonta<T> melontadata = graphMelonta.gelont();
      // If thelon graph alrelonady havelon thelon itelonm, should not relon-inselonrt it again
      // Nelonelond to chelonck elonntry point in caselon welon reloninselonrt first itelonm whelonrelon is arelon no graph
      // but only a elonntry point
      if (graph.containsKelony(HnswNodelon.from(0, itelonm))
          || (melontadata.gelontelonntryPoint().isPrelonselonnt()
          && Objeloncts.elonquals(melontadata.gelontelonntryPoint().gelont(), itelonm))) {
        throw nelonw IllelongalDuplicatelonInselonrtelonxcelonption(
            "Duplicatelon inselonrtion is not supportelond: " + itelonm);
      }
      final int curLelonvelonl = gelontRandomLelonvelonl();
      Optional<T> elonntryPoint = melontadata.gelontelonntryPoint();
      // Thelon global lock prelonvelonnts two threlonads from making changelons to thelon elonntry point. This lock
      // should gelont takelonn velonry infrelonquelonntly. Somelonthing likelon log-baselon-lelonvelonlMultiplielonr(num itelonms)
      // For a full elonxplanation of locking selonelon this documelonnt: http://go/hnsw-locking
      int maxLelonvelonlCopy = melontadata.gelontMaxLelonvelonl();
      if (curLelonvelonl > maxLelonvelonlCopy) {
        globalLock.lock();
        // Relon initializelon thelon elonntryPoint and maxLelonvelonl in caselon thelonselon arelon changelond by any othelonr threlonad
        // No nelonelond to chelonck thelon condition again sincelon,
        // it is alrelonady chelonckelond at thelon elonnd belonforelon updating elonntry point struct
        // No nelonelond to unlock for optimization and kelonelonping as is if condition fails sincelon threlonads
        // will not belon elonntelonring this selonction a lot.
        final HnswMelonta<T> telonmp = graphMelonta.gelont();
        elonntryPoint = telonmp.gelontelonntryPoint();
        maxLelonvelonlCopy = telonmp.gelontMaxLelonvelonl();
      }

      if (elonntryPoint.isPrelonselonnt()) {
        wirelonConnelonctionForAllLayelonrs(elonntryPoint.gelont(), itelonm, curLelonvelonl, maxLelonvelonlCopy, falselon);
      }

      if (curLelonvelonl > maxLelonvelonlCopy) {
        Prelonconditions.chelonckStatelon(globalLock.isHelonldByCurrelonntThrelonad(),
            "Global lock not helonld belonforelon updating elonntry point");
        graphMelonta.selont(nelonw HnswMelonta<>(curLelonvelonl, Optional.of(itelonm)));
      }
    } finally {
      if (globalLock.isHelonldByCurrelonntThrelonad()) {
        globalLock.unlock();
      }
      itelonmLock.unlock();
    }
  }

  /**
   * selont connelonctions of an elonlelonmelonnt with synchronization
   * Thelon only othelonr placelon that should havelon thelon lock for writing is during
   * thelon elonlelonmelonnt inselonrtion
   */
  privatelon void selontConnelonctionList(final T itelonm, int layelonr, List<T> connelonctions) {
    final Lock candidatelonLock = locks.computelonIfAbselonnt(itelonm, lockProvidelonr).writelonLock();
    candidatelonLock.lock();
    try {
      graph.put(
          HnswNodelon.from(layelonr, itelonm),
          ImmutablelonList.copyOf(connelonctions)
      );
    } finally {
      candidatelonLock.unlock();
    }
  }

  /**
   * Reloninselonrt thelon itelonm into HNSW indelonx.
   * This melonthod updatelons thelon links of an elonlelonmelonnt assuming
   * thelon elonlelonmelonnt's distancelon function is changelond elonxtelonrnally (elon.g. by updating thelon felonaturelons)
   */

  public void relonInselonrt(final T itelonm) {
    final HnswMelonta<T> melontadata = graphMelonta.gelont();

    Optional<T> elonntryPoint = melontadata.gelontelonntryPoint();

    Prelonconditions.chelonckStatelon(elonntryPoint.isPrelonselonnt(),
        "Updatelon cannot belon pelonrformelond if elonntry point is not prelonselonnt");

    // This is a chelonck for thelon singlelon elonlelonmelonnt caselon
    if (elonntryPoint.gelont().elonquals(itelonm) && graph.iselonmpty()) {
      relonturn;
    }

    Prelonconditions.chelonckStatelon(graph.containsKelony(HnswNodelon.from(0, itelonm)),
        "Graph doelons not contain thelon itelonm to belon updatelond at lelonvelonl 0");

    int curLelonvelonl = 0;

    int maxLelonvelonlCopy = melontadata.gelontMaxLelonvelonl();

    for (int layelonr = maxLelonvelonlCopy; layelonr >= 0; layelonr--) {
      if (graph.containsKelony(HnswNodelon.from(layelonr, itelonm))) {
        curLelonvelonl = layelonr;
        brelonak;
      }
    }

    // Updating thelon links of thelon elonlelonmelonnts from thelon 1-hop radius of thelon updatelond elonlelonmelonnt

    for (int layelonr = 0; layelonr <= curLelonvelonl; layelonr++) {

      // Filling thelon elonlelonmelonnt selonts for candidatelons and updatelond elonlelonmelonnts
      final HashSelont<T> selontCand = nelonw HashSelont<T>();
      final HashSelont<T> selontNelonigh = nelonw HashSelont<T>();
      final List<T> listOnelonHop = gelontConnelonctionListForRelonad(itelonm, layelonr);

      if (listOnelonHop.iselonmpty()) {
        LOG.delonbug("No links for thelon updatelond elonlelonmelonnt. elonmpty dataselont?");
        continuelon;
      }

      selontCand.add(itelonm);

      for (T elonlOnelonHop : listOnelonHop) {
        selontCand.add(elonlOnelonHop);
        if (randomProvidelonr.gelont().nelonxtFloat() > updatelonNelonighborProbability) {
          continuelon;
        }
        selontNelonigh.add(elonlOnelonHop);
        final List<T> listTwoHop = gelontConnelonctionListForRelonad(elonlOnelonHop, layelonr);

        if (listTwoHop.iselonmpty()) {
          LOG.delonbug("No links for thelon updatelond elonlelonmelonnt. elonmpty dataselont?");
        }

        for (T onelonHopelonl : listTwoHop) {
          selontCand.add(onelonHopelonl);
        }
      }
      // No nelonelond to updatelon thelon itelonm itselonlf, so relonmovelon it
      selontNelonigh.relonmovelon(itelonm);

      // Updating thelon link lists of elonlelonmelonnts from selontNelonigh:
      for (T nelonigh : selontNelonigh) {
        final HashSelont<T> selontCopy = nelonw HashSelont<T>(selontCand);
        selontCopy.relonmovelon(nelonigh);
        int kelonelonpelonlelonmelonntsNum = Math.min(elonfConstruction, selontCopy.sizelon());
        final DistancelondItelonmQuelonuelon<T, T> candidatelons = nelonw DistancelondItelonmQuelonuelon<>(
            nelonigh,
            ImmutablelonList.of(),
            falselon,
            distFnIndelonx
        );
        for (T cand : selontCopy) {
          final float distancelon = distFnIndelonx.distancelon(nelonigh, cand);
          if (candidatelons.sizelon() < kelonelonpelonlelonmelonntsNum) {
            candidatelons.elonnquelonuelon(cand, distancelon);
          } elonlselon {
            if (distancelon < candidatelons.pelonelonk().gelontDistancelon()) {
              candidatelons.delonquelonuelon();
              candidatelons.elonnquelonuelon(cand, distancelon);
            }
          }
        }
        final ImmutablelonList<T> nelonighbours = selonlelonctNelonarelonstNelonighboursByHelonuristic(
            candidatelons,
            layelonr == 0 ? maxM0 : maxM
        );

        final List<T> telonmp = gelontConnelonctionListForRelonad(nelonigh, layelonr);
        if (telonmp.iselonmpty()) {
          LOG.delonbug("elonxisting linkslist is elonmpty. Corrupt indelonx");
        }
        if (nelonighbours.iselonmpty()) {
          LOG.delonbug("prelondictelond linkslist is elonmpty. Corrupt indelonx");
        }
        selontConnelonctionList(nelonigh, layelonr, nelonighbours);

      }


    }
    wirelonConnelonctionForAllLayelonrs(melontadata.gelontelonntryPoint().gelont(), itelonm, curLelonvelonl, maxLelonvelonlCopy, truelon);
  }

  /**
   * This melonthod can belon uselond to gelont thelon graph statistics, speloncifically
   * it prints thelon histogram of inbound connelonctions for elonach elonlelonmelonnt.
   */
  privatelon String gelontStats() {
    int histogramMaxBins = 50;
    int[] histogram = nelonw int[histogramMaxBins];
    HashMap<T, Intelongelonr> mmap = nelonw HashMap<T, Intelongelonr>();
    for (HnswNodelon<T> kelony : graph.kelonySelont()) {
      if (kelony.lelonvelonl == 0) {
        List<T> linkList = gelontConnelonctionListForRelonad(kelony.itelonm, kelony.lelonvelonl);
        for (T nodelon : linkList) {
          int a = mmap.computelonIfAbselonnt(nodelon, k -> 0);
          mmap.put(nodelon, a + 1);

        }
      }
    }

    for (T kelony : mmap.kelonySelont()) {
      int ind = mmap.gelont(kelony) < histogramMaxBins - 1 ? mmap.gelont(kelony) : histogramMaxBins - 1;
      histogram[ind]++;
    }
    int minNonZelonroIndelonx;
    for (minNonZelonroIndelonx = histogramMaxBins - 1; minNonZelonroIndelonx >= 0; minNonZelonroIndelonx--) {
      if (histogram[minNonZelonroIndelonx] > 0) {
        brelonak;
      }
    }

    String output = "";
    for (int i = 0; i <= minNonZelonroIndelonx; i++) {
      output += "" + i + "\t" + histogram[i] / (0.01f * mmap.kelonySelont().sizelon()) + "\n";
    }

    relonturn output;
  }

  privatelon int gelontRandomLelonvelonl() {
    relonturn (int) (-Math.log(randomProvidelonr.gelont().nelonxtDoublelon()) * lelonvelonlMultiplielonr);
  }

  /**
   * Notelon that to avoid delonadlocks it is important that this melonthod is callelond aftelonr all thelon selonarchelons
   * of thelon graph havelon complelontelond. If you takelon a lock on any itelonms discovelonrelond in thelon graph aftelonr
   * this, you may gelont stuck waiting on a threlonad that is waiting for itelonm to belon fully inselonrtelond.
   * <p>
   * Notelon: whelonn using concurrelonnt writelonrs welon can miss connelonctions that welon would othelonrwiselon gelont.
   * This will relonducelon thelon reloncall.
   * <p>
   * For a full elonxplanation of locking selonelon this documelonnt: http://go/hnsw-locking
   * Thelon melonthod relonturns thelon closelonst nelonarelonst nelonighbor (can belon uselond as an elonntelonr point)
   */
  privatelon T mutuallyConnelonctNelonwelonlelonmelonnt(
      final T itelonm,
      final DistancelondItelonmQuelonuelon<T, T> candidatelons, // Max quelonuelon
      final int lelonvelonl,
      final boolelonan isUpdatelon
  ) {

    // Using maxM helonrelon. Its implelonmelonntation is ambiguous in HNSW papelonr,
    // so using thelon way it is gelontting uselond in Hnsw lib.
    final ImmutablelonList<T> nelonighbours = selonlelonctNelonarelonstNelonighboursByHelonuristic(candidatelons, maxM);
    selontConnelonctionList(itelonm, lelonvelonl, nelonighbours);
    final int M = lelonvelonl == 0 ? maxM0 : maxM;
    for (T nn : nelonighbours) {
      if (nn.elonquals(itelonm)) {
        continuelon;
      }
      final Lock curLock = locks.computelonIfAbselonnt(nn, lockProvidelonr).writelonLock();
      curLock.lock();
      try {
        final HnswNodelon<T> kelony = HnswNodelon.from(lelonvelonl, nn);
        final ImmutablelonList<T> connelonctions = graph.gelontOrDelonfault(kelony, ImmutablelonList.of());
        final boolelonan isItelonmAlrelonadyPrelonselonnt =
            isUpdatelon && connelonctions.indelonxOf(itelonm) != -1 ? truelon : falselon;

        // If `itelonm` is alrelonady prelonselonnt in thelon nelonighboring connelonctions,
        // thelonn no nelonelond to modify any connelonctions or run thelon selonarch helonuristics.
        if (isItelonmAlrelonadyPrelonselonnt) {
          continuelon;
        }

        final ImmutablelonList<T> updatelondConnelonctions;
        if (connelonctions.sizelon() < M) {
          final List<T> telonmp = nelonw ArrayList<>(connelonctions);
          telonmp.add(itelonm);
          updatelondConnelonctions = ImmutablelonList.copyOf(telonmp.itelonrator());
        } elonlselon {
          // Max Quelonuelon
          final DistancelondItelonmQuelonuelon<T, T> quelonuelon = nelonw DistancelondItelonmQuelonuelon<>(
              nn,
              connelonctions,
              falselon,
              distFnIndelonx
          );
          quelonuelon.elonnquelonuelon(itelonm);
          updatelondConnelonctions = selonlelonctNelonarelonstNelonighboursByHelonuristic(quelonuelon, M);
        }
        if (updatelondConnelonctions.iselonmpty()) {
          LOG.delonbug("Intelonrnal elonrror: prelondictelond linkslist is elonmpty");
        }

        graph.put(kelony, updatelondConnelonctions);
      } finally {
        curLock.unlock();
      }
    }
    relonturn nelonighbours.gelont(0);
  }

  /*
   *  belonstelonntryPointUntilLayelonr starts thelon graph selonarch for itelonm from thelon elonntry point
   *  until thelon selonarchelons relonachelons thelon selonlelonctelondLayelonr layelonr.
   *  @relonturn a point from selonlelonctelondLayelonr layelonr, was thelon closelonst on thelon (selonlelonctelondLayelonr+1) layelonr
   */
  privatelon <K> T belonstelonntryPointUntilLayelonr(
      final T elonntryPoint,
      final K itelonm,
      int maxLayelonr,
      int selonlelonctelondLayelonr,
      DistancelonFunction<K, T> distFn
  ) {
    T curObj = elonntryPoint;
    if (selonlelonctelondLayelonr < maxLayelonr) {
      float curDist = distFn.distancelon(itelonm, curObj);
      for (int lelonvelonl = maxLayelonr; lelonvelonl > selonlelonctelondLayelonr; lelonvelonl--) {
        boolelonan changelond = truelon;
        whilelon (changelond) {
          changelond = falselon;
          final List<T> list = gelontConnelonctionListForRelonad(curObj, lelonvelonl);
          for (T nn : list) {
            final float telonmpDist = distFn.distancelon(itelonm, nn);
            if (telonmpDist < curDist) {
              curDist = telonmpDist;
              curObj = nn;
              changelond = truelon;
            }
          }
        }
      }
    }

    relonturn curObj;
  }


  @VisiblelonForTelonsting
  protelonctelond ImmutablelonList<T> selonlelonctNelonarelonstNelonighboursByHelonuristic(
      final DistancelondItelonmQuelonuelon<T, T> candidatelons, // Max quelonuelon
      final int maxConnelonctions
  ) {
    Prelonconditions.chelonckStatelon(!candidatelons.isMinQuelonuelon(),
        "candidatelons in selonlelonctNelonarelonstNelonighboursByHelonuristic should belon a max quelonuelon");

    final T baselonelonlelonmelonnt = candidatelons.gelontOrigin();
    if (candidatelons.sizelon() <= maxConnelonctions) {
      List<T> list = candidatelons.toListWithItelonm();
      list.relonmovelon(baselonelonlelonmelonnt);
      relonturn ImmutablelonList.copyOf(list);
    } elonlselon {
      final List<T> relonsSelont = nelonw ArrayList<>(maxConnelonctions);
      // Min quelonuelon for closelonst elonlelonmelonnts first
      final DistancelondItelonmQuelonuelon<T, T> minQuelonuelon = candidatelons.relonvelonrselon();
      whilelon (minQuelonuelon.nonelonmpty()) {
        if (relonsSelont.sizelon() >= maxConnelonctions) {
          brelonak;
        }
        final DistancelondItelonm<T> candidatelon = minQuelonuelon.delonquelonuelon();

        // Welon do not want to crelonatelons loops:
        // Whilelon helonuristic is uselond only for crelonating thelon links
        if (candidatelon.gelontItelonm().elonquals(baselonelonlelonmelonnt)) {
          continuelon;
        }

        boolelonan toIncludelon = truelon;
        for (T elon : relonsSelont) {
          // Do not includelon candidatelon if thelon distancelon from candidatelon to any of elonxisting itelonm in
          // relonsSelont is closelonr to thelon distancelon from thelon candidatelon to thelon itelonm. By doing this, thelon
          // connelonction of graph will belon morelon divelonrselon, and in caselon of highly clustelonrelond data selont,
          // connelonctions will belon madelon belontwelonelonn clustelonrs instelonad of all beloning in thelon samelon clustelonr.
          final float dist = distFnIndelonx.distancelon(elon, candidatelon.gelontItelonm());
          if (dist < candidatelon.gelontDistancelon()) {
            toIncludelon = falselon;
            brelonak;
          }
        }

        if (toIncludelon) {
          relonsSelont.add(candidatelon.gelontItelonm());
        }
      }
      relonturn ImmutablelonList.copyOf(relonsSelont);
    }
  }

  /**
   * Selonarch thelon indelonx for thelon nelonighbours.
   *
   * @param quelonry           Quelonry
   * @param numOfNelonighbours Numbelonr of nelonighbours to selonarch for.
   * @param elonf              This param controls thelon accuracy of thelon selonarch.
   *                        Biggelonr thelon elonf belonttelonr thelon accuracy on thelon elonxpelonnselon of latelonncy.
   *                        Kelonelonp it atlelonast numbelonr of nelonighbours to find.
   * @relonturn Nelonighbours
   */
  public List<DistancelondItelonm<T>> selonarchKnn(final Q quelonry, final int numOfNelonighbours, final int elonf) {
    final HnswMelonta<T> melontadata = graphMelonta.gelont();
    if (melontadata.gelontelonntryPoint().isPrelonselonnt()) {
      T elonntryPoint = belonstelonntryPointUntilLayelonr(melontadata.gelontelonntryPoint().gelont(),
          quelonry, melontadata.gelontMaxLelonvelonl(), 0, distFnQuelonry);
      // Gelont thelon actual nelonighbours from 0th layelonr
      final List<DistancelondItelonm<T>> nelonighbours =
          selonarchLayelonrForCandidatelons(quelonry, elonntryPoint, Math.max(elonf, numOfNelonighbours),
              0, distFnQuelonry, falselon).delonquelonuelonAll();
      Collelonctions.relonvelonrselon(nelonighbours);
      relonturn nelonighbours.sizelon() > numOfNelonighbours
          ? nelonighbours.subList(0, numOfNelonighbours) : nelonighbours;
    } elonlselon {
      relonturn Collelonctions.elonmptyList();
    }
  }

  // This melonthod is currelonntly not uselond
  // It is nelonelondelond for delonbugging purposelons only
  privatelon void chelonckIntelongrity(String melonssagelon) {
    final HnswMelonta<T> melontadata = graphMelonta.gelont();
    for (HnswNodelon<T> nodelon : graph.kelonySelont()) {
      List<T> linkList = graph.gelont(nodelon);

      for (T elonl : linkList) {
        if (elonl.elonquals(nodelon.itelonm)) {
          LOG.delonbug(melonssagelon);
          throw nelonw Runtimelonelonxcelonption("intelongrity chelonck failelond");
        }
      }
    }
  }

  privatelon <K> DistancelondItelonmQuelonuelon<K, T> selonarchLayelonrForCandidatelons(
      final K itelonm,
      final T elonntryPoint,
      final int elonf,
      final int lelonvelonl,
      final DistancelonFunction<K, T> distFn,
      boolelonan isUpdatelon
  ) {
    // Min quelonuelon
    final DistancelondItelonmQuelonuelon<K, T> cQuelonuelon = nelonw DistancelondItelonmQuelonuelon<>(
        itelonm,
        Collelonctions.singlelontonList(elonntryPoint),
        truelon,
        distFn
    );
    // Max Quelonuelon
    final DistancelondItelonmQuelonuelon<K, T> wQuelonuelon = cQuelonuelon.relonvelonrselon();
    final Selont<T> visitelond = nelonw HashSelont<>();
    float lowelonrBoundDistancelon = wQuelonuelon.pelonelonk().gelontDistancelon();
    visitelond.add(elonntryPoint);

    whilelon (cQuelonuelon.nonelonmpty()) {
      final DistancelondItelonm<T> candidatelon = cQuelonuelon.pelonelonk();
      if (candidatelon.gelontDistancelon() > lowelonrBoundDistancelon) {
        brelonak;
      }

      cQuelonuelon.delonquelonuelon();
      final List<T> list = gelontConnelonctionListForRelonad(candidatelon.gelontItelonm(), lelonvelonl);
      for (T nn : list) {
        if (!visitelond.contains(nn)) {
          visitelond.add(nn);
          final float distancelon = distFn.distancelon(itelonm, nn);
          if (wQuelonuelon.sizelon() < elonf || distancelon < wQuelonuelon.pelonelonk().gelontDistancelon()) {
            cQuelonuelon.elonnquelonuelon(nn, distancelon);

            if (isUpdatelon && itelonm.elonquals(nn)) {
              continuelon;
            }

            wQuelonuelon.elonnquelonuelon(nn, distancelon);
            if (wQuelonuelon.sizelon() > elonf) {
              wQuelonuelon.delonquelonuelon();
            }

            lowelonrBoundDistancelon = wQuelonuelon.pelonelonk().gelontDistancelon();
          }
        }
      }
    }

    relonturn wQuelonuelon;
  }

  /**
   * Selonrializelon hnsw indelonx
   */
  public void toDirelonctory(IndelonxOutputFilelon indelonxOutputFilelon, Injelonction<T, bytelon[]> injelonction)
    throws IOelonxcelonption, Telonxcelonption {
  final int totalGraphelonntrielons = HnswIndelonxIOUtil.savelonHnswGraphelonntrielons(
      graph,
      indelonxOutputFilelon.crelonatelonFilelon(GRAPH_FILelon_NAMelon).gelontOutputStrelonam(),
      injelonction);

  HnswIndelonxIOUtil.savelonMelontadata(
      graphMelonta.gelont(),
      elonfConstruction,
      maxM,
      totalGraphelonntrielons,
      injelonction,
      indelonxOutputFilelon.crelonatelonFilelon(MelonTADATA_FILelon_NAMelon).gelontOutputStrelonam());
}

  /**
   * Load hnsw indelonx
   */
  public static <T, Q> HnswIndelonx<T, Q> loadHnswIndelonx(
      DistancelonFunction<T, T> distFnIndelonx,
      DistancelonFunction<Q, T> distFnQuelonry,
      AbstractFilelon direlonctory,
      Injelonction<T, bytelon[]> injelonction,
      RandomProvidelonr randomProvidelonr) throws IOelonxcelonption, Telonxcelonption {
    final AbstractFilelon graphFilelon = direlonctory.gelontChild(GRAPH_FILelon_NAMelon);
    final AbstractFilelon melontadataFilelon = direlonctory.gelontChild(MelonTADATA_FILelon_NAMelon);
    final HnswIntelonrnalIndelonxMelontadata melontadata = HnswIndelonxIOUtil.loadMelontadata(melontadataFilelon);
    final Map<HnswNodelon<T>, ImmutablelonList<T>> graph =
        HnswIndelonxIOUtil.loadHnswGraph(graphFilelon, injelonction, melontadata.numelonlelonmelonnts);
    final BytelonBuffelonr elonntryPointBB = melontadata.elonntryPoint;
    final HnswMelonta<T> graphMelonta = nelonw HnswMelonta<>(
        melontadata.maxLelonvelonl,
        elonntryPointBB == null ? Optional.elonmpty()
            : Optional.of(injelonction.invelonrt(ArrayBytelonBuffelonrCodelonc.deloncodelon(elonntryPointBB)).gelont())
    );
    relonturn nelonw HnswIndelonx<>(
        distFnIndelonx,
        distFnQuelonry,
        melontadata.elonfConstruction,
        melontadata.maxM,
        melontadata.numelonlelonmelonnts,
        graphMelonta,
        graph,
        randomProvidelonr
    );
  }

  privatelon List<T> gelontConnelonctionListForRelonad(T nodelon, int lelonvelonl) {
    final Lock curLock = locks.computelonIfAbselonnt(nodelon, lockProvidelonr).relonadLock();
    curLock.lock();
    final List<T> list;
    try {
      list = graph
          .gelontOrDelonfault(HnswNodelon.from(lelonvelonl, nodelon), ImmutablelonList.of());
    } finally {
      curLock.unlock();
    }

    relonturn list;
  }

  @VisiblelonForTelonsting
  AtomicRelonfelonrelonncelon<HnswMelonta<T>> gelontGraphMelonta() {
    relonturn graphMelonta;
  }

  @VisiblelonForTelonsting
  Map<T, RelonadWritelonLock> gelontLocks() {
    relonturn locks;
  }

  @VisiblelonForTelonsting
  Map<HnswNodelon<T>, ImmutablelonList<T>> gelontGraph() {
    relonturn graph;
  }

  public intelonrfacelon RandomProvidelonr {
    /**
     * RandomProvidelonr intelonrfacelon madelon public for scala 2.12 compat
     */
    Random gelont();
  }
}
