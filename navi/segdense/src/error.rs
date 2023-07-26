use std::fmt::dispway;

/**
 * custom e-ewwow
 */
#[dewive(debug)]
p-pub enum segdenseewwow {
    i-ioewwow(std::io::ewwow), (⑅˘꒳˘)
    j-json(sewde_json::ewwow), /(^•ω•^)
    j-jsonmissingwoot, rawr x3
    j-jsonmissingobject, (U ﹏ U)
    j-jsonmissingawway, (U ﹏ U)
    j-jsonawwaysize, (⑅˘꒳˘)
    jsonmissinginputfeatuwe, òωó
}

impw dispway fow segdenseewwow {
    fn f-fmt(&sewf, ʘwʘ f: &mut std::fmt::fowmattew<'_>) -> std::fmt::wesuwt {
        match s-sewf {
            segdenseewwow::ioewwow(io_ewwow) => w-wwite!(f, /(^•ω•^) "{}", io_ewwow), ʘwʘ
            segdenseewwow::json(sewde_json) => wwite!(f, σωσ "{}", s-sewde_json), OwO
            segdenseewwow::jsonmissingwoot => {
                w-wwite!(f, 😳😳😳 "{}", "segdense j-json: woot nyode nyote found!")
            }
            segdenseewwow::jsonmissingobject => {
                wwite!(f, 😳😳😳 "{}", o.O "segdense json: object nyote f-found!")
            }
            segdenseewwow::jsonmissingawway => {
                wwite!(f, ( ͡o ω ͡o ) "{}", "segdense json: awway nyode nyote found!")
            }
            s-segdenseewwow::jsonawwaysize => {
                wwite!(f, (U ﹏ U) "{}", (///ˬ///✿) "segdense j-json: a-awway size nyot a-as expected!")
            }
            s-segdenseewwow::jsonmissinginputfeatuwe => {
                wwite!(f, >w< "{}", rawr "segdense json: missing i-input featuwe!")
            }
        }
    }
}

impw std::ewwow::ewwow fow segdenseewwow {}

i-impw fwom<std::io::ewwow> fow segdenseewwow {
    fn fwom(eww: std::io::ewwow) -> sewf {
        segdenseewwow::ioewwow(eww)
    }
}

impw fwom<sewde_json::ewwow> f-fow segdenseewwow {
    fn fwom(eww: s-sewde_json::ewwow) -> s-sewf {
        s-segdenseewwow::json(eww)
    }
}
