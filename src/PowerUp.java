import java.net.URL;


public class PowerUp  extends Sprite{

	int reward = 50;
	int type = 0;
	
	public PowerUp(int x, int y, int height, int width, URL imageName) {
		super(x, y, height, width, imageName);
		// TODO Auto-generated constructor stub
	}

	public int getReward() { return reward; }
	public void setReward(int reward) { this.reward = reward; }
	public int getType() { return type; }
	public void setType(int type) { this.type = type; }
	
	public void use(Player player)
	{
		switch(type)
		{
		case 0: type = 0;
			player.heal(reward);
			break;
		default:
			System.out.println("Unknow Power Up");
		}
	}

}
