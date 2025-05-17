package IPL_Auction;

import javax.swing.*;
import java.awt.*;


import java.util.*;
import java.util.List;

public class Auction {
    static PlayerImageWindow imageWindow = new PlayerImageWindow();
    public static void bidding(Player player, Teams t, Calculate cal) {



//    PlayerImageWindow imageWindow = new PlayerImageWindow(); // Create the image window
        imageWindow.showPlayerImage(player.name); // Show current player's image



        Scanner sc = new Scanner(System.in);
        int currPrice = player.basePrice;
        int bid;
        int index = -1;
        int[] budget = {0,0,0,0};



        String winningTeam = "Unsold";

        System.out.println("\nNow bidding for '" + player.name +"'"+
                "\n Base Price : '"+ (player.basePrice) +"'"+
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
                    cal.TeamPoints(index + 1, player.points);
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
        imageWindow.setVisible(true); // Optional

    }

    public static void main(String[] args) {
        Teams t = new Teams();
        Players p = new Players();
        Calculate cal = new Calculate();

//        PlayerImageWindow imageWindow = new PlayerImageWindow(); // Show window early
        imageWindow.showPlayerImage("default"); // Show default image first

        System.out.println("\n=== Welcome to the 'APL Auction 2025' ===");
        System.out.println("Enter an initial purse amount for each team: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < 4; i++) {
            t.setPurse(i, n);
        }

        System.out.println("Initial purse: " + Arrays.toString(t.getPurse()));



        // Starting instructions

        System.out.println("Instructions:");
        System.out.println("- Press team number (1: MI, 2: CSK, 3: RCB, 4: KKR) to bid.");
        System.out.println("- Press 0 to stop bidding for the current player.");
        System.out.println("- Press 5 to view current squads.\n");
        System.out.println("- Remember the bid will always be increased by ₹ 100 Lakhs.\n");
        System.out.println("1. Press 1 to START the auction.");
        System.out.println("2. Press 2 to SEE the list of players coming up in the auction.");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();

        if (choice == 2) {
            System.out.println("\nList of Players in the Auction:");
            for (Player pr : p.playerList) {
                System.out.println(pr.name + " - ₹" + pr.basePrice + " Lakhs");
            }

            // Shuffle player list
            Collections.shuffle(p.playerList);
            System.out.println("Now press 1 to start the auction: ");
            choice = sc.nextInt();
        }

        while (choice != 1) {
            System.out.print("Invalid choice. Please enter 1 to start the auction: ");
            choice = sc.nextInt();
        }

        for (int i = 0; i < 20; i++) {
            Player player = p.playerList.get(i);

            bidding(player, t, cal);
        }

        for(int i=0; i<=4; i++){
            t.printSquad(i);
        }



        double Arr[] = new double[4];

        Arr[0]= cal.getTotalPoints(cal.MIpoints);
        Arr[1]= cal.getTotalPoints(cal.CSKpoints);
        Arr[2]= cal.getTotalPoints(cal.RCBpoints);
        Arr[3]= cal.getTotalPoints(cal.KKRpoints);


        System.out.println("Do you want to know the analysis of each Team,\nmeans which Team has perfomed well in this Auction\n" +
                "type '8' YES  or  '9' for NO");
        int input = sc.nextInt();
        if(input==8){
            System.out.println("MI -> "+ cal.getTotalPoints(cal.MIpoints));
            System.out.println("CSK -> "+ cal.getTotalPoints(cal.CSKpoints));
            System.out.println("RCB -> "+ cal.getTotalPoints(cal.RCBpoints));
            System.out.println("KKR -> "+ cal.getTotalPoints(cal.KKRpoints));
        } else if (input==9) {
            return;

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
    double points;

    // Constructor
    public Player(String name, int basePrice, int age, String role, int matches, int runs, double strikeRate, int wickets, double bowlingEconomy, int catches,double points) {
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
        this.points = points;
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
        playerList.add(new Player("Virat Kohli", 200, 36, "Batsman", 262, 8447, 132.32, 5, 8.8, 231,9.9));
        playerList.add(new Player("MS Dhoni", 180, 39, "Wicket-Keeper", 350, 5000, 125.3, 10, 0.0, 100,9.8));
        playerList.add(new Player("Rohit Sharma", 180, 34, "Batsman", 300, 7200, 135.8, 2, 0.0, 70,9.8));
        playerList.add(new Player("Jasprit Bumrah", 150, 28, "Bowler", 150, 250, 145.6, 200, 6.8, 30,9.6));
        playerList.add(new Player("KL Rahul", 140, 31, "Batsman/Wicket-Keeper", 150, 4800, 140.2, 3, 0.0, 40,9.0));
        playerList.add(new Player("Suryakumar Yadav", 150, 30, "Batsman", 120, 3600, 155.7, 1, 0.0, 50,9.2));
        playerList.add(new Player("Hardik Pandya", 150, 28, "All-Rounder", 120, 2200, 145.0, 50, 7.0, 45,9.0));
        playerList.add(new Player("Shubman Gill", 100, 23, "Batsman", 80, 2200, 128.4, 0, 0.0, 25,8.5));
        playerList.add(new Player("Rishabh Pant", 110, 26, "Wicket-Keeper", 100, 2900, 145.0, 2, 0.0, 30,8.8));
        playerList.add(new Player("Bhuvneshwar Kumar", 160, 33, "Bowler", 130, 300, 120.0, 180, 6.5, 40,8.7));
        playerList.add(new Player("Ravindra Jadeja", 190, 34, "All-Rounder", 170, 2500, 130.5, 120, 6.7, 55,9.3));
        playerList.add(new Player("Yuzvendra Chahal", 130, 33, "Bowler", 100, 200, 100.0, 160, 7.5, 30,8.5));
        playerList.add(new Player("Kuldeep Yadav", 110, 30, "Bowler", 80, 150, 110.0, 140, 7.2, 25,8.2));
        playerList.add(new Player("Sanju Samson", 130, 29, "Wicket-Keeper", 120, 3100, 135.5, 1, 0.0, 35,8.6));
        playerList.add(new Player("Abhishek Sharma", 60, 23, "All-Rounder", 70, 1500, 140.0, 20, 7.1, 20,8));
        playerList.add(new Player("Axar Patel", 100, 31, "All-Rounder", 90, 1400, 125.0, 80, 6.4, 28,8.4));
        playerList.add(new Player("Shreyas Iyer", 120, 29, "Batsman", 110, 3200, 132.0, 0, 0.0, 30,8.6));
        playerList.add(new Player("Mohammed Shami", 140, 33, "Bowler", 120, 100, 120.0, 150, 7.0, 35,8.8));
        playerList.add(new Player("Mohammed Siraj", 90, 29, "Bowler", 80, 70, 125.0, 100, 7.3, 20,8.3));
        playerList.add(new Player("Ishan Kishan", 100, 26, "Wicket-Keeper", 90, 2400, 145.0, 1, 0.0, 25,8.5));

//        Collections.shuffle(playerList);
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



class PlayerImageWindow extends JFrame {
    private JLabel imageLabel;

    public PlayerImageWindow() {
        setTitle("Player Image");
        setSize(350, 450);
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void showPlayerImage(String playerName) {
        String formattedName = playerName.replace(" ", "_");
        String imagePath = "images/" + formattedName + ".jpg"; // e.g., images/Virat_Kohli.jpg

        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getIconWidth() > 0) {
            Image scaledImage = icon.getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setText("");
        } else {
            // Load default image
            ImageIcon defaultIcon = new ImageIcon("images/default.png");
            if (defaultIcon.getIconWidth() > 0) {
                Image scaledImage = defaultIcon.getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));

            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("Default image missing!");
            }
        }
    }
}
class Calculate {
    ArrayList<Double> MIpoints = new ArrayList<>();
    ArrayList<Double> CSKpoints = new ArrayList<>();
    ArrayList<Double> RCBpoints = new ArrayList<>();
    ArrayList<Double> KKRpoints = new ArrayList<>();


    public void TeamPoints(int j, double points) {
        switch (j) {
            case 1 -> MIpoints.add(points);
            case 2 -> CSKpoints.add(points);
            case 3 -> RCBpoints.add(points);
            case 4 -> KKRpoints.add(points);

        }
    }

    public double getTotalPoints(ArrayList<Double> teamPoints) {
        double total = 0;
        for (double point : teamPoints) {
            total += point;
        }
        return total;
    }

}

