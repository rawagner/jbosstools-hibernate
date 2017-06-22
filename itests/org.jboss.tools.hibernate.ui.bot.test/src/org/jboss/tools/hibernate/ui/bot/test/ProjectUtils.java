package org.jboss.tools.hibernate.ui.bot.test;

import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;

public class ProjectUtils {
	
	public static ProjectItem getItem(String project, String pckg, String clazz){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		if(pe.getProject(project).containsItem("Java Resources")){
			return pe.getProject(project).getProjectItem("Java Resources","src/main/java",pckg,clazz);
		}
		return pe.getProject(project).getProjectItem("src/main/java",pckg,clazz);
	}
	
	public static ProjectItem getPackage(String project, String pckg){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		if(pe.getProject(project).containsItem("Java Resources")){
			return pe.getProject(project).getProjectItem("Java Resources","src/main/java",pckg);
		}
		return pe.getProject(project).getProjectItem("src/main/java",pckg);
	}

}
