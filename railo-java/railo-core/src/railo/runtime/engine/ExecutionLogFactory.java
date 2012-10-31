package railo.runtime.engine;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import railo.runtime.PageContext;
import railo.runtime.type.Struct;
import railo.runtime.type.StructImpl;

public class ExecutionLogFactory {
	
	private Class clazz;
	private Map<String, String> arguments;
	private ExecutionLog executionLog;

	public ExecutionLogFactory(Class clazz, Map<String, String> arguments){
		this.clazz=clazz;
		this.arguments=arguments;
	} 
	
	public ExecutionLog getInstance(PageContext pc){
		ExecutionLog el = executionLog;
		if(el==null) {
			
			try {
				el=executionLog = (ExecutionLog) clazz.newInstance();
			} catch (Exception e) {
				el= new ConsoleExecutionLog();
			}
			el.init(null, arguments);
		}
		return el;
	}
	
	public String toString(){
		return super.toString()+":"+clazz.getName();
	}
	
	public Class getClazz(){
		return clazz;
	}
	
	public Struct getArgumentsAsStruct(){
		StructImpl sct=new StructImpl();
		if(arguments!=null) {
			Iterator<Entry<String, String>> it = arguments.entrySet().iterator();
			Entry<String, String> e;
			while(it.hasNext()){
				e = it.next();
				sct.setEL(e.getKey(), e.getValue());
			}
		}
		return sct;
	}
}
