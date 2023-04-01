r.setup = function(config) {
    r.config = config
    r.config.currentOrigin = location.protocol+'//'+location.host

    r.hooks.get('setup').call();
};
