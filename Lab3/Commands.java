/* Student: Saba Ramadan
 * Assignment: Lab 2
 * Section: 01
 *
 * Description: Deals with the commands, interactive modes and takes in arraylist of instruction codes and script files
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Commands 
{
   private Scanner scanIN;
   private Simulator sim;
   private boolean temp;

   public Commands(ArrayList<String> icodes) 
   {
      sim = new Simulator(icodes);
      scanIN= new Scanner(System.in);
      temp = true;
   }

   public Commands(ArrayList<String> icodes, File file) 
   {
      sim = new Simulator(icodes);

      try 
      {
         scanIN = new Scanner(file);
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
            simulatorStep(arr);
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

   private void simulatorStep(String [] arr) 
   {
      int i;
      if (arr.length == 2) 
      {
         i = sim.checkCode(Integer.parseInt(arr[1]));
      } 
      else 
      {
         i = sim.checkCode(1);
      }

      System.out.println("        " + i + " instruction(s) executed\n");
   }

   private void runSim() 
   {
      sim.checkCode(Integer.MAX_VALUE);
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
      System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
      System.out.println("s num = step through num instructions of the program");
      System.out.println("r = run until the program ends");
      System.out.println("m num1 num2 = display data memory from location num1 to num2");
      System.out.println("c = clear all registers, memory, and the program counter to 0");
      System.out.println("q = exit the program");
      System.out.println();
   }
}
