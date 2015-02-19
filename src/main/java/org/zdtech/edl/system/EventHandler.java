package org.zdtech.edl.system;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.CompoundExpression;
import org.springframework.expression.spel.ast.Literal;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.zdtech.edl.EDLCallback;
import org.zdtech.edl.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Schekalev
 * @since 18.02.15
 */
public class EventHandler implements EDLParameterListener {
    private Event event;
    private EDLCallback callback;
    private SpelExpression spelExpression;
    private EvaluationContext evaluationContext;
    private EDLSystem system;
    private List<EDLParameter> subscribedParameters;

    public EventHandler(EDLSystem system, Event event, EDLCallback callback) {
        this.event = event;
        this.callback = callback;
        this.system = system;
        this.subscribedParameters = new ArrayList<>();
        this.evaluationContext = new EDLEvaluationContext(this.system);
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(event.getExpression());
        if (expression instanceof SpelExpression) {
            this.spelExpression = (SpelExpression) expression;
            List<String> propertiesNames = getPropertyNames(spelExpression.getAST());

            if (isAllParametersExists(propertiesNames)) {
                try {
                    Class valueType = expression.getValueType(this.evaluationContext);
                    if (valueType.equals(Boolean.class) || valueType.equals(Boolean.TYPE)) {
                        subscribeParameters(propertiesNames);
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception exp) {
                    throw new RuntimeException("Not right expression: " + event.getExpression() +"\n "+ exp);
                }

            } else throw new RuntimeException("Not all properties exists");
        }

    }

    private void subscribeParameters(List<String> propertiesNames) {
        for (String propertyName : propertiesNames) {
            EDLParameter parameter = this.system.getParameterByName(propertyName);
            parameter.addParameterListener(this);
            this.subscribedParameters.add(parameter);
        }
    }

    private boolean isAllParametersExists(List<String> propertiesNames) {
        for (String propertyName : propertiesNames) {
            if (!this.system.isParameterExists(propertyName))
                return false;
        }
        return true;
    }

    private List<String> getPropertyNames(SpelNode ast) {
        List<String> propertyNames = new ArrayList<>();
        if (ast.getChildCount() > 0) {
            for (int i = 0; i < ast.getChildCount(); i++) {
                if (ast.getChild(i) instanceof CompoundExpression) {
                    CompoundExpression compoundExpression = (CompoundExpression) ast.getChild(i);
                    propertyNames.add(compoundExpression.toStringAST());
                } else {
                    propertyNames.addAll(getPropertyNames(ast.getChild(i)));
                }
            }
        } else {
            if (!(ast instanceof Literal))
                propertyNames.add(ast.toStringAST());
        }
        return propertyNames;
    }

    @Override
    public void onParameterChanged(Object value) {
        if (this.spelExpression != null) {
            Boolean result = (Boolean) this.spelExpression.getValue(this.evaluationContext);
            if (result) {
                callback.onEventHappened(event);
            }
        }
    }

    public void dispose() {
        for (EDLParameter parameter : this.subscribedParameters) {
            parameter.removeParameterListener(this);
        }
    }
}
