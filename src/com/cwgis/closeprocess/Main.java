package com.cwgis.closeprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

public class Main {

    public static void main(String[] args)
            throws Exception
    {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // get current_pid
        String current_pid = name.split("@")[0];
        System.out.println(name+ "  当前进程PID="+current_pid);

        //args=new String[1];
        //args[0]="dd";
	    // write your code here
        //System.out.println("helloworld");
        if(args==null) throw new IOException("args参数不能为空");
        if(args.length<0)throw new IOException("args参数至少为一个参数");
        String pName=args[0];
        System.out.println("当前正在查找的进程名称为:"+pName);
        boolean havePID = closeProcessBypName(pName,current_pid);
        if(havePID==false)
        {
            System.out.println(" PID未找到进程编号");
        }
    }
    public static boolean closeProcessBypName(String pName,String current_pid){
        boolean rbc=false;
        BufferedReader reader =null;
        try{
            String PID="";
            String t_pName="";
            //显示所有进程
            Process process = Runtime.getRuntime().exec("jps");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                String[] strs = line.split(" ");
                if(strs.length>=2) {
                    PID = strs[0];
                    t_pName=strs[1];
                }
                if(PID.equalsIgnoreCase(current_pid)==false && t_pName.equalsIgnoreCase(pName)){
                    rbc=true;
                    System.out.println("相关信息 -----> "+line+" 找到PID="+PID+" ");
                    //
                    closeLinuxProcess(PID);  //kill -9  PID
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        return rbc;
    }

    /**
     * 关闭Linux进程
     * @param Pid 进程的PID
     */
    public static void closeLinuxProcess(String Pid){
        Process process = null;
        BufferedReader reader =null;
        try{
            //杀掉进程
            process = Runtime.getRuntime().exec("kill -9 "+Pid);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println("杀掉进程PID="+line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(process!=null){
                process.destroy();
            }

            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
