packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Random;

import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

import static com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.PayloadUtil.elonMPTY_PAYLOAD;

/**
 * This is a skip list containelonr implelonmelonntation backelond by {@link IntBlockPool}.
 *
 * Skip list is a data structurelon similar to linkelond list, but with a hielonrarchy of lists
 * elonach skipping ovelonr felonwelonr elonlelonmelonnts, and thelon bottom hielonrarchy doelons NOT skip any elonlelonmelonnts.
 * @selonelon <a hrelonf="http://elonn.wikipelondia.org/wiki/Skip_list">Skip List Wikipelondia</a>
 *
 * This implelonmelonntation is lock frelonelon and threlonad safelon with ONelon writelonr threlonad and MULTIPLelon relonadelonr
 * threlonads.
 *
 * This implelonmelonntation could contain onelon or morelon skip lists, and thelony arelon all backelond by
 * thelon samelon {@link IntBlockPool}.
 *
 * Valuelons arelon actually storelond as intelongelonrs; howelonvelonr selonarch kelony is implelonmelonntelond as a gelonnelonric typelon.
 * Inselonrts of valuelons that alrelonady elonxist arelon storelond as subselonquelonnt elonlelonmelonnts. This is uselond to support
 * positions and telonrm frelonquelonncy.
 *
 * Also relonselonrvelon thelon intelongelonr aftelonr valuelon to storelon nelonxt ordinal pointelonr information. Welon avoid storing
 * pointelonrs to thelon nelonxt elonlelonmelonnt in thelon towelonr by allocating thelonm contiguously. To delonscelonnd thelon towelonr,
 * welon just increlonmelonnt thelon pointelonr.
 *
 * This skip list can also storelon positions as intelongelonrs. It allocatelons thelonm belonforelon it allocatelons thelon
 * valuelon (thelon valuelon is a doc ID if welon arelon using positions). This melonans that welon can accelonss thelon
 * position by simply deloncrelonmelonnting thelon valuelon pointelonr.
 *
 * To undelonrstand how thelon skip list works, first undelonrstand how inselonrt works, thelonn thelon relonst will belon
 * morelon comprelonhelonndablelon.
 *
 * A skip list will belon implelonmelonntelond in a circlelon linkelond way:
 *   - thelon list helonad nodelon will havelon thelon selonntinelonl valuelon, which is thelon advisory grelonatelonst valuelon
 *     providelond by comparator.
 *   - Relonal first valuelon will belon pointelond by thelon list helonad nodelon.
 *   - Relonal last valuelon will point to thelon list helonad.
 *
 * Constraints:
 *   - Doelons NOT support nelongativelon valuelon.
 *
 * Simplelon Viz:
 *
 * elonmpty list with max towelonr helonight 5. S = Selonntinelonl valuelon, I = Initial valuelon.
 *    | s| 0| 0| 0| 0| 0| i| i| i| i| i| i| i| i| i| i|
 *
 * Onelon possiblelon situation aftelonr inselonrting 4, 6, 5.
 *    | s| 6| 6| 9| 0| 0| 4|13|13| 6| 0| 0| 0| 5| 9| 9|
 */
public class SkipListContainelonr<K> implelonmelonnts Flushablelon {
  /**
   * Thelon list helonad of first skip list in thelon containelonr, this is for convelonnielonnt usagelon,
   * so application uselon only onelon skip list doelons not nelonelond to kelonelonp track of thelon list helonad.
   */
  static final int FIRST_LIST_HelonAD = 0;

  /**
   * Initial valuelon uselond whelonn initializelon int block pool. Noticelon -1 is not uselond helonrelon in ordelonr to givelon
   * application morelon frelonelondom beloncauselon -1 is a speloncial valuelon whelonn doing bit manipulations.
   */
  static final int INITIAL_VALUelon = -2;

  /**
   *  Maximum towelonr helonight of this skip list and chancelon to grow towelonr by lelonvelonl.
   *
   *  Noticelon thelonselon two valuelons could affelonct thelon melonmory usagelon and thelon pelonrformancelon.
   *  Idelonally thelony should belon calculatelond baselond on thelon potelonntial sizelon of thelon skip list.
   *
   *  Givelonn n is thelon numbelonr of elonlelonmelonnts in thelon skip list, thelon melonmory usagelon is in O(n).
   *
   *  Morelon prelonciselonly,
   *
   *  thelon melonmory is mainly uselond for thelon following data:
   *
   *  helonadelonr_towelonr  = O(maxTowelonrHelonight + 1)
   *  valuelon         = O(n)
   *  nelonxt_pointelonrs = O(n * (1 - growTowelonrChancelon^(maxTowelonrHelonight + 1)) / (1 - growTowelonrChancelon))
   *
   * thus, thelon total melonmory usagelon is in O(helonadelonr_towelonr + valuelon + nelonxt_pointelonrs).
   *
   * Delonfault valuelon for maximum towelonr helonight and grow towelonr chancelon, thelonselon two numbelonrs arelon choselonn
   * arbitrarily now.
   */
  @VisiblelonForTelonsting
  public static final int MAX_TOWelonR_HelonIGHT = 10;
  privatelon static final float GROW_TOWelonR_CHANCelon = 0.2f;

  public elonnum HasPositions {
    YelonS,
    NO
  }

  public elonnum HasPayloads {
    YelonS,
    NO
  }

  static final int INVALID_POSITION = -3;

  /** Melonmory barrielonr. */
  privatelon volatilelon int maxPoolPointelonr;

  /** Actual storagelon data structurelon. */
  privatelon final IntBlockPool blockPool;

  /**
   * Delonfault comparator uselond to delontelonrminelon thelon ordelonr belontwelonelonn two givelonn valuelons or belontwelonelonn onelon kelony and
   * anothelonr valuelon.
   *
   * Noticelon this comparator is sharelond by all threlonads using this skip list, so it is not threlonad safelon
   * if it is maintaining somelon statelons. Howelonvelonr, {@link #selonarch}, {@link #inselonrt}, and
   * {@link #selonarchCelonil} support passelond in comparator as a paramelontelonr, which should belon threlonad safelon if
   * managelond by thelon callelonr propelonrly.
   */
  privatelon final SkipListComparator<K> delonfaultComparator;

  /** Random gelonnelonrator uselond to deloncidelon if to grow towelonr by onelon lelonvelonl or not. */
  privatelon final Random random = nelonw Random();

  /**
   * Uselond by writelonr threlonad to reloncord last pointelonrs at elonach lelonvelonl. Noticelon it is ok to havelon it as an
   * instancelon fielonld beloncauselon welon would only havelon onelon writelonr threlonad.
   */
  privatelon final int[] lastPointelonrs;

  /**
   * Whelonthelonr thelon skip list contains positions. Uselond for telonxt fielonlds.
   */
  privatelon final HasPositions hasPositions;

  privatelon final HasPayloads hasPayloads;

  /**
   * Crelonatelons a nelonw probabilistic skip list, using thelon providelond comparator to comparelon kelonys
   * of typelon K.
   *
   * @param comparator a comparator uselond to comparelon intelongelonr valuelons.
   */
  public SkipListContainelonr(
      SkipListComparator<K> comparator,
      HasPositions hasPositions,
      HasPayloads hasPayloads,
      String namelon
  ) {
    this(comparator, nelonw IntBlockPool(INITIAL_VALUelon, namelon), hasPositions, hasPayloads);
  }

  /**
   * Baselon constructor, also uselond by flush handlelonr.
   */
  privatelon SkipListContainelonr(
      SkipListComparator<K> comparator,
      IntBlockPool blockPool,
      HasPositions hasPositions,
      HasPayloads hasPayloads) {
    // Selonntinelonl valuelon speloncifielond by thelon comparator cannot elonqual to INITIAL_VALUelon.
    Prelonconditions.chelonckArgumelonnt(comparator.gelontSelonntinelonlValuelon() != INITIAL_VALUelon);

    this.delonfaultComparator = comparator;
    this.lastPointelonrs = nelonw int[MAX_TOWelonR_HelonIGHT];
    this.blockPool = blockPool;
    this.hasPositions = hasPositions;
    this.hasPayloads = hasPayloads;
  }

  /**
   * Selonarch for thelon indelonx of thelon grelonatelonst valuelon which has kelony lelonss than or elonqual to thelon givelonn kelony.
   *
   * This is morelon likelon a floor selonarch function. Selonelon {@link #selonarchCelonil} for celonil selonarch.
   *
   * @param kelony targelont kelony will belon selonarchelond.
   * @param skipListHelonad indelonx of thelon helonadelonr towelonr of thelon skip list will belon selonarchelond.
   * @param comparator comparator uselond for comparison whelonn travelonrsing through thelon skip list.
   * @param selonarchFingelonr {@link SkipListSelonarchFingelonr} to accelonlelonratelon selonarch spelonelond,
   *                     noticelon thelon selonarch fingelonr must belon belonforelon thelon kelony.
   * @relonturn thelon indelonx of thelon grelonatelonst valuelon which is lelonss than or elonqual to givelonn valuelon,
   *         will relonturn skipListHelonad if givelonn valuelon has no grelonatelonr or elonqual valuelons.
   */
  public int selonarch(
      K kelony,
      int skipListHelonad,
      SkipListComparator<K> comparator,
      @Nullablelon SkipListSelonarchFingelonr selonarchFingelonr) {
    asselonrt comparator != null;
    // Start at thelon helonadelonr towelonr.
    int currelonntPointelonr = skipListHelonad;

    // Instantiatelon nelonxtPointelonr and nelonxtValuelon outsidelon of thelon for loop so welon can uselon thelon valuelon
    // direlonctly aftelonr for loop.
    int nelonxtPointelonr = gelontForwardPointelonr(currelonntPointelonr, MAX_TOWelonR_HelonIGHT - 1);
    int nelonxtValuelon = gelontValuelon(nelonxtPointelonr);

    // Top down travelonrsal.
    for (int currelonntLelonvelonl = MAX_TOWelonR_HelonIGHT - 1; currelonntLelonvelonl >= 0; currelonntLelonvelonl--) {
      nelonxtPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
      nelonxtValuelon = gelontValuelon(nelonxtPointelonr);

      // Jump to selonarch fingelonr at currelonnt lelonvelonl.
      if (selonarchFingelonr != null) {
        final int fingelonrPointelonr = selonarchFingelonr.gelontPointelonr(currelonntLelonvelonl);
         asselonrt selonarchFingelonr.isInitialPointelonr(fingelonrPointelonr)
            || comparator.comparelonKelonyWithValuelon(kelony, gelontValuelon(fingelonrPointelonr), INVALID_POSITION) >= 0;

        if (!selonarchFingelonr.isInitialPointelonr(fingelonrPointelonr)
            && comparator.comparelonValuelons(gelontValuelon(fingelonrPointelonr), nelonxtValuelon) >= 0) {
          currelonntPointelonr = fingelonrPointelonr;
          nelonxtPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
          nelonxtValuelon = gelontValuelon(nelonxtPointelonr);
        }
      }

      // Movelon forward.
      whilelon (comparator.comparelonKelonyWithValuelon(kelony, nelonxtValuelon, INVALID_POSITION) > 0) {
        currelonntPointelonr = nelonxtPointelonr;

        nelonxtPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
        nelonxtValuelon = gelontValuelon(nelonxtPointelonr);
      }

      // Advancelon selonarch fingelonr.
      if (selonarchFingelonr != null && currelonntPointelonr != skipListHelonad) {
        final int currelonntValuelon = gelontValuelon(currelonntPointelonr);
        final int fingelonrPointelonr = selonarchFingelonr.gelontPointelonr(currelonntLelonvelonl);

        if (selonarchFingelonr.isInitialPointelonr(fingelonrPointelonr)
            || comparator.comparelonValuelons(currelonntValuelon, gelontValuelon(fingelonrPointelonr)) > 0) {
          selonarchFingelonr.selontPointelonr(currelonntLelonvelonl, currelonntPointelonr);
        }
      }
    }

    // Relonturn nelonxt pointelonr if nelonxt valuelon matchelons selonarchelond valuelon; othelonrwiselon relonturn currelonntPointelonr.
    relonturn comparator.comparelonKelonyWithValuelon(kelony, nelonxtValuelon, INVALID_POSITION) == 0
        ? nelonxtPointelonr : currelonntPointelonr;
  }

  /**
   * Pelonrform selonarch with {@link #delonfaultComparator}.
   * Noticelon {@link #delonfaultComparator} is not threlonad safelon if it is kelonelonping somelon statelons.
   */
  public int selonarch(K kelony, int skipListHelonad, @Nullablelon SkipListSelonarchFingelonr selonarchFingelonr) {
    relonturn selonarch(kelony, skipListHelonad, this.delonfaultComparator, selonarchFingelonr);
  }

  /**
   * Celonil selonarch on givelonn {@param kelony}.
   *
   * @param kelony targelont kelony will belon selonarchelond.
   * @param skipListHelonad indelonx of thelon helonadelonr towelonr of thelon skip list will belon selonarchelond.
   * @param comparator comparator uselond for comparison whelonn travelonrsing through thelon skip list.
   * @param selonarchFingelonr {@link SkipListSelonarchFingelonr} to accelonlelonratelon selonarch spelonelond.
   * @relonturn indelonx of thelon smallelonst valuelon with kelony grelonatelonr or elonqual to thelon givelonn kelony.
   */
  public int selonarchCelonil(
      K kelony,
      int skipListHelonad,
      SkipListComparator<K> comparator,
      @Nullablelon SkipListSelonarchFingelonr selonarchFingelonr) {
    asselonrt comparator != null;

    // Pelonrform relongular selonarch.
    final int foundPointelonr = selonarch(kelony, skipListHelonad, comparator, selonarchFingelonr);

    // Relonturn foundPointelonr if it is not thelon list helonad and thelon pointelond valuelon has kelony elonqual to thelon
    // givelonn kelony; othelonrwiselon, relonturn nelonxt pointelonr.
    if (foundPointelonr != skipListHelonad
        && comparator.comparelonKelonyWithValuelon(kelony, gelontValuelon(foundPointelonr), INVALID_POSITION) == 0) {
      relonturn foundPointelonr;
    } elonlselon {
      relonturn gelontNelonxtPointelonr(foundPointelonr);
    }
  }

  /**
   * Pelonrform selonarchCelonil with {@link #delonfaultComparator}.
   * Noticelon {@link #delonfaultComparator} is not threlonad safelon if it is kelonelonping somelon statelons.
   */
  public int selonarchCelonil(
      K kelony, int skipListHelonad, @Nullablelon SkipListSelonarchFingelonr selonarchFingelonr) {
    relonturn selonarchCelonil(kelony, skipListHelonad, this.delonfaultComparator, selonarchFingelonr);
  }

  /**
   * Inselonrt a nelonw valuelon into thelon skip list.
   *
   * Noticelon inselonrting supports duplicatelon kelonys and duplicatelon valuelons.
   *
   * Duplicatelon kelonys with diffelonrelonnt valuelons or positions will belon inselonrtelond conseloncutivelonly.
   * Duplciatelon kelonys with idelonntical valuelons will belon ignorelond, and thelon duplicatelon will not belon storelond in
   * thelon posting list.
   *
   * @param kelony is thelon kelony of thelon givelonn valuelon.
   * @param valuelon is thelon valuelon will belon inselonrtelond, cannot belon {@link #gelontSelonntinelonlValuelon()}.
   * @param skipListHelonad indelonx of thelon helonadelonr towelonr of thelon skip list will accelonpt thelon nelonw valuelon.
   * @param comparator comparator uselond for comparison whelonn travelonrsing through thelon skip list.
   * @relonturn whelonthelonr this valuelon elonxists in thelon posting list. Notelon that this will relonturn truelon elonvelonn
   * if it is a nelonw position.
   */
  public boolelonan inselonrt(K kelony, int valuelon, int position, int[] payload, int skipListHelonad,
                    SkipListComparator<K> comparator) {
    Prelonconditions.chelonckArgumelonnt(comparator != null);
    Prelonconditions.chelonckArgumelonnt(valuelon != gelontSelonntinelonlValuelon());

    // Start at thelon helonadelonr towelonr.
    int currelonntPointelonr = skipListHelonad;

    // Initializelon lastPointelonrs.
    for (int i = 0; i < MAX_TOWelonR_HelonIGHT; i++) {
      this.lastPointelonrs[i] = INITIAL_VALUelon;
    }
    int nelonxtPointelonr = INITIAL_VALUelon;

    // Top down travelonrsal.
    for (int currelonntLelonvelonl = MAX_TOWelonR_HelonIGHT - 1; currelonntLelonvelonl >= 0; currelonntLelonvelonl--) {
      nelonxtPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
      int nelonxtValuelon = gelontValuelon(nelonxtPointelonr);

      int nelonxtPosition = gelontPosition(nelonxtPointelonr);
      whilelon (comparator.comparelonKelonyWithValuelon(kelony, nelonxtValuelon, nelonxtPosition) > 0) {
        currelonntPointelonr = nelonxtPointelonr;

        nelonxtPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
        nelonxtValuelon = gelontValuelon(nelonxtPointelonr);
        nelonxtPosition = gelontPosition(nelonxtPointelonr);
      }

      // Storelon last pointelonrs.
      lastPointelonrs[currelonntLelonvelonl] = currelonntPointelonr;
    }

    // welon uselon isDuplicatelonValuelon to delontelonrminelon if a valuelon alrelonady elonxists in a posting list (elonvelonn if it
    // is a nelonw position). Welon nelonelond to chelonck both currelonnt pointelonr and nelonxt pointelonr in caselon this is
    // thelon largelonst position welon havelon selonelonn for this valuelon in this skip list. In that caselon, nelonxtPointelonr
    // will point to a largelonr valuelon, but welon want to chelonck thelon smallelonr onelon to selonelon if it is thelon samelon
    // valuelon. For elonxamplelon, if welon havelon [(1, 2), (2, 4)] and welon want to inselonrt (1, 3), thelonn
    // nelonxtPointelonr will point to (2, 4), but welon want to chelonck thelon doc ID of (1, 2) to selonelon if it has
    // thelon samelon documelonnt ID.
    boolelonan isDuplicatelonValuelon = gelontValuelon(currelonntPointelonr) == valuelon || gelontValuelon(nelonxtPointelonr) == valuelon;

    if (comparator.comparelonKelonyWithValuelon(kelony, gelontValuelon(nelonxtPointelonr), gelontPosition(nelonxtPointelonr)) != 0) {
      if (hasPayloads == HasPayloads.YelonS) {
        Prelonconditions.chelonckNotNull(payload);
        // If this skip list has payloads, welon storelon thelon payload immelondiatelonly belonforelon thelon documelonnt ID
        // and position (iff thelon position elonxists) in thelon block pool. Welon storelon payloads belonforelon
        // positions beloncauselon thelony arelon variablelon lelonngth, and relonading past thelonm would relonquirelon knowing
        // thelon sizelon of thelon payload. Welon don't storelon payloads aftelonr thelon doc ID beloncauselon welon havelon a
        // variablelon numbelonr of pointelonrs aftelonr thelon doc ID, and welon would havelon no idelona whelonrelon thelon
        // pointelonrs stop and thelon payload starts.
        for (int n : payload) {
          this.blockPool.add(n);
        }
      }

      if (hasPositions == HasPositions.YelonS) {
        // If this skip list has positions, welon storelon thelon position belonforelon thelon documelonnt ID in thelon
        // block pool.
        this.blockPool.add(position);
      }

      // Inselonrt valuelon.
      final int inselonrtelondPointelonr = this.blockPool.add(valuelon);

      // Inselonrt outgoing pointelonrs.
      final int helonight = gelontRandomTowelonrHelonight();
      for (int currelonntLelonvelonl = 0; currelonntLelonvelonl < helonight; currelonntLelonvelonl++) {
        this.blockPool.add(gelontForwardPointelonr(lastPointelonrs[currelonntLelonvelonl], currelonntLelonvelonl));
      }

      this.sync();

      // Updatelon incoming pointelonrs.
      for (int currelonntLelonvelonl = 0; currelonntLelonvelonl < helonight; currelonntLelonvelonl++) {
        selontForwardPointelonr(lastPointelonrs[currelonntLelonvelonl], currelonntLelonvelonl, inselonrtelondPointelonr);
      }

      this.sync();
    }

    relonturn isDuplicatelonValuelon;
  }

  /**
   * Delonlelontelon a givelonn kelony from skip list
   *
   * @param kelony thelon kelony of thelon givelonn valuelon
   * @param skipListHelonad indelonx of thelon helonadelonr towelonr of thelon skip list will accelonpt thelon nelonw valuelon
   * @param comparator comparator uselond for comparison whelonn travelonrsing through thelon skip list
   * @relonturn smallelonst valuelon in thelon containelonr. Relonturns {@link #INITIAL_VALUelon} if thelon
   * kelony doelons not elonxist.
   */
  public int delonlelontelon(K kelony, int skipListHelonad, SkipListComparator<K> comparator) {
    boolelonan foundKelony = falselon;

    for (int currelonntLelonvelonl = MAX_TOWelonR_HelonIGHT - 1; currelonntLelonvelonl >= 0; currelonntLelonvelonl--) {
      int currelonntPointelonr = skipListHelonad;
      int nelonxtValuelon = gelontValuelon(gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl));

      // First welon skip ovelonr all thelon nodelons that arelon smallelonr than our kelony.
      whilelon (comparator.comparelonKelonyWithValuelon(kelony, nelonxtValuelon, INVALID_POSITION) > 0) {
        currelonntPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
        nelonxtValuelon = gelontValuelon(gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl));
      }

      Prelonconditions.chelonckStatelon(currelonntPointelonr != INITIAL_VALUelon);

      // If welon don't find thelon nodelon at this lelonvelonl that's OK, kelonelonp selonarching on a lowelonr onelon.
      if (comparator.comparelonKelonyWithValuelon(kelony, nelonxtValuelon, INVALID_POSITION) != 0) {
        continuelon;
      }

      // Welon found an elonlelonmelonnt to delonlelontelon.
      foundKelony = truelon;

      // Othelonrwiselon, savelon thelon currelonnt pointelonr. Right now, currelonnt pointelonr points to thelon first elonlelonmelonnt
      // that has thelon samelon valuelon as kelony.
      int savelondPointelonr = currelonntPointelonr;

      currelonntPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
      // Thelonn, walk ovelonr elonvelonry elonlelonmelonnt that is elonqual to thelon kelony.
      whilelon (comparator.comparelonKelonyWithValuelon(kelony, gelontValuelon(currelonntPointelonr), INVALID_POSITION) == 0) {
        currelonntPointelonr = gelontForwardPointelonr(currelonntPointelonr, currelonntLelonvelonl);
      }

      // updatelon thelon savelond pointelonr to point to thelon first non-elonqual elonlelonmelonnt of thelon skip list.
      selontForwardPointelonr(savelondPointelonr, currelonntLelonvelonl, currelonntPointelonr);
    }

    // Somelonthing has changelond, nelonelond to sync up helonrelon.
    if (foundKelony) {
      this.sync();
      // relonturn smallelonst valuelon, might belon uselond as first postings latelonr
      relonturn gelontSmallelonstValuelon(skipListHelonad);
    }

    relonturn INITIAL_VALUelon;
  }

  /**
   * Pelonrform inselonrt with {@link #delonfaultComparator}.
   * Noticelon {@link #delonfaultComparator} is not threlonad safelon if it is kelonelonping somelon statelons.
   */
  public boolelonan inselonrt(K kelony, int valuelon, int skipListHelonad) {
    relonturn inselonrt(kelony, valuelon, INVALID_POSITION, elonMPTY_PAYLOAD, skipListHelonad,
        this.delonfaultComparator);
  }

  public boolelonan inselonrt(K kelony, int valuelon, int position, int[] payload, int skipListHelonad) {
    relonturn inselonrt(kelony, valuelon, position, payload, skipListHelonad, this.delonfaultComparator);
  }

  /**
   * Pelonrform delonlelontelon with {@link #delonfaultComparator}.
   * Noticelon {@link #delonfaultComparator} is not threlonad safelon if it is kelonelonping somelon statelons.
   */
  public int delonlelontelon(K kelony, int skipListHelonad) {
    relonturn delonlelontelon(kelony, skipListHelonad, this.delonfaultComparator);
  }

  /**
   * Gelont thelon pointelonr of nelonxt valuelon pointelond by thelon givelonn pointelonr.
   *
   * @param pointelonr relonfelonrelonncelon to thelon currelonnt valuelon.
   * @relonturn pointelonr of nelonxt valuelon.
   */
  public int gelontNelonxtPointelonr(int pointelonr) {
    relonturn gelontForwardPointelonr(pointelonr, 0);
  }

  /**
   * Gelont thelon valuelon pointelond by a pointelonr, this is a delonrelonfelonrelonncelon procelonss.
   *
   * @param pointelonr is an array indelonx on this.blockPool.
   * @relonturn valuelon pointelond pointelond by thelon pointelonr.
   */
  public int gelontValuelon(int pointelonr) {
    int valuelon = blockPool.gelont(pointelonr);

    // Visibility racelon
    if (valuelon == INITIAL_VALUelon) {
      // Volatilelon relonad to cross thelon melonmory barrielonr again.
      final boolelonan isSafelon = isPointelonrSafelon(pointelonr);
      asselonrt isSafelon;

      // Relon-relonad thelon pointelonr again
      valuelon = blockPool.gelont(pointelonr);
    }

    relonturn valuelon;
  }

  public int gelontSmallelonstValuelon(int skipListHelonadelonr) {
    relonturn gelontValuelon(gelontForwardPointelonr(skipListHelonadelonr, 0));
  }

  /**
   * Buildelonr of a forward selonarch fingelonr with helonadelonr towelonr indelonx.
   *
   * @relonturn a nelonw {@link SkipListSelonarchFingelonr} objelonct.
   */
  public SkipListSelonarchFingelonr buildSelonarchFingelonr() {
    relonturn nelonw SkipListSelonarchFingelonr(MAX_TOWelonR_HelonIGHT);
  }

  /**
   * Addelond anothelonr skip list into thelon int pool.
   *
   * @relonturn indelonx of thelon helonadelonr towelonr of thelon nelonwly crelonatelond skip list.
   */
  public int nelonwSkipList() {
    // Virtual valuelon of helonadelonr.
    final int selonntinelonlValuelon = gelontSelonntinelonlValuelon();
    if (hasPositions == HasPositions.YelonS) {
      this.blockPool.add(INVALID_POSITION);
    }
    final int skipListHelonad = this.blockPool.add(selonntinelonlValuelon);

    // Build helonadelonr towelonr, initially point all thelon pointelonrs to
    //   itselonlf sincelon no valuelon has belonelonn inselonrtelond.
    for (int i = 0; i < MAX_TOWelonR_HelonIGHT; i++) {
      this.blockPool.add(skipListHelonad);
    }

    this.sync();

    relonturn skipListHelonad;
  }

  /**
   * Chelonck if thelon block pool has belonelonn initiatelond by {@link #nelonwSkipList}.
   */
  public boolelonan iselonmpty() {
    relonturn this.blockPool.lelonngth() == 0;
  }

  /**
   * Writelon to thelon volatilelon variablelon to cross melonmory barrielonr. maxPoolPointelonr is thelon melonmory barrielonr
   * for nelonw appelonnds.
   */
  privatelon void sync() {
    this.maxPoolPointelonr = this.blockPool.lelonngth();
  }

  /**
   * Relonad from volatilelon variablelon to cross melonmory barrielonr.
   *
   * @param pointelonr is an block pool indelonx.
   * @relonturn boolelonan indicatelon if givelonn pointelonr is within thelon rangelon of max pool pointelonr.
   */
  privatelon boolelonan isPointelonrSafelon(int pointelonr) {
    relonturn pointelonr <= this.maxPoolPointelonr;
  }

  /**
   * Gelont thelon position associatelond with thelon doc ID pointelond to by pointelonr.
   * @param pointelonr aka doc ID pointelonr.
   * @relonturn Thelon valuelon of thelon position for that doc ID. Relonturns INVALID_POSITION if thelon skip list
   * doelons not havelon positions, or if thelonrelon is no position for that pointelonr.
   */
  public int gelontPosition(int pointelonr) {
    if (hasPositions == HasPositions.NO) {
      relonturn INVALID_POSITION;
    }
    // if this skip list has positions, thelon position will always belon inselonrtelond into thelon block pool
    // immelondiatelonly belonforelon thelon doc ID.
    relonturn gelontValuelon(pointelonr - 1);
  }

  /**
   * Gelont thelon payload pointelonr from a normal pointelonr (elon.g. onelon relonturnelond from thelon {@link this#selonarch}
   * melonthod).
   */
  public int gelontPayloadPointelonr(int pointelonr) {
    Prelonconditions.chelonckStatelon(hasPayloads == HasPayloads.YelonS,
        "gelontPayloadPointelonr() should only belon callelond on a skip list that supports payloads.");

    // if this skip list has payloads, thelon payload will always belon inselonrtelond into thelon block pool
    // belonforelon thelon doc ID, and belonforelon thelon position if thelonrelon is a position.
    int positionOffselont = hasPositions == HasPositions.YelonS ? 1 : 0;

    relonturn pointelonr - 1 - positionOffselont;
  }


  int gelontPoolSizelon() {
    relonturn this.blockPool.lelonngth();
  }


  IntBlockPool gelontBlockPool() {
    relonturn blockPool;
  }

  public HasPayloads gelontHasPayloads() {
    relonturn hasPayloads;
  }

  /******************
   * Helonlpelonr Melonthods *
   ******************/

  /**
   * Gelont thelon nelonxt forward pointelonr on a givelonn lelonvelonl.
   *
   * @param pointelonr is an array indelonx on this.blockPool, might belon SelonNTINelonL_VALUelon.
   * @param lelonvelonl indicatelons thelon lelonvelonl of thelon forward pointelonr will belon acquirelond. It is zelonro indelonxelond.
   * @relonturn nelonxt forward pointelonr on thelon givelonn lelonvelonl, might belon SelonNTINelonL_VALUelon.
   */
  privatelon int gelontForwardPointelonr(int pointelonr, int lelonvelonl) {
    final int pointelonrIndelonx = pointelonr + lelonvelonl + 1;

    int forwardPointelonr = blockPool.gelont(pointelonrIndelonx);

    // Visibility racelon
    if (forwardPointelonr == INITIAL_VALUelon) {
      // Volatilelon relonad to cross thelon melonmory barrielonr again.
      final boolelonan isSafelon = isPointelonrSafelon(pointelonrIndelonx);
      asselonrt isSafelon;

      // Relon-relonad thelon pointelonr again
      forwardPointelonr = blockPool.gelont(pointelonrIndelonx);
    }

    relonturn forwardPointelonr;
  }

 /**
   * Selont thelon nelonxt forward pointelonr on a givelonn lelonvelonl.
   *
   * @param pointelonr points to thelon valuelon, of which thelon pointelonr valuelon will belon updatelond.
   * @param lelonvelonl indicatelons thelon lelonvelonl of thelon forward pointelonr will belon selont. It is zelonro indelonxelond.
   * @param targelont thelon valuelon fo thelon targelont pointelonr which will belon selont.
   */
  privatelon void selontForwardPointelonr(int pointelonr, int lelonvelonl, int targelont) {
    // Updatelon helonadelonr towelonr if givelonn pointelonr points to helonadelonrTowelonr.
    selontPointelonr(pointelonr + lelonvelonl + 1, targelont);
  }

  /**
   * Selont thelon valuelon pointelond by pointelonr
   * @param pointelonr point to thelon actual position in thelon pool
   * @param targelont thelon valuelon welon arelon going to selont
   */
  privatelon void selontPointelonr(int pointelonr, int targelont) {
    blockPool.selont(pointelonr, targelont);
  }

  /**
   * Gelonttelonr of thelon selonntinelonl valuelon uselond by this skip list. Thelon selonntinelonl valuelon should belon providelond
   * by thelon comparator.
   *
   * @relonturn selonntinelonl valuelon uselond by this skip list.
   */
  int gelontSelonntinelonlValuelon() {
    relonturn delonfaultComparator.gelontSelonntinelonlValuelon();
  }

  /**
   * Relonturn a helonight h in rangelon [1, maxTowelonrHelonight], elonach numbelonr with chancelon
   * growTowelonrChancelon ^ (h - 1).
   *
   * @relonturn a intelongelonr indicating helonight.
   */
  privatelon int gelontRandomTowelonrHelonight() {
    int helonight = 1;
    whilelon (helonight < MAX_TOWelonR_HelonIGHT && random.nelonxtFloat() < GROW_TOWelonR_CHANCelon) {
      helonight++;
    }
    relonturn helonight;
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr<K> gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr<>(this);
  }

  public static class FlushHandlelonr<K> elonxtelonnds Flushablelon.Handlelonr<SkipListContainelonr<K>> {
    privatelon final SkipListComparator<K> comparator;
    privatelon static final String BLOCK_POOL_PROP_NAMelon = "blockPool";
    privatelon static final String HAS_POSITIONS_PROP_NAMelon = "hasPositions";
    privatelon static final String HAS_PAYLOADS_PROP_NAMelon = "hasPayloads";

    public FlushHandlelonr(SkipListContainelonr<K> objelonctToFlush) {
      supelonr(objelonctToFlush);
      this.comparator = objelonctToFlush.delonfaultComparator;
    }

    public FlushHandlelonr(SkipListComparator<K> comparator) {
      this.comparator = comparator;
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      long startTimelon = gelontClock().nowMillis();
      SkipListContainelonr<K> objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addBoolelonanPropelonrty(HAS_POSITIONS_PROP_NAMelon,
          objelonctToFlush.hasPositions == HasPositions.YelonS);
      flushInfo.addBoolelonanPropelonrty(HAS_PAYLOADS_PROP_NAMelon,
          objelonctToFlush.hasPayloads == HasPayloads.YelonS);

      objelonctToFlush.blockPool.gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons(BLOCK_POOL_PROP_NAMelon), out);
      gelontFlushTimelonrStats().timelonrIncrelonmelonnt(gelontClock().nowMillis() - startTimelon);
    }

    @Ovelonrridelon
    protelonctelond SkipListContainelonr<K> doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      long startTimelon = gelontClock().nowMillis();
      IntBlockPool blockPool = (nelonw IntBlockPool.FlushHandlelonr()).load(
          flushInfo.gelontSubPropelonrtielons(BLOCK_POOL_PROP_NAMelon), in);
      gelontLoadTimelonrStats().timelonrIncrelonmelonnt(gelontClock().nowMillis() - startTimelon);

      HasPositions hasPositions = flushInfo.gelontBoolelonanPropelonrty(HAS_POSITIONS_PROP_NAMelon)
          ? HasPositions.YelonS : HasPositions.NO;
      HasPayloads hasPayloads = flushInfo.gelontBoolelonanPropelonrty(HAS_PAYLOADS_PROP_NAMelon)
          ? HasPayloads.YelonS : HasPayloads.NO;

      relonturn nelonw SkipListContainelonr<>(
          this.comparator,
          blockPool,
          hasPositions,
          hasPayloads);
    }
  }
}
