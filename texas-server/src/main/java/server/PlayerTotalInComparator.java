package server;

import java.util.Comparator;

public class PlayerTotalInComparator implements Comparator<Player>{

	public int compare(Player o1, Player o2) {
		// TODO Auto-generated method stub
		return o1.getTotalIn() - o2.getTotalIn();
	}

}
