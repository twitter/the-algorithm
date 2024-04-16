# fndef TWML_L BTWML_ NCLUDE_TWML_COMMON_H_
#def ne TWML_L BTWML_ NCLUDE_TWML_COMMON_H_

#def ne USE_ABSE L_HASH 1

# f def ned(USE_ABSE L_HASH)
# nclude "absl/conta ner/flat_hash_map.h"
# nclude "absl/conta ner/flat_hash_set.h"
#el f def ned(USE_DENSE_HASH)
# nclude <sparsehash/dense_hash_map>
# nclude <sparsehash/dense_hash_set>
#else
# nclude <unordered_map>
# nclude <unordered_set>
#end f  // USE_ABSE L_HASH


na space twml {
# f def ned(USE_ABSE L_HASH)
  template<typena  KeyType, typena  ValueType>
    us ng Map = absl::flat_hash_map<KeyType, ValueType>;

  template<typena  KeyType>
    us ng Set = absl::flat_hash_set<KeyType>;
#el f def ned(USE_DENSE_HASH)
// Do not use t  unless an proper empty key can be found.
  template<typena  KeyType, typena  ValueType>
    us ng Map = google::dense_hash_map<KeyType, ValueType>;

  template<typena  KeyType>
    us ng Set = google::dense_hash_set<KeyType>;
#else
  template<typena  KeyType, typena  ValueType>
    us ng Map = std::unordered_map<KeyType, ValueType>;

  template<typena  KeyType>
    us ng Set = std::unordered_set<KeyType>;
#end f  // USE_DENSE_HASH

}  // na space twml

#end f  // TWML_L BTWML_ NCLUDE_TWML_COMMON_H_