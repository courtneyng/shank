package shank;

    public class AssignmentNode extends StatementNode{
        private VariableReferenceNode target;
        private Node value;

        public AssignmentNode(VariableReferenceNode target, Node value){
            this.target = target;
            this.value = value;
        }

        public VariableReferenceNode getTarget(){
            return target;
        }

        public void setValue(Node newValue){
            value = newValue;
        }

        public Node getValue(){
            return value;
        }

        @Override
        public String toString() {
            return "Assignment [ Name: " + target + "Expression: " + value.toString() + "] ";
        }
    }
