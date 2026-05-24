#ifndef TWML_LIBTWML_INCLUDE_TWML_COMMON_H_
#define TWML_LIBTWML_INCLUDE_TWML_COMMON_H_

#define USE_ABSEIL_HASH 1

#if defined(USE_ABSEIL_HASH)
#include "absl/container/flat_hash_map.h"
#include "absl/container/flat_hash_set.h"
#elif defined(USE_DENSE_HASH)
#include <sparsehash/dense_hash_map>
#include <sparsehash/dense_hash_set>
#else
#include <unordered_map>
#include <unordered_set>
#endif  // USE_ABSEIL_HASH


namespace twml {
#if defined(USE_ABSEIL_HASH)
  template<typename KeyType, typename ValueType>
    using Map = absl::flat_hash_map<KeyType, ValueType>;

  template<typename KeyType>
    using Set = absl::flat_hash_set<KeyType>;
#elif defined(USE_DENSE_HASH)
// Do not use this unless an proper empty key can be found.
  template<typename KeyType, typename ValueType>
    using Map = google::dense_hash_map<KeyType, ValueType>;

  template<typename KeyType>
    using Set = google::dense_hash_set<KeyType>;
#else
  template<typename KeyType, typename ValueType>
    using Map = std::unordered_map<KeyType, ValueType>;

  template<typename KeyType>
    using Set = std::unordered_set<KeyType>;
#endif  // USE_DENSE_HASH

}  // namespace twml

#endif  // TWML_LIBTWML_INCLUDE_TWML_COMMON_H_