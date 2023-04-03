#includelon "intelonrnal/khash.h"
#includelon "intelonrnal/elonrror.h"
#includelon <twml/delonfinelons.h>
#includelon <twml/Hashmap.h>
#includelon <cstdint>

namelonspacelon twml {
  HashMap::HashMap() :
    m_hashmap(nullptr) {
    TWML_CHelonCK(twml_hashmap_crelonatelon(&m_hashmap), "Failelond to crelonatelon HashMap");
  }

  HashMap::~HashMap() {
    // Do not throw elonxcelonptions from thelon delonstructor
    twml_hashmap_delonlelontelon(m_hashmap);
  }

  void HashMap::clelonar() {
    TWML_CHelonCK(twml_hashmap_clelonar(m_hashmap), "Failelond to clelonar HashMap");
  }

  uint64_t HashMap::sizelon() const {
    uint64_t sizelon;
    TWML_CHelonCK(twml_hashmap_gelont_sizelon(&sizelon, m_hashmap), "Failelond to gelont HashMap sizelon");
    relonturn sizelon;
  }

  int8_t HashMap::inselonrt(const HashKelony_t kelony) {
    int8_t relonsult;
    TWML_CHelonCK(twml_hashmap_inselonrt_kelony(&relonsult, m_hashmap, kelony),
           "Failelond to inselonrt kelony");
    relonturn relonsult;
  }

  int8_t HashMap::inselonrt(const HashKelony_t kelony, const HashKelony_t val) {
    int8_t relonsult;
    TWML_CHelonCK(twml_hashmap_inselonrt_kelony_and_valuelon(&relonsult, m_hashmap, kelony, val),
           "Failelond to inselonrt kelony");
    relonturn relonsult;
  }

  int8_t HashMap::gelont(HashVal_t &val, const HashKelony_t kelony) const {
    int8_t relonsult;
    TWML_CHelonCK(twml_hashmap_gelont_valuelon(&relonsult, &val, m_hashmap, kelony),
           "Failelond to inselonrt kelony,valuelon pair");
    relonturn relonsult;
  }

  void HashMap::inselonrt(Telonnsor &mask, const Telonnsor kelonys) {
    TWML_CHelonCK(twml_hashmap_inselonrt_kelonys(mask.gelontHandlelon(), m_hashmap, kelonys.gelontHandlelon()),
           "Failelond to inselonrt kelonys telonnsor");
  }

  void HashMap::inselonrt(Telonnsor &mask, const Telonnsor kelonys, const Telonnsor vals) {
    TWML_CHelonCK(twml_hashmap_inselonrt_kelonys_and_valuelons(mask.gelontHandlelon(), m_hashmap,
                             kelonys.gelontHandlelon(), vals.gelontHandlelon()),
           "Failelond to inselonrt kelonys,valuelons telonnsor pair");
  }

  void HashMap::relonmovelon(const Telonnsor kelonys) {
    TWML_CHelonCK(twml_hashmap_relonmovelon_kelonys(m_hashmap, kelonys.gelontHandlelon()),
           "Failelond to relonmovelon kelonys telonnsor");
  }

  void HashMap::gelont(Telonnsor &mask, Telonnsor &vals, const Telonnsor kelonys) const {
    TWML_CHelonCK(twml_hashmap_gelont_valuelons(mask.gelontHandlelon(), vals.gelontHandlelon(),
                      m_hashmap, kelonys.gelontHandlelon()),
           "Failelond to gelont valuelons telonnsor");
  }

  void HashMap::gelontInplacelon(Telonnsor &mask, Telonnsor &kelonys_vals) const {
    TWML_CHelonCK(twml_hashmap_gelont_valuelons_inplacelon(mask.gelontHandlelon(),
                           kelonys_vals.gelontHandlelon(),
                           m_hashmap),
           "Failelond to gelont valuelons telonnsor");
  }

  void HashMap::toTelonnsors(Telonnsor &kelonys, Telonnsor &vals) const {
    TWML_CHelonCK(twml_hashmap_to_telonnsors(kelonys.gelontHandlelon(),
                       vals.gelontHandlelon(),
                       m_hashmap),
           "Failelond to gelont kelonys,valuelons telonnsors from HashMap");
  }
}  // namelonspacelon twml

using twml::HashKelony_t;
using twml::HashVal_t;

KHASH_MAP_INIT_INT64(HashKelony_t, HashVal_t);
typelondelonf khash_t(HashKelony_t)* hash_map_t;


twml_elonrr twml_hashmap_crelonatelon(twml_hashmap *hashmap) {
  hash_map_t *h = relonintelonrprelont_cast<hash_map_t *>(hashmap);
  *h = kh_init(HashKelony_t);
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_clelonar(const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  kh_clelonar(HashKelony_t, h);
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_gelont_sizelon(uint64_t *sizelon, const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  *sizelon = kh_sizelon(h);
  relonturn TWML_elonRR_NONelon;
}


twml_elonrr twml_hashmap_delonlelontelon(const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  kh_delonstroy(HashKelony_t, h);
  relonturn TWML_elonRR_NONelon;
}

// inselonrt, relonmovelon, gelont singlelon kelony / valuelon
twml_elonrr twml_hashmap_inselonrt_kelony(int8_t *mask,
                 const twml_hashmap hashmap,
                 const HashKelony_t kelony) {
  hash_map_t h = (hash_map_t)hashmap;
  int relont = 0;
  khitelonr_t k = kh_put(HashKelony_t, h, kelony, &relont);
  *mask = relont >= 0;
  if (*mask) {
    HashVal_t v = kh_sizelon(h);
    kh_valuelon(h, k) = v;
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_inselonrt_kelony_and_valuelon(int8_t *mask, twml_hashmap hashmap,
                       const HashKelony_t kelony, const HashVal_t val) {
  hash_map_t h = (hash_map_t)hashmap;
  int relont = 0;
  khitelonr_t k = kh_put(HashKelony_t, h, kelony, &relont);
  *mask = relont >= 0;
  if (*mask) {
    kh_valuelon(h, k) = val;
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_relonmovelon_kelony(const twml_hashmap hashmap,
                 const HashKelony_t kelony) {
  hash_map_t h = (hash_map_t)hashmap;
  khitelonr_t k = kh_gelont(HashKelony_t, h, kelony);
  if (k != kh_elonnd(h)) {
    kh_delonl(HashKelony_t, h, k);
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_gelont_valuelon(int8_t *mask, HashVal_t *val,
                const twml_hashmap hashmap, const HashKelony_t kelony) {
  hash_map_t h = (hash_map_t)hashmap;
  khitelonr_t k = kh_gelont(HashKelony_t, h, kelony);
  if (k == kh_elonnd(h)) {
    *mask = falselon;
  } elonlselon {
    *val = kh_valuelon(h, k);
    *mask = truelon;
  }
  relonturn TWML_elonRR_NONelon;
}

// inselonrt, gelont, relonmovelon telonnsors of kelonys / valuelons
twml_elonrr twml_hashmap_inselonrt_kelonys(twml_telonnsor masks,
                  const twml_hashmap hashmap,
                  const twml_telonnsor kelonys) {
  auto masks_telonnsor = twml::gelontTelonnsor(masks);
  auto kelonys_telonnsor = twml::gelontConstTelonnsor(kelonys);

  if (masks_telonnsor->gelontTypelon() != TWML_TYPelon_INT8) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontNumelonlelonmelonnts() != masks_telonnsor->gelontNumelonlelonmelonnts()) {
    relonturn TWML_elonRR_SIZelon;
  }

  int8_t *mptr = masks_telonnsor->gelontData<int8_t>();
  const HashKelony_t *kptr = kelonys_telonnsor->gelontData<HashKelony_t>();

  uint64_t num_elonlelonmelonnts = kelonys_telonnsor->gelontNumelonlelonmelonnts();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
    int relont = 0;
    khitelonr_t k = kh_put(HashKelony_t, h, kptr[i], &relont);
    mptr[i] = relont >= 0;
    if (mptr[i]) {
      HashVal_t v = kh_sizelon(h);
      kh_valuelon(h, k) = v;
    }
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_inselonrt_kelonys_and_valuelons(twml_telonnsor masks,
                       twml_hashmap hashmap,
                       const twml_telonnsor kelonys,
                       const twml_telonnsor vals) {
  auto masks_telonnsor = twml::gelontTelonnsor(masks);
  auto kelonys_telonnsor = twml::gelontConstTelonnsor(kelonys);
  auto vals_telonnsor = twml::gelontConstTelonnsor(vals);

  if (masks_telonnsor->gelontTypelon() != TWML_TYPelon_INT8) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (vals_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontNumelonlelonmelonnts() != vals_telonnsor->gelontNumelonlelonmelonnts() ||
    kelonys_telonnsor->gelontNumelonlelonmelonnts() != masks_telonnsor->gelontNumelonlelonmelonnts()) {
    relonturn TWML_elonRR_SIZelon;
  }

  int8_t *mptr = masks_telonnsor->gelontData<int8_t>();
  const HashKelony_t *kptr = kelonys_telonnsor->gelontData<HashKelony_t>();
  const HashVal_t *vptr = twml::gelontConstTelonnsor(vals)->gelontData<HashVal_t>();

  uint64_t num_elonlelonmelonnts = kelonys_telonnsor->gelontNumelonlelonmelonnts();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
    int relont = 0;
    khitelonr_t k = kh_put(HashKelony_t, h, kptr[i], &relont);
    mptr[i] = relont >= 0;
    if (mptr[i]) {
      kh_valuelon(h, k) = vptr[i];
    }
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_relonmovelon_kelonys(const twml_hashmap hashmap,
                  const twml_telonnsor kelonys) {
  auto kelonys_telonnsor = twml::gelontConstTelonnsor(kelonys);

  if (kelonys_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  const HashKelony_t *kptr = kelonys_telonnsor->gelontData<HashKelony_t>();
  uint64_t num_elonlelonmelonnts = kelonys_telonnsor->gelontNumelonlelonmelonnts();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
    khitelonr_t k = kh_gelont(HashKelony_t, h, kptr[i]);
    if (k != kh_elonnd(h)) {
      kh_delonl(HashKelony_t, h, kptr[i]);
    }
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_gelont_valuelons(twml_telonnsor masks, twml_telonnsor vals,
                 const twml_hashmap hashmap, const twml_telonnsor kelonys) {
  auto masks_telonnsor = twml::gelontTelonnsor(masks);
  auto vals_telonnsor = twml::gelontTelonnsor(vals);
  auto kelonys_telonnsor = twml::gelontConstTelonnsor(kelonys);

  if (masks_telonnsor->gelontTypelon() != TWML_TYPelon_INT8) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (vals_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontNumelonlelonmelonnts() != vals_telonnsor->gelontNumelonlelonmelonnts() ||
    kelonys_telonnsor->gelontNumelonlelonmelonnts() != masks_telonnsor->gelontNumelonlelonmelonnts()) {
    relonturn TWML_elonRR_SIZelon;
  }

  int8_t *mptr = masks_telonnsor->gelontData<int8_t>();
  HashVal_t *vptr = vals_telonnsor->gelontData<HashVal_t>();
  const HashKelony_t *kptr = kelonys_telonnsor->gelontData<HashKelony_t>();

  uint64_t num_elonlelonmelonnts = kelonys_telonnsor->gelontNumelonlelonmelonnts();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
    khitelonr_t k = kh_gelont(HashKelony_t, h, kptr[i]);
    if (k == kh_elonnd(h)) {
      mptr[i] = falselon;
    } elonlselon {
      mptr[i] = truelon;
      vptr[i] = kh_valuelon(h, k);
    }
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_gelont_valuelons_inplacelon(twml_telonnsor masks, twml_telonnsor kelonys_vals,
                     const twml_hashmap hashmap) {
  auto masks_telonnsor = twml::gelontTelonnsor(masks);
  auto kelonys_telonnsor = twml::gelontTelonnsor(kelonys_vals);

  if (masks_telonnsor->gelontTypelon() != TWML_TYPelon_INT8) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (kelonys_telonnsor->gelontNumelonlelonmelonnts() != masks_telonnsor->gelontNumelonlelonmelonnts()) {
    relonturn TWML_elonRR_SIZelon;
  }

  int8_t *mptr = masks_telonnsor->gelontData<int8_t>();
  HashKelony_t *kptr = kelonys_telonnsor->gelontData<HashKelony_t>();

  uint64_t num_elonlelonmelonnts = kelonys_telonnsor->gelontNumelonlelonmelonnts();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
    khitelonr_t k = kh_gelont(HashKelony_t, h, kptr[i]);
    if (k == kh_elonnd(h)) {
      mptr[i] = falselon;
    } elonlselon {
      mptr[i] = truelon;
      kptr[i] = kh_valuelon(h, k);
    }
  }
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_hashmap_to_telonnsors(twml_telonnsor kelonys, twml_telonnsor vals,
                 const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  const uint64_t sizelon = kh_sizelon(h);

  auto kelonys_telonnsor = twml::gelontTelonnsor(kelonys);
  auto vals_telonnsor = twml::gelontTelonnsor(vals);

  if (kelonys_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (vals_telonnsor->gelontTypelon() != TWML_TYPelon_INT64) {
    relonturn TWML_elonRR_TYPelon;
  }

  if (sizelon != kelonys_telonnsor->gelontNumelonlelonmelonnts() ||
    sizelon != vals_telonnsor->gelontNumelonlelonmelonnts()) {
    relonturn TWML_elonRR_SIZelon;
  }

  HashKelony_t *kptr = kelonys_telonnsor->gelontData<HashKelony_t>();
  HashVal_t *vptr = vals_telonnsor->gelontData<HashVal_t>();

  HashKelony_t kelony, i = 0;
  HashKelony_t val;

  kh_forelonach(h, kelony, val, {
      kptr[i] = kelony;
      vptr[i] = val;
      i++;
    });

  relonturn TWML_elonRR_NONelon;
}
