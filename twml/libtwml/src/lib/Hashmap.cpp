#include "internal/khash.h"
#include "internal/error.h"
#include <twml/defines.h>
#include <twml/Hashmap.h>
#include <cstdint>

namespace twml {
  HashMap::HashMap() :
    m_hashmap(nullptr) {
    TWML_CHECK(twml_hashmap_create(&m_hashmap), "Failed to create HashMap");
  }

  HashMap::~HashMap() {
    // Do not throw exceptions from the destructor
    twml_hashmap_delete(m_hashmap);
  }

  void HashMap::clear() {
    TWML_CHECK(twml_hashmap_clear(m_hashmap), "Failed to clear HashMap");
  }

  uint64_t HashMap::size() const {
    uint64_t size;
    TWML_CHECK(twml_hashmap_get_size(&size, m_hashmap), "Failed to get HashMap size");
    return size;
  }

  int8_t HashMap::insert(const HashKey_t key) {
    int8_t result;
    TWML_CHECK(twml_hashmap_insert_key(&result, m_hashmap, key),
           "Failed to insert key");
    return result;
  }

  int8_t HashMap::insert(const HashKey_t key, const HashKey_t val) {
    int8_t result;
    TWML_CHECK(twml_hashmap_insert_key_and_value(&result, m_hashmap, key, val),
           "Failed to insert key");
    return result;
  }

  int8_t HashMap::get(HashVal_t &val, const HashKey_t key) const {
    int8_t result;
    TWML_CHECK(twml_hashmap_get_value(&result, &val, m_hashmap, key),
           "Failed to insert key,value pair");
    return result;
  }

  void HashMap::insert(Tensor &mask, const Tensor keys) {
    TWML_CHECK(twml_hashmap_insert_keys(mask.getHandle(), m_hashmap, keys.getHandle()),
           "Failed to insert keys tensor");
  }

  void HashMap::insert(Tensor &mask, const Tensor keys, const Tensor vals) {
    TWML_CHECK(twml_hashmap_insert_keys_and_values(mask.getHandle(), m_hashmap,
                             keys.getHandle(), vals.getHandle()),
           "Failed to insert keys,values tensor pair");
  }

  void HashMap::remove(const Tensor keys) {
    TWML_CHECK(twml_hashmap_remove_keys(m_hashmap, keys.getHandle()),
           "Failed to remove keys tensor");
  }

  void HashMap::get(Tensor &mask, Tensor &vals, const Tensor keys) const {
    TWML_CHECK(twml_hashmap_get_values(mask.getHandle(), vals.getHandle(),
                      m_hashmap, keys.getHandle()),
           "Failed to get values tensor");
  }

  void HashMap::getInplace(Tensor &mask, Tensor &keys_vals) const {
    TWML_CHECK(twml_hashmap_get_values_inplace(mask.getHandle(),
                           keys_vals.getHandle(),
                           m_hashmap),
           "Failed to get values tensor");
  }

  void HashMap::toTensors(Tensor &keys, Tensor &vals) const {
    TWML_CHECK(twml_hashmap_to_tensors(keys.getHandle(),
                       vals.getHandle(),
                       m_hashmap),
           "Failed to get keys,values tensors from HashMap");
  }
}  // namespace twml

using twml::HashKey_t;
using twml::HashVal_t;

KHASH_MAP_INIT_INT64(HashKey_t, HashVal_t);
typedef khash_t(HashKey_t)* hash_map_t;


twml_err twml_hashmap_create(twml_hashmap *hashmap) {
  hash_map_t *h = reinterpret_cast<hash_map_t *>(hashmap);
  *h = kh_init(HashKey_t);
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_clear(const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  kh_clear(HashKey_t, h);
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_get_size(uint64_t *size, const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  *size = kh_size(h);
  return TWML_ERR_NONE;
}


twml_err twml_hashmap_delete(const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  kh_destroy(HashKey_t, h);
  return TWML_ERR_NONE;
}

// insert, remove, get single key / value
twml_err twml_hashmap_insert_key(int8_t *mask,
                 const twml_hashmap hashmap,
                 const HashKey_t key) {
  hash_map_t h = (hash_map_t)hashmap;
  int ret = 0;
  khiter_t k = kh_put(HashKey_t, h, key, &ret);
  *mask = ret >= 0;
  if (*mask) {
    HashVal_t v = kh_size(h);
    kh_value(h, k) = v;
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_insert_key_and_value(int8_t *mask, twml_hashmap hashmap,
                       const HashKey_t key, const HashVal_t val) {
  hash_map_t h = (hash_map_t)hashmap;
  int ret = 0;
  khiter_t k = kh_put(HashKey_t, h, key, &ret);
  *mask = ret >= 0;
  if (*mask) {
    kh_value(h, k) = val;
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_remove_key(const twml_hashmap hashmap,
                 const HashKey_t key) {
  hash_map_t h = (hash_map_t)hashmap;
  khiter_t k = kh_get(HashKey_t, h, key);
  if (k != kh_end(h)) {
    kh_del(HashKey_t, h, k);
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_get_value(int8_t *mask, HashVal_t *val,
                const twml_hashmap hashmap, const HashKey_t key) {
  hash_map_t h = (hash_map_t)hashmap;
  khiter_t k = kh_get(HashKey_t, h, key);
  if (k == kh_end(h)) {
    *mask = false;
  } else {
    *val = kh_value(h, k);
    *mask = true;
  }
  return TWML_ERR_NONE;
}

// insert, get, remove tensors of keys / values
twml_err twml_hashmap_insert_keys(twml_tensor masks,
                  const twml_hashmap hashmap,
                  const twml_tensor keys) {
  auto masks_tensor = twml::getTensor(masks);
  auto keys_tensor = twml::getConstTensor(keys);

  if (masks_tensor->getType() != TWML_TYPE_INT8) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getNumElements() != masks_tensor->getNumElements()) {
    return TWML_ERR_SIZE;
  }

  int8_t *mptr = masks_tensor->getData<int8_t>();
  const HashKey_t *kptr = keys_tensor->getData<HashKey_t>();

  uint64_t num_elements = keys_tensor->getNumElements();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elements; i++) {
    int ret = 0;
    khiter_t k = kh_put(HashKey_t, h, kptr[i], &ret);
    mptr[i] = ret >= 0;
    if (mptr[i]) {
      HashVal_t v = kh_size(h);
      kh_value(h, k) = v;
    }
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_insert_keys_and_values(twml_tensor masks,
                       twml_hashmap hashmap,
                       const twml_tensor keys,
                       const twml_tensor vals) {
  auto masks_tensor = twml::getTensor(masks);
  auto keys_tensor = twml::getConstTensor(keys);
  auto vals_tensor = twml::getConstTensor(vals);

  if (masks_tensor->getType() != TWML_TYPE_INT8) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (vals_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getNumElements() != vals_tensor->getNumElements() ||
    keys_tensor->getNumElements() != masks_tensor->getNumElements()) {
    return TWML_ERR_SIZE;
  }

  int8_t *mptr = masks_tensor->getData<int8_t>();
  const HashKey_t *kptr = keys_tensor->getData<HashKey_t>();
  const HashVal_t *vptr = twml::getConstTensor(vals)->getData<HashVal_t>();

  uint64_t num_elements = keys_tensor->getNumElements();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elements; i++) {
    int ret = 0;
    khiter_t k = kh_put(HashKey_t, h, kptr[i], &ret);
    mptr[i] = ret >= 0;
    if (mptr[i]) {
      kh_value(h, k) = vptr[i];
    }
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_remove_keys(const twml_hashmap hashmap,
                  const twml_tensor keys) {
  auto keys_tensor = twml::getConstTensor(keys);

  if (keys_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  const HashKey_t *kptr = keys_tensor->getData<HashKey_t>();
  uint64_t num_elements = keys_tensor->getNumElements();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elements; i++) {
    khiter_t k = kh_get(HashKey_t, h, kptr[i]);
    if (k != kh_end(h)) {
      kh_del(HashKey_t, h, kptr[i]);
    }
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_get_values(twml_tensor masks, twml_tensor vals,
                 const twml_hashmap hashmap, const twml_tensor keys) {
  auto masks_tensor = twml::getTensor(masks);
  auto vals_tensor = twml::getTensor(vals);
  auto keys_tensor = twml::getConstTensor(keys);

  if (masks_tensor->getType() != TWML_TYPE_INT8) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (vals_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getNumElements() != vals_tensor->getNumElements() ||
    keys_tensor->getNumElements() != masks_tensor->getNumElements()) {
    return TWML_ERR_SIZE;
  }

  int8_t *mptr = masks_tensor->getData<int8_t>();
  HashVal_t *vptr = vals_tensor->getData<HashVal_t>();
  const HashKey_t *kptr = keys_tensor->getData<HashKey_t>();

  uint64_t num_elements = keys_tensor->getNumElements();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elements; i++) {
    khiter_t k = kh_get(HashKey_t, h, kptr[i]);
    if (k == kh_end(h)) {
      mptr[i] = false;
    } else {
      mptr[i] = true;
      vptr[i] = kh_value(h, k);
    }
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_get_values_inplace(twml_tensor masks, twml_tensor keys_vals,
                     const twml_hashmap hashmap) {
  auto masks_tensor = twml::getTensor(masks);
  auto keys_tensor = twml::getTensor(keys_vals);

  if (masks_tensor->getType() != TWML_TYPE_INT8) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (keys_tensor->getNumElements() != masks_tensor->getNumElements()) {
    return TWML_ERR_SIZE;
  }

  int8_t *mptr = masks_tensor->getData<int8_t>();
  HashKey_t *kptr = keys_tensor->getData<HashKey_t>();

  uint64_t num_elements = keys_tensor->getNumElements();

  hash_map_t h = (hash_map_t)hashmap;
  for (uint64_t i = 0; i < num_elements; i++) {
    khiter_t k = kh_get(HashKey_t, h, kptr[i]);
    if (k == kh_end(h)) {
      mptr[i] = false;
    } else {
      mptr[i] = true;
      kptr[i] = kh_value(h, k);
    }
  }
  return TWML_ERR_NONE;
}

twml_err twml_hashmap_to_tensors(twml_tensor keys, twml_tensor vals,
                 const twml_hashmap hashmap) {
  hash_map_t h = (hash_map_t)hashmap;
  const uint64_t size = kh_size(h);

  auto keys_tensor = twml::getTensor(keys);
  auto vals_tensor = twml::getTensor(vals);

  if (keys_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (vals_tensor->getType() != TWML_TYPE_INT64) {
    return TWML_ERR_TYPE;
  }

  if (size != keys_tensor->getNumElements() ||
    size != vals_tensor->getNumElements()) {
    return TWML_ERR_SIZE;
  }

  HashKey_t *kptr = keys_tensor->getData<HashKey_t>();
  HashVal_t *vptr = vals_tensor->getData<HashVal_t>();

  HashKey_t key, i = 0;
  HashKey_t val;

  kh_foreach(h, key, val, {
      kptr[i] = key;
      vptr[i] = val;
      i++;
    });

  return TWML_ERR_NONE;
}
