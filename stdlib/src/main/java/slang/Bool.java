package slang;

import slang.visitors.Truth;

import java.util.function.IntPredicate;

/**
 * @author Antoine Chauvin
 */
public final class Bool {
    public static void load(EvaluationContextInterface context) {
        Stdlib.loadFn(context, SName.of("="), Bool::eq, true);
        Stdlib.loadFn(context, SName.of("!="), Bool::neq, true);
        Stdlib.loadFn(context, SName.of("not"), Bool::not, true);
        Stdlib.loadFn(context, SName.of("<="), Bool::lte, true);
        Stdlib.loadFn(context, SName.of("<"), Bool::lt, true);
        Stdlib.loadFn(context, SName.of(">="), Bool::gte, true);
        Stdlib.loadFn(context, SName.of(">"), Bool::gt, true);
        Stdlib.loadFn(context, SName.of("truth?"), Bool::truthTest, true);
        Stdlib.loadFn(context, SName.of("nil?"), Bool::nilTest, true);
        Stdlib.loadFn(context, SName.of("many?"), Bool::manyTest, true);
        Stdlib.loadFn(context, SName.of("list?"), Bool::listTest, true);
        Stdlib.loadFn(context, SName.of("map?"), Bool::mapTest, true);
        Stdlib.loadFn(context, SName.of("set?"), Bool::setTest, true);
        Stdlib.loadFn(context, SName.of("vec?"), Bool::vecTest, true);
        Stdlib.loadFn(context, SName.of("int?"), Bool::intTest, true);
        Stdlib.loadFn(context, SName.of("dec?"), Bool::decTest, true);
        Stdlib.loadFn(context, SName.of("str?"), Bool::strTest, true);
        Stdlib.loadFn(context, SName.of("atom?"), Bool::atomTest, true);
        Stdlib.loadFn(context, SName.of("fn?"), Bool::fnTest, true);
    }

    private static Object _bool(boolean b) {
        return b ? SAtom.of("true") : SList.nil;
    }

    public static Object eq(EvaluationContextInterface context, SList arguments) {
        Object lhs = arguments.head();
        Object rhs = arguments.tail().head();

        if (lhs instanceof Number && rhs instanceof Number) {
            long l = ((Number) lhs).longValue();
            long r = ((Number) rhs).longValue();
            return _bool(l == r);
        }

        return _bool(lhs.equals(rhs));
    }

    public static Object neq(EvaluationContextInterface context, SList arguments) {
        Object lhs = arguments.head();
        Object rhs = arguments.tail().head();

        if (lhs instanceof Number && rhs instanceof Number) {
            long l = ((Number) lhs).longValue();
            long r = ((Number) rhs).longValue();
            return _bool(l != r);
        }

        return _bool(!lhs.equals(rhs));
    }

    public static Object not(EvaluationContextInterface context, SList arguments) {
        return _bool(!Truth.truthy(arguments.head()));
    }

    private static Object _cmp(SList arguments, IntPredicate function) {
        Object lhs = arguments.head();
        Object rhs = arguments.tail().head();

        if (!lhs.getClass().isAssignableFrom(rhs.getClass()) || !rhs.getClass().isAssignableFrom(lhs.getClass())) {
            throw new IllegalArgumentException(String.format("%s and %s are not comparable",
                    lhs.getClass().getName(), rhs.getClass().getName()));
        }

        if (!(lhs instanceof Comparable)) {
            throw new IllegalArgumentException(String.format("%s and %s are not comparable",
                    lhs.getClass().getName(), rhs.getClass().getName()));
        }

        //noinspection unchecked
        return _bool(function.test(((Comparable) lhs).compareTo(rhs)));
    }

    public static Object lte(EvaluationContextInterface context, SList arguments) {
        return _cmp(arguments, x -> x <= 0);
    }

    public static Object lt(EvaluationContextInterface context, SList arguments) {
        return _cmp(arguments, x -> x < 0);
    }

    public static Object gte(EvaluationContextInterface context, SList arguments) {
        return _cmp(arguments, x -> x >= 0);
    }

    public static Object gt(EvaluationContextInterface context, SList arguments) {
        return _cmp(arguments, x -> x > 0);
    }

    public static Object truthTest(EvaluationContextInterface context, SList arguments) {
        return _bool(Truth.truthy(arguments.head()));
    }

    public static Object nilTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() == SList.nil);
    }

    public static Object manyTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SMany);
    }

    public static Object listTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SList);
    }

    public static Object mapTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SMap);
    }

    public static Object setTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SSet);
    }

    public static Object vecTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SVector);
    }

    public static Object intTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof Byte
                || arguments.head() instanceof Short
                || arguments.head() instanceof Integer
                || arguments.head() instanceof Long);
    }

    public static Object decTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof Float || arguments.head() instanceof Double);
    }

    public static Object strTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof String);
    }

    public static Object atomTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SName);
    }

    public static Object fnTest(EvaluationContextInterface context, SList arguments) {
        return _bool(arguments.head() instanceof SFunction);
    }
}
