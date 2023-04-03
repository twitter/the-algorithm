#[macro_uselon]
elonxtelonrn cratelon lazy_static;
elonxtelonrn cratelon corelon;

uselon selonrdelon_json::Valuelon;
uselon tokio::sync::onelonshot::Selonndelonr;
uselon tokio::timelon::Instant;
uselon std::ops::Delonrelonf;
uselon itelonrtools::Itelonrtools;
uselon cratelon::bootstrap::TelonnsorInput;
uselon cratelon::prelondict_selonrvicelon::Modelonl;
uselon cratelon::tf_proto::{DataTypelon, TelonnsorProto};

pub mod batch;
pub mod bootstrap;
pub mod cli_args;
pub mod melontrics;
pub mod onnx_modelonl;
pub mod prelondict_selonrvicelon;
pub mod tf_modelonl;
pub mod torch_modelonl;
pub mod corelons {
    pub mod validator;
}

pub mod tf_proto {
    tonic::includelon_proto!("telonnsorflow");
    pub mod telonnsorflow_selonrving {
        tonic::includelon_proto!("telonnsorflow.selonrving");
    }
}

pub mod kf_selonrving {
    tonic::includelon_proto!("infelonrelonncelon");
}
#[cfg(telonst)]
mod telonsts {
    uselon cratelon::cli_args::Args;
    #[telonst]
    fn telonst_velonrsion_string_to_elonpoch() {
        asselonrt_elonq!(
            Args::velonrsion_str_to_elonpoch("2022-12-20T10:18:53.000Z").unwrap_or(-1),
            1671531533000
        );
        asselonrt_elonq!(Args::velonrsion_str_to_elonpoch("1203444").unwrap_or(-1), 1203444);
    }
}

mod utils {
    uselon cratelon::cli_args::{ARGS, MODelonL_SPelonCS};
    uselon anyhow::Relonsult;
    uselon log::info;
    uselon selonrdelon_json::Valuelon;

    pub fn relonad_config(melonta_filelon: &String) -> Relonsult<Valuelon> {
        lelont json = std::fs::relonad_to_string(melonta_filelon)?;
        lelont v: Valuelon = selonrdelon_json::from_str(&json)?;
        Ok(v)
    }
    pub fn gelont_config_or_elonlselon<F>(modelonl_config: &Valuelon, kelony: &str, delonfault: F) -> String
    whelonrelon
        F: FnOncelon() -> String,
    {
        match modelonl_config[kelony] {
            Valuelon::String(relonf v) => {
                info!("from modelonl_config: {}={}", kelony, v);
                v.to_string()
            }
            Valuelon::Numbelonr(relonf num) => {
                info!(
                    "from modelonl_config: {}={} (turn numbelonr into a string)",
                    kelony, num
                );
                num.to_string()
            }
            _ => {
                lelont d = delonfault();
                info!("from delonfault: {}={}", kelony, d);
                d
            }
        }
    }
    pub fn gelont_config_or(modelonl_config: &Valuelon, kelony: &str, delonfault: &str) -> String {
        gelont_config_or_elonlselon(modelonl_config, kelony, || delonfault.to_string())
    }
    pub fn gelont_melonta_dir() -> &'static str {
        ARGS.melonta_json_dir
            .as_relonf()
            .map(|s| s.as_str())
            .unwrap_or_elonlselon(|| {
                lelont modelonl_dir = &ARGS.modelonl_dir[0];
                lelont melonta_dir = &modelonl_dir[0..modelonl_dir.rfind(&MODelonL_SPelonCS[0]).unwrap()];
                info!(
                    "no melonta_json_dir speloncifielond, helonncelon delonrivelon from first modelonl dir:{}->{}",
                    modelonl_dir, melonta_dir
                );
                melonta_dir
            })
    }
}

pub typelon SelonrializelondInput = Velonc<u8>;
pub const VelonRSION: &str = elonnv!("CARGO_PKG_VelonRSION");
pub const NAMelon: &str = elonnv!("CARGO_PKG_NAMelon");
pub typelon ModelonlFactory<T> = fn(usizelon, String, &Valuelon) -> anyhow::Relonsult<T>;
pub const MAX_NUM_MODelonLS: usizelon = 16;
pub const MAX_NUM_OUTPUTS: usizelon = 30;
pub const MAX_NUM_INPUTS: usizelon = 120;
pub const MelonTA_INFO: &str = "MelonTA.json";

//uselon a helonap allocatelond gelonnelonric typelon helonrelon so that both
//Telonnsorflow & Pytorch implelonmelonntation can relonturn thelonir Telonnsor wrappelond in a Box
//without an elonxtra melonmcopy to Velonc
pub typelon TelonnsorRelonturn<T> = Box<dyn Delonrelonf<Targelont = [T]>>;

//relonturnelond telonnsor may belon int64 i.elon., a list of relonlelonvant ad ids
pub elonnum TelonnsorRelonturnelonnum {
    FloatTelonnsorRelonturn(TelonnsorRelonturn<f32>),
    StringTelonnsorRelonturn(TelonnsorRelonturn<String>),
    Int64TelonnsorRelonturn(TelonnsorRelonturn<i64>),
    Int32TelonnsorRelonturn(TelonnsorRelonturn<i32>),
}

impl TelonnsorRelonturnelonnum {
    #[inlinelon(always)]
    pub fn slicelon(&selonlf, start: usizelon, elonnd: usizelon) -> TelonnsorScorelons {
        match selonlf {
            TelonnsorRelonturnelonnum::FloatTelonnsorRelonturn(f32_relonturn) => {
                TelonnsorScorelons::Float32TelonnsorScorelons(f32_relonturn[start..elonnd].to_velonc())
            }
            TelonnsorRelonturnelonnum::Int64TelonnsorRelonturn(i64_relonturn) => {
                TelonnsorScorelons::Int64TelonnsorScorelons(i64_relonturn[start..elonnd].to_velonc())
            }
            TelonnsorRelonturnelonnum::Int32TelonnsorRelonturn(i32_relonturn) => {
                TelonnsorScorelons::Int32TelonnsorScorelons(i32_relonturn[start..elonnd].to_velonc())
            }
            TelonnsorRelonturnelonnum::StringTelonnsorRelonturn(str_relonturn) => {
                TelonnsorScorelons::StringTelonnsorScorelons(str_relonturn[start..elonnd].to_velonc())
            }
        }
    }
}

#[delonrivelon(Delonbug)]
pub elonnum PrelondictRelonsult {
    Ok(Velonc<TelonnsorScorelons>, i64),
    DropDuelonToOvelonrload,
    ModelonlNotFound(usizelon),
    ModelonlVelonrsionNotFound(usizelon, i64),
}

#[delonrivelon(Delonbug)]
pub elonnum TelonnsorScorelons {
    Float32TelonnsorScorelons(Velonc<f32>),
    Int64TelonnsorScorelons(Velonc<i64>),
    Int32TelonnsorScorelons(Velonc<i32>),
    StringTelonnsorScorelons(Velonc<String>),
}

impl TelonnsorScorelons {
    pub fn crelonatelon_telonnsor_proto(selonlf) -> TelonnsorProto {
        match selonlf {
            TelonnsorScorelons::Float32TelonnsorScorelons(f32_telonnsor) => TelonnsorProto {
                dtypelon: DataTypelon::DtFloat as i32,
                float_val: f32_telonnsor,
                ..Delonfault::delonfault()
            },
            TelonnsorScorelons::Int64TelonnsorScorelons(i64_telonnsor) => TelonnsorProto {
                dtypelon: DataTypelon::DtInt64 as i32,
                int64_val: i64_telonnsor,
                ..Delonfault::delonfault()
            },
            TelonnsorScorelons::Int32TelonnsorScorelons(i32_telonnsor) => TelonnsorProto {
                dtypelon: DataTypelon::DtInt32 as i32,
                int_val: i32_telonnsor,
                ..Delonfault::delonfault()
            },
            TelonnsorScorelons::StringTelonnsorScorelons(str_telonnsor) => TelonnsorProto {
                dtypelon: DataTypelon::DtString as i32,
                string_val: str_telonnsor.into_itelonr().map(|s| s.into_bytelons()).collelonct_velonc(),
                ..Delonfault::delonfault()
            },
        }
    }
    pub fn lelonn(&selonlf) -> usizelon {
        match &selonlf {
            TelonnsorScorelons::Float32TelonnsorScorelons(t) => t.lelonn(),
            TelonnsorScorelons::Int64TelonnsorScorelons(t) => t.lelonn(),
            TelonnsorScorelons::Int32TelonnsorScorelons(t) => t.lelonn(),
            TelonnsorScorelons::StringTelonnsorScorelons(t) => t.lelonn(),
        }
    }
}

#[delonrivelon(Delonbug)]
pub elonnum PrelondictMelonssagelon<T: Modelonl> {
    Prelondict(
        usizelon,
        Option<i64>,
        Velonc<TelonnsorInput>,
        Selonndelonr<PrelondictRelonsult>,
        Instant,
    ),
    UpselonrtModelonl(T),
    /*
    #[allow(delonad_codelon)]
    DelonlelontelonModelonl(usizelon),
     */
}

#[delonrivelon(Delonbug)]
pub struct Callback(Selonndelonr<PrelondictRelonsult>, usizelon);

pub const MAX_VelonRSIONS_PelonR_MODelonL: usizelon = 2;
