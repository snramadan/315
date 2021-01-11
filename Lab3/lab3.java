/* Student: Saba Ramadan
 * Assignment: Lab 2
 * Section: 01
 *
 * Description:  MIPS emulator which will model the execution of instructions on a MIPS CPU.  
 *               This program will work like SPIM in that it will emulate the state of the registers and memory.
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class lab3 
{
   private static void filecheck(File file) 
   {
      if (!file.exists()) 
      {
         System.out.println("Error: File not found");
         System.exit(0);
      }
   }

   public static void main(String [] argv) throws FileNotFoundException 
   {
      ArrayList<String> instr = null;
      ArrayList<String> icodes = null;
      HashMap<String, Integer> addr = null;
      Emulator emul = new Emulator();
      Commands coms = null;
      File f = null;
      File in = null;
      Scanner scan = null;
      Scanner scanIn = null;

      if (argv.length > 2 || argv.length == 0) 
      {
         System.out.println("Error: Need asm input files");
         return;
      }

      if (argv.length == 2)
      {
         in = new File(argv[1]);
         filecheck(in);
      }

      f = new File(argv[0]);
      filecheck(f);

      instr = new ArrayList<String>();
      addr = new HashMap<String, Integer>();
      scan= new Scanner(f);

      emul.first(scan, instr, addr);
      icodes = emul.second(instr, addr);

      if (in != null) 
      {
         coms = new Commands(icodes, in);
      } 
      else 
      {
         coms = new Commands(icodes);
      }
      coms.execute();
   }
}
