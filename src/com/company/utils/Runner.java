package com.company.utils;

import java.io.*;
import java.util.Arrays;

import static com.company.utils.LoggerUtils.i;

/**
 * Created by nikolai on 11.12.2014.
 */
public class Runner {

    public static boolean isInstalledApk(String pkg, String deviceId) {
        String[] command = new String[]{"adb", "-s", deviceId, "shell", "pm", "list", "packages", "|", "grep", pkg};
        boolean isInstalled = runProcess(command, pkg);
        if(isInstalled) {
            i("Build with pkg:" + pkg + " is installed on device");
        }

        return isInstalled;
    }

    public static boolean runProcess(final String[] command, String waitLine) {
        i("Run process:" + Arrays.toString(command).replaceAll(",", ""));
        boolean isSuccessProcess = false;
        Process process = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(command);
            is = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                i(line);
                if(line.contains(waitLine)) {
                    isSuccessProcess = true;
                    break;
                }
            }
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(process != null)
                process.destroy();
        }
        return isSuccessProcess;
    }

    public static Process runProcess(final String[] command, boolean isWaitForProcess) {
        i("Run process:" + Arrays.toString(command).replaceAll(",", ""));
        Process process = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(command);
            is = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine())!=null){
                i(line);
            }
            if(isWaitForProcess) {
                process.waitFor();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(process != null)
                process.destroy();
        }
        return process;
    }
}
