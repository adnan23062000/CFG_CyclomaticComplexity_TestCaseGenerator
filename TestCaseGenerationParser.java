package cfg;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCaseGenerationParser {
    ArrayList<String> Lines = new ArrayList<>();
    ArrayList<ConditionChecking> conditions = new ArrayList<>();
    ArrayList<Variable> variables = new ArrayList<>();
    SyntaxIdentifier checker = new SyntaxIdentifier();
    HashMap<String, Integer> map = new HashMap<>();

    public TestCaseGenerationParser(ArrayList<String> lines) {
        this.Lines = lines;
    }

    public void start() {
        for (String line : Lines) {
            int bracketFlag = 0;
            ConditionChecking temporaryCondition = new ConditionChecking();
            String variable = "";
            String comparison = "";
            if (checker.isIf(line) || checker.isElseIf(line) || checker.isWhile(line)) {
                for (int i = 0; i < line.length(); i++) {
                    if (bracketFlag == 1 && line.charAt(i) != ')' && line.charAt(i) != '{' && line.charAt(i) != '}') {
                        if (checker.isComparisonSign(line.charAt(i))) {
                            comparison = comparison + line.charAt(i);
                            if (!variable.isEmpty()) {
                                Variable temporary = new Variable(variable, 0);
                                temporaryCondition.addVariable(temporary);
                                variable = "";
                            }
                        } else if (line.charAt(i) != ' ') {
                            variable = variable + line.charAt(i);
                            if (!comparison.isEmpty()) {
                                temporaryCondition.setComparisonSign(comparison);
                                comparison = "";
                            }
                        }
                    }
                    if (line.charAt(i) == '(') bracketFlag = 1;
                }
                if (!variable.isEmpty()) {
                    Variable temporary = new Variable(variable, 0);
                    temporaryCondition.addVariable(temporary);
                    variable = "";
                }
                conditions.add(temporaryCondition);
            } else if (checker.isInt(line)) {
                //System.out.println(line);
                String temporary = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ',' || line.charAt(i) == ' ') {
                        Variable tempVariable = new Variable(temporary);
                        if (!temporary.equals("int") && !temporary.isEmpty()) {
                            variables.add(tempVariable);
                            map.put(temporary, 0);
                        }
                        temporary = "";
                    } else if (line.charAt(i) != ';') {
                        temporary = temporary + line.charAt(i);
                    }
                }
                Variable tempVariable = new Variable(temporary);
                if (!temporary.equals("int")) variables.add(tempVariable);
                map.put(temporary, 0);
            }
        }
        for (ConditionChecking condition : conditions) {
            System.out.println(condition.comparisonSign);
            condition.printVariables();
        }

        for (Variable variable : variables) {
            System.out.println(variable.variableName + " " + variable.value);
        }


    }

    public void statementCoverage() {
        System.out.println("\n\nStatement Coverage: \n");
        for (Variable variable : variables) {
            System.out.println(variable.variableName + ": " + 1);
        }
    }

    public void conditionCoverage() {
        System.out.println("\n\nCondition Coverage:");
        //System.out.println(conditions.size());

        for (int i = 0; i < conditions.size(); i++) {
            System.out.println("\n\nTest case: " + (i + 1) + "\n");
            generateConditionTests(i);
        }

    }

    public void generateConditionTests(int i) {

//        System.out.println(conditions.get(i).variables.get(0).variableName);
//        System.out.println(conditions.get(i).comparisonSign);
//        System.out.println(conditions.get(i).variables.get(1).variableName);

        if (conditions.get(i).comparisonSign.equals("==")) {
            System.out.println(conditions.get(i).variables.get(0).variableName + ": " + 2);
            if (!checkVariableInt(conditions.get(i).variables.get(1).variableName)) {
                System.out.println(conditions.get(i).variables.get(1).variableName + ": " + 2);
            }
        }

        else if (conditions.get(i).comparisonSign.equals("!=")) {
            System.out.println(conditions.get(i).variables.get(0).variableName + ": " + 2);
            if (!checkVariableInt(conditions.get(i).variables.get(1).variableName)) {
                System.out.println(conditions.get(i).variables.get(1).variableName + ": " + 4);
            }
        }

        else if (conditions.get(i).comparisonSign.equals(">")) {
            System.out.println(conditions.get(i).variables.get(0).variableName + ": " + 5);
            if (!checkVariableInt(conditions.get(i).variables.get(1).variableName)) {
                System.out.println(conditions.get(i).variables.get(1).variableName + ": " + 3);
            }
        }

        else if (conditions.get(i).comparisonSign.equals("<")) {
            System.out.println(conditions.get(i).variables.get(0).variableName + ": " + 1);
            if (!checkVariableInt(conditions.get(i).variables.get(1).variableName)) {
                System.out.println(conditions.get(i).variables.get(1).variableName + ": " + 5);
            }
        }
    }


    public static boolean isNumeric(String string) {
        int intValue;

        //System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            //System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            //System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    public boolean checkVariableInt(String str) {
        if (isNumeric(str))
            return true;
        else
            return false;
    }
}






