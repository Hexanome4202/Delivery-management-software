package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyTestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(PlanTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		result = JUnitCore.runClasses(ItineraireTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		result = JUnitCore.runClasses(TronconTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}
}