package com.ogaclejapan.qiitanium;

final class Modules {

    static Object[] list(Qiitanium app) {
        return new Object[]{
                new QiitaniumModule(app)
        };
    }

    private Modules() {
        // No instances.
    }

}
