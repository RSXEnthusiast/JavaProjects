
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Joel Kalich
 * 
 * I did the Extra credit.
 * Tried to make my code a bit neater this time around, and it worked a bit,
 * though I think my fight method could be better.
 */
public class HeroesVersusMonsters {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean isFinished = false;
        while (!isFinished) {
            Hero hero;
            Monster monster;
            hero = selectHero();
            monster = selectMonster();
            int result = fight(hero, monster);
            switch (result) {
                case 1:
                    System.out.println("\nYou win!");
                    break;
                case 2:
                    System.out.println("\nYou lose!");
                    break;
                case 3:
                    System.out.println("\nYou sacrificed all honor and ran away, at least you didn't die,");
                    System.out.println("though some might consider this to be worse.");
                    break;
            }
            boolean isValid = false;
            while (!isValid) {
                System.out.print("\nPlay again?\n1. Yes\n2. No\n\nMake a selection: ");
                int selection = getUserSelection();
                System.out.println("_________________________________________________________________________");
                switch (selection) {
                    case 1:
                        System.out.println("\n\n\n\n");
                        isValid = true;
                        break;
                    case 2:
                        System.out.println("\nThanks for playing!\nCome back later, or don't, it's not like I'm being paid for this.\n");
                        isValid = true;
                        isFinished = true;
                        break;
                    default:
                        System.out.println("Make a valid selection.");
                }
            }
        }
    }

    private static Hero selectHero() {
        boolean isValid = false;
        Hero hero = null;
        while (!isValid) {
            System.out.println("1. Warrior\n2. Sorceress\n3. Theif\n4. God");
            System.out.print("Choose a Hero by entering the corresponding number: ");
            int heroSelect = getUserSelection();
            System.out.println("_________________________________________________________________________");
            System.out.println("");
            switch (heroSelect) {
                case 1:
                    System.out.println("You have selected Warrior!");
                    hero = new Warrior();
                    isValid = true;
                    break;
                case 2:
                    System.out.println("You have selected Sorceress!");
                    hero = new Sorceress();
                    isValid = true;
                    break;
                case 3:
                    System.out.println("You have selected Theif!");
                    hero = new Theif();
                    isValid = true;
                    break;
                case 4:
                    System.out.println("You have selected God!");
                    hero = new God();
                    isValid = true;
                    break;
                default:
                    System.out.println("Please select a valid Hero!");
                    break;
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a name for your hero: ");
        hero.setName(scanner.next());
        System.out.println("_________________________________________________________________________");
        return hero;
    }

    private static Monster selectMonster() {
        Random rand = new Random();
        int monsterSelect = rand.nextInt(3) + 1;
        Monster monster = null;
        switch (monsterSelect) {
            case 1:
                System.out.println("A wild Gremlin appears! FIGHT!!!");
                monster = new Gremlin();
                break;
            case 2:
                System.out.println("A wild Ogre appears! FIGHT!!!");
                monster = new Ogre();
                break;
            case 3:
                System.out.println("A wild Skeleton appears! FIGHT!!!");
                monster = new Skeleton();
                break;
        }
        return monster;
    }

    private static int getUserSelection() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static int fight(Hero hero, Monster monster) {
        hero.calcRoundTurns(monster);
        boolean isFinished = false;
        while (!isFinished) {
            for (int i = 0; i < hero.getRoundTurns(); i++) {
                System.out.println("\nYou have " + hero.getHealth() + " Health Points left.");
                System.out.println(monster.getName() + " has " + monster.getHealth() + " Health Points left.");
                System.out.println("You have " + (hero.getRoundTurns() - i) + " attack(s) left.\n");
                switch (hero.getCharacterType()) {
                    case "warrior":
                        boolean isValid = false;
                        while (!isValid) {
                            System.out.print("1. Attack!\n2. Crushing Blow!\n\nMake a selection: ");
                            int selection = getUserSelection();
                            System.out.println("_________________________________________________________________________");
                            switch (selection) {
                                case 1:
                                    monster.setHealth(monster.getHealth() - hero.attack());
                                    isValid = true;
                                    break;
                                case 2:
                                    monster.setHealth(monster.getHealth() - ((Warrior) hero).crushingBlow());
                                    isValid = true;
                                    break;
                                default:
                                    System.out.println("Make a valid entry!");
                                    break;
                            }
                        }
                        break;
                    case "sorceress":
                        isValid = false;
                        while (!isValid) {
                            System.out.print("1. Attack!\n2. Heal!\n\nMake a selection: ");
                            int selection = getUserSelection();
                            System.out.println("_________________________________________________________________________");
                            switch (selection) {
                                case 1:
                                    monster.setHealth(monster.getHealth() - hero.attack());
                                    isValid = true;
                                    break;
                                case 2:
                                    ((Sorceress) hero).heal();
                                    isValid = true;
                                    break;
                                default:
                                    System.out.println("Make a valid entry!");
                                    break;
                            }
                        }
                        break;
                    case "theif":
                        isValid = false;
                        while (!isValid) {
                            System.out.print("1. Attack!\n2. Sneak attack! (attack without using a turn)\n\nMake a selection: ");
                            int selection = getUserSelection();
                            System.out.println("_________________________________________________________________________");
                            switch (selection) {
                                case 1:
                                    monster.setHealth(monster.getHealth() - hero.attack());
                                    isValid = true;
                                    break;
                                case 2:
                                    if (((Theif) hero).extraTurn()) {
                                        monster.setHealth(monster.getHealth() - hero.attack());
                                        i -= 1;
                                    }
                                    isValid = true;
                                    break;
                                default:
                                    System.out.println("Make a valid entry!");
                                    break;
                            }
                        }
                        break;
                    case "god":
                        isValid = false;
                        while (!isValid) {
                            System.out.print("1. Attack!\n2. Steal health!\n\nMake a selection: ");
                            int selection = getUserSelection();
                            System.out.println("_________________________________________________________________________");
                            switch (selection) {
                                case 1:
                                    monster.setHealth(monster.getHealth() - hero.attack());
                                    isValid = true;
                                    break;
                                case 2:
                                    int temp = ((God) hero).stealHealth();
                                    monster.setHealth(monster.getHealth() - temp);
                                    hero.setHealth(hero.getHealth() + temp);
                                    isValid = true;
                                    break;
                                default:
                                    System.out.println("Make a valid entry!");
                                    break;
                            }
                        }
                        break;
                }
                if (monster.getHealth() <= 0) {
                    return 1;
                } else if (hero.getHealth() <= 0) {
                    return 2;
                }
            }
            System.out.println("\nYou have " + hero.getHealth() + " Health Points left.");
            System.out.println(monster.getName() + " has " + monster.getHealth() + " Health Points left.\n");

            switch (monster.getName()) {
                case "Gremlin":
                    hero.setHealth(hero.getHealth() - ((Gremlin) monster).claw());
                    break;
                case "Ogre":
                    int temp = ((Ogre) monster).bodySlam();
                    if (temp == -1) {
                        return 2;
                    } else {
                        hero.setHealth(hero.getHealth() - temp);
                    }
                    break;
                case "Skeleton":
                    hero.setHealth(hero.getHealth() - ((Skeleton) monster).throwsBomb());
            }
            if (monster.getHealth() <= 0) {
                return 1;
            } else if (hero.getHealth() <= 0) {
                return 2;
            }
            monster.heal();
            System.out.println("\nYou have " + hero.getHealth() + " Health Points left.");
            System.out.println(monster.getName() + " has " + monster.getHealth() + " Health Points left.\n");
            boolean isValid = false;
            while (!isValid) {
                System.out.print("Attempt to escape?\n1. Stay and fight!\n2. Escape\n\nMake a selection: ");
                int selection = getUserSelection();
                System.out.println("_________________________________________________________________________");
                switch (selection) {
                    case 1:
                        isValid = true;
                        break;
                    case 2:
                        return 3;
                    default:
                        System.out.println("Make a valid entry!");
                        break;
                }
            }
        }

        return 2;
    }
}
