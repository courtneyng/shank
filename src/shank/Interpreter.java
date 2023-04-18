package shank;

import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {

    HashMap <String, InterpreterDataType> localVars = new HashMap<>();

    /**
     * Add interpretFunction(FunctionNode) as a method to a new Interpreter class. Create a hash map
     * (name🡪IDT) for local variables. Loop over the constants and local variables and create new IDTs
     * and add them to the hash map. Finally, pass the hash map and the collection of StatementNode to
     * interpretBlock().
     * @param node - function node
     */
    public void interpretFunction(FunctionNode node, HashMap<String, InterpreterDataType> map) throws SyntaxErrorException {
        ArrayList<VariableNode> params = node.getParameters();
        ArrayList<VariableNode> variables = node.getVariables();
        ArrayList<StatementNode> statements = node.getStatements();

        parameterMap(map, params);
        constantsAndVariablesMap(map, variables);
        interpretBlock(map, statements);
        if(params != null){
            ArrayList<InterpreterDataType> arguments = new ArrayList<>();
            for (VariableNode param : params) {
                InterpreterDataType varData = map.get(param.getName());
                arguments.add(varData);
            }
            node.updateArguments(arguments);
        }
    }

    /**
     * interpretBlock should loop over the StatementNode collection. For each node type (use instanceOf)
     * call a method for that node type (IfNode, VariableReferenceNode,MathOpNode, etc.) Those methods
     * will vary in return type and parameters. InterpretBlock is a little bit of recursive magic – you
     * will be in the middle of this and call it again for loops or conditionals of any kind.
     */
    public void interpretBlock(HashMap<String, InterpreterDataType> map, ArrayList<StatementNode> statementNodeArrayList) throws SyntaxErrorException{
        if(statementNodeArrayList != null){
            for (StatementNode statementNode : statementNodeArrayList) {
                if (statementNode instanceof IfNode ifNode) {
                    ifNodeFunction(map, ifNode);
                } else if (statementNode instanceof ForNode forNode) {
                    forNodeFunction(map, forNode);
                } else if (statementNode instanceof RepeatNode repeatNode) {
                    repeatNodeFunction(map, repeatNode);
                } else if (statementNode instanceof WhileNode whileNode) {
                    whileNodeFunction(map, whileNode);
                } else if (statementNode instanceof AssignmentNode assignmentNode) {
                    assignmentNodeFunction(map, assignmentNode);
                } else if (statementNode instanceof FunctionCallNode functionCallNode) {
                    functionCallNodeFunction(map, functionCallNode);
                }
            }
        }
    }

    /**
     * Creates a map of parameters
     * @param paramMap - the parameter map
     * @param params - the params to put in
     * @throws SyntaxErrorException - throws exceptions
     */
    private void parameterMap(HashMap<String, InterpreterDataType> paramMap, ArrayList<VariableNode> params) throws SyntaxErrorException {
        if(params != null){
            for (VariableNode current : params) {
                String name = current.getName();

                switch (current.getType()) {
                    case INTEGER -> {
                        if (current.getValue() == null) {
                            IntegerDataType intData = new IntegerDataType(0, current.isChangeable());
                            paramMap.put(name, intData);
                        } else {
                            IntegerNode intNode = (IntegerNode) current.getValue();
                            IntegerDataType intData = new IntegerDataType(intNode.getValue(), current.isChangeable());
                            paramMap.put(name, intData);
                        }
                    }
                    case REAL -> {
                        if (current.getValue() == null) {
                            RealDataType realData = new RealDataType(0, current.isChangeable());
                            paramMap.put(name, realData);
                        } else {
                            RealNode realNode = (RealNode) current.getValue();
                            RealDataType realData = new RealDataType(realNode.getValue(), current.isChangeable());
                            paramMap.put(name, realData);
                        }
                    }
                    case CHARACTER -> {
                        if (current.getValue() == null) {
                            CharacterDataType charData = new CharacterDataType(' ', current.isChangeable());
                            paramMap.put(name, charData);
                        } else {
                            CharacterNode charNode = (CharacterNode) current.getValue();
                            CharacterDataType charData = new CharacterDataType(charNode.getValue(), current.isChangeable());
                            paramMap.put(name, charData);
                        }
                    }
                    case STRING -> {
                        if (current.getValue() == null) {
                            StringDataType strData = new StringDataType("", current.isChangeable());
                            paramMap.put(name, strData);
                        } else {
                            StringNode strNode = (StringNode) current.getValue();
                            StringDataType strData = new StringDataType(strNode.getValue(), current.isChangeable());
                            paramMap.put(name, strData);
                        }
                    }
                    case BOOLEAN -> {
                        if (current.getValue() == null) {
                            BooleanDataType boolData = new BooleanDataType(false, current.isChangeable());
                            paramMap.put(name, boolData);
                        } else {
                            BooleanNode boolNode = (BooleanNode) current.getValue();
                            BooleanDataType boolData = new BooleanDataType(boolNode.getValue(), current.isChangeable());
                            paramMap.put(name, boolData);
                        }
                    }
                    case ARRAY -> {
                        int startIndex = current.getFromInt(), endIndex = current.getToInt();
                        ArrayDataType arrData;

                        if (current.getType() != null) {
                            switch (current.getType()) {
                                case INTEGER -> {
                                    arrData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, startIndex, endIndex, current.isChangeable());
                                    if (current.getArrValue(startIndex, VariableNode.varType.INTEGER) != null) {
                                        ArrayList<InterpreterDataType> arrDataList = arrData.getData();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            IntegerNode atIndex = (IntegerNode) current.getArrValue(j, VariableNode.varType.INTEGER);
                                            IntegerDataType dataAtIndex = new IntegerDataType(atIndex.getValue(), current.isChangeable());
                                            arrDataList.set(j, dataAtIndex);
                                        }
                                        arrData.setData(arrDataList);
                                    }
                                }
                                case REAL -> {
                                    arrData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, startIndex, endIndex, current.isChangeable());
                                    if (current.getArrValue(startIndex, VariableNode.varType.REAL) != null) {
                                        ArrayList<InterpreterDataType> arrDataList = arrData.getData();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            RealNode atIndex = (RealNode) current.getArrValue(j, VariableNode.varType.REAL);
                                            RealDataType dataAtIndex = new RealDataType(atIndex.getValue(), current.isChangeable());
                                            arrDataList.set(j, dataAtIndex);
                                        }
                                        arrData.setData(arrDataList);
                                    }
                                }
                                case CHARACTER -> {
                                    arrData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, startIndex, endIndex, current.isChangeable());
                                    if (current.getArrValue(startIndex, VariableNode.varType.CHARACTER) != null) {
                                        ArrayList<InterpreterDataType> arrDataList = arrData.getData();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            CharacterNode atIndex = (CharacterNode) current.getArrValue(j, VariableNode.varType.CHARACTER);
                                            CharacterDataType dataAtIndex = new CharacterDataType(atIndex.getValue(), current.isChangeable());
                                            arrDataList.set(j, dataAtIndex);
                                        }
                                        arrData.setData(arrDataList);
                                    }
                                }
                                case STRING -> {
                                    arrData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, startIndex, endIndex, current.isChangeable());
                                    if (current.getArrValue(startIndex, VariableNode.varType.STRING) != null) {
                                        ArrayList<InterpreterDataType> arrDataList = arrData.getData();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            StringNode atIndex = (StringNode) current.getArrValue(j, VariableNode.varType.STRING);
                                            StringDataType dataAtIndex = new StringDataType(atIndex.getValue(), current.isChangeable());
                                            arrDataList.set(j, dataAtIndex);
                                        }
                                        arrData.setData(arrDataList);
                                    }
                                }
                                case BOOLEAN -> {
                                    arrData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, startIndex, endIndex, current.isChangeable());
                                    if (current.getArrValue(startIndex, VariableNode.varType.BOOLEAN) != null) {
                                        ArrayList<InterpreterDataType> arrDataList = arrData.getData();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            BooleanNode atIndex = (BooleanNode) current.getArrValue(j, VariableNode.varType.BOOLEAN);
                                            BooleanDataType dataAtIndex = new BooleanDataType(atIndex.getValue(), current.isChangeable());
                                            arrDataList.set(j, dataAtIndex);
                                        }
                                        arrData.setData(arrDataList);
                                    }
                                }
                                default -> throw new SyntaxErrorException("[Interpreter parameterMap] Exception: Needs proper data type (int, real, char, string, bool)");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds constants and variables to the map
     * @param map - the var map
     * @param arr - the arraylsit of var nodes
     * @throws SyntaxErrorException - if theres a type error
     */
    public void constantsAndVariablesMap(HashMap<String, InterpreterDataType> map, ArrayList<VariableNode> arr) throws SyntaxErrorException {
        if(arr != null){
            for (VariableNode current : arr) {
                if (current.isChangeable()) variableMap(map, current);
                else constantMap(map, current);
            }
        }
    }

    /**
     * Adds constatns to map
     * @param map - the var map
     * @param varNode - the var node to add
     * @throws SyntaxErrorException - if theres a type error
     */
    private void constantMap(HashMap<String, InterpreterDataType> map, VariableNode varNode) throws SyntaxErrorException {
        String name = varNode.getName();
        switch(varNode.getType()){
            case INTEGER -> {
                IntegerNode intNode = (IntegerNode) varNode.getValue();
                IntegerDataType intData = new IntegerDataType(intNode.getValue(), false);
                map.put(name, intData);
            }
            case REAL -> {
                RealNode realNode = (RealNode) varNode.getValue();
                RealDataType realData = new RealDataType(realNode.getValue(), false);
                map.put(name, realData);
            }
            case STRING -> {
                StringNode stringNode = (StringNode) varNode.getValue();
                StringDataType stringData = new StringDataType(stringNode.getValue(), false);
                map.put(name, stringData);
            }
            case CHARACTER -> {
                CharacterNode characterNode = (CharacterNode) varNode.getValue();
                CharacterDataType charData = new CharacterDataType(characterNode.getValue(), false);
                map.put(name, charData);
            }
            case BOOLEAN -> {
                BooleanNode booleanNode = (BooleanNode) varNode.getValue();
                BooleanDataType boolData = new BooleanDataType(booleanNode.getValue(), false);
                map.put(name, boolData);
            }
            case ARRAY -> {
                int start = varNode.getFromInt(), end = varNode.getToInt();
                ArrayDataType arrData;
                switch(varNode.getType()){
                    case INTEGER -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, start, end, varNode.isChangeable());
                    case REAL -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, start, end, varNode.isChangeable());
                    case STRING -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, start, end, varNode.isChangeable());
                    case CHARACTER -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, start, end, varNode.isChangeable());
                    case BOOLEAN -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, start, end, varNode.isChangeable());
                    default -> throw new SyntaxErrorException("[Interpreter constantNodesFunction] Exception: No array type");
                }
                map.put(name, arrData);
            }
            default -> throw new SyntaxErrorException("[Interpreter constantNodesFunction] Exception: No variable type");
        }
    }

    /**
     * Adds variables to map
     * @param map - the var map
     * @param varNode - the var node to add
     * @throws SyntaxErrorException = if theres a type error
     */
    private void variableMap(HashMap<String, InterpreterDataType> map, VariableNode varNode) throws SyntaxErrorException{
        String name = varNode.getName();
        switch(varNode.getType()){
            case INTEGER -> {
                IntegerDataType intData = new IntegerDataType(0, true);
                map.put(name, intData);
            }
            case REAL -> {
                RealDataType realData = new RealDataType(0, true);
                map.put(name, realData);
            }
            case STRING -> {
                StringDataType stringData = new StringDataType("", true);
                map.put(name, stringData);
            }
            case CHARACTER -> {
                CharacterDataType charData = new CharacterDataType(' ', true);
                map.put(name, charData);
            }
            case BOOLEAN -> {
                BooleanDataType booleanData = new BooleanDataType(false, true);
                map.put(name, booleanData);
            }
            case ARRAY -> {
                int start = varNode.getFromInt(), end = varNode.getToInt();
                ArrayDataType arrData;
                switch(varNode.getType()){
                    case INTEGER -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, start, end, varNode.isChangeable());
                    case REAL -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, start, end, varNode.isChangeable());
                    case STRING -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, start, end, varNode.isChangeable());
                    case CHARACTER -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, start, end, varNode.isChangeable());
                    case BOOLEAN -> arrData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, start, end, varNode.isChangeable());
                    default -> throw new SyntaxErrorException("[Interpreter constantNodesFunction] Exception: No array type");
                }
                map.put(name, arrData);
            }
            default -> throw new SyntaxErrorException("[Interpreter variableMap] Exception: Variable type incorrect");
        }
    }

    private Node expression(HashMap<String, InterpreterDataType> map, Node node){
        return null;
    }

    /**
     * handles boolean compare nodes
     * @param map = the hashmap
     * @param boolCompareNode - bool compare node
     * @return - boolean data type
     * @throws SyntaxErrorException - exceptions
     */
    private BooleanDataType booleanCompareNodeFunction(HashMap<String, InterpreterDataType> map, BooleanCompareNode boolCompareNode) throws SyntaxErrorException {
        Node left = expression(map, boolCompareNode.getLeftExpr()), right = expression(map, boolCompareNode.getRightExpr());
        boolean val = false;
        if(left instanceof IntegerNode leftInt){
            if(right instanceof IntegerNode rightInt){

                if(boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.EQUAL){
                    if(leftInt.getValue() == rightInt.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.GREATERTHAN) {
                    if(leftInt.getValue() > rightInt.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.GREATEROREQUAL) {
                    if(leftInt.getValue() >= rightInt.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.LESSTHAN) {
                    if(leftInt.getValue() < rightInt.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.LESSOREQUAL) {
                    if(leftInt.getValue() <= rightInt.getValue()) val = true;
                } else throw new SyntaxErrorException("[Interpreter booleanCompareNodeFunction] Incorrect operator syntax");
            } else throw new SyntaxErrorException("[Interpreter booleanCompareNodeFunction] Incorrect right data syntax");
        } else if (left instanceof RealNode leftReal) {
            if(right instanceof RealNode rightReal){

                if(boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.EQUAL){
                    if(leftReal.getValue() == rightReal.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.GREATERTHAN) {
                    if(leftReal.getValue() > rightReal.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.GREATEROREQUAL) {
                    if(leftReal.getValue() >= rightReal.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.LESSTHAN) {
                    if(leftReal.getValue() < rightReal.getValue()) val = true;
                } else if (boolCompareNode.getComparator() == BooleanCompareNode.comparisonType.LESSOREQUAL) {
                    if(leftReal.getValue() <= rightReal.getValue()) val = true;
                } else throw new SyntaxErrorException("[Interpreter booleanCompareNodeFunction] Incorrect operator syntax");
            } else throw new SyntaxErrorException("[Interpreter booleanCompareNodeFunction] Incorrect right data syntax");
        } else throw new SyntaxErrorException("[Interpreter booleanCompareNodeFunction] Incorrect left data syntax");
        return new BooleanDataType(val, false);
    }

    /**
     * handles variable reference nodee
     * @param map - the hashmap
     * @param varRefNode - var reference node
     * @return - interepreter data type
     * @throws SyntaxErrorException - exceptions
     */
    private InterpreterDataType variableReferenceNodeFunction(HashMap<String, InterpreterDataType> map, VariableReferenceNode varRefNode) throws SyntaxErrorException {
        if(varRefNode.getIndex() != null){
            Node arrIndex = varRefNode.getIndex();
            String name = varRefNode.getName();
            InterpreterDataType arrData =  map.get(name);
            if(arrData instanceof ArrayDataType arrayDataType){
                if(arrIndex instanceof IntegerNode intNode){
                    return arrayDataType.getIndexData(intNode.getValue());
                } else if (arrIndex instanceof MathOpNode mathOpNode) {
                    InterpreterDataType data = mathOpNodeFunction(map, mathOpNode);
                    if(data instanceof IntegerDataType intData){
                        return arrayDataType.getIndexData(intData.getValue());
                    } else{
                        throw new SyntaxErrorException("[Interpreter variableReferenceNodeFunction] Exception: Couldn't recognize data type");
                    }
                } else if (arrIndex instanceof VariableReferenceNode variableReferenceNode) {
                    InterpreterDataType data = variableReferenceNodeFunction(map, variableReferenceNode);
                    if(data instanceof IntegerDataType intData){
                        return arrayDataType.getIndexData(intData.getValue());
                    } else{
                        throw new SyntaxErrorException("[Interpreter variableReferenceNodeFunction] Exception: Couldn't recognize data type");
                    }
                }
            } else{
                throw new SyntaxErrorException("[Interpreter variableReferenceNodeFunction] Exception: Couldn't recognize data type");
            }
        } else{
            throw new SyntaxErrorException("[Interpreter variableReferenceNodeFunction] Exception: Index is not an array data type");
        }
        return null;
    }

    /**
     * handles mathop nodes
     * @param variableMap - the hashmap
     * @param opNode - the mathop node
     * @return - interpreter data type
     * @throws SyntaxErrorException - exceptions
     */
    private InterpreterDataType mathOpNodeFunction(HashMap<String, InterpreterDataType> variableMap, MathOpNode opNode) throws SyntaxErrorException{
        Node left = expression(variableMap, opNode.getLeft()), right = expression(variableMap, opNode.getRight());
        switch (left) {
            case StringNode leftStr when right instanceof StringNode -> {
                if (opNode.getOp() != MathOpNode.MathOp.ADD)
                    throw new SyntaxErrorException("[Interpreter mathOpNodeFunction] null");
                StringNode rightStr = (StringNode) right;
                String finalStr = leftStr.getValue() + rightStr.getValue();
                return new StringDataType(finalStr, false);
            }
            case IntegerNode leftReal when right instanceof IntegerNode -> {
                IntegerNode rightReal = (IntegerNode) right;
                int leftVal = leftReal.getValue();
                int rightVal = rightReal.getValue();
                int finalVal;

                switch (opNode.getOp()) {
                    case ADD -> finalVal = leftVal + rightVal;
                    case SUBTRACT -> finalVal = leftVal - rightVal;
                    case MULTIPLY -> finalVal = leftVal * rightVal;
                    case DIVIDE -> finalVal = leftVal / rightVal;
                    case MODULO -> finalVal = leftVal % rightVal;
                    default -> throw new SyntaxErrorException("[Interpreter mathOpNodeFunction] Unknown operator");
                }

                return new IntegerDataType(finalVal, false);
            }
            case RealNode leftReal when right instanceof RealNode -> {
                RealNode rightReal = (RealNode) right;
                float leftVal = leftReal.getValue();
                float rightVal = rightReal.getValue();
                float finalVal;

                switch (opNode.getOp()) {
                    case ADD -> finalVal = leftVal + rightVal;
                    case SUBTRACT -> finalVal = leftVal - rightVal;
                    case MULTIPLY -> finalVal = leftVal * rightVal;
                    case DIVIDE -> finalVal = leftVal / rightVal;
                    case MODULO -> finalVal = leftVal % rightVal;
                    default -> throw new SyntaxErrorException("[Interpreter mathOpNodeFunction] Unknown operator");
                }

                return new RealDataType(finalVal, false);
            }
            case null, default -> {
                return null;
            }
        }
    }

    /**
     * handles if case
     * @param map - the hashmap
     * @param ifNode = if nodes
     * @throws SyntaxErrorException - exceptions
     */
    private void ifNodeFunction(HashMap<String, InterpreterDataType> map, IfNode ifNode) throws SyntaxErrorException {
        BooleanCompareNode ifNodeCondition = ifNode.getCondition();
        BooleanDataType ifCondition = booleanCompareNodeFunction(map, ifNodeCondition);
        while(!ifCondition.getValue()){
            ifNode = ifNode.getNext();
            if(ifNode == null || ifNode.getCondition() == null) break;
            ifNodeCondition = ifNode.getCondition();
            ifCondition = booleanCompareNodeFunction(map, ifNodeCondition);
        }
        if(ifNode != null) interpretBlock(map, ifNode.getStatements());
    }

    /**
     * handles repeat nodes
     * @param map - the hashmap
     * @param repeatNode - repeat nodes
     * @throws SyntaxErrorException - exceptions
     */
    private void repeatNodeFunction(HashMap<String, InterpreterDataType> map, RepeatNode repeatNode) throws SyntaxErrorException {
        BooleanDataType boolCompare = booleanCompareNodeFunction(map, repeatNode.getCondition());
        do{
            interpretBlock(map, repeatNode.getStatements());
            boolCompare = booleanCompareNodeFunction(map, repeatNode.getCondition());
        } while (!boolCompare.getValue());
    }

    /**
     * handles for nodes
     * @param map - the hashmap
     * @param forNode = for nodes
     * @throws SyntaxErrorException - exception
     */
    private void forNodeFunction(HashMap<String, InterpreterDataType> map, ForNode forNode) throws SyntaxErrorException{
        Node fromNode = expression(map, forNode.getFrom());
        Node toNode = expression(map, forNode.getTo());
        if(!(fromNode instanceof IntegerNode && toNode instanceof IntegerNode)) throw new SyntaxErrorException("[Interpreter forNodeFunction] Exception: For loop value invalid");
        if(!(map.containsKey(forNode.getVariable().getName()))) throw new SyntaxErrorException("[Interpreter forNodeFunction] Exception: Int variable not declared");

        IntegerNode from = (IntegerNode) fromNode;
        IntegerNode to = (IntegerNode) toNode;
        int fromVal = from.getValue();
        int toVal = to.getValue();
        if(fromVal > toVal) throw new SyntaxErrorException("[Interpreter forNodeFunction] Exception: Starting index greater than end index");

        IntegerDataType fromData = new IntegerDataType(fromVal, true);
        String name = forNode.getVariable().getName();
        map.put(name, fromData);
        InterpreterDataType varData = map.get(name);
        IntegerDataType intVarData = (IntegerDataType) varData;
        while(intVarData.getValue() <= toVal){
            interpretBlock(map, forNode.getStatements());
            intVarData.setValue(intVarData.getValue() + 1);
        }
    }

    /**
     * handles while
     * @param map - the hashmap
     * @param whileNode - while nodes
     * @throws SyntaxErrorException - exceptions
     */
    private void whileNodeFunction(HashMap<String, InterpreterDataType> map, WhileNode whileNode) throws SyntaxErrorException {
        BooleanDataType boolCompare = booleanCompareNodeFunction(map, whileNode.getCondition());
        while(boolCompare.getValue()){
            interpretBlock(map, whileNode.getStatements());
            boolCompare = booleanCompareNodeFunction(map, whileNode.getCondition());
        }
    }
    private void assignmentNodeFunction(HashMap<String, InterpreterDataType> map, AssignmentNode assignmentNode) throws SyntaxErrorException {
        String target = assignmentNode.getTarget().getName();
        Node value = assignmentNode.getValue();
        InterpreterDataType assignVar = map.get(target);
        if(!(assignVar.isChangeable())) throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Exception: Value is a constant");
        if(value instanceof VariableReferenceNode){
            VariableReferenceNode varRefNode = (VariableReferenceNode) value;
            InterpreterDataType varRefData = variableReferenceNodeFunction(map, varRefNode);
            if(varRefData instanceof IntegerDataType){
                IntegerDataType intData = (IntegerDataType) varRefData;
                IntegerNode intNode = new IntegerNode(intData.getValue());
                value = intNode;
            } else if (varRefData instanceof RealDataType) {
                RealDataType realData = (RealDataType) varRefData;
                RealNode realNode = new RealNode(realData.getValue());
                value = realNode;
            } else if (varRefData instanceof StringDataType) {
                StringDataType stringData = (StringDataType)  varRefData;
                StringNode stringNode = new StringNode(stringData.getValue());
                value = stringNode;
            } else if (varRefData instanceof CharacterDataType) {
                CharacterDataType charData = (CharacterDataType) varRefData;
                CharacterNode charNode = new CharacterNode(charData.getValue());
                value = charNode;
            }
        }
        if(assignmentNode.getTarget().getIndex() != null){
            if(assignVar instanceof ArrayDataType){
                ArrayDataType arr = (ArrayDataType) assignVar;
                int arrIndex = 0;
                if(assignmentNode.getTarget().getIndex() instanceof IntegerNode){
                    IntegerNode indexNode = (IntegerNode) assignmentNode.getTarget().getIndex();
                    arrIndex = indexNode.getValue();
                } else if (assignmentNode.getTarget().getIndex() instanceof MathOpNode) {
                    MathOpNode mathOpIndex = (MathOpNode)  assignmentNode.getTarget().getIndex();
                    InterpreterDataType indexData = mathOpNodeFunction(map, mathOpIndex);
                    if(indexData instanceof IntegerDataType){
                        IntegerDataType intIndex = (IntegerDataType) indexData;
                        arrIndex = intIndex.getValue();
                        IntegerNode arrIndexNode = new IntegerNode(arrIndex);
                        assignmentNode.getTarget().setIndex(arrIndexNode);
                    } else throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Incorrect data type");
                } else if (assignmentNode.getTarget().getIndex() instanceof VariableReferenceNode) {
                    VariableReferenceNode varRefIndex = (VariableReferenceNode) assignmentNode.getTarget().getIndex();
                    if(map.containsKey(varRefIndex.getName())){
                        InterpreterDataType refData = map.get(varRefIndex.getName());
                        if (refData instanceof InterpreterDataType) {
                            IntegerDataType indexData = (IntegerDataType) refData;
                            arrIndex = indexData.getValue();
                        } else throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Exception: Incorrect data type");
                    }
                } else throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Exception: Incorrect data type");
                if(value instanceof MathOpNode){
                    MathOpNode mathOpNode = (MathOpNode) value;
                    InterpreterDataType mathExpr = mathOpNodeFunction(map, mathOpNode);
                    if(mathExpr instanceof IntegerDataType && arr.getType() == ArrayDataType.arrayDataType.INTEGER){
                        IntegerDataType intData = (IntegerDataType) mathExpr;
                        arr.setIndex(arrIndex, intData);
                        map.put(target, arr);
                    } else if (mathExpr instanceof RealDataType && arr.getType() == ArrayDataType.arrayDataType.REAL) {
                        RealDataType realData = (RealDataType)  mathExpr;
                        arr.setIndex(arrIndex, realData);
                        map.put(target, arr);
                    } else if (mathExpr instanceof StringDataType && arr.getType() == ArrayDataType.arrayDataType.STRING) {
                        StringDataType stringData = (StringDataType) mathExpr;
                        arr.setIndex(arrIndex, stringData);
                        map.put(target, arr);
                    } else throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Exception: Incorrect data type");
                } else if (value instanceof IntegerNode && arr.getType() == ArrayDataType.arrayDataType.INTEGER) {
                    IntegerNode assignIntVal = (IntegerNode) value;
                    int intVal = assignIntVal.getValue();
                    IntegerDataType intData = new IntegerDataType(intVal, false);
                    arr.setIndex(arrIndex, intData);
                    map.put(target, arr);
                } else if (value instanceof RealNode && arr.getType() == ArrayDataType.arrayDataType.REAL) {
                    RealNode assignRealVal = (RealNode) value;
                    float realVal = assignRealVal.getValue();
                    RealDataType realData = new RealDataType(realVal, false);
                    arr.setIndex(arrIndex, realData);
                    map.put(target, arr);
                } else if (value instanceof StringNode && arr.getType() == ArrayDataType.arrayDataType.STRING) {
                    StringNode assignStringVal = (StringNode) value;
                    String stringVal = assignStringVal.getValue();
                    StringDataType stringData = new StringDataType(stringVal, false);
                    arr.setIndex(arrIndex, stringData);
                    map.put(target, arr);
                } else if (value instanceof CharacterNode && arr.getType() == ArrayDataType.arrayDataType.CHARACTER) {
                    CharacterNode assignCharVal = (CharacterNode) value;
                    char charVal = assignCharVal.getValue();
                    CharacterDataType charData = new CharacterDataType(charVal, false);
                    arr.setIndex(arrIndex, charData);
                    map.put(target, arr);
                } else if (value instanceof BooleanNode && arr.getType() == ArrayDataType.arrayDataType.BOOLEAN) {
                    BooleanNode assignBoolVal = (BooleanNode) value;
                    boolean boolVal = assignBoolVal.getValue();
                    BooleanDataType boolData = new BooleanDataType(boolVal, false);
                    arr.setIndex(arrIndex, boolData);
                    map.put(target, arr);
                } else throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Exception: Does not match array type");
            } else throw new SyntaxErrorException("[Interpreter assignmentNodeFunction] Exception: Index for non-array type");
        }
    }
    private void functionCallNodeFunction(HashMap<String, InterpreterDataType> map, FunctionCallNode functionCallNode){
        ArrayList<InterpreterDataType> functionCallArr = new ArrayList<>();
    }



}
