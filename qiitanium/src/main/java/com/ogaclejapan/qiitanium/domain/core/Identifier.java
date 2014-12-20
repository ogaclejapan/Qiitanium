package com.ogaclejapan.qiitanium.domain.core;

public abstract class Identifier {

    public final String id;

    protected Identifier(String id) {
        this.id = id;
    }

    public static WithT0 create(String id) {
        return new WithT0(id);
    }

    public static <T1> WithT1<T1> create(
            String id, T1 t1) {
        return new WithT1<T1>(id, t1);
    }

    public static <T1, T2> WithT2<T1, T2> create(
            String id, T1 t1, T2 t2) {
        return new WithT2<T1, T2>(id, t1, t2);
    }

    public static <T1, T2, T3> WithT3<T1, T2, T3> create(
            String id, T1 t1, T2 t2, T3 t3) {
        return new WithT3<T1, T2, T3>(id, t1, t2, t3);
    }

    public static <T1, T2, T3, T4> WithT4<T1, T2, T3, T4> create(
            String id, T1 t1, T2 t2, T3 t3, T4 t4) {
        return new WithT4<T1, T2, T3, T4>(id, t1, t2, t3, t4);
    }

    public static <T1, T2, T3, T4, T5> WithT5<T1, T2, T3, T4, T5> create(
            String id, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new WithT5<T1, T2, T3, T4, T5>(id, t1, t2, t3, t4, t5);
    }

    public static <T1, T2, T3, T4, T5, T6> WithT6<T1, T2, T3, T4, T5, T6> create(
            String id, T1 t1, T2 t2, T3 t3, T4 t4,
            T5 t5, T6 t6) {
        return new WithT6<T1, T2, T3, T4, T5, T6>(id, t1, t2, t3, t4, t5, t6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> WithT7<T1, T2, T3, T4, T5, T6, T7> create(
            String id, T1 t1, T2 t2, T3 t3, T4 t4,
            T5 t5, T6 t6, T7 t7) {
        return new WithT7<T1, T2, T3, T4, T5, T6, T7>(id, t1, t2, t3, t4, t5, t6, t7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> WithT8<T1, T2, T3, T4, T5, T6, T7, T8> create(
            String id, T1 t1, T2 t2, T3 t3, T4 t4,
            T5 t5, T6 t6, T7 t7, T8 t8) {
        return new WithT8<T1, T2, T3, T4, T5, T6, T7, T8>(id, t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> WithT9<T1, T2, T3, T4, T5, T6, T7, T8, T9> create(
            String id, T1 t1, T2 t2, T3 t3, T4 t4,
            T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return new WithT9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(id, t1, t2, t3, t4, t5, t6, t7, t8,
                t9);
    }

    public static class WithT0 extends Identifier {

        private WithT0(String id) {
            super(id);
        }
    }

    public static class WithT1<T1> extends WithT0 {

        public final T1 t1;

        private WithT1(String id, final T1 t1) {
            super(id);
            this.t1 = t1;
        }
    }

    public static class WithT2<T1, T2> extends WithT1<T1> {

        public final T2 t2;

        private WithT2(String id, T1 t1, T2 t2) {
            super(id, t1);
            this.t2 = t2;
        }
    }

    public static class WithT3<T1, T2, T3> extends WithT2<T1, T2> {

        public final T3 t3;

        private WithT3(String id, T1 t1, T2 t2, T3 t3) {
            super(id, t1, t2);
            this.t3 = t3;
        }
    }

    public static class WithT4<T1, T2, T3, T4> extends WithT3<T1, T2, T3> {

        public final T4 t4;

        private WithT4(String id, T1 t1, T2 t2, T3 t3, T4 t4) {
            super(id, t1, t2, t3);
            this.t4 = t4;
        }
    }

    public static class WithT5<T1, T2, T3, T4, T5> extends WithT4<T1, T2, T3, T4> {

        public final T5 t5;

        private WithT5(String id, T1 t1, T2 t2, T3 t3, T4 t4,
                T5 t5) {
            super(id, t1, t2, t3, t4);
            this.t5 = t5;
        }
    }

    public static class WithT6<T1, T2, T3, T4, T5, T6>
            extends WithT5<T1, T2, T3, T4, T5> {

        public final T6 t6;

        private WithT6(String id, T1 t1, T2 t2, T3 t3, T4 t4,
                T5 t5, T6 t6) {
            super(id, t1, t2, t3, t4, t5);
            this.t6 = t6;
        }
    }

    public static class WithT7<T1, T2, T3, T4, T5, T6, T7>
            extends WithT6<T1, T2, T3, T4, T5, T6> {

        public final T7 t7;

        private WithT7(String id, T1 t1, T2 t2, T3 t3, T4 t4,
                T5 t5, T6 t6, T7 t7) {
            super(id, t1, t2, t3, t4, t5, t6);
            this.t7 = t7;
        }
    }

    public static class WithT8<T1, T2, T3, T4, T5, T6, T7, T8>
            extends WithT7<T1, T2, T3, T4, T5, T6, T7> {

        public final T8 t8;

        private WithT8(String id, T1 t1, T2 t2, T3 t3, T4 t4,
                T5 t5, T6 t6, T7 t7, T8 t8) {
            super(id, t1, t2, t3, t4, t5, t6, t7);
            this.t8 = t8;
        }
    }

    public static class WithT9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
            extends WithT8<T1, T2, T3, T4, T5, T6, T7, T8> {

        public final T9 t9;

        private WithT9(String id, T1 t1, T2 t2, T3 t3, T4 t4,
                T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
            super(id, t1, t2, t3, t4, t5, t6, t7, t8);
            this.t9 = t9;
        }
    }

}
