package com.miljanilic.sql.parser.resolver;

import com.miljanilic.sql.ast.node.Join;

public class JSQLJoinTypeResolver {

    public Join.JoinType resolveJoinType(net.sf.jsqlparser.statement.select.Join join) {
        if (join.isInner()) {
            return Join.JoinType.INNER;
        } else if (join.isLeft()) {
            return Join.JoinType.LEFT;
        } else if (join.isRight()) {
            return Join.JoinType.RIGHT;
        } else if (join.isFull()) {
            return Join.JoinType.FULL;
        } else if (join.isCross()) {
            return Join.JoinType.CROSS;
        } else if (join.isNatural()) {
            throw new UnsupportedOperationException("NATURAL JOIN is not supported.");
        } else if (join.isSemi()) {
            throw new UnsupportedOperationException("SEMI JOIN is not supported.");
        } else if (join.isOuter()) {
            throw new UnsupportedOperationException("OUTER JOIN without LEFT/RIGHT/FULL specification is not supported.");
        } else {
            throw new UnsupportedOperationException("Unknown join type.");
        }
    }
}
