package IPL_Auction;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Auction {
    public static void bidding(String name, int basePrice, Teams t) {
        Scanner sc = new Scanner(System.in);
        int currPrice = basePrice;
        int bid;
        int index = -1;

        String winningTeam = "Unsold";

        System.out.println("\nNow bidding for " + name + ". Base Price: Rs. " + basePrice);

        while (true) {
            System.out.println("Enter a team number to bid (1: MI, 2: CSK, 3: RCB, 4: KKR, 0: Stop bidding, 5: View Squads): ");
            bid = sc.nextInt();

            if (bid == 0) {
                System.out.println(name + " is sold to " + winningTeam + " for Rs. " + currPrice);
                if (index != -1) {
                    t.setPurse(index, t.getPurse()[index]);
                }
                t.TeamSquad(index + 1, name, currPrice);
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
                if (actual_Purse[teamIndex] >= currPrice + 10) {
                    currPrice += 10;
                    actual_Purse[teamIndex] -= 10;
                    winningTeam = switch (bid) {
                        case 1 -> "MI";
                        case 2 -> "CSK";
                        case 3 -> "RCB";
                        case 4 -> "KKR";
                        default -> "Unknown";
                    };

                    System.out.println("The team '" + winningTeam + "' bid for " + name +
                            " at Rs. " + currPrice + " (Remaining Purse: Rs. " + actual_Purse[teamIndex] + ")\n");
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
            String name = p.players[i];
            int basePrice = p.basePrice[i];
            bidding(name, basePrice, t);
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
            case -1 -> UnSold.add(name + " for Rs. " + price);
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

class Players {
    String[] players = {
            "Virat Kohli", "MS Dhoni", "Rohit Sharma", "Jasprit Bumrah",
            "KL Rahul", "Rashid Khan", "Hardik Pandya", "Ben Stokes",
            "David Warner", "Shubman Gill", "Suryakumar Yadav", "Trent Boult"
    };

    int[] basePrice = {20, 18, 18, 15, 14, 12, 15, 10, 12, 10, 15, 12};

    public Players() {}
}
