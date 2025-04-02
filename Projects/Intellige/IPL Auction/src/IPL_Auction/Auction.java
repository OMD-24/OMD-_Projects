package IPL_Auction;

import java.util.*;

public class Auction {
    public static void bidding(String name, int basePrice, Teams t) {
        Scanner sc = new Scanner(System.in);
        int currPrice = basePrice;
        int bid;
        int index = -1;
        int[] budget = {0,0,0,0};

        String winningTeam = "Unsold";

        System.out.println("\nNow bidding for " + name + ". Base Price: Rs. " + basePrice);

        while (true) {
            System.out.println("Enter a team number to bid (1: MI, 2: CSK, 3: RCB, 4: KKR, 0: Stop bidding, 5: View Squads): ");
            bid = sc.nextInt();

            if (bid == 0) {
                System.out.println(name + " is sold to " + winningTeam + " for Rs. " + currPrice);
                if (index != -1) {
                    t.setPurse(index, budget[index]);
                }
                t.TeamSquad(index + 1, name, currPrice);
                int[] printMoney=t.getPurse();

                    System.out.println("MI : "+printMoney[0]);
                    System.out.println("CSK : "+printMoney[1]);
                    System.out.println("RCB : "+printMoney[2]);
                    System.out.println("KKR : "+printMoney[3]);

                break;
            }

            if (bid == 5) {
                System.out.println("Which team squad do you want to see? (1: MI, 2: CSK, 3: RCB, 4: KKR, 0: Unsold Players): ");
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

                    System.out.println("The team '" + winningTeam + "' bid for " + name +
                            "\n at Rs. " + currPrice + " (Remaining Purse: Rs. " + budget[teamIndex] + ")\n");
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
            bidding(player.toString(), player.basePrice, t);
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
    int runs;
    int wickets;
    double strikeRate;

    // Constructor
    public Player(String name, int basePrice, int runs, int wickets, double strikeRate) {
        this.name = name;
        this.basePrice = basePrice;
        this.runs = runs;
        this.wickets = wickets;
        this.strikeRate = strikeRate;
    }



    // Override toString() for easy printing
    @Override
    public String toString() {
        return name + " | Base Price: Rs." + basePrice + " | Runs: " + runs +
                " | Wickets: " + wickets + " | Strike Rate: " + strikeRate;
    }

}

class Players {
    List<Player> playerList = new ArrayList<>();
    List<Integer> BasePrice = new ArrayList<>();



    public Players() {
        // Adding players with stats
        playerList.add(new Player("Virat Kohli", 200, 7500, 5, 130.5));
        playerList.add(new Player("MS Dhoni", 180, 5000, 10, 125.3));
        playerList.add(new Player("Rohit Sharma", 180, 7200, 2, 135.8));
        playerList.add(new Player("Jasprit Bumrah", 150, 250, 200, 145.6));
        playerList.add(new Player("KL Rahul", 140, 4800, 3, 140.2));
        playerList.add(new Player("Rashid Khan", 120, 1000, 180, 125.5));
        playerList.add(new Player("Hardik Pandya", 150, 2200, 50, 145.0));
        playerList.add(new Player("Ben Stokes", 100, 2800, 40, 138.9));
        playerList.add(new Player("David Warner", 120, 6000, 1, 140.1));
        playerList.add(new Player("Shubman Gill", 100, 2200, 0, 128.4));
        playerList.add(new Player("Suryakumar Yadav", 150, 3600, 1, 155.7));
        playerList.add(new Player("Trent Boult", 120, 150, 170, 140.3));

        Collections.shuffle(playerList);

        basePrice();
    }

    public void basePrice() {
        BasePrice.add(200);
        BasePrice.add(180);
        BasePrice.add(180);
        BasePrice.add(150);
        BasePrice.add(140);
        BasePrice.add(120);
        BasePrice.add(150);
        BasePrice.add(100);
        BasePrice.add(120);
        BasePrice.add(100);
        BasePrice.add(150);
        BasePrice.add(120);
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
