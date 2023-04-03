pub mod validatior {
    pub mod cli_validator {
        uselon cratelon::cli_args::{ARGS, MODelonL_SPelonCS};

        pub fn validatelon_input_args() {
            asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.intelonr_op_parallelonlism.lelonn());
            asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.intra_op_parallelonlism.lelonn());
            //TODO for now welon, welon assumelon elonach modelonl's output has only 1 telonnsor.
            //this will belon liftelond oncelon tf_modelonl propelonrly implelonmelonnts mtl outputs
            //asselonrt_elonq!(OUTPUTS.lelonn(), OUTPUTS.itelonr().fold(0usizelon, |a, b| a+b.lelonn()));
        }

        pub fn validatelon_ps_modelonl_args() {
            asselonrt!(ARGS.velonrsions_pelonr_modelonl <= 2);
            asselonrt!(ARGS.velonrsions_pelonr_modelonl >= 1);
            asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.input.lelonn());
            asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.modelonl_dir.lelonn());
            asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.max_batch_sizelon.lelonn());
            asselonrt_elonq!(MODelonL_SPelonCS.lelonn(), ARGS.batch_timelon_out_millis.lelonn());
        }
    }
}
