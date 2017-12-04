package santa.santa2015;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;


public class Santa21 {

	public static class Person {
		
		public Person(int hitPoints, int damage, int armor) {
			this.hitPoints = hitPoints;
			this.damage = damage;
			this.armor = armor;
		}
		
		int hitPoints;
		int damage;
		int armor;
		int cost;
		
		public void addItem(Item item) {
			damage += item.damage;
			armor += item.armor;
			cost += item.cost;
		}
		
		public void removeItem(Item item) {
			damage -= item.damage;
			armor -= item.armor;
			cost -= item.cost;
		}
	}
	
	public static class Item {
		
		public Item(int cost, int damage, int armor) {
			this.cost = cost;
			this.damage = damage;
			this.armor = armor;
		}
		
		int cost;
		int damage;
		int armor;
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + armor;
			result = prime * result + cost;
			result = prime * result + damage;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Item other = (Item) obj;
			if (armor != other.armor)
				return false;
			if (cost != other.cost)
				return false;
			if (damage != other.damage)
				return false;
			return true;
		}
		
		
	}
		
	static int min = Integer.MAX_VALUE;
	static int max = 0;
	
	public static void main(String[] args) {

		Person boss = new Person(100, 8, 2);
		Person player = new Person(100, 0, 0);

		List<Item> weapons = new ArrayList<Item>();
		weapons.add(new Item(8, 4, 0));
		weapons.add(new Item(10, 5, 0));
		weapons.add(new Item(25, 6, 0));
		weapons.add(new Item(40, 7, 0));
		weapons.add(new Item(74, 8, 0));
		
		List<Item> armors = new ArrayList<Item>();
		armors.add(new Item(13, 0, 1));
		armors.add(new Item(31, 0, 2));
		armors.add(new Item(53, 0, 3));
		armors.add(new Item(75, 0, 4));
		armors.add(new Item(102, 0, 5));

		List<Item> rings = new ArrayList<Item>();
		rings.add(new Item(25, 1, 0));
		rings.add(new Item(50, 2, 0));
		rings.add(new Item(100, 3, 0));
		rings.add(new Item(20, 0, 1));
		rings.add(new Item(40, 0, 2));
		rings.add(new Item(80, 0, 3));
		
		for (Item weapon : weapons) {
			player.addItem(weapon);
			fight(player, boss);
			
			for (Item ring : rings) {
				player.addItem(ring);
				fight(player, boss);
				for (Item ring2 : rings) {
					if (!ring2.equals(ring)) {
						player.addItem(ring2);
						fight(player, boss);
						player.removeItem(ring2);
					}
				}
				player.removeItem(ring);
			}
			
			for (Item armor : armors) {				
				player.addItem(armor);
				fight(player, boss);
				for (Item ring : rings) {
					player.addItem(ring);
					fight(player, boss);
					for (Item ring2 : rings) {
						if (!ring2.equals(ring)) {
							player.addItem(ring2);
							fight(player, boss);
							player.removeItem(ring2);
						}
					}
					player.removeItem(ring);
				}
				player.removeItem(armor);
			}
			player.removeItem(weapon);
		}
		System.out.println("Minimum " + min);
		System.out.println("Maximum " + max);
	}
	
	public static void fight(Person player, Person boss) {
		int bossPoints = boss.hitPoints;
		int playerPoints = player.hitPoints;
		int round = 0;
		while (player.hitPoints > 0 && boss.hitPoints > 0) {
			if (round % 2 == 0) {
				setDamage(player, boss);
			} else {
				setDamage(boss, player);
			}
			round++;
		}
		if (player.hitPoints > 0) {
			if (min > player.cost) {
				min = player.cost;
			}			
			System.out.println("player wins " + player.cost);
		}
		if (boss.hitPoints > 0) {
			if (max < player.cost) {
				max = player.cost;
			}			
			System.out.println("player loose " + player.cost);
		}
		player.hitPoints = playerPoints;
		boss.hitPoints = bossPoints;
	}
	


	public static void setDamage(Person attacker, Person defender) {
		int damage = attacker.damage - defender.armor;
		if (damage <= 0) {
			damage = 1;
		}
		defender.hitPoints -= damage;
	}

}
