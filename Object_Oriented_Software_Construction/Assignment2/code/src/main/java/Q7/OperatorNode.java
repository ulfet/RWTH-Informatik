package Q7;

public class OperatorNode implements Node
{
	Operator op;
	Node childA;
	Node childB;
	
	public OperatorNode(Operator op, Node childA, Node childB)
	{
		this.op = op;
		this.childA = childA;
		this.childB = childB;
	}
	
	public double evaluate()
	{
		return op.evaluate(childA.evaluate(), childB.evaluate());
	}
}