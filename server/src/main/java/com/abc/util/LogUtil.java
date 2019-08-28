package com.abc.util;


import org.slf4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author cjie
 * @version 1.0
 */ 

public class LogUtil {
  private LogUtil(){
  };


  public static String getStackTrace(Throwable ex){
	  StringWriter sw = null;
      PrintWriter pw = null;
      try {
          sw = new StringWriter();
          pw = new PrintWriter(sw);
          ex.printStackTrace(pw);
          pw.flush();
          sw.flush();
      } finally {
          if (sw != null) {
              try {
                  sw.close();
              } catch (IOException e1) {
                  e1.printStackTrace();
              }
          }
          if (pw != null) {
              pw.close();
          }
      }
      return sw.toString();
  }

  public static void fatalStackTrace(Logger log, Throwable ex){
	  StringWriter sw = null;
      PrintWriter pw = null;
      try {
          sw = new StringWriter();
          pw = new PrintWriter(sw);
          ex.printStackTrace(pw);
          pw.flush();
          sw.flush();
      } finally {
          if (sw != null) {
              try {
                  sw.close();
              } catch (IOException e1) {
                  e1.printStackTrace();
              }
          }
          if (pw != null) {
              pw.close();
          }
      }
      log.error("错误"+sw.toString());
  }

}
