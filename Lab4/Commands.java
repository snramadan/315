/* Student: Saba Ramadan
 * Assignment: Lab 4
 * Section: 01
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Commands 
{
   private Scanner scanIN;
   private Simulator sim;
   private boolean temp;
   private Pipeline pl;

   public Commands(ArrayList<String> icodes, ArrayList<Registers> pti) 
   {
      sim = new Simulator(icodes, pti); 
      scanIN= new Scanner(System.in);
      temp = true;
      pl = new Pipeline();
   }

   public Commands(ArrayList<String> icodes, ArrayList<Registers> pti, File f) 
   {
      sim = new Simulator(icodes, pti);
      pl = new Pipeline();

      try 
      {
         scanIN = new Scanner(f);
         temp = false;
      } 
      catch (Exception e) 
      {
         System.exit(0);
      }

   }

   public void execute()
   {
      System.out.print("mips> ");

      while (scanIN.hasNextLine()) 
      {
         String l = scanIN.nextLine();
         String [] arr = l.split(" ");
         String command = arr[0].trim();
         
         if (!temp) 
         {
            System.out.println(l);
         }

         if (command.equals("h"))
         {
            printCommands();
         }
         else if (command.equals("d"))
         {
            printRegs();
         }
         else if (command.equals("s"))
         {
            simStep(arr);
         }
         else if (command.equals("r"))
         {
            runSim();
         }
         else if (command.equals("m"))
         {
            printMem(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
         }
         else if (command.equals("c"))
         {
            clearSim();
         }
         else if (command.equals("q"))
         {
            System.exit(0);
         } 
         else if (command.equals("p")) 
         {
            pl.printPL();
         }  
         else
         {
            System.out.println("        " + command + " is not a valid command\n");
         }

         System.out.print("mips> ");
      }
   }

   private void printRegs() 
   {
      sim.printRegs();
   }

   private void simStep(String [] arr) 
   {
      int x = 1;
      int run = 0;
      int i, c;
      boolean hold;
      if (arr.length == 2) 
      {
         x = Integer.parseInt(arr[1]);
      } 
      for (i = 0; i < x; i++) 
      {
         c = sim.checkCode();
         hold = pl.cycle(sim);
         if (c == 0 || hold) 
         {
            break;
         }
         else
         {
            ++run;
         }
      }

      pl.printPL();
   }

   private void runSim() 
   {
      int i, run;
      for (i = 0; i < Integer.MAX_VALUE; i++) 
      {
         run = sim.checkCode();
         boolean hold = pl.cycle(sim);
         if (hold) 
         {
            break;
         }
      }
   }

   private void printMem(int x, int y) 
   {
      sim.printMem(x, y);
   }

   private void clearSim() 
   {
      sim.clear();
      System.out.println("        Simulator reset\n");
   }

   private void printCommands() 
   {
      System.out.println();
      System.out.println("h = show help");
      System.out.println("d = dump register state");
      System.out.println("p = show pipeline registers");
      System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
      System.out.println("s num = step through num instructions of the program");
      System.out.println("r = run until the program ends");
      System.out.println("m num1 num2 = display data memory from location num1 to num2");
      System.out.println("c = clear all registers, memory, and the program counter to 0");
      System.out.println("q = exit the program");
      System.out.println();
   }
}
