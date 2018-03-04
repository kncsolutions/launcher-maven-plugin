package in.kncsolutions.dhelm.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
*Copyright 2018 Pallav@KNC Solutions Private Limited

*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at

* http://www.apache.org/licenses/LICENSE-2.0

*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/
public class ShellScriptTemplate {

	
/**
 *@param appName : The name of the application
 *@return Returns the header for the shell script. 
 */
public static String getHeader(String appName) {
  String s="#!/bin/sh\n# Creates a desktop launcher for "+ appName+".\n"
  + "# This must be run from the  bin directory of the package where the shell script to excute the main App resides.\n";
  return s;
}
/**
 *@return Returns the license text for the shell script. 
 */
public static String getLicenseText() {
  String s="#Copyright 2018 @KNC Solutions Private Limited\n"
		  +"#\n"
		  +"# Licensed under the Apache License, Version 2.0 (the"+ "License"+")\n"
		  +"# you may not use this file except in compliance with the License.\n"
		  +"# *You may obtain a copy of the License at\n"
		  +"#"
		  +"#  http://www.apache.org/licenses/LICENSE-2.0\n"
		  +"#"
		  +"# Unless required by applicable law or agreed to in writing, software\n"
		  +"# distributed under the License is distributed on an" +"AS IS"+" BASIS,\n"
		  +"# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
		  +"# See the License for the specific language governing permissions and\n"
		  +"# limitations under the License.\n"
		  +"#\n";
  return s;
}
/**
 * @param f:The file containing the license text.
 *@return Returns the license text for the shell script. 
 */
public static String getLicenseText(File f) {
  String s="";
  try {
	BufferedReader br = new BufferedReader(new FileReader(f));
	String line;
	  while ((line = br.readLine()) != null) {
	    s=s+"\n"+line;
	  }
  } catch (FileNotFoundException e) {	
	e.printStackTrace();
  } catch (IOException e) {
	e.printStackTrace();
}
  
  return s;
}
/**
 *@return Returns the shell script for getting absolute path for the present directory and the parent directory of the present
 *directory.
 */
public static String getParentDirCommand() {
	String s="bin_dir=$(pwd)\nbinp_dir=\"$(dirname \"$bin_dir\")\"\n";
	return s;
}
/**
 * @param path : file path should be relative to the base directory of the final application package.
 * @return returns the shell script to set Icon path
 */
public static String getIconCommand(String path) {
	String s="icon_dir=\"$binp_dir/"+path+"\"\n";
	return s;
}
/**
 *@param filename : The filename for .desktop file
 *@return Returns the shell script command assigning the .desktop filename
 */
public static String getDesktopFileNameCommand(String filename ) {
  String s="FILE=\"$bin_dir/"+filename+".desktop\"\n";
  return s;
}
/**
 * @return Returns the first line of the .desktop file.
 */
public static String getFirstLine() {
	String s = "echo \"[Desktop Entry]\"  >>$FILE\n";
	return s;
}
/**
 * 
 * @param version : version
 * @param appName : name of the application
 * @param genericName : generic name of the application
 * @return Command set.
 */
public static String getSecondSet(String version, String appName, String genericName) {
	String s="";
	s =s+ "echo \"Encoding=UTF-8\" >> $FILE\n";
	if(!version.equals(""))s=s+"echo \"Version="+version+"\" >> $FILE\n";
	s=s+"echo \"Name[en_US]="+appName+"\" >> $FILE\n";
	if(!genericName.equals(""))s=s+"echo \"GenericName="+genericName+"\" >> $FILE\n";
	return s;
}
/**
 * @param executableFile : the file with .sh extension to execute the application.
 * @return Returns the command.
 */
public static String getExecutable(String executableFile) {
	String s="echo \"Exec=$bin_dir/"+executableFile+"\"  >> $FILE\n";
	return s;
}
/**
 *@param terminal: The terminal status 
 *@return Returns the command set
 */
public static String getTerminalCommand(String terminal) {
	String s="echo \"Terminal="+terminal+"\" >> $FILE\n";
	return s;
}
/**
 * @param iconPath: relative path for the icon file w.r.t base directory
 * @return Returns the command.
 */
public static String getdesktopIconCommand(String iconPath) {
	String s="echo \"Icon=$icon_dir\" >> $FILE\n";
	return s;
}
/**
 * 
 * @param comment: Comment if any
 * @return Returns the command.
 */
public static String getFinalCommand(String comment) {
	String s="echo \"Type=Application\" >> $FILE\n"+
			 "echo \"Categories=Application;\" >> $FILE\n"+
			  "echo \"Comment[en_US]="+comment+"\" >> $FILE\n"+
			  "chmod 775 $FILE\n"
			  +"if [ -d ~/Desktop ];\n"
			  +"then\n"
			  +" cp $FILE ~/Desktop/\n"
			+"fi\n";
	return s;
}
}