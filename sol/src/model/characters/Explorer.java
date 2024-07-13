package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import model.world.Cell;

public class Explorer extends Hero {

	public Explorer(String name, int maxHp, int attackDamage, int maxActions) {
		super(name, maxHp, attackDamage, maxActions);
	}

	@Override
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		super.useSpecial();
		for (Cell[] element : Game.map) {
			for(int j = 0; j < element.length; j++) {
				element[j].setVisible(true);
			}
		}
	}

}
