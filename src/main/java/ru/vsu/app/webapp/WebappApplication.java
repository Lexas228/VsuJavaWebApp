package ru.vsu.app.webapp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

import java.io.PrintStream;

@SpringBootApplication
@EnableCaching
public class WebappApplication{

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(WebappApplication.class);
		if (args.length == 0) { // This can be any condition you want
			app.web(WebApplicationType.SERVLET);
		} else {
			app.web(WebApplicationType.NONE);
		}
		app.run(args);
	}


	public static void printHelp(PrintStream printStream){
		printStream.println("-w for webserver start");
		printStream.println("-c for console integration");
	}



}
