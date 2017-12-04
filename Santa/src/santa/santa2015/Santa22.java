package santa.santa2015;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Santa22 {

    static List<Spell> spells = new ArrayList<Spell>();

    public static void doStartTurnActions(State state) {
        for (Iterator iterator = state.activeSpells.iterator(); iterator.hasNext();) {
            Spell spell = (Spell) iterator.next();        
            state.player.spellMana += spell.newMana;
            state.boss.hitPoints -= spell.damage;
            spell.duration--;
            if (spell.duration == 0) {
                state.player.armor -= spell.armor;
                iterator.remove();
            }
        }
    }

    static int min = Integer.MAX_VALUE;
    static int max = 0;

    public static void main(String[] args) {

        //   Person boss = new Person(51, 9, 0, 0);
        // Person player = new Person(50, 0, 0, 500);
        Person boss = new Person(14, 8, 0, 0);
        Person player = new Person(10, 0, 0, 250);
        /*
         * Magic Missile costs 53 mana. It instantly does 4 damage. Drain costs
         * 73 mana. It instantly does 2 damage and heals you for 2 hit points.
         * Shield costs 113 mana. It starts an effect that lasts for 6 turns.
         * While it is active, your armor is increased by 7. Poison costs 173
         * mana. It starts an effect that lasts for 6 turns. At the start of
         * each turn while it is active, it deals the boss 3 damage. Recharge
         * costs 229 mana. It starts an effect that lasts for 5 turns. At the
         * start of each turn while it is active, it gives you 101 new mana.
         * Effects all w
         */

        spells.add(new Spell(SpellName.RECHARGE, 229, 0, 0, 5, 0, 101));
        spells.add(new Spell(SpellName.MISSILE, 53, 4, 0, Integer.MAX_VALUE, 0, 0));
        spells.add(new Spell(SpellName.DRAIN, 73, 2, 2, Integer.MAX_VALUE, 0, 0));
        spells.add(new Spell(SpellName.SHIELD, 113, 0, 0, 6, 7, 0));
        spells.add(new Spell(SpellName.POISON, 173, 3, 0, 6, 0, 0));

        try {
            fight(new State(new ArrayList<Spell>(), boss, player));
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Minimum " + min);
        System.out.println("Maximum " + max);
        System.out.println("Spent mana " + (player.initialMana - min));
        System.out.println("Spent mana " + (player.initialMana - max));
    }

    public static boolean addSpell(Spell spell, State state) {
        if (state.activeSpells.contains(spell)) {
            return false;
        }

        if (((state.player.spellMana + state.player.mana) - spell.manaCosts) < 0) {
            return false;
        }
        int difference = spell.manaCosts - state.player.spellMana;
        if (difference <= 0) {
            state.player.spellMana -= spell.manaCosts;
        } else {
            state.player.spellMana = 0;
            state.player.mana -= difference;
        }
        state.player.armor += spell.armor;
        state.player.hitPoints += spell.heal;
        if (state.player.hitPoints > state.player.initialHitPoints) {
            state.player.hitPoints = state.player.initialHitPoints;
        }        
        
        if (spell.name != SpellName.MISSILE && spell.name != SpellName.DRAIN) {
            state.activeSpells.add(spell);
        } else {
            state.boss.hitPoints -= spell.damage;
        }
        return true;
    }

    public static boolean checkPlayers(State state) {
        if (state.player.hitPoints <= 0 || state.boss.hitPoints <= 0) {
            if (state.player.hitPoints > 0) {
                if (min > state.player.mana) {
                    min = state.player.mana;
                }
                if (max < state.player.mana) {
                    max = state.player.mana;
                }
                System.out.println("Player wins: " + state.player.mana + " mana");
                System.out.println(state);
            }
            return false;
        }
        return true;
    }

    public static void fight(State state) throws CloneNotSupportedException {

        for (Spell spell : spells) {

           
            State startState = new State();
            startState.boss = (Person) state.boss.clone();
            startState.player = (Person) state.player.clone();
            List<Spell> spells = new ArrayList<Spell>();
            for (Spell s : state.activeSpells) {
                spells.add((Spell) s.clone());
            }
            startState.activeSpells = spells;
            
            if (state.activeSpells.contains(new Spell(SpellName.POISON)) &&
                    state.activeSpells.contains(new Spell(SpellName.SHIELD))) {
                System.out.println("bla");
            }
            if (state.player.spellMana > 600) {
                state = startState;
                continue;
            }
                        
            // player turn
            doStartTurnActions(state);
            if (!checkPlayers(state)) {
                state = startState;
                continue;
            }
            if (!addSpell(spell, state)) {
                state = startState;
                continue;
            }
            if (!checkPlayers(state)) {
                state = startState;
                continue;
            }
            

            // boss turn
            doStartTurnActions(state);
            if (!checkPlayers(state)) {
                state = startState;
                continue;
            }
            setDamage(state.boss, state.player);
            if (!checkPlayers(state)) {
                state = startState;
                continue;
            }

            State newState = new State();
            newState.boss = (Person) state.boss.clone();
            newState.player = (Person) state.player.clone();
            List<Spell> spells2 = new ArrayList<Spell>();
            for (Spell s : state.activeSpells) {
                spells2.add((Spell) s.clone());
            }
            newState.activeSpells = spells2;
            fight(newState);
            state = startState;

        }
    }

    public static void setDamage(Person attacker, Person defender) {
        int damage = attacker.damage - defender.armor;
        if (damage <= 0) {
            damage = 1;
        }
        defender.hitPoints -= damage;
    }

    public static class Spell implements Cloneable {
        SpellName name;
        int manaCosts;
        int damage;
        int heal;
        int duration;
        int armor;
        int newMana;

        public Spell(SpellName name, int manaCosts, int damage, int heal, int duration, int armor,
                int newMana) {
            super();
            this.name = name;
            this.manaCosts = manaCosts;
            this.damage = damage;
            this.heal = heal;
            this.duration = duration;
            this.armor = armor;
            this.newMana = newMana;

        }

        public Spell(SpellName poison) {
            // TODO Auto-generated constructor stub
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
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
            Spell other = (Spell) obj;
            if (name != other.name)
                return false;
            return true;
        }


        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return "Spell [name=" + name + ", manaCosts=" + manaCosts + ", damage=" + damage
                    + ", heal=" + heal + ", duration=" + duration + ", armor=" + armor
                    + ", newMana=" + newMana + "]";
        }
        
        
    }

    public static enum SpellName {
        MISSILE, DRAIN, SHIELD, POISON, RECHARGE
    }

    public static class State {

        public State() {

        }

        public State(List<Spell> activeSpells, Person boss, Person player) {
            super();
            this.activeSpells = activeSpells;
            this.usedSpells = activeSpells;
            this.boss = boss;
            this.player = player;
        }

        List<Spell> activeSpells;
        List<Spell> usedSpells;
        Person boss;
        Person player;
        @Override
        public String toString() {
            return "State [activeSpells=" + activeSpells + ", boss=" + boss + ", player=" + player
                    + "]";
        }
        
        
    }

    public static class Person implements Cloneable {

        public Person(int hitPoints, int damage, int armor, int mana) {
            this.hitPoints = hitPoints;
            this.initialHitPoints = hitPoints;
            this.damage = damage;
            this.armor = armor;
            this.mana = mana;
            this.initialMana = mana;
        }

        int hitPoints;
        int damage;
        int armor;
        int cost;
        int mana;
        int spellMana;
        int initialMana;
        int initialHitPoints;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return super.clone();
        }

        public void doEndTurnActions() {

        }

        @Override
        public String toString() {
            return "Person [hitPoints=" + hitPoints + ", damage=" + damage + ", armor=" + armor
                    + ", cost=" + cost + ", mana=" + mana + ", spellMana=" + spellMana + "]";
        }
        
    }

}
