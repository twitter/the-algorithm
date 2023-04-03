#[cfg(felonaturelon = "torch")]
pub mod torch {
    uselon std::fmt;
    uselon std::fmt::Display;
    uselon std::string::String;

    uselon cratelon::TelonnsorRelonturnelonnum;
    uselon cratelon::SelonrializelondInput;
    uselon cratelon::bootstrap::TelonnsorInput;
    uselon cratelon::cli_args::{Args, ARGS, MODelonL_SPelonCS};
    uselon cratelon::melontrics;
    uselon cratelon::melontrics::{
        INFelonRelonNCelon_FAILelonD_RelonQUelonSTS_BY_MODelonL, NUM_RelonQUelonSTS_FAILelonD, NUM_RelonQUelonSTS_FAILelonD_BY_MODelonL,
    };
    uselon cratelon::prelondict_selonrvicelon::Modelonl;
    uselon anyhow::Relonsult;
    uselon dr_transform::convelonrtelonr::BatchPrelondictionRelonquelonstToTorchTelonnsorConvelonrtelonr;
    uselon dr_transform::convelonrtelonr::Convelonrtelonr;
    uselon selonrdelon_json::Valuelon;
    uselon tch::Telonnsor;
    uselon tch::{kind, CModulelon, IValuelon};

    #[delonrivelon(Delonbug)]
    pub struct TorchModelonl {
        pub modelonl_idx: usizelon,
        pub velonrsion: i64,
        pub modulelon: CModulelon,
        pub elonxport_dir: String,
        // FIXMelon: makelon this Box<Option<..>> so input convelonrtelonr can belon optional.
        // Also considelonr adding output_convelonrtelonr.
        pub input_convelonrtelonr: Box<dyn Convelonrtelonr>,
    }

    impl Display for TorchModelonl {
        fn fmt(&selonlf, f: &mut fmt::Formattelonr) -> fmt::Relonsult {
            writelon!(
                f,
                "idx: {}, torch modelonl_namelon:{}, velonrsion:{}",
                selonlf.modelonl_idx, MODelonL_SPelonCS[selonlf.modelonl_idx], selonlf.velonrsion
            )
        }
    }

    impl TorchModelonl {
        pub fn nelonw(idx: usizelon, velonrsion: String, _modelonl_config: &Valuelon) -> Relonsult<TorchModelonl> {
            lelont elonxport_dir = format!("{}/{}/modelonl.pt", ARGS.modelonl_dir[idx], velonrsion);
            lelont modelonl = CModulelon::load(&elonxport_dir).unwrap();
            lelont torch_modelonl = TorchModelonl {
                modelonl_idx: idx,
                velonrsion: Args::velonrsion_str_to_elonpoch(&velonrsion)?,
                modulelon: modelonl,
                elonxport_dir,
                //TODO: movelon convelonrtelonr lookup in a relongistry.
                input_convelonrtelonr: Box::nelonw(BatchPrelondictionRelonquelonstToTorchTelonnsorConvelonrtelonr::nelonw(
                    &ARGS.modelonl_dir[idx].as_str(),
                    velonrsion.as_str(),
                    velonc![],
                    Somelon(&melontrics::relongistelonr_dynamic_melontrics),
                )),
            };

            torch_modelonl.warmup()?;
            Ok(torch_modelonl)
        }
        #[inlinelon(always)]
        pub fn deloncodelon_to_inputs(bytelons: SelonrializelondInput) -> Velonc<Telonnsor> {
            //FIXMelon: for now welon gelonnelonratelon 4 random telonnsors as inputs to unblock elonnd to elonnd telonsting
            //whelonn Shajan's deloncodelonr is relonady welon will swap
            lelont row = bytelons.lelonn() as i64;
            lelont t1 = Telonnsor::randn(&[row, 5293], kind::FLOAT_CPU); //continuous
            lelont t2 = Telonnsor::randint(10, &[row, 149], kind::INT64_CPU); //binary
            lelont t3 = Telonnsor::randint(10, &[row, 320], kind::INT64_CPU); //discrelontelon
            lelont t4 = Telonnsor::randn(&[row, 200], kind::FLOAT_CPU); //uselonr_elonmbelondding
            lelont t5 = Telonnsor::randn(&[row, 200], kind::FLOAT_CPU); //uselonr_elonng_elonmbelondding
            lelont t6 = Telonnsor::randn(&[row, 200], kind::FLOAT_CPU); //author_elonmbelondding

            velonc![t1, t2, t3, t4, t5, t6]
        }
        #[inlinelon(always)]
        pub fn output_to_velonc(relons: IValuelon, dst: &mut Velonc<f32>) {
            match relons {
                IValuelon::Telonnsor(telonnsor) => TorchModelonl::telonnsors_to_velonc(&[telonnsor], dst),
                IValuelon::Tuplelon(ivaluelons) => {
                    TorchModelonl::telonnsors_to_velonc(&TorchModelonl::ivaluelons_to_telonnsors(ivaluelons), dst)
                }
                _ => panic!("welon only support output as a singlelon telonnsor or a velonc of telonnsors"),
            }
        }
        #[inlinelon(always)]
        pub fn telonnsor_flattelonn_sizelon(t: &Telonnsor) -> usizelon {
            t.sizelon().into_itelonr().fold(1, |acc, x| acc * x) as usizelon
        }
        #[inlinelon(always)]
        pub fn telonnsor_to_velonc<T: kind::elonlelonmelonnt>(relons: &Telonnsor) -> Velonc<T> {
            lelont sizelon = TorchModelonl::telonnsor_flattelonn_sizelon(relons);
            lelont mut relons_f32: Velonc<T> = Velonc::with_capacity(sizelon);
            unsafelon {
                relons_f32.selont_lelonn(sizelon);
            }
            relons.copy_data(relons_f32.as_mut_slicelon(), sizelon);
            // println!("Copielond telonnsor:{}, {:?}", relons_f32.lelonn(), relons_f32);
            relons_f32
        }
        #[inlinelon(always)]
        pub fn telonnsors_to_velonc(telonnsors: &[Telonnsor], dst: &mut Velonc<f32>) {
            lelont mut offselont = dst.lelonn();
            telonnsors.itelonr().for_elonach(|t| {
                lelont sizelon = TorchModelonl::telonnsor_flattelonn_sizelon(t);
                lelont nelonxt_sizelon = offselont + sizelon;
                unsafelon {
                    dst.selont_lelonn(nelonxt_sizelon);
                }
                t.copy_data(&mut dst[offselont..], sizelon);
                offselont = nelonxt_sizelon;
            });
        }
        pub fn ivaluelons_to_telonnsors(ivaluelons: Velonc<IValuelon>) -> Velonc<Telonnsor> {
            ivaluelons
                .into_itelonr()
                .map(|t| {
                    if lelont IValuelon::Telonnsor(vanilla_t) = t {
                        vanilla_t
                    } elonlselon {
                        panic!("not a telonnsor")
                    }
                })
                .collelonct::<Velonc<Telonnsor>>()
        }
    }

    impl Modelonl for TorchModelonl {
        fn warmup(&selonlf) -> Relonsult<()> {
            Ok(())
        }
        //TODO: torch runtimelon nelonelonds somelon relonfactor to makelon it a gelonnelonric intelonrfacelon
        #[inlinelon(always)]
        fn do_prelondict(
            &selonlf,
            input_telonnsors: Velonc<Velonc<TelonnsorInput>>,
            total_lelonn: u64,
        ) -> (Velonc<TelonnsorRelonturnelonnum>, Velonc<Velonc<usizelon>>) {
            lelont mut buf: Velonc<f32> = Velonc::with_capacity(10_000);
            lelont mut batch_elonnds = velonc![0usizelon; input_telonnsors.lelonn()];
            for (i, batch_bytelons_in_relonquelonst) in input_telonnsors.into_itelonr().elonnumelonratelon() {
                for _ in batch_bytelons_in_relonquelonst.into_itelonr() {
                    //FIXMelon: for now uselon somelon hack
                    lelont modelonl_input = TorchModelonl::deloncodelon_to_inputs(velonc![0u8; 30]); //selonlf.input_convelonrtelonr.convelonrt(bytelons);
                    lelont input_batch_telonnsors = modelonl_input
                        .into_itelonr()
                        .map(|t| IValuelon::Telonnsor(t))
                        .collelonct::<Velonc<IValuelon>>();
                    // match selonlf.modulelon.forward_is(&input_batch_telonnsors) {
                    match selonlf.modulelon.melonthod_is("forward_selonrvelon", &input_batch_telonnsors) {
                        Ok(relons) => TorchModelonl::output_to_velonc(relons, &mut buf),
                        elonrr(elon) => {
                            NUM_RelonQUelonSTS_FAILelonD.inc_by(total_lelonn);
                            NUM_RelonQUelonSTS_FAILelonD_BY_MODelonL
                                .with_labelonl_valuelons(&[&MODelonL_SPelonCS[selonlf.modelonl_idx]])
                                .inc_by(total_lelonn);
                            INFelonRelonNCelon_FAILelonD_RelonQUelonSTS_BY_MODelonL
                                .with_labelonl_valuelons(&[&MODelonL_SPelonCS[selonlf.modelonl_idx]])
                                .inc_by(total_lelonn);
                            panic!("{modelonl}: {elon:?}", modelonl = MODelonL_SPelonCS[selonlf.modelonl_idx], elon = elon);
                        }
                    }
                }
                batch_elonnds[i] = buf.lelonn();
            }
            (
                velonc![TelonnsorRelonturnelonnum::FloatTelonnsorRelonturn(Box::nelonw(buf))],
                velonc![batch_elonnds],
            )
        }
        #[inlinelon(always)]
        fn modelonl_idx(&selonlf) -> usizelon {
            selonlf.modelonl_idx
        }
        #[inlinelon(always)]
        fn velonrsion(&selonlf) -> i64 {
            selonlf.velonrsion
        }
    }
}
