package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;

public class Function extends Expression {
    private final String functionName;
    private final Expression arguments;

    public Function(String functionName, Expression arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Expression getArguments() {
        return arguments;
    }

    @Override
    public <T, S> T accept(ASTVisitor<T, S> visitor, S context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "Function{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}