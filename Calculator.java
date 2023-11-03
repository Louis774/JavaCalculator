import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
/**
 * GUI calculator.
 *
 * @author Louis 
 * @version 
 */
public class Calculator implements ActionListener
{
    // instance variables

    private String number;
    private JFrame frame;
    private JLabel screen;
    private ArrayList<String> chain;
    // Overwrites the screen when u press a button after pressing =
    private boolean reset;

    /**
     * Constructor for objects of class Calculator
     */
    public Calculator()
    {
        // initialise instance variables
        number = "";
        chain = new ArrayList<String>();
        makeCalculator();
        frame.pack();
    }

    /**
     * Creates the gui for the calculator
     */
    public void makeCalculator()
    {
        frame = new JFrame("Calculator");
        frame.setSize(1000, 500);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        screen = new JLabel("0", JLabel.RIGHT);
        screen.setFont(new Font("Arial", Font.BOLD, 40));
        screen.setOpaque(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        JButton num1 = new JButton("1");
        JButton num2 = new JButton("2");
        JButton num3 = new JButton("3");
        JButton num4 = new JButton("4");
        JButton num5 = new JButton("5");
        JButton num6 = new JButton("6");
        JButton num7 = new JButton("7");
        JButton num8 = new JButton("8");
        JButton num9 = new JButton("9");
        JButton num0 = new JButton("0");
        JButton divSign = new JButton("÷");
        divSign.setForeground(Color.BLUE);
        JButton timesSign = new JButton("x");
        timesSign.setForeground(Color.BLUE);
        JButton minusSign = new JButton("-");
        minusSign.setForeground(Color.BLUE);
        JButton addSign = new JButton("+");
        addSign.setForeground(Color.BLUE);
        JButton ac = new JButton("AC");
        ac.setForeground(Color.ORANGE);
        JButton equalsSign = new JButton("=");
        equalsSign.setForeground(Color.RED);

        num1.addActionListener(this);
        num2.addActionListener(this);
        num3.addActionListener(this);
        num4.addActionListener(this);
        num5.addActionListener(this);
        num6.addActionListener(this);
        num7.addActionListener(this);
        num8.addActionListener(this);
        num9.addActionListener(this);
        num0.addActionListener(this);
        divSign.addActionListener(this);
        timesSign.addActionListener(this);
        minusSign.addActionListener(this);
        addSign.addActionListener(this);
        ac.addActionListener(this);
        equalsSign.addActionListener(this);

        panel.add(num7);
        panel.add(num8);
        panel.add(num9);
        panel.add(divSign);
        panel.add(num4);
        panel.add(num5);
        panel.add(num6);
        panel.add(timesSign);
        panel.add(num1);
        panel.add(num2);
        panel.add(num3);
        panel.add(minusSign);
        panel.add(ac);
        panel.add(num0);
        panel.add(equalsSign);
        panel.add(addSign);

        contentPane.add(BorderLayout.NORTH, screen);
        contentPane.add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    /**
     * Handles the logic for the calculator
     *
     * @return  the total after doing all the sums
     */
    public void handleLogic(){
        double total = 0;
        int count = 0;

        if (chain.contains("x") || chain.contains("÷")){
            // Handle multiplication and division
            while(count < chain.size()){
                if (chain.get(count) == "x"){
                    // Replaces the numbers in the chain arraylist with the total
                    double prev = Double.parseDouble(chain.get(count-1));
                    double next = Double.parseDouble(chain.get(count+1));
                    total = prev * next;
                    chain.remove(count-1);
                    chain.remove(count-1);
                    chain.remove(count-1);
                    chain.add(count-1, Double.toString(total));
                    if(count < 2){
                        count = 0;
                    }
                    else{
                        count = count-2;
                    }
                }
                else if (chain.get(count) == "÷"){
                    // Replaces the numbers in the chain arraylist with the total
                    double prev = Double.parseDouble(chain.get(count-1));
                    double next = Double.parseDouble(chain.get(count+1));
                    total = prev / next;
                    chain.remove(count-1);
                    chain.remove(count-1);
                    chain.remove(count-1);
                    chain.add(count-1, Double.toString(total));
                    if(count < 2){
                        count = 0;
                    }
                    else{
                        count = count-2;
                    }
                }
                count++;
            }
        }

        // Does the other calculations
        count = 0;
        while(count < chain.size()){
            if (chain.get(count) == "+"){
                // Replaces the numbers in the chain arraylist with the total
                double prev = Double.parseDouble(chain.get(count-1));
                double next = Double.parseDouble(chain.get(count+1));
                total = prev + next;
                chain.remove(count-1);
                chain.remove(count-1);
                chain.remove(count-1);
                chain.add(count-1, Double.toString(total));
                if(count < 2){
                    count = 0;
                }
                else{
                    count = count-2;
                }
            }
            if (chain.get(count) == "-"){
                // Replaces the numbers in the chain arraylist with the total
                double prev = Double.parseDouble(chain.get(count-1));
                double next = Double.parseDouble(chain.get(count+1));
                total = prev - next;
                chain.remove(count-1);
                chain.remove(count-1);
                chain.remove(count-1);
                chain.add(count-1, Double.toString(total));
                if(count < 2){
                    count = 0;
                }
                else{
                    count = count-2;
                }
            }
            count++;
        }
    }

    /**
     * Checks whether the digit is an operator or not
     *
     * @param  digit  a string to be checked
     * @return    true or false
     */
    public boolean isOperator(String digit){
        return (digit.equals("x") || digit.equals("+") || digit.equals("-") || digit.equals("÷"));
    }

    /**
     * Triggers when a button in the gui is clicked
     *
     * @param  e  The event attached to the gui button that was clicked
     */
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        if (command.equals("AC")){
            // Clear calculator
            chain.clear();
            number = "";
            screen.setText("0");
        }
        else if (command.equals("=")){
            // Perform calculation
            if (number != ""){
                chain.add(number);
                number = "";
            }

            int count = 0;

            // 'trims' the arraylist, basically removing operators from the start and end and any duplicates
            while (count < chain.size()){
                if (isOperator(chain.get(count))){
                    if (count == 0){
                        // If at the start
                        if (chain.get(count) == "-"){

                            double num = Double.parseDouble(chain.get(count+1));
                            num = num * -1;
                            chain.set(count+1, Double.toString(num));
                        }
                        chain.remove(count);
                        count--;
                    }
                    else if (count == chain.size()-1){
                        // If at the end
                        chain.remove(count);
                        count--;
                    }
                    else if (isOperator(chain.get(count+1))){
                        // Next item is an operator
                        chain.remove(count);
                        count--;
                    }
                }
                count++;
            }

            // Do all the calculations
            handleLogic();

            // Get the final total and output as an integer if it has a .0
            double finalNum = Double.parseDouble(chain.get(0));
            if ( finalNum == Math.floor(finalNum) ){
                int temp = (int) finalNum;
                screen.setText(Integer.toString(temp));
            }
            else{
                screen.setText(Double.toString(finalNum));
            }

            // Clear the array
            chain.clear();
            reset = true;

            //System.out.println(String.join(", ", chain));
            //String output = Double.toString(handleLogic());
            //screen.setText(output);
        }
        else if (isOperator(command)){
            // Set operator
            if (number != ""){
                chain.add(number);
                number = "";
            }
            chain.add(command);
            screen.setText(command);
        }
        else{
            // Display num
            String displayNum = screen.getText().trim();

            // Stops the calculator from displaying numbers after operators or 0s
            if (isOperator(displayNum) || displayNum.equals("0") || reset == true){
                reset = false;
                screen.setText(" ");
            }
            // Allows you to type numbers with more than one digit
            number = screen.getText().trim() + command;
            screen.setText(number);
        }
    }
}