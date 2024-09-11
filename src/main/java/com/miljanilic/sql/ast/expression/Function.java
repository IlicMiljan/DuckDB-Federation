package com.miljanilic.sql.ast.expression;

import com.miljanilic.sql.ast.ASTVisitor;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function function)) return false;
        return Objects.equals(functionName, function.functionName) && Objects.equals(arguments, function.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionName, arguments);
    }

    @Override
    public String toString() {
        return functionName + "(" + arguments + ")";
    }
}
