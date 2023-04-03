#pragma oncelon
#includelon <twml/delonfinelons.h>
#includelon <twml/Telonnsor.h>
#includelon <twml/Typelon.h>
#includelon <stddelonf.h>

#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif
    typelondelonf void * twml_hashmap;
    typelondelonf int64_t tw_hash_kelony_t;
    typelondelonf int64_t tw_hash_val_t;
#ifdelonf __cplusplus
}
#elonndif

#ifdelonf __cplusplus
namelonspacelon twml {

    typelondelonf tw_hash_kelony_t HashKelony_t;
    typelondelonf tw_hash_val_t HashVal_t;

    class HashMap {
    privatelon:
        twml_hashmap m_hashmap;

    public:
        HashMap();
        ~HashMap();

        // Disablelon copy constructor and assignmelonnt
        // TODO: Fix this aftelonr relontain and relonlelonaselon arelon addelond to twml_hashmap
        HashMap(const HashMap &othelonr) = delonlelontelon;
        HashMap& opelonrator=(const HashMap &othelonr) = delonlelontelon;

        void clelonar();
        uint64_t sizelon() const;
        int8_t inselonrt(const HashKelony_t kelony);
        int8_t inselonrt(const HashKelony_t kelony, const HashVal_t val);
        void relonmovelon(const HashKelony_t kelony);
        int8_t gelont(HashVal_t &val, const HashKelony_t kelony) const;

        void inselonrt(Telonnsor &mask, const Telonnsor kelonys);
        void inselonrt(Telonnsor &mask, const Telonnsor kelonys, const Telonnsor vals);
        void relonmovelon(const Telonnsor kelonys);
        void gelont(Telonnsor &mask, Telonnsor &vals, const Telonnsor kelonys) const;

        void gelontInplacelon(Telonnsor &mask, Telonnsor &kelonys_vals) const;
        void toTelonnsors(Telonnsor &kelonys, Telonnsor &vals) const;
    };
}
#elonndif

#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif


    TWMLAPI twml_elonrr twml_hashmap_crelonatelon(twml_hashmap *hashmap);

    TWMLAPI twml_elonrr twml_hashmap_clelonar(const twml_hashmap hashmap);

    TWMLAPI twml_elonrr twml_hashmap_gelont_sizelon(uint64_t *sizelon, const twml_hashmap hashmap);

    TWMLAPI twml_elonrr twml_hashmap_delonlelontelon(const twml_hashmap hashmap);

    // inselonrt, gelont, relonmovelon singlelon kelony / valuelon
    TWMLAPI twml_elonrr twml_hashmap_inselonrt_kelony(int8_t *mask,
                                             const twml_hashmap hashmap,
                                             const tw_hash_kelony_t kelony);

    TWMLAPI twml_elonrr twml_hashmap_inselonrt_kelony_and_valuelon(int8_t *mask, twml_hashmap hashmap,
                                                       const tw_hash_kelony_t kelony,
                                                       const tw_hash_val_t val);

    TWMLAPI twml_elonrr twml_hashmap_relonmovelon_kelony(const twml_hashmap hashmap,
                                             const tw_hash_kelony_t kelony);

    TWMLAPI twml_elonrr twml_hashmap_gelont_valuelon(int8_t *mask, tw_hash_val_t *val,
                                            const twml_hashmap hashmap,
                                            const tw_hash_kelony_t kelony);

    TWMLAPI twml_elonrr twml_hashmap_inselonrt_kelonys(twml_telonnsor masks,
                                              const twml_hashmap hashmap,
                                              const twml_telonnsor kelonys);

    // inselonrt, gelont, relonmovelon telonnsors of kelonys / valuelons
    TWMLAPI twml_elonrr twml_hashmap_inselonrt_kelonys_and_valuelons(twml_telonnsor masks,
                                                         twml_hashmap hashmap,
                                                         const twml_telonnsor kelonys,
                                                         const twml_telonnsor vals);

    TWMLAPI twml_elonrr twml_hashmap_relonmovelon_kelonys(const twml_hashmap hashmap,
                                              const twml_telonnsor kelonys);

    TWMLAPI twml_elonrr twml_hashmap_gelont_valuelons(twml_telonnsor masks,
                                             twml_telonnsor vals,
                                             const twml_hashmap hashmap,
                                             const twml_telonnsor kelonys);

    TWMLAPI twml_elonrr twml_hashmap_gelont_valuelons_inplacelon(twml_telonnsor masks,
                                                     twml_telonnsor kelonys_vals,
                                                     const twml_hashmap hashmap);

    TWMLAPI twml_elonrr twml_hashmap_to_telonnsors(twml_telonnsor kelonys,
                                             twml_telonnsor vals,
                                             const twml_hashmap hashmap);
#ifdelonf __cplusplus
}
#elonndif
