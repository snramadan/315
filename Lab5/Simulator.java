/* Student: Saba Ramadan
 * Assignment: Lab 4
 * Section: 01
 *
 * Description: Simulates operation of MIPS instructions
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Simulator 
{
   private HashMap<String, Integer> map; 
   private int pc;            
   private ArrayList<String> icodes;
   private int [] mem;                 
   private int [] regs; 
   private int stype;
   private int scount;
   private LinkedList<Boolean> branches;
   private ArrayList<Registers> pti;
   private Branches brPred;


   public Simulator(ArrayList<String> icodes, ArrayList<Registers> pti, int cSize) 
   {
      map = changeBits();
      this.icodes = icodes;
      this.pti = pti;
      pc = 0;
      mem = new int[8192];
      regs = new int[32];
      branches = new LinkedList<Boolean>();
      scount = 0;
      brPred = new Branches(cSize);
   }

   public String getIn(int num)
   {
      if (num >= icodes.size()) 
      {
         return "empty";
      } 
      else 
      {
         return icodes.get(num);
      }
   }

   public int getInSize() 
   {
      return icodes.size();
   }

   public boolean getBranch() 
   {
      return branches.poll();
   }

   public int getReg(String str) 
   {
      return regs[getCode(str)];
   }

   public void dumpMem() throws FileNotFoundException 
   {
      File f1 = new File("coordinates.csv");
      FileOutputStream fs = new FileOutputStream(f1);
      PrintStream ps1 = System.out;
      PrintStream ps2 = new PrintStream(fs);
      int i;
      System.setOut(ps2);
      
      for (i = 0; i < mem.length; i += 2) 
      {
         if (mem[i] != 0 && mem[i + 1] != 0) 
         {
            System.out.printf("%d, %d\n", mem[i], mem[i + 1]);
         }
      }

      System.setOut(ps1);
   }

   private boolean Rcheck(String str) 
   {
      return str.equals("000000");
   }

   private boolean Jcheck(String str) 
   {
      return str.equals("000010") || str.equals("000011");
   }

   public int checkCode() 
   {
      int total = 0;

      if (scount > 0) 
      {
         scount--;
         return 1;
      }

      while (total <  1 & pc != icodes.size()) 
      {
         String instr = icodes.get(pc);
         String[] arr = instr.split(" ");

         if (Rcheck(arr[0])) 
         {
            runR(arr);
         } 
         else if (Jcheck(arr[0])) 
         {
            runJ(arr);
         } 
         else 
         {
            runI(arr);
         }

         pc++;
         total++;
      }

      return total;
   }

   public void printMem(int x, int y) 
   {
      int i = 0;
      System.out.println();
      for (i = x; i <= y; i++) 
      {
         System.out.println("[" +  i + "] = " + mem[i]);
      }
      System.out.println();
   }

   public void clear() 
   {
      pc = 0;
      mem = new int[8192];
      regs = new int[32];
   }

   private void runR(String [] arr) 
   {
      int last = arr.length - 1;
      String fcode = arr[last];
      int rs = getCode(arr[1]), rt = 0, rd = 0, shamt = 0;
      
      if (!fcode.equals("001000")) 
      {       
         rt = getCode(arr[2]);
         rd = getCode(arr[3]);
         shamt = getCode(arr[4]);
      }

      if (fcode.equals("100100")) 
      {        
         regs[rd] = regs[rs] & regs[rt];
      } 
      else if (fcode.equals("100101")) 
      { 
         regs[rd] = regs[rs] | regs[rt];
      } 
      else if (fcode.equals("100000")) 
      { 
         regs[rd] = regs[rs] + regs[rt];
      } 
      else if (fcode.equals("000000")) 
      { 
         regs[rd] = regs[rt] << shamt;
      } 
      else if (fcode.equals("100010")) 
      { 
         regs[rd] = regs[rs] - regs[rt];
      } 
      else if (fcode.equals("101010")) 
      { 
         regs[rd] = (regs[rs] < regs[rt]) ? 1 : 0;
      } 
      else 
      { 
         pc = regs[rs] - 1;
         scount = 1;
         stype = 1;
      }
   }

   private void runJ(String [] arr) 
   {
      int i = ~~Integer.parseInt(arr[1], 2);

      if (arr[0].equals("000011")) 
      {    
         regs[31] = pc + 1;
      }

      scount = 1;
      stype = 1;
      pc = i - 1;  
   }

   private String extend(String str) 
   {
      int size = str.length();
      int total = 32;
      String sub = str.substring(0, 1);

      while (size != total) 
      {
         str = sub + str;
         ++size;
      }
      return str;
   }

   private int getCode(String str) 
   {
      return map.get(str);
   }

   private void runI(String [] arr)
   {
      int l = arr.length - 1;
      int temp = (int) Long.parseLong(extend(arr[l]), 2);
      int rs = getCode(arr[1]);
      int rt = getCode(arr[2]);

      if (arr[0].equals("001000")) 
      {          
         regs[rt] = regs[rs] + temp;
      } 
      else if (arr[0].equals("000100")) 
      {  
         if (regs[rs] == regs[rt])
         {
            pc = pc + temp;
            branches.add(true);
            scount += 3;
            brPred.changePreds(true);
         }
         else
         {
            branches.add(false);
            brPred.changePreds(false);
         }
      } 
      else if (arr[0].equals("000101")) 
      {   
         if (regs[rs] != regs[rt])
         {
            pc = pc + temp;
            branches.add(true);
            scount += 3;
            brPred.changePreds(true);
         }
         else
         {
            branches.add(false);
            brPred.changePreds(false);
         }
      } 
      else if (arr[0].equals("100011")) 
      {  
         regs[rt] = mem[temp + regs[rs]];
         String s1 = arr[2];
         String s2 = pti.get(pc + 1).rt;
         String s3 = pti.get(pc + 1).rs;
         
         if (s1.equals(s2) || s1.equals(s3))
         {
            scount = 1;
            stype = 2;
         }
      } 
      else if (arr[0].equals("101011")) 
      { 
         mem[temp + regs[rs]] = regs[rt];
      }
   }

   public void prints() 
   {
      brPred.prints();
   }

   private HashMap<String, Integer> changeBits() 
   {
      HashMap<String, Integer> registers = new HashMap<String, Integer>();

      registers.put("00000", 0);
      registers.put("00001", 1);
      registers.put("00010", 2);
      registers.put("00011", 3);
      registers.put("00100", 4);
      registers.put("00101", 5);
      registers.put("00110", 6);
      registers.put("00111", 7);
      registers.put("01000", 8);
      registers.put("01001", 9);
      registers.put("01010", 10);
      registers.put("01011", 11);
      registers.put("01100", 12);
      registers.put("01101", 13);
      registers.put("01110", 14);
      registers.put("01111", 15);
      registers.put("11000", 24);
      registers.put("11001", 25);
      registers.put("10000", 16);
      registers.put("10001", 17);
      registers.put("10010", 18);
      registers.put("10011", 19);
      registers.put("10100", 20);
      registers.put("10101", 21);
      registers.put("10110", 22);
      registers.put("10111", 23);
      registers.put("11010", 26);
      registers.put("11011", 27);
      registers.put("11100", 28);
      registers.put("11101", 29);
      registers.put("11110", 30);
      registers.put("11111", 31);

      return registers;
   }

   public void printRegs() 
   {
      System.out.println();
      System.out.println("pc = " + pc);
      System.out.println("$0 = " + regs[0] + "\t\t$v0 = " + regs[2] + "\t\t$v1 = " + regs[3] + "\t\t$a0 = " + regs[4]);
      System.out.println("$a1 = " + regs[5] + "\t\t$a2 = " + regs[6] + "\t\t$a3 = " + regs[7] + "\t\t$t0 = " + regs[8]);
      System.out.println("$t1 = " + regs[9] + "\t\t$t2 = " + regs[10] + "\t\t$t3 = " + regs[11] + "\t\t$t4 = " + regs[12]);
      System.out.println("$t5 = " + regs[13] + "\t\t$t6 = " + regs[14] + "\t\t$t7 = " + regs[15] + "\t\t$s0 = " + regs[16]);
      System.out.println("$s1 = " + regs[17] + "\t\t$s2 = " + regs[18] + "\t\t$s3 = " + regs[19] + "\t\t$s4 = " + regs[20]);
      System.out.println("$s5 = " + regs[21] + "\t\t$s6 = " + regs[22] + "\t\t$s7 = " + regs[23] + "\t\t$t8 = " + regs[24]);
      System.out.println("$t9 = " + regs[25] + "\t\t$sp = " + regs[29] + "\t\t$ra = " + regs[31]);
      System.out.println();
   }
}
