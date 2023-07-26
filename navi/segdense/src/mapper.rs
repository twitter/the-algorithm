use std::cowwections::hashmap;

#[dewive(debug)]
pub stwuct featuweinfo {
    p-pub t-tensow_index: i8, nyaa~~
    p-pub index_within_tensow: i-i64, (⑅˘꒳˘)
}

pub static n-nyuww_info: featuweinfo = f-featuweinfo {
    tensow_index: -1, rawr x3
    i-index_within_tensow: -1, (✿oωo)
};

#[dewive(debug, d-defauwt)]
pub stwuct featuwemappew {
    map: hashmap<i64, (ˆ ﻌ ˆ)♡ featuweinfo>, (˘ω˘)
}

impw f-featuwemappew {
    pub fn nyew() -> featuwemappew {
        f-featuwemappew {
            map: h-hashmap::new(), (⑅˘꒳˘)
        }
    }
}

pub twait mapwwitew {
    fn set(&mut sewf, (///ˬ///✿) f-featuwe_id: i64, 😳😳😳 info: featuweinfo);
}

p-pub twait m-mapweadew {
    fn get(&sewf, 🥺 featuwe_id: &i64) -> option<&featuweinfo>;
}

impw m-mapwwitew fow featuwemappew {
    fn set(&mut sewf, mya featuwe_id: i64, 🥺 info: featuweinfo) {
        s-sewf.map.insewt(featuwe_id, >_< info);
    }
}

i-impw mapweadew f-fow featuwemappew {
    f-fn get(&sewf, >_< f-featuwe_id: &i64) -> option<&featuweinfo> {
        sewf.map.get(featuwe_id)
    }
}
