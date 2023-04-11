#pragma once
#include <twml/defines.h>
#include <twml/Tensor.h>
#include <twml/Type.h>
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif
    typedef void * twml_hashmap;
    typedef int64_t tw_hash_key_t;
    typedef int64_t tw_hash_val_t;
#ifdef __cplusplus
}
#endif

#ifdef __cplusplus
namespace twml {

    typedef tw_hash_key_t HashKey_t;
    typedef tw_hash_val_t HashVal_t;

    class HashMap {
    private:
        twml_hashmap m_hashmap;

    public:
        HashMap();
        ~HashMap();

        // Disable copy constructor and assignment
        // TODO: Fix this after retain and release are added to twml_hashmap
        HashMap(const HashMap &other) = delete;
        HashMap& operator=(const HashMap &other) = delete;

        void clear();
        uint64_t size() const;
        int8_t insert(const HashKey_t key);
        int8_t insert(const HashKey_t key, const HashVal_t val);
        void remove(const HashKey_t key);
        int8_t get(HashVal_t &val, const HashKey_t key) const;

        void insert(Tensor &mask, const Tensor keys);
        void insert(Tensor &mask, const Tensor keys, const Tensor vals);
        void remove(const Tensor keys);
        void get(Tensor &mask, Tensor &vals, const Tensor keys) const;

        void getInplace(Tensor &mask, Tensor &keys_vals) const;
        void toTensors(Tensor &keys, Tensor &vals) const;
    };
}
#endif

#ifdef __cplusplus
extern "C" {
#endif


    TWMLAPI twml_err twml_hashmap_create(twml_hashmap *hashmap);

    TWMLAPI twml_err twml_hashmap_clear(const twml_hashmap hashmap);

    TWMLAPI twml_err twml_hashmap_get_size(uint64_t *size, const twml_hashmap hashmap);

    TWMLAPI twml_err twml_hashmap_delete(const twml_hashmap hashmap);

    // insert, get, remove single key / value
    TWMLAPI twml_err twml_hashmap_insert_key(int8_t *mask,
                                             const twml_hashmap hashmap,
                                             const tw_hash_key_t key);

    TWMLAPI twml_err twml_hashmap_insert_key_and_value(int8_t *mask, twml_hashmap hashmap,
                                                       const tw_hash_key_t key,
                                                       const tw_hash_val_t val);

    TWMLAPI twml_err twml_hashmap_remove_key(const twml_hashmap hashmap,
                                             const tw_hash_key_t key);

    TWMLAPI twml_err twml_hashmap_get_value(int8_t *mask, tw_hash_val_t *val,
                                            const twml_hashmap hashmap,
                                            const tw_hash_key_t key);

    TWMLAPI twml_err twml_hashmap_insert_keys(twml_tensor masks,
                                              const twml_hashmap hashmap,
                                              const twml_tensor keys);

    // insert, get, remove tensors of keys / values
    TWMLAPI twml_err twml_hashmap_insert_keys_and_values(twml_tensor masks,
                                                         twml_hashmap hashmap,
                                                         const twml_tensor keys,
                                                         const twml_tensor vals);

    TWMLAPI twml_err twml_hashmap_remove_keys(const twml_hashmap hashmap,
                                              const twml_tensor keys);

    TWMLAPI twml_err twml_hashmap_get_values(twml_tensor masks,
                                             twml_tensor vals,
                                             const twml_hashmap hashmap,
                                             const twml_tensor keys);

    TWMLAPI twml_err twml_hashmap_get_values_inplace(twml_tensor masks,
                                                     twml_tensor keys_vals,
                                                     const twml_hashmap hashmap);

    TWMLAPI twml_err twml_hashmap_to_tensors(twml_tensor keys,
                                             twml_tensor vals,
                                             const twml_hashmap hashmap);
#ifdef __cplusplus
}
#endif
