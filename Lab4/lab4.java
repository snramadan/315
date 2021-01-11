/* Student: Saba Ramadan
 * Assignment: Lab 4
 * Section: 01
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class lab4 
{   
   private static void checkFile(File f) 
   {
      if (!f.exists()) 
      {
         System.out.println("Error: File not found");
         System.exit(0);
      }
   }

   public static void main(String [] argv) throws FileNotFoundException 
   {
      ArrayList<String> instrs = null;
      ArrayList<String> icodes = null;
      ArrayList<Registers> pti = null;
      HashMap<String, Integer> addr = null;
      File f = null;
      File in = null;
      Scanner fscan = null;
      Scanner iscan = null;
      Emulator em = new Emulator();
      Commands commands = null;

      if (argv.length > 2 || argv.length == 0) 
      {
         System.out.println("Error: Need asm input files");
         return;
      }

      if (argv.length == 2)
      {
         in = new File(argv[1]);
         checkFile(in);
      }

      f = new File(argv[0]);
      checkFile(f);

      instrs = new ArrayList<String>();
      addr = new HashMap<String, Integer>();
      fscan = new Scanner(f);
      em.first(fscan, instrs, addr);
      icodes = em.second(instrs, addr);

      pti = em.third(icodes);
      
      if (in != null) 
      {
         commands = new Commands(icodes, pti, in);
      } 
      else 
      {
         commands = new Commands(icodes, pti);
      }

      commands.execute();
   }
}
