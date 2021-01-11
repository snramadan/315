/* Students: Saba Ramadan
 * Assignment: Lab 2
 * Section: 01
 *
 * Description: A 2-pass assembler in Java.  
 *              This assembler will load in MIPS assembly files and output the corresponding machine code (to the screen). 
 *              The input to your assembler will be MIPS assembly files with comments, labels, and whitespace (spaces and tabs).
 */


import java.lang.*;
import java.io.*;
import java.util.*;

public class lab2
{
    private static String switchGoal(String instr, String goal, String c) 
    {
        int index = 0;
        if (!instr.contains(goal)) 
        {
            return instr;
        } 
        else 
        {
            index = instr.indexOf(goal);
            return instr.substring(0, index) + c + instr.substring(index + 1);
        }
    }

    private static ArrayList<String> removeWhiteSpace(String [] temp)
    {
        ArrayList<String> arr = new ArrayList<String>();

        for (String str : temp) 
        {
            if (str.length() > 0) 
            {
                arr.add(str.trim());
            }
        }
        return arr;
    }

    private static int checkLabel(String str)
    {
        int index = 0;

        if (str.indexOf(":") == str.length() - 1) 
        {
            return -1;
        }

        index = str.indexOf(":");
        
        if (index > 0) 
        {
            for (char ch : str.substring(index + 1).toCharArray()) 
            {
                if (ch == '#') 
                {
                    return -1;
                } 
                else if (Character.isWhitespace(ch)) 
                {
                    continue;
                }
                else 
                {
                    return str.indexOf(":") + 1;
                }
            }
        }
        return -1;
    }

    public static void roundOne(Scanner f, ArrayList<String> instr, HashMap<String, Integer> addr) 
    {
        String str;
        int done = 0;
        boolean l = false;
        boolean ex = true;

        while (f.hasNextLine()) 
        {
            str = f.nextLine();
            str = str.trim();

            if (str.startsWith("#") || str.length() == 0 ) 
            {    
                continue;                           
            }      

            if (str.contains(":"))
            {     
                addr.put(str.substring(0, str.indexOf(":")), instr.size());
                l = true;
            }

            done = checkLabel(str);

            if (l) 
            {          
                l = false;
                if (done >= 0)
                {  
                    str = str.substring(done, str.length()).trim(); 
                    ex = true;
                }
                else 
                {
                    ex = false;
                }
            }

            if (str.contains("#")) 
            {                 
                str = str.substring(0, str.indexOf("#")); 
            }

            if (ex)
            {
                instr.add(str);
            }

            l = false;
            ex = true;
        }
    }

    public static ArrayList<String> roundTwo(ArrayList<String> instr, HashMap<String, Integer> addr) 
    {
        ArrayList<String> icodes = new ArrayList<String>();
        OPhashmaps OPhash = new OPhashmaps(addr);
        int temp = 0;      

        for (String in : instr) 
        {
            ArrayList<String> ops;
            String oper;

            in = in.replace('\t', ' ');
            in = in.replace(',', ' ');
            in = switchGoal(in, "$", " $");
            ops = removeWhiteSpace(in.split(" "));

            if (OPhash.checkInstr(ops.get(0))) 
            {
                icodes.add(OPhash.create(ops, temp));
            } 

            else 
            {
                icodes.add("invalid instruction: " + ops.get(0) + "\n");
                return icodes;
            }
            temp++; 
        }
        return icodes;
    }

    public static void main(String [] argv) throws FileNotFoundException
    {
        ArrayList<String> instr;
        ArrayList<String> icodes;
        HashMap<String, Integer> addr;
        File file;
        Scanner scan;

        if (argv.length != 1) 
        {
            System.out.println("Error: Need asm input files");
            return;
        }

        file = new File(argv[0]);

        if (!file.exists()) 
        {
            System.out.println("Error: File not found");
            return;
        }

        instr = new ArrayList<String>();
        addr = new HashMap<String, Integer>();
        scan = new Scanner(file);
        roundOne(scan, instr, addr);
        icodes = roundTwo(instr, addr);

        for (String str : icodes) 
        {
            System.out.println(str);
        }
    }
}
