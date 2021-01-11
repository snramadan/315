/* Student: Saba Ramadan
 * Assignment: Lab 4
 * Section: 01
 */

public class Pipeline
{
   private int pc;
   private int instrs;
   private int cycles;
   private int jrAddr;
   private boolean done;
   private boolean sd;
   private boolean jmp;
   private Registers if_id;
   private Registers id_exe;
   private Registers exe_mem;
   private Registers mem_wb;

   public Pipeline()
   {
      pc = 0;
      instrs = 0;
      cycles = 0;
      jrAddr = 0;
      if_id = new Registers("empty");
      id_exe = new Registers("empty");
      exe_mem = new Registers("empty");
      mem_wb = new Registers("empty");
      done = false;
      sd = false;
      jmp = false;
   }

   public void printPL()
   {
      System.out.println("\npc\tif/id\tid/exe\texe/mem mem/wb");
      System.out.println(pc + "\t" + if_id.op + "\t" + id_exe.op + "\t" + exe_mem.op + "\t" + mem_wb.op + "\n");
   }
   
   private void memWb(Simulator sim) 
   {
      String memTest = mem_wb.op;

      if (memTest.equals("squash") || memTest.equals("stall")) 
      {
         //++cycles;
      } 
      else if (memTest.equals("empty")) 
      {
        ++cycles;
      } 
      else 
      {
        ++instrs;
        ++cycles;
      }

      mem_wb = exe_mem;
      
      if (mem_wb.op.equals("bne") || mem_wb.op.equals("beq")) 
      {
        if (sim.getBranch()) 
        {
            cycles += 3;
            jmp = true;
            pc += mem_wb.brAddr - 2;
            exe_mem = new Registers("squash");
            id_exe = new Registers("squash");
            if_id = new Registers("squash");
        }
      }
   }

   public boolean cycle(Simulator sim) 
   {
      if (done) 
      {
        return done;
      }

      memWb(sim);
      exeMem();
      idExe(sim);
      ifId(sim);

      if (if_id.op.equals("empty") && id_exe.op.equals("empty") && exe_mem.op.equals("empty") && mem_wb.op.equals("empty") && pc != 0) 
        {
            done = true;
            /*
              System.out.println("\nProgram complete");
              System.out.printf("CPI = %.3f\tCycles = %d\tInstructions = %d\n\n", cycles * 1.0/instrs, cycles, instrs);
            */
        }

      return done;
   }

   private void exeMem() 
   {
      if (sd || jmp) 
      {
        return;
      }
      exe_mem = id_exe;

      if (exe_mem.op.equals("lw") && (exe_mem.rt.equals(if_id.rt) || exe_mem.rt.equals(if_id.rs))) 
      {
        sd = true;
        id_exe = new Registers("stall");
        cycles++;
      }
   }

   private void idExe(Simulator sim) 
   {
      if (sd || jmp) 
      {
        return;
      }

      id_exe = if_id;

      String op = id_exe.op;

      if (op.equals("j") || op.equals("jal") || op.equals("jr")) 
      {
        pc = jrAddr;
        jmp = true;
        if_id = new Registers("squash");
        cycles++;
      }
   }

   private void ifId(Simulator sim)
   {
      if (sd || jmp) 
      {
        sd = false;  
        jmp = false;
        return;
      }

      String instr = sim.getIn(pc++);

      if (pc > sim.getInSize()) 
      {
        pc--;
      }

      if (instr.equals("empty")) 
      {
        if_id = new Registers("empty");
        return;
      } 
      else 
      {
        if_id = new Registers(instr);
      }

      String[] icodes = instr.split("\\s+");
      String opcode = icodes[0];
      String[] arr = new String[4];


      if (Conversions.checkR(opcode)) {
        arr = Conversions.parseR(icodes);
        if_id.rs = arr[0];
        if_id.rt = arr[1];
        if_id.rd = arr[2];
        if_id.op = arr[3];
      } 
      else if (Conversions.checkJ(opcode)) 
      {
        arr = Conversions.parseJ(icodes);
        if_id.brAddr = Integer.parseInt(arr[0]);
        if_id.op = arr[3];
      }
      else
      {
        arr = Conversions.parseI(icodes);
        if_id.rs = arr[0];
        if_id.rt = arr[1];
        if_id.brAddr = Integer.parseInt(arr[2]);
        if_id.op = arr[3];
      }

      if (if_id.op.equals("j") || if_id.op.equals("jal") || if_id.op.equals("jr")) 
      {
         if (if_id.rs != null) 
         {
            jrAddr = sim.getReg(if_id.rs);
				//cycles++;
         } 
         else 
         {
            jrAddr = if_id.brAddr;
         }
      }
   }
   
   private class Registers 
   {
      String icode;
      String rd;
      String rs;
      String rt;
      int progC;
      String op;
      int brAddr;

      Registers(String op) 
      {
        this.op = op;
      }
   }
}
