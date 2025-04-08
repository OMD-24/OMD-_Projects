package IPL_Auction;

import java.util.*;

public class Auction {
    public static void bidding(Player player, Teams t) {
        Scanner sc = new Scanner(System.in);
        int currPrice = player.basePrice;
        int bid;
        int index = -1;
        int[] budget = {0,0,0,0};

        String winningTeam = "Unsold";

        System.out.println("\nNow bidding for '" + player.name +"'"+
                "\n Base Price : '"+ (player.basePrice+100) +"'"+
                "\n Age : "+player.age+
                "\n Role : "+player.role+
                "\n Matches : "+player.matches+
                "\n Runs : "+player.runs+
                "\n Strike Rate : "+player.strikeRate+
                "\n Wickets : "+ player.wickets+
                "\n Bowling Economy : "+player.bowlingEconomy+
                "\n Catches : "+player.catches );

        while (true) {
            System.out.println("\nEnter a team number to bid (1: MI, 2: CSK, 3: RCB, 4: KKR, 0: Stop bidding, 5: View Squads): ");
            bid = sc.nextInt();

            if (bid == 0) {
                System.out.println(player.name + " is sold to " + winningTeam + " for Rs. " + currPrice);
                if (index != -1) {
                    t.setPurse(index, budget[index]);
                }
                t.TeamSquad(index + 1, player.name, currPrice);
                int[] printMoney=t.getPurse();

                System.out.println("MI : "+printMoney[0]);
                System.out.println("CSK : "+printMoney[1]);
                System.out.println("RCB : "+printMoney[2]);
                System.out.println("KKR : "+printMoney[3]);

                break;
            }

            if (bid == 5) {
                System.out.println("\nWhich team squad do you want to see? (1: MI, 2: CSK, 3: RCB, 4: KKR, 0: Unsold Players): ");
                int j = sc.nextInt();
                t.printSquad(j);
                continue;
            }

            int teamIndex = bid - 1;
            int[] actual_Purse = t.getPurse();

            if (bid >= 1 && bid <= 4) {
                if (actual_Purse[teamIndex] >= currPrice + 100) {
                    currPrice += 100;
                    budget[teamIndex] = actual_Purse[teamIndex] - currPrice;
                    winningTeam = switch (bid) {
                        case 1 -> "MI";
                        case 2 -> "CSK";
                        case 3 -> "RCB";
                        case 4 -> "KKR";
                        default -> "Unknown";
                    };

                    System.out.println("The team '" + winningTeam + "' bid for " + player.name +
                            " at Rs. " + currPrice + "\n(If they buy it, they will have Remaining Purse: Rs. " + budget[teamIndex] + ")");
                } else {
                    System.out.println("Team does not have enough purse left!");
                }
            } else {
                System.out.println("Invalid bid! Enter a number between 1-4, or 0 to stop.");
            }

            index = teamIndex; // Store last bidder index
        }
    }

    public static void main(String[] args) {
        Teams t = new Teams();
        Players p = new Players();

        System.out.println("Enter an initial purse amount for each team: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < 4; i++) {
            t.setPurse(i, n);
        }

        System.out.println("Initial purse: " + Arrays.toString(t.getPurse()));

        for (int i = 0; i < 12; i++) {
            Player player = p.playerList.get(i);
//            Integer Price = p.BasePrice.get(i);
            bidding(player, t);
        }

        for(int i=0; i<=4; i++){
            t.printSquad(i);
        }
    }
}

class Teams {
    private int[] actual_Purse;

    public Teams() {
        this.actual_Purse = new int[4];
    }

    public int[] getPurse() {
        return actual_Purse;
    }

    public void setPurse(int i, int p) {
        actual_Purse[i] = p;
    }

    ArrayList<String> MI = new ArrayList<>();
    ArrayList<String> CSK = new ArrayList<>();
    ArrayList<String> RCB = new ArrayList<>();
    ArrayList<String> KKR = new ArrayList<>();
    ArrayList<String> UnSold = new ArrayList<>();

    public void TeamSquad(int i, String name, int price) {
        switch (i) {
            case 1 -> MI.add(name + " for Rs. " + price);
            case 2 -> CSK.add(name + " for Rs. " + price);
            case 3 -> RCB.add(name + " for Rs. " + price);
            case 4 -> KKR.add(name + " for Rs. " + price);
            case 0 -> UnSold.add(name + " for Rs. " + price);
        }
    }

    public void printSquad(int j) {
        switch (j) {
            case 1 -> System.out.println("MI Squad: " + MI);
            case 2 -> System.out.println("CSK Squad: " + CSK);
            case 3 -> System.out.println("RCB Squad: " + RCB);
            case 4 -> System.out.println("KKR Squad: " + KKR);
            case 0 -> System.out.println("Unsold Players: " + UnSold);
            default -> System.out.println("Invalid option!");
        }
    }
}



class Player {
    String name;
    int basePrice;
    int age;
    String role;
    int matches;
    int runs;
    double strikeRate;
    int wickets;
    double bowlingEconomy;
    int catches;

    // Constructor
    public Player(String name, int basePrice, int age, String role, int matches, int runs, double strikeRate, int wickets, double bowlingEconomy, int catches) {
        this.name = name;
        this.basePrice = basePrice;
        this.age = age;
        this.role = role;
        this.matches = matches;
        this.runs = runs;
        this.strikeRate = strikeRate;
        this.wickets = wickets;
        this.bowlingEconomy = bowlingEconomy;
        this.catches = catches;
    }





    // Override toString() for easy printing
    @Override
    public String toString() {
        return "Name: " + name
                + ", Base Price: " + basePrice
                + ", Age: " + age
                + ", Role: " + role
                + ", Matches: " + matches
                + ", Runs: " + runs
                + ", Strike Rate: " + strikeRate
                + ", Wickets: " + wickets
                + ", Bowling Economy: " + bowlingEconomy
                + ", Catches: " + catches;
    }

}

class Players {
    List<Player> playerList = new ArrayList<>();
    List<Integer> BasePrice = new ArrayList<>();



    public Players() {
        // Adding players with stats
        playerList.add(new Player("Virat Kohli", 200, 32, "Batsman", 250, 7500, 130.5, 5, 0.0, 50));
        playerList.add(new Player("MS Dhoni", 180, 39, "Wicket-Keeper", 350, 5000, 125.3, 10, 0.0, 100));
        playerList.add(new Player("Rohit Sharma", 180, 34, "Batsman", 300, 7200, 135.8, 2, 0.0, 70));
        playerList.add(new Player("Jasprit Bumrah", 150, 28, "Bowler", 150, 250, 145.6, 200, 6.8, 30));
        playerList.add(new Player("KL Rahul", 140, 31, "Batsman/Wicket-Keeper", 150, 4800, 140.2, 3, 0.0, 40));
        playerList.add(new Player("Rashid Khan", 120, 26, "Bowler", 100, 1000, 125.5, 180, 6.0, 35));
        playerList.add(new Player("Hardik Pandya", 150, 28, "All-Rounder", 120, 2200, 145.0, 50, 7.0, 45));
        playerList.add(new Player("Ben Stokes", 100, 30, "All-Rounder", 110, 2800, 138.9, 40, 7.8, 55));
        playerList.add(new Player("David Warner", 120, 35, "Batsman", 180, 6000, 140.1, 1, 0.0, 65));
        playerList.add(new Player("Shubman Gill", 100, 23, "Batsman", 80, 2200, 128.4, 0, 0.0, 25));
        playerList.add(new Player("Suryakumar Yadav", 150, 30, "Batsman", 120, 3600, 155.7, 1, 0.0, 50));
        playerList.add(new Player("Trent Boult", 120, 33, "Bowler", 130, 150, 140.3, 170, 7.2, 40));

        Collections.shuffle(playerList);


    }



    // Print all players with stats
    public void displayPlayers() {
        for (Player p : playerList) {
            System.out.println(p);

        }
    }

    // Get a specific player
    public Player getPlayer(int index) {
        return playerList.get(index);
    }
}