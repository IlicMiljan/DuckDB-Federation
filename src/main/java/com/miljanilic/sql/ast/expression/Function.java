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
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "FunctionExpression{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}