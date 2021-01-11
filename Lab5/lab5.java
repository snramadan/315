/* Student: Saba Ramadan
 * Assignment: Lab 5
 * Section: 01
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class lab5 
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
      File input = null;
      Scanner fscan = null;
      Scanner scan = null;
      Emulator emul = new Emulator();
      Commands commands = null;
      int size = 2;

      if (argv.length > 3 || argv.length == 0) 
      {
         System.out.println("Error: Need asm input files");
         return;
      }

      if (argv.length >= 2)
      {
         input = new File(argv[1]);
         checkFile(input);
      }

      if (argv.length == 3)
      {
         size = Integer.parseInt(argv[2]);
      }

      f = new File(argv[0]);
      checkFile(f);

      instrs = new ArrayList<String>();
      addr = new HashMap<String, Integer>();
      fscan = new Scanner(f);

      emul.first(fscan, instrs, addr);
      icodes = emul.second(instrs, addr);
      pti = emul.third(icodes);
      
      if (input != null) 
      {
         commands = new Commands(icodes, pti, input, size);
      } 
      else 
      {
         commands = new Commands(icodes, pti, size);
      }

      commands.execute();
   }
}
