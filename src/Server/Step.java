package Server;

public class Step {
				
				private Player player;
				private int move;
				
				public Step(Player p, int m) {
								this.player = p;
								this.move = m;
				}
				
				public Player getPlayer() {
								return this.player;
				}
				
				public int getMove() {
								return this.move;
				}
}