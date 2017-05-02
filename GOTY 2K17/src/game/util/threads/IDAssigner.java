package game.util.threads;

public class IDAssigner {
	private int baseId;
	
	public IDAssigner(int baseId){
		this.baseId = baseId;
	}
	public int next(){
		return baseId++;
	}
	public int getCurrentId(){
		return baseId;
	}
}
