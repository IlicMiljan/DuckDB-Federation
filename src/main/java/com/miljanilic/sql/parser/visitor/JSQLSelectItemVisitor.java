package com.miljanilic.sql.parser.visitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.miljanilic.sql.ast.expression.Expression;
import com.miljanilic.sql.ast.node.Select;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;

@Singleton
public class JSQLSelectItemVisitor extends SelectItemVisitorAdapter<Select> {
    private final ExpressionVisitor<Expression> expressionVisitor;

    @Inject
    public JSQLSelectItemVisitor(ExpressionVisitor<Expression> expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public <S> Select visit(SelectItem<? extends net.sf.jsqlparser.expression.Expression> item, S context) {
        return new Select(
                item.getExpression().accept(this.expressionVisitor, context),
                item.getAlias() != null ? item.getAlias().getName() : null
        );
    }
}
