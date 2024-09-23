package com.miljanilic.sql.deparser.visitor.base;

import com.miljanilic.sql.ast.visitor.ASTVisitorAdapter;
import net.sf.jsqlparser.statement.select.Join;

public abstract class BaseAstToJSqlJoinVisitor extends ASTVisitorAdapter<Join, Void> {
    private final BaseAstToJSqlExpressionVisitor expressionVisitor;
    private final BaseAstToJSqlFromItemVisitor fromItemVisitor;

    public BaseAstToJSqlJoinVisitor(BaseAstToJSqlExpressionVisitor expressionVisitor, BaseAstToJSqlFromItemVisitor fromItemVisitor) {
        this.expressionVisitor = expressionVisitor;
        this.fromItemVisitor = fromItemVisitor;
    }

    @Override
    public Join visit(com.miljanilic.sql.ast.node.Join join, Void context) {
        Join jsqlJoin = new Join();

        jsqlJoin.setRightItem(join.getFrom().accept(fromItemVisitor, null));

        // Set the join type
        switch (join.getJoinType()) {
            case INNER:
                jsqlJoin.setInner(true);
                break;
            case LEFT:
                jsqlJoin.setLeft(true);
                break;
            case RIGHT:
                jsqlJoin.setRight(true);
                break;
            case FULL:
                jsqlJoin.setFull(true);
                break;
            case CROSS:
                jsqlJoin.setCross(true);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported join type: " + join.getJoinType());
        }

        if (join.getConditions() != null) {
            jsqlJoin.addOnExpression(join.getConditions().accept(expressionVisitor, null));
        }

        return jsqlJoin;
    }
}
