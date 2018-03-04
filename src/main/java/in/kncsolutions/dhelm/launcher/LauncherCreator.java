package in.kncsolutions.dhelm.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
/**
 * Goal which creates Desktop launcher for Ubuntu.
 * @goal gen
 * @phase package
 */
public class LauncherCreator  extends AbstractMojo{
	/**
     * Location of the file. 
     * @parameter expression = "${outputDirectory}"
     * @required
     */
	private String outputDirectory;
	/**
     * Path for the icon with .png extension.The file should reside in the same directory where the bin folder with shell
     * script to run the application resides. The filepath should be relative to the base directory of the final application package.
     *@parameter expression= "${iconPath}"  default-value = ""
     */
	
	private String iconPath;
	/**
     * Name of the .desktop file.
     * @parameter expression = "${fileName}" default-value = "desktopIni"      
     */	
	private String fileName;
	/**
     * Name of the Application. 
     * @parameter expression = "${appName}"  default-value= "myApp"   
     */
	private String appName;
	/**
     * Version of the Application. 
     * @parameter expression = "${version}"  default-value = ""   
     */	  
	private String version;
	/**
     * Generic name of the Application.
     *  @parameter expression = "${genericName}"  default-value = "" 
     */
	private String genericName;
	/**
     *  Executable. 
     * @parameter expression = "${executableFile}" 
     * @required   
     */	
	private String executableFile;
	/**
     *Visual status for terminal window .
     * @parameter expression = "${terminalStatus}"  default-value = "false"  
     */	
	private String terminalStatus;
	/**
     * Comments if any. 
     * @parameter expression= "${comment}"  default-value = "" 
     */
	 
	private String comment;
	public void execute() throws MojoExecutionException, MojoFailureException {		
		String shellScript=ShellScriptTemplate.getHeader(appName);
		shellScript=shellScript+ShellScriptTemplate.getLicenseText();
		shellScript=shellScript+ShellScriptTemplate.getParentDirCommand();
		if(!iconPath.equals(""))
		  shellScript=shellScript+ShellScriptTemplate.getIconCommand(iconPath);
		shellScript=shellScript+ShellScriptTemplate.getDesktopFileNameCommand(fileName);
		shellScript=shellScript+ShellScriptTemplate.getFirstLine();
		shellScript=shellScript+ShellScriptTemplate.getSecondSet(version,appName,genericName);
		shellScript=shellScript+ShellScriptTemplate.getExecutable(executableFile);		
		if(terminalStatus.equals("true")||terminalStatus.equals("false"))
		  shellScript=shellScript+ShellScriptTemplate.getTerminalCommand(terminalStatus);
	    if(!iconPath.equals(""))
		shellScript=shellScript+ShellScriptTemplate.getdesktopIconCommand(iconPath);
		shellScript=shellScript+ShellScriptTemplate.getFinalCommand(comment);
		System.out.println(shellScript);
		File desktopFile = new File(outputDirectory+"/create_desktop_launcher.sh");	
		BufferedWriter bw;
		try {
			bw =new BufferedWriter(new FileWriter(desktopFile));
			bw.write(shellScript);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}